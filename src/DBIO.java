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

                int userID = rs.getInt(0);
                String userName = rs.getString(1);
                String userPassword = rs.getString(2);

                //TODO Work in progress  HasSEENEXIST
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

                //TODO work in progress SavedMediaEXIST
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

            String query = "INSERT INTO ? (username, medias) VALUES (?, '')";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, tableName);
            statement.setString(2, userName);
            statement.execute(query);

            statement.close();
            connection.close();

        }  catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void createUser(String userName, String password) {

        Streaming stream = Streaming.getInstance();

        getConnection();
        try {

            if(checkUsername(userName)){
                String query = "INSERT INTO users (username,password) VALUES (?,?)";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1,userName);
                statement.setString(2,password);

                statement.execute(query);
                ResultSet rs1 = statement.getResultSet();

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
            String query = "SELECT * FROM users WHERE NOT EXISTS(SELECT userName FROM users where username = ?)";
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setString(1, username);

            statement.execute(query);
            boolean rs = statement.getResultSet().getBoolean(1);

            statement.close();
            connection.close();

            return rs;
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
            String query = "SELECT * FROM ?";

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, tableName);
            statement.execute();

            ResultSet rs = statement.getResultSet();

            while (rs.next()) {

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
                connection.close();
            }
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

                String query = "UPDATE savedMedias SET medias = ? WHERE username = ?";

                updatePlaylistInDB(p, query);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateHasSeenPlaylists() {
        Streaming stream = Streaming.getInstance();

        try {

            for (Playlist p : stream.getHasSeenPlaylists()) {

                String query = "UPDATE hasSeen SET medias = ? WHERE username = ?";

                updatePlaylistInDB(p, query);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void updatePlaylistInDB(Playlist p, String query) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(query);

        String playlistRAW = "";

        for (int i = 0; i < p.medias.size(); i ++) {
            if(i == 0)
                playlistRAW += p.medias.get(i).getName();
            else
                playlistRAW += ", " + p.medias.get(i).getName();
        }

        statement.setString(1, playlistRAW);
        statement.setString(2, p.ownerName);

        statement.execute();

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