import java.util.ArrayList;
import java.util.Collection;

public class User {
    private String name;
    private String password;
    private final ArrayList<Media> hasSeen;
    private final ArrayList<Media> savedMedia;

    public User(String name, String password, ArrayList<Media> hasSeen, ArrayList<Media> savedMedia) {
        this.name = name;
        this.password = password;
        this.hasSeen = hasSeen;
        this.savedMedia = savedMedia;
    }

    public User(String name, String password) {
        this.name = name;
        this.password = password;
        this.hasSeen = new ArrayList<>();
        this.savedMedia = new ArrayList<>();
    }

    public boolean addToSaved(Media media) {

        int index = savedMedia.indexOf(media);

        if(index != -1)
            return false;

        savedMedia.add(media);
        DBIO.updateSavedPlaylists();
        return true;
    }

    public boolean removeFromSaved(Media media) {

        int index = savedMedia.indexOf(media);

        if(index == -1)
            return false;

        savedMedia.remove(media);
        DBIO.updateSavedPlaylists();
        return true;
    }

    public void watchMovie(Media media) {
        int index = hasSeen.indexOf(media);

        if(index != -1)
            return;

        hasSeen.add(media);
        DBIO.updateHasSeenPlaylists();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ArrayList<Media> getHasSeen() {
        return hasSeen;
    }

    public ArrayList<Media> getSavedMedia() {
        return savedMedia;
    }

}
