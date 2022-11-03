import java.util.ArrayList;

public class Series extends Media {
    private final ArrayList<String> seasons;


    public Series(String name, int year, float rating, ArrayList<String> genre, ArrayList<String> seasons) {
        super(name, year, rating, genre);
        this.seasons = seasons;
    }

    @Override
    public String toString() {
        return "Series{ " + super.toString() + ", seasons=" + seasons + " }";
    }
}
