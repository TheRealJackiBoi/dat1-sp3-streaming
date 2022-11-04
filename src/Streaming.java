import java.util.ArrayList;
import java.util.stream.Stream;

public class Streaming {
    private static Streaming stream = null;

    public synchronized static Streaming getInstance() {
        if (stream == null) {
            stream = new Streaming();
        }
        return stream;
    }

    private Streaming() {}
    private ArrayList<User> users;
    private User currentUser;

    public boolean checkUserName(String un) {
        User user = null;

        for (User u :
                users) {
            if (u.getName().equals(un)) {
                user = u;
                break;
            }
        }
        return user != null;
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

}
