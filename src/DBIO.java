import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.sql.*;

public class DBIO implements IO {

    private static Connection connection;
    public static ArrayList<Movie> setupMovies() {
        ArrayList<Movie> arr = new ArrayList<>();

        String query = "SELECT * FROM movies";
        getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.execute(query);
            ResultSet rs = statement.getResultSet();

            while (rs.next()) {
                String[] genreArr = rs.getString(4).trim().split(",");
                ArrayList<String> genres = new ArrayList<>(Arrays.asList(genreArr));
                Movie movie = new Movie(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getFloat(5), genres);
                arr.add(movie);
            }
            statement.close();

            connection.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return arr;
    }

    public static ArrayList<Series> setupSeries() {
        ArrayList<Series> arr = new ArrayList<>();

        String query = "SELECT * FROM series";
        getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.execute(query);
            ResultSet rs = statement.getResultSet();

            while (rs.next()) {
                String[] genreArr = rs.getString(4).trim().split(",");
                ArrayList<String> genres = new ArrayList<>(Arrays.asList(genreArr));

                String[] seasonArr = rs.getString(6).trim().split(", ");
                ArrayList<String> seasons = new ArrayList<>(Arrays.asList(seasonArr));

                String[] years = rs.getString(3).trim().split("-");
                int yearStart = Integer.parseInt(years[0].trim());
                int yearEnd = 0;
                if(years.length > 1)
                    yearEnd = Integer.parseInt(years[1].trim());

                Series series = new Series (rs.getInt(1),rs.getString(2), rs.getFloat(5), genres, seasons, yearStart, yearEnd);
                arr.add(series);
            }
            statement.close();

            connection.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return arr;
    }


    public static ArrayList<User> setUpUser(){
        ArrayList<User> userData = new ArrayList<>();

        String query = "SELECT * FROM users";

        getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.execute(query);
            ResultSet rs = statement.getResultSet();


            Streaming stream = Streaming.getInstance();
            ArrayList<Playlist> hasSeenPlaylists = stream.getHasSeenPlaylists();
            ArrayList<Playlist> savedMediaData = stream.getSavedPlaylists();

            while (rs.next()) {

                int userID = rs.getInt(1);
                String userName = rs.getString(2);
                String userPassword = rs.getString(3);

                boolean userHasSeenExist = false;
                ArrayList<Media> userHasSeen = new ArrayList<>();
                for (Playlist p : hasSeenPlaylists) {
                    if (p.ownerName.equals(userName)) {
                        userHasSeenExist = true;
                        userHasSeen = p.medias;
                        break;
                    }
                }
                if (!userHasSeenExist) {
                    stream.addHasSeenPlaylistsDB(new Playlist(userName, new ArrayList<Media>()));
                    addNewPlaylistToTableDB("hasSeen", userName);

                }

                boolean savedMediaDataListExist = false;
                ArrayList<Media> userSaved = new ArrayList<>();
                for (Playlist p : savedMediaData) {
                    if (p.ownerName.equals(userName)) {
                        savedMediaDataListExist = true;
                        userSaved = p.medias;
                        break;
                    }
                }
                if (!savedMediaDataListExist) {
                    stream.addSavedPlaylistsDB(new Playlist(userName, new ArrayList<Media>()));
                    addNewPlaylistToTableDB("savedMedias", userName);
                }
                userData.add(new User(userName, userPassword, userHasSeen, userSaved));
            }
            statement.close();

            connection.close();
        }
        catch (SQLException e){
            e.printStackTrace();
        }

