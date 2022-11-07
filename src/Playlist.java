import java.util.ArrayList;

public class Playlist {
    public final String ownerName;
    public final ArrayList<Media> medias;

    public Playlist(String ownerName, ArrayList<Media> medias) {
        this.ownerName = ownerName;
        this.medias = medias;
    }
}
