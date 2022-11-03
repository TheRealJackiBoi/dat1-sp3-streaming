import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class FileIO {

    public ArrayList<String> readData(File path){
        File file = path;
        ArrayList<String> media = new ArrayList<>();
        try {
            Scanner input = new Scanner(file);
            input.nextLine();

            while(input.hasNextLine());{
                media.add(input.nextLine());
            }
        } catch (FileNotFoundException e){
            media = null;
        }
        return media;
    }

    public ArrayList<Movie> setupMovies(){
        ArrayList<String> data = readData(new File("data/movies.txt"));
        ArrayList<Movie> movies = new ArrayList<>();
        for (String s: data) {
            String[] values = s.split("; ");

            String name = values[0];
            int year = Integer.parseInt(values[1]);

            ArrayList<String> genres = new ArrayList<>();
            String[] genreArr = values[2].split(", ");
            genres.addAll(Arrays.asList(genreArr));

            float rating = Float.parseFloat(values[3]);

            movies.add(new Movie(name, year, rating, genres));
        }
        return movies;
    }

    public ArrayList<Series> setupSeries(){
        ArrayList<String> data = readData(new File("data/series.txt"));
        ArrayList<Series> series = new ArrayList<>();
        for (String s: data){
            String[] values = s.split("; ");

            String name = values[0];
            int year = Integer.parseInt(values[1]);

            ArrayList<String> genres = new ArrayList<>();
            String[] genreArr = values[2].split(", ");
            genres.addAll(Arrays.asList(genreArr));

            float rating = Float.parseFloat(values[3]);

            ArrayList<String> seasons = new ArrayList<>();
            String[] seasonArr = values[4].split(", ");
            seasons.addAll(Arrays.asList(seasonArr));

            series.add(new Series(name, year, rating, genres, seasons));
        }
        return series;
    }

}
