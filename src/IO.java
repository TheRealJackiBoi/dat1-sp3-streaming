import java.io.File;
import java.util.ArrayList;

public interface IO {

    public static ArrayList<Movie> setupMovies(){
        return new ArrayList<Movie>();
    };

    public static ArrayList<Series> setupSeries(){
        return new ArrayList<Series>();
    };

    public static ArrayList<User> setUpUser() {
        return new ArrayList<User>();
    }

    public static ArrayList<Playlist> setupPlaylist() {
        return new ArrayList<Playlist>();
    }

    public static void createUser(String username, String password) {}

    public static void updateSavedPlaylists() {}

    public static void updateHasSeenPlaylists() {}
}
