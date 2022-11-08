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

    public void addToSavedMedia(Media media) {

    }

    public void removeFromSaved(Media media) {

    }

    public void watchMovie(Media media) {

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
