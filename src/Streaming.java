import java.io.File;
import java.util.ArrayList;

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

        movies = DBIO.setupMovies();
        series = DBIO.setupSeries();
        //savedPlaylists = FileIO.setupPlaylist(new File("data/savedMedias.txt"));
        savedPlaylists = DBIO.setupPlaylist("savedMedias");
        //hasSeenPlaylists = FileIO.setupPlaylist(new File("data/watchedMedia.txt"));
        hasSeenPlaylists = DBIO.setupPlaylist("hasSeen");
        users = DBIO.setUpUser();
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

    public void addSavedPlaylists(Playlist p) {
        savedPlaylists.add(p);
        FileIO.updateSavedPlaylists();
    }
    public void addSavedPlaylistsDB(Playlist p) {
        savedPlaylists.add(p);
    }

    public void addHasSeenPlaylists(Playlist p) {
        hasSeenPlaylists.add(p);
        FileIO.updateHasSeenPlaylists();
    }

    public void addHasSeenPlaylistsDB(Playlist p) {
        hasSeenPlaylists.add(p);
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void addUser(User u) {
        users.add(u);
    }
}