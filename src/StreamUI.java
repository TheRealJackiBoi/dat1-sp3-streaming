import javax.swing.*;
import java.awt.*;

import static java.awt.BorderLayout.*;
import static java.awt.BorderLayout.CENTER;

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

    private JPanel savedTitlesPanel;


    public JFrame createFrame() {

        if (frame != null)
            throw new IllegalArgumentException("Frame already created");


        frame = new JFrame("Streamy");
        JPanel contentPane = new JPanel(new BorderLayout());
        if (currentPanel != null) {
            frame.remove(currentPanel);
        }
        contentPane.add(currentPanel = createLoginPanel(), CENTER);
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

        JLabel welcomeText = new JLabel("Welcome to Streamy");

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
        welcomeText.setAlignmentX(Component.CENTER_ALIGNMENT);

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
                    swapPanel(createMainPanel());
                }
            } else {
                //username incorrect
                JOptionPane.showMessageDialog(frame, "Username incorrect");
            }
        });

        buttonPanel.add(bCreate);
        buttonPanel.add(bLogin);

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(welcomeText);
        panel.add(loginLabel);
        panel.add(userLabel);
        panel.add(userText);
        panel.add(passwordLabel);
        panel.add(passwordText);
        panel.add(buttonPanel);

        loginPanel.add(panel, CENTER);

        return loginPanel;
    }

    private JPanel createMainPanel() {
        if(mainPanel != null)
            return mainPanel;

        mainPanel = new JPanel();
        Streaming stream = Streaming.getInstance();

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel panel = new JPanel();

        JLabel name = new JLabel(stream.getCurrentUser().getName());

        JButton search = new JButton("Search");
        JButton savedTitles = new JButton("Saved Titles");
        JButton seenTitles = new JButton("Seen Titles");
        JButton logout = new JButton("Logout");

        savedTitles.addActionListener(e -> {

            if (stream.getCurrentUser().getSavedMedia().isEmpty()) {
                JOptionPane.showMessageDialog(frame, "You have no saved titles, save some first");
                return;
            }

            //goto savedTitlesPane
            swapPanel(createSavedTitlesPane());
        });

        logout.addActionListener(e -> {

            int a = JOptionPane.showConfirmDialog(frame, "Confirm you want to logout");
            if (a== JOptionPane.YES_OPTION) {
                stream.setCurrentUser(null);
                swapPanel(createLoginPanel());
            }
        });

        search.setAlignmentX(Component.CENTER_ALIGNMENT);
        savedTitles.setAlignmentX(Component.CENTER_ALIGNMENT);
        seenTitles.setAlignmentX(Component.CENTER_ALIGNMENT);
        logout.setAlignmentX(Component.CENTER_ALIGNMENT);

        topPanel.add(name);

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(search);
        panel.add(savedTitles);
        panel.add(seenTitles);
        panel.add(logout);

        mainPanel.setLayout(new BorderLayout());

        mainPanel.add(topPanel, NORTH);
        mainPanel.add(panel, CENTER);

        return mainPanel;
    }

    private JPanel createSavedTitlesPane() {
        if(savedTitlesPanel != null)
            return savedTitlesPanel;

        savedTitlesPanel = new JPanel();
        Streaming stream = Streaming.getInstance();

        JPanel panel = new JPanel(new BorderLayout());
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel contPanel = new JPanel();

        JButton bGoToMain = new JButton("Menu");

        JLabel header = new JLabel("Saved Titles");

        JList<Media> list = new JList<>(stream.getCurrentUser().getSavedMedia().toArray(new Media[0]));

        JPanel bPanel = new JPanel(new GridLayout(1, 2));

        JButton bPlay = new JButton("Play");
        JButton bDelete = new JButton("Delete");


        JScrollPane titlesSPanel = new JScrollPane(list);

        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        list.addListSelectionListener(e -> {
            boolean b = e.getFirstIndex() != -1;
            bPlay.setEnabled(b);
            bDelete.setEnabled(b);
        });

        //gotomain add going to main menu panel
        bGoToMain.addActionListener(e -> {
            //goto mainpanel
            swapPanel(createMainPanel());
        });


        bPlay.addActionListener(e -> {

            //goto playPanel
            swapPanel(createPlayPanel(list.getSelectedValue()));
        });

        //TODO: Create delete from savedlist
        //delete media from savedList
        bDelete.addActionListener(e -> {

        });

        bPlay.setEnabled(false);
        bDelete.setEnabled(false);



        contPanel.setLayout(new BorderLayout());

        bPanel.add(bPlay);
        bPanel.add(bDelete);
        topPanel.add(bGoToMain);
        contPanel.add(header, NORTH);
        contPanel.add(titlesSPanel, CENTER);
        contPanel.add(bPanel, SOUTH);
        panel.add(topPanel, NORTH);
        panel.add(contPanel, CENTER);

        savedTitlesPanel.add(panel, CENTER);

        return savedTitlesPanel;
    }

    private JPanel createPlayPanel(Media media) {
        return new JPanel();
    }

    private void swapPanel(JPanel panel) {
        if (currentPanel != null) {
            frame.remove(currentPanel);
        }
        frame.getContentPane().add(currentPanel = panel, CENTER);
        frame.revalidate();
        frame.repaint();
    }

}
