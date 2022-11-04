import java.util.Collection;

public class User {
    private String name;
    private String password;
    private final Collection<Media> hasSeen;
    private final Collection<Media> savedMedia;

    public User(String name, String password, Collection<Media> hasSeen, Collection<Media> savedMedia) {
        this.name = name;
        this.password = password;
        this.hasSeen = hasSeen;
        this.savedMedia = savedMedia;
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

    public Collection<Media> getHasSeen() {
        return hasSeen;
    }

    public Collection<Media> getSavedMedia() {
        return savedMedia;
    }
}
