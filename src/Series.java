import java.util.ArrayList;

public class Series extends Media {
    private final ArrayList<String> seasons;

    private final int yearStart;
    private final int yearEnd;


    public Series(String name, int yearStart, int yearEnd, float rating, ArrayList<String> genre, ArrayList<String> seasons) {
        super(name, rating, genre);
        this.seasons = seasons;
        this.yearStart = yearStart;
        this.yearEnd = yearEnd;
    }

    public int getYearStart() {
        return yearStart;
    }

    public int getYearEnd() {
        return yearEnd;
    }

    public ArrayList<String> getSeasons() {
        return seasons;
    }
}
