import java.util.ArrayList;

public abstract class Media {
    private final String name;
    private float rating;
    private final ArrayList<String> genre;

    public Media(String name, float rating, ArrayList<String> genre) {
        this.name = name;
        this.rating = rating;
        this.genre = genre;
    }

    public String getName() {
        return name;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public ArrayList<String> getGenre() {
        return genre;
    }

    @Override
    public String toString() {
        return name;
    }


}
