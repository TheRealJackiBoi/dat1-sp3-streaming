import java.io.File;
import java.util.ArrayList;
import java.util.stream.Stream;

public class Streaming {
    private static Streaming stream = null;

    public synchronized static Streaming getInstance() {
        if (stream == null) {
            stream = new Streaming();
        }
        return stream;
    }

    private Streaming() {}
    private ArrayList<User> users;
    private User currentUser;
    private Media currentMedia;
    private ArrayList<Playlist> savedPlaylists;
    private ArrayList<Playlist> hasSeenPlaylists;
    private ArrayList<Movie> movies;
    private ArrayList<Series> series;

    public boolean checkUserName(String un) {
        User user = null;

        for (User u :
                users) {
            if (u.getName().equals(un)) {
                user = u;
                break;
            }
        }
        return user != null;
    }

    public User checkUserPW(String un, String pw) {

        User user = null;

        for (User u :
                users) {
            if (u.getName().equals(un)) {
                user = u;
                break;
            }
        }
        if (user == null) {
            return null;
        }
        if (user.getPassword().equals(pw)) {
            return user;
        } else {
            return null;
        }
    }

    public void setUpStream(){

        movies = FileIO.setupMovies();
        series = FileIO.setupSeries();
        savedPlaylists = FileIO.setupPlaylist(new File("data/savedMedias.txt"));
        hasSeenPlaylists = FileIO.setupPlaylist(new File("data/watchedMedia.txt"));
        users = FileIO.setUpUser();
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public ArrayList<Movie> getMovies() {
        return movies;
    }

    public ArrayList<Series> getSeries() {
        return series;
    }
    public Media getCurrentMedia() {
        return currentMedia;
    }

    public void setCurrentMedia(Media currentMedia) {
        this.currentMedia = currentMedia;
    }

    public ArrayList<Playlist> getSavedPlaylists() {
        return savedPlaylists;
    }

    public ArrayList<Playlist> getHasSeenPlaylists() {
        return hasSeenPlaylists;
    }

    public ArrayList<Movie> getMovies() {
        return movies;
    }

    public ArrayList<Series> getSeries() {
        return series;
    }
    public Media getCurrentMedia() {
        return currentMedia;
    }

    public void setCurrentMedia(Media currentMedia) {
        this.currentMedia = currentMedia;
    }

    public ArrayList<Playlist> getSavedPlaylists() {
        return savedPlaylists;
    }

    public ArrayList<Playlist> getHasSeenPlaylists() {
        return hasSeenPlaylists;
    }
}