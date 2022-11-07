import java.util.ArrayList;

public class Movie extends Media {

    private final int year;
    public Movie(String name, int year, float rating, ArrayList<String> genre) {
        super(name, rating, genre);
        this.year = year;
    }


}
