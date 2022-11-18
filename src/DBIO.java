import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.sql.*;
import static org.junit.Assert.assertTrue;

public class DBIO implements IO {
    public static void setupMovies() throws SQLException {
        ArrayList<Movie> arr = new ArrayList<>();

        String url = "jdbc:mysql://localhost:3306/movies?" + "autoReconnect=true&useSSL=false"; // your SQL database IP
        String username = "root"; // your SQL username
        String password = "gh9sp6vp4"; // your SQL password
        String query = "SELECT * FROM movies";

        Connection connection = DriverManager.getConnection(url, username, password);
        assertTrue(connection.isValid(1));

        PreparedStatement statement = connection.prepareStatement(query);
        statement.close();
        statement.execute(query);
        ResultSet rs = statement.getResultSet();

        while (rs.next()) {
            Movie movie = new Movie(rs.getString(2), rs.getInt(3), rs.getFloat(4), rs.getString(5));

        }
    }
}