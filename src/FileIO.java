import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Scanner;

public class FileIO {

    public static ArrayList<String> readData(File path){
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

    public static ArrayList<Movie> setupMovies(){
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

    public static ArrayList<Series> setupSeries(){
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

    public static ArrayList<User> setUpUser(){
        ArrayList<String> data = readData(new File("data/users.txt"));
        ArrayList<User> userData = new ArrayList<>();
            for (String u: data) {
                String[] userLogin = u.split("; ");

                String userName = userLogin[0];
                String userPassword = userLogin[1];

                ArrayList<Media> userHasSeen = new ArrayList<>();
                ArrayList<Media> userSaved = new ArrayList<>();

                userData.add(new User(userName, userPassword, userHasSeen, userSaved));
            }
        return userData;
    }



}
