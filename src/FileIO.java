import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class FileIO {

    public static List<String> readData(File file){
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            return reader.lines().collect(Collectors.toList());
        } catch (IOException e){
            System.err.println(e);
        }
        return Collections.emptyList();
    }

    public static ArrayList<Movie> setupMovies(){
        List<String> data = readData(new File("data/movies.txt"));
        ArrayList<Movie> movies = new ArrayList<>();
        for (String s: data) {
            String[] values = s.split(";");

            String name = values[0].trim();
            int year = Integer.parseInt(values[1].trim());

            ArrayList<String> genres = new ArrayList<>();
            String[] genreArr = values[2].trim().split(",");
            genres.addAll(Arrays.asList(genreArr));

            float rating = Float.parseFloat(values[3].trim().replace(",", ".").replace(";", ""));

            movies.add(new Movie(name, year, rating, genres));
        }
        return movies;
    }

    public static ArrayList<Series> setupSeries(){
        List<String> data = readData(new File("data/series.txt"));
        ArrayList<Series> series = new ArrayList<>();
        for (String s: data){
            String[] values = s.split(";");

            String name = values[0].trim();
            String[] years = values[1].trim().split("-");
            int  yearStart = Integer.parseInt(years[0].trim());
            int yearEnd = 0;
            if(years.length > 1)
                yearEnd = Integer.parseInt(years[1].trim());

            ArrayList<String> genres = new ArrayList<>();
            String[] genreArr = values[2].trim().split(", ");
            genres.addAll(Arrays.asList(genreArr));

            float rating = Float.parseFloat(values[3].trim().replace(",", "."));

            ArrayList<String> seasons = new ArrayList<>();
            String[] seasonArr = values[4].trim().split(", ");
            seasons.addAll(Arrays.asList(seasonArr));

            series.add(new Series(name, yearStart, yearEnd , rating, genres, seasons));
        }
        return series;
    }

    public static ArrayList<User> setUpUser(){
        List<String> data = readData(new File("data/users.txt"));
        ArrayList<User> userData = new ArrayList<>();
            for (String u: data) {
                String[] userLogin = u.split("; ");

                String userName = userLogin[0];
                String userPassword = userLogin[1];

                ArrayList<Media> userHasSeen = new ArrayList<>();
                ArrayList<Media> userSaved = new ArrayList<>(Arrays.asList(new Movie("Indiana Jones", 2020, 8.7f, new ArrayList<>(Arrays.asList("Horro"))), new Movie("Star Wars", 2020, 8.7f, new ArrayList<>(Arrays.asList("Horror")))));

                userData.add(new User(userName, userPassword, userHasSeen, userSaved));
            }
        return userData;
    }



}
