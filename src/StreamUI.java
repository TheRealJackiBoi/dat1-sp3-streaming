import javax.imageio.ImageIO;
import javax.sound.midi.Soundbank;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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
    private JPanel hasWatchPanel;
    private JPanel playPanel;


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

            FileIO.createUser(username, password);
            JOptionPane.showMessageDialog(frame, "You created user: " + username);

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

        seenTitles.addActionListener(e -> {
            if (stream.getCurrentUser().getHasSeen().isEmpty()){
                JOptionPane.showMessageDialog(frame, "You have not seen any titels yet");
                return;
            }

            swapPanel(createHasWatch());
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
            stream.setCurrentMedia(list.getSelectedValue());
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

    private JPanel createHasWatch(){
        if(hasWatchPanel != null)
            return hasWatchPanel;

        hasWatchPanel = new JPanel();
        Streaming stream = Streaming.getInstance();

        JPanel panel = new JPanel(new BorderLayout());
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel contPanel = new JPanel();

        JButton bGoToMain = new JButton("Menu");

        JLabel header = new JLabel("Saved Titles");

        JList<Media> list = new JList<>(stream.getCurrentUser().getHasSeen().toArray(new Media[0]));

        JPanel bPanel = new JPanel(new GridLayout(1, 2));

        JButton bPlay = new JButton("Play");

        JScrollPane titlesSPanel = new JScrollPane(list);

        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        list.addListSelectionListener(e -> {
            boolean b = e.getFirstIndex() != -1;
            bPlay.setEnabled(b);

        });

        //gotomain add going to main menu panel
        bGoToMain.addActionListener(e -> {
            //goto mainpanel
            swapPanel(createMainPanel());
        });

        bPlay.addActionListener(e -> {
            stream.setCurrentMedia(list.getSelectedValue());
            //goto playPanel
            swapPanel(createPlayPanel(list.getSelectedValue()));
        });

        bPlay.setEnabled(false);


        contPanel.setLayout(new BorderLayout());

        bPanel.add(bPlay);
        topPanel.add(bGoToMain);
        contPanel.add(header, NORTH);
        contPanel.add(titlesSPanel, CENTER);
        contPanel.add(bPanel, SOUTH);
        panel.add(topPanel, NORTH);
        panel.add(contPanel, CENTER);

        hasWatchPanel.add(panel, CENTER);

        return hasWatchPanel;

    }

    private JPanel createPlayPanel(Media streaming) {

        playPanel = new JPanel(new BorderLayout());

        Streaming stream = Streaming.getInstance();

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JButton bToMainMenu = new JButton("Main Menu");

        BufferedImage imageMedia;
        JLabel imageLabel = new JLabel();

        //get mediaName
        JLabel mediaCurrentPlaying = new JLabel(stream.getCurrentMedia() + " is playing");

        bToMainMenu.addActionListener(e -> {
            stream.setCurrentMedia(null);
            swapPanel(createMainPanel());
        });

        try{
            Media media = stream.getCurrentMedia();

            if (media instanceof Series) {
                String imageName = "data/seriesImages/" + media.getName() +".jpg";
                imageLabel = new JLabel(new ImageIcon(imageName));
            } else if (media instanceof Movie) {
                String imageName = "data/moviesImages/" + media.getName() + ".jpg";
                imageLabel = new JLabel(new ImageIcon(imageName));
            }
        } catch (Exception e){
            System.out.println(e);
        }

        topPanel.add(bToMainMenu);
        mediaCurrentPlaying.setHorizontalAlignment(JLabel.CENTER);
        {
            Font font = mediaCurrentPlaying.getFont();
            font = font.deriveFont(font.getSize2D()*2);
            mediaCurrentPlaying.setFont(font);
        }

        playPanel.add(mediaCurrentPlaying, SOUTH);
        playPanel.add(imageLabel, CENTER);
        playPanel.add(topPanel, NORTH);

        return playPanel;
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
