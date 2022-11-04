import java.util.ArrayList;

public class Streaming {

    private ArrayList<User> users;
    private User currentUser;
    private ArrayList<Movie> movies;
    private ArrayList<Series> series;

    public boolean checkUserName(String un) {
        User user = null;

        for (User u :
                users) {
            if (u.getName().equals(un)) {
                user = u;
                break;
            }
        }
        if (user == null) {
            return false;
        }
        return true;
    }

    public User checkUserPW(String un, String pw) {

        User user = null;

        for (User u :
                users) {
            if (u.getName().equals(un)) {
                user = u;
                break;
            }
        }
        if (user == null) {
            return null;
        }
        if (user.getPassword().equals(pw)) {
            return user;
        } else {
            return null;
        }
    }

    public void setUpStream(){

        users = FileIO.setUpUser();
        movies = FileIO.setupMovies();
        series = FileIO.setupSeries();

    }

}