        return userData;
    }

    public static void addNewPlaylistToTableDB(String tableName, String userName) {
        Streaming stream = Streaming.getInstance();

        getConnection();
        try {

            String query = "INSERT INTO " + tableName + " (username, medias) VALUES ('" + userName + "', '')";
            PreparedStatement statement = connection.prepareStatement(query);
            //statement.setString(1, userName);
            statement.execute(query);

            statement.close();
            connection.close();

        }  catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void createUser(String userName, String password) {

        Streaming stream = Streaming.getInstance();

        try {
            if(!checkUsername(userName)){
                getConnection();
                String query = "INSERT INTO users (username,password) VALUES ('" + userName + "' ,'" + password + "')";
                Statement statement = connection.createStatement();

                statement.execute(query);
                ResultSet rs1 = statement.getResultSet();

                System.out.println(rs1);

                statement.close();
                connection.close();
            }

            User u = new User(userName, password);
            stream.addUser(u);

            stream.addSavedPlaylistsDB(new Playlist(userName, new ArrayList<Media>()));
            stream.addHasSeenPlaylistsDB(new Playlist(userName, new ArrayList<Media>()));


        }  catch (SQLException e) {
            e.printStackTrace();
        }
    }



    private static boolean checkUsername(String username){

        getConnection();
        try {
            String query = "SELECT username FROM users WHERE username= '" + username +"'";
            Statement statement = connection.createStatement();

            statement.execute(query);

            ResultSet rs = statement.getResultSet();

            String s  = rs.first() ? rs.getString(1) : "";

            statement.close();
            connection.close();

            return s.equals(username);
        }
        catch (SQLException e){
            e.printStackTrace();
        }

        return false;
    }


    public static ArrayList<Playlist> setupPlaylist(String tableName) {
        Streaming stream = Streaming.getInstance();
        ArrayList<Movie> movies = stream.getMovies();
        ArrayList<Series> series = stream.getSeries();

        ArrayList<Playlist> playlists = new ArrayList<>();

        try {
            getConnection();
            String query = "SELECT * FROM " + tableName;

            Statement statement = connection.createStatement();
            statement.execute(query);

            ResultSet rs = statement.getResultSet();
            while (!rs.isClosed() && rs.next()) {

                String ownerName = rs.getString(1);

                ArrayList<Media> medias = new ArrayList<>();

                if (rs.getString(2) == null) {
                    String[] mediasRaw = rs.getString(2).trim().split(", ");

                    for (String mediaName : mediasRaw) {

                        boolean mediaAdded = false;

                        for (Movie m :
                                movies) {
                            if (m.getName().equals(mediaName)) {
                                medias.add(m);
                                mediaAdded = true;
                                break;
                            }
                        }
                        if (!mediaAdded) {
                            for (Series m :
                                    series) {
                                if (m.getName().equals(mediaName)) {
                                    medias.add(m);
                                    mediaAdded = true;
                                    break;
                                }
                            }
                        }
                    }
                }
                Playlist p = new Playlist(ownerName, medias);
                playlists.add(p);
            }
            connection.close();
            rs.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return playlists;
    }

    public static void updateSavedPlaylists() {
        Streaming stream = Streaming.getInstance();

        try {

            for (Playlist p : stream.getSavedPlaylists()) {

                updatePlaylistInDB(p, "savedMedias");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateHasSeenPlaylists() {
        Streaming stream = Streaming.getInstance();

        try {

            for (Playlist p : stream.getHasSeenPlaylists()) {

                updatePlaylistInDB(p, "hasSeen");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void updatePlaylistInDB(Playlist p, String tableName) throws SQLException {
        getConnection();
        Statement statement = connection.createStatement();

        String playlistRAW = "";

        for (int i = 0; i < p.medias.size(); i ++) {
            if(i == 0)
                playlistRAW += p.medias.get(i).getName();
            else
                playlistRAW += ", " + p.medias.get(i).getName();
        }

        String query = "UPDATE savedMedias SET medias = '" + playlistRAW + "' WHERE username = '" + p.ownerName + "'";

        statement.execute(query);

        statement.close();
        connection.close();
    }


    private static void getConnection() {

        try {
            String url = "jdbc:mysql://mysql24.unoeuro.com:3306/cudia_dk_db?" + "autoReconnect=true&useSSL=false"; // your SQL database IP
            String username = "cudia_dk"; // your SQL username
            String password = "mEy6xwhn2gczftDkerba"; // your SQL password

            connection = DriverManager.getConnection(url, username, password);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}