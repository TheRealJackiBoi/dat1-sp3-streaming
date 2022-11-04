import javax.swing.*;
import java.awt.*;

public class StreamUI {
    static {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println(e);
        }
    }
    private JFrame frame;
    private JPanel currentPanel = null;
    private JPanel loginPanel;
    private JPanel mainPanel;



    public JFrame createFrame() {

        if (frame != null)
            throw new IllegalArgumentException("Frame already created");


        frame = new JFrame("Streamy");
        JPanel contentPane = new JPanel(new BorderLayout());
        if (currentPanel != null) {
            frame.remove(currentPanel);
        }
        contentPane.add(currentPanel = createLoginPanel(), BorderLayout.CENTER);
        contentPane.setPreferredSize(new Dimension(800, 600));

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setContentPane(contentPane);
        frame.setLocationRelativeTo(null);
        frame.pack();

        return frame;
    }

    public JFrame getFrame() {

        if(frame == null) {
            return createFrame();
        } else {
            return frame;
        }
    }


    private JPanel createLoginPanel() {

        if(loginPanel != null)
            return loginPanel;

        loginPanel = new JPanel();

        JPanel panel = new JPanel();

        JLabel loginLabel = new JLabel("Login");

        //User input
        JLabel userLabel = new JLabel("Username");
        JTextField userText = new JTextField();

        //Password input
        JLabel passwordLabel = new JLabel("Password");
        JPasswordField passwordText = new JPasswordField();

        //login an create new user button
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
        JButton bLogin = new JButton("Login");
        JButton bCreate = new JButton("Create User");

        loginLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        userLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        passwordLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        //workinprogress
        //createuserbutton action
        bCreate.addActionListener(e -> {
            String username = userText.getText();
            String password = String.valueOf(passwordText.getPassword());
            Streaming stream = Streaming.getInstance();

            if (stream.checkUserName(username)) {
                JOptionPane.showMessageDialog(frame,"User already exists");
                return;
            }


        });
        //loginbutton action
        bLogin.addActionListener(e -> {
            String username = userText.getText();
            String password = String.valueOf(passwordText.getPassword());

            Streaming stream = Streaming.getInstance();

            if(stream.checkUserName(username)) {
                User user = stream.checkUserPW(username, password);

                if (user == null) {
                    //popup
                    JOptionPane.showMessageDialog(frame, "Password incorrect");
                    return;
                } else {
                    //set currentuser
                    stream.setCurrentUser(user);
                    //goto mainpanel
                    if (currentPanel != null) {
                        frame.remove(currentPanel);
                    }
                    frame.getContentPane().add(currentPanel = createMainPanel(), BorderLayout.CENTER);
                    frame.revalidate();
                }
            } else {
                //username incorrect
                JOptionPane.showMessageDialog(frame, "Username incorrect");
            }
        });

        buttonPanel.add(bCreate);
        buttonPanel.add(bLogin);

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(loginLabel);
        panel.add(userLabel);
        panel.add(userText);
        panel.add(passwordLabel);
        panel.add(passwordText);
        panel.add(buttonPanel);

        loginPanel.add(panel, BorderLayout.CENTER);

        return loginPanel;
    }

    private JPanel createMainPanel() {
        if(mainPanel != null)
            return mainPanel;

        mainPanel = new JPanel();
        Streaming stream = Streaming.getInstance();

        JPanel topPanel = new JPanel();
        JPanel panel = new JPanel();

        JLabel name = new JLabel(stream.getCurrentUser().getName());

        JButton search = new JButton("Search");
        JButton savedTitles = new JButton("Saved Titles");
        JButton seenTitles = new JButton("Seen Titles");
        JButton logout = new JButton("Logout");

        name.setAlignmentX(Component.LEFT_ALIGNMENT);

        search.setAlignmentX(Component.CENTER_ALIGNMENT);
        savedTitles.setAlignmentX(Component.CENTER_ALIGNMENT);
        seenTitles.setAlignmentX(Component.CENTER_ALIGNMENT);
        logout.setAlignmentX(Component.CENTER_ALIGNMENT);

        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
        topPanel.add(name);

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(search);
        panel.add(savedTitles);
        panel.add(seenTitles);
        panel.add(logout);

        mainPanel.setLayout(new BorderLayout());

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(panel, BorderLayout.CENTER);

        return mainPanel;
    }


}
