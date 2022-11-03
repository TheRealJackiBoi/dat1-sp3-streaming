import java.util.ArrayList;

public class Movie extends Media {

    public Movie(String name, int year, int rating, ArrayList<String> genre) {
        super(name, year, rating, genre);
    }

    @Override
    public String toString() {
        return "Movie{ " + super.toString() + " }";
    }
}
