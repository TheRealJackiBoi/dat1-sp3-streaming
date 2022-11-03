import java.util.ArrayList;

public abstract class Media {
    private final String name;
    private final int year;
    private int rating;
    private final ArrayList<String> genre;

    public Media(String name, int year, int rating, ArrayList<String> genre) {
        this.name = name;
        this.year = year;
        this.rating = rating;
        this.genre = genre;
    }

    public String getName() {
        return name;
    }

    public int getYear() {
        return year;
    }

    public int getRating() {
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
        return  "name='" + name + '\'' +
                ", year=" + year +
                ", rating=" + rating +
                ", genre=" + genre;
    }
}
