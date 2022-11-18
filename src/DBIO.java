import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.sql.*;
import static org.junit.Assert.assertTrue;

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
        System.out.println(arr);

        return arr;
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