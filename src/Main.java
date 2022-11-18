import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        DBIO.setupMovies();
        Streaming stream = Streaming.getInstance();
        StreamUI ui = new StreamUI();

        stream.setUpStream();

        ui.getFrame().setVisible(true);

    }
}