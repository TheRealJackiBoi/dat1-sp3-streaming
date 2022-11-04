import java.util.ArrayList;

public class Streaming {

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
