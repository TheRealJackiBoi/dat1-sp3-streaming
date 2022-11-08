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
        Streaming stream = Streaming.getInstance();
        ArrayList<Playlist> hasSeenPlaylists = stream.getHasSeenPlaylists();
        ArrayList<Playlist> savedMediaData = stream.getSavedPlaylists();
        ArrayList<User> userData = new ArrayList<>();
        for (String u: data) {
                String[] userLogin = u.split("; ");

                String userName = userLogin[0];
                String userPassword = userLogin[1];

                boolean userHasSeenExist = false;
                ArrayList<Media> userHasSeen = new ArrayList<>();
                for (Playlist p : hasSeenPlaylists) {
                    if (p.ownerName.equals(userName)) {
                        userHasSeenExist = true;
                        userHasSeen = p.medias;
                    }
                }

                boolean savedMediaDataListExist = false;
                ArrayList<Media> userSaved = new ArrayList<>();
                for (Playlist p : savedMediaData) {
                    if (p.ownerName.equals(userName)) {
                        savedMediaDataListExist = true;
                        userSaved = p.medias;
                    }
                }
                userData.add(new User(userName, userPassword, userHasSeen, userSaved));
            }
        return userData;
    }

    public static ArrayList<Playlist> setupPlaylist(File file) {
        List<String> mediaData = readData(file);
        ArrayList<Playlist> playlists = new ArrayList<>();
        Streaming stream = Streaming.getInstance();
        ArrayList<Movie> movies = stream.getMovies();
        ArrayList<Series> series = stream.getSeries();

        for (String s :
                mediaData) {
            String[] data = s.split(";");

            String ownerName = data[0].trim();

            ArrayList<Media> medias = new ArrayList<>();

            String[] mediasRaw = data[1].trim().split(", ");

            for (String mediaName : mediasRaw) {

                boolean mediaAdded = false;

                for (Movie m :
                        movies) {
                    if (m.getName().equals(mediaName)) {
                        medias.add(m);
                        mediaAdded = true;
                        break;
                    }
                }
                if (!mediaAdded) {
                    for (Series m :
                            series) {
                        if (m.getName().equals(mediaName)) {
                            medias.add(m);
                            mediaAdded = true;
                            break;
                        }
                    }
                }
            }
            Playlist p = new Playlist(ownerName, medias);
            playlists.add(p);
        }
        return playlists;
    }

    public static void createUser(String username, String password) {
        Writer writer = null;
        Streaming stream = Streaming.getInstance();
        try {
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("data/users.txt", true), "UTF-8"));

            User u = new User(username, password);
            stream.addUser(u);

            for (User user :
                    stream.getUsers()) {
                writer.write(user.getName() + "; " + user.getPassword() + "\n");
            }
            writer.close();
        } catch (FileNotFoundException nfx) {
            System.err.println(nfx);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
