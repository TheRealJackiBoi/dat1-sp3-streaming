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
        catch (Exception e) {
            e.printStackTrace();
        }
        //System.out.println(arr);

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
        catch (Exception e) {
            e.printStackTrace();
        }
        //System.out.println(arr);

        return arr;

    }


    public static ArrayList<User> setUpUser(){
        ArrayList<User> userData = new ArrayList<>();

        String query = "SELECT * FROM user";

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
                    stream.addHasSeenPlaylists(new Playlist(userName, new ArrayList<Media>()));
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
                    stream.addSavedPlaylists(new Playlist(userName, new ArrayList<Media>()));
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

    public static void createUser(String username, String password) {

        Streaming stream = Streaming.getInstance();

        getConnection();
        try {

            if(checkUsername(username)){
                String query = "INSERT INTO users (userName,password) VALUES (?,?)";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1,username);
                statement.setString(2,password);

                statement.execute(query);
                ResultSet rs1 = statement.getResultSet();

                statement.close();

                connection.close();
            }

            User u = new User(username, password);
            stream.addUser(u);

            stream.addSavedPlaylists(new Playlist(username, new ArrayList<Media>()));
            stream.addHasSeenPlaylists(new Playlist(username, new ArrayList<Media>()));


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