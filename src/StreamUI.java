import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;

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
    private JPanel allMediaPanel;
    private JPanel allMoviesPanel;
    private JPanel allSeriesPanel;
    private JPanel viewMediaPanel;
    private JPanel searchPanel;
    SearchFilter searchFilter = new SearchFilter();

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
            if (stream.ioType == 'd') {
                DBIO.createUser(username, password);
            }
            else {
                FileIO.createUser(username, password);
            }
            JOptionPane.showMessageDialog(frame, "You created user: " + username);
            JOptionPane.showMessageDialog(frame, "Please restart to use new user");

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
        {
            Font font = welcomeText.getFont();
            font = font.deriveFont(font.getSize2D()*2);
            welcomeText.setFont(font);
        }
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
        JButton allMedia = new JButton("All Titles");
        JButton logout = new JButton("Logout");

        savedTitles.addActionListener(e -> {
            if (stream.getCurrentUser().getSavedMedia().isEmpty()) {
                JOptionPane.showMessageDialog(frame, "You have no saved titles, save some first");
                return;
            }

            //goto savedTitlesPane
            savedTitlesPanel = null;
            swapPanel(createSavedTitlesPane());
        });

        search.addActionListener(e -> {
            swapPanel(createSearchPanel());
        });

        seenTitles.addActionListener(e -> {
            if (stream.getCurrentUser().getHasSeen().isEmpty()){
                JOptionPane.showMessageDialog(frame, "You have not seen any titels yet");
                return;
            }

            hasWatchPanel = null;
            swapPanel(createHasWatch());
        });

        allMedia.addActionListener(e -> {
            swapPanel(createAllMedia());
        });

        logout.addActionListener(e -> {

            int a = JOptionPane.showConfirmDialog(frame, "Confirm you want to logout");
            if (a== JOptionPane.YES_OPTION) {
                stream.setCurrentUser(null);
                ArrayList<JPanel> allPanels = new ArrayList<>(Arrays.asList(loginPanel, mainPanel, savedTitlesPanel, hasWatchPanel));
                for (JPanel p :
                        allPanels) {
                    p = null;
                }
                swapPanel(createLoginPanel());
            }
        });

        search.setAlignmentX(Component.CENTER_ALIGNMENT);
        savedTitles.setAlignmentX(Component.CENTER_ALIGNMENT);
        seenTitles.setAlignmentX(Component.CENTER_ALIGNMENT);
        allMedia.setAlignmentX(Component.CENTER_ALIGNMENT);
        logout.setAlignmentX(Component.CENTER_ALIGNMENT);

        topPanel.add(name);

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(search);
        panel.add(savedTitles);
        panel.add(seenTitles);
        panel.add(allMedia);
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
        JButton bViewMedia = new JButton("Details");
        JButton bDelete = new JButton("Delete");


        JScrollPane titlesSPanel = new JScrollPane(list);

        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        list.addListSelectionListener(e -> {
            boolean b = e.getFirstIndex() != -1;
            bPlay.setEnabled(b);
            bDelete.setEnabled(b);
            bViewMedia.setEnabled(b);
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

        bViewMedia.addActionListener(e -> {
            stream.setCurrentMedia(list.getSelectedValue());

            swapPanel(createViewPanel());
        });

        //delete media from savedList
        bDelete.addActionListener(e -> {
            Media m = list.getSelectedValue();
            boolean b = stream.getCurrentUser().removeFromSaved(m);
            if (!b) {
                JOptionPane.showMessageDialog(frame, "Media is already deleted");
            } else {
                JOptionPane.showMessageDialog(frame, m + " is deleted");
            }
            savedTitlesPanel = null;
            swapPanel(createSavedTitlesPane());
        });

        bPlay.setEnabled(false);
        bDelete.setEnabled(false);
        bViewMedia.setEnabled(false);


        contPanel.setLayout(new BorderLayout());

        bPanel.add(bPlay);
        bPanel.add(bDelete);
        bPanel.add(bViewMedia);
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

        JButton bViewMedia = new JButton("Details");

        JScrollPane titlesSPanel = new JScrollPane(list);

        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        list.addListSelectionListener(e -> {
            boolean b = e.getFirstIndex() != -1;
            bPlay.setEnabled(b);
            bViewMedia.setEnabled(b);
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

        bViewMedia.addActionListener(e -> {
            stream.setCurrentMedia(list.getSelectedValue());

            swapPanel(createViewPanel());
        });

        bPlay.setEnabled(false);
        bViewMedia.setEnabled(false);

        contPanel.setLayout(new BorderLayout());

        bPanel.add(bPlay);
        bPanel.add(bViewMedia);
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

        stream.getCurrentUser().watchMovie(streaming);

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

            String mName = media.getName();
            if (mName.contains("'")) {
                mName = mName.replaceAll("'", "_");
            }
            if (media instanceof Series) {
                String imageName = "data/seriesImages/" + mName +".jpg";
                imageLabel = new JLabel(new ImageIcon(imageName));
            } else if (media instanceof Movie) {
                String imageName = "data/moviesImages/" + mName + ".jpg";
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

    private JPanel createAllMedia(){
        if(allMediaPanel != null)
            return allMediaPanel;

        allMediaPanel = new JPanel(new BorderLayout());

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton bToMainMenu = new JButton("Main Menu");

        JLabel allMedia = new JLabel("Select if you want to watch a Movie or Series!");

        JPanel panel = new JPanel();

        JButton movies = new JButton("Movies");

        JButton series = new JButton("Series");

        bToMainMenu.addActionListener(e -> {
            swapPanel(createMainPanel());
        });

        movies.addActionListener(e -> {
            swapPanel(createAllMoviesPanel());

        });

        series.addActionListener(e -> {
            swapPanel(createAllSeriesPanel());
        });


        topPanel.add(bToMainMenu, Component.LEFT_ALIGNMENT);
        topPanel.add(allMedia);

        topPanel.addComponentListener(new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent e) {
                int xCordi = (topPanel.getWidth() - allMedia.getWidth())/2;
                allMedia.setLocation(xCordi,allMedia.getY());
            }

            @Override
            public void componentMoved(ComponentEvent e) {}

            @Override
            public void componentShown(ComponentEvent e) {}

            @Override
            public void componentHidden(ComponentEvent e) {}
        });

        panel.add(movies);
        panel.add(series);


        allMediaPanel.add(panel, CENTER);
        allMediaPanel.add(topPanel, NORTH);

        return allMediaPanel;
    }

    private JPanel createAllMoviesPanel(){
        if (allMoviesPanel != null)
            return allMoviesPanel;

        allMoviesPanel= new JPanel();
        Streaming stream = Streaming.getInstance();

        JPanel panel = new JPanel(new BorderLayout());
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel contPanel = new JPanel();

        JButton bGoToMain = new JButton("Menu");

        JLabel allMovies = new JLabel("These are all our Movies!");

        JList<Media> list = new JList<>(stream.getMovies().toArray(new Movie[0]));

        JPanel bPanel = new JPanel(new GridLayout(1, 2));


        JButton bPlay = new JButton("Play");
        JButton bSave = new JButton("Save");

        JButton bViewMedia = new JButton("Movie details");

        JScrollPane titlesSPanel = new JScrollPane(list);
        titlesSPanel.setPreferredSize(new Dimension(300,500));

        list.addListSelectionListener(e -> {
                    boolean b = e.getFirstIndex() != -1;
                    bPlay.setEnabled(b);
                    bSave.setEnabled(b);
                    bViewMedia.setEnabled(b);
                });
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);


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

         bSave.addActionListener(e -> {
             Media m = list.getSelectedValue();
                boolean b = stream.getCurrentUser().addToSaved(m);
                if (!b) {
                    JOptionPane.showMessageDialog(frame, "Media is already saved");
                } else {
                    JOptionPane.showMessageDialog(frame, m + " is saved");
                }
                });

        bViewMedia.addActionListener(e -> {
            stream.setCurrentMedia(list.getSelectedValue());

            swapPanel(createViewPanel());
        });

        bPlay.setEnabled(false);
        bSave.setEnabled(false);
        bViewMedia.setEnabled(false);


        contPanel.setLayout(new BorderLayout());

        bPanel.add(bPlay);
        bPanel.add(bSave);
        bPanel.add(bViewMedia);
        topPanel.add(bGoToMain);
        contPanel.add(allMovies, NORTH);
        contPanel.add(titlesSPanel, CENTER);
        contPanel.add(bPanel, SOUTH);
        panel.add(topPanel, NORTH);
        panel.add(contPanel, CENTER);

        allMoviesPanel.add(panel, CENTER);

        return allMoviesPanel;
    }

    private JPanel createAllSeriesPanel(){
        if (allSeriesPanel != null)
            return allSeriesPanel;

        allSeriesPanel= new JPanel();
        Streaming stream = Streaming.getInstance();

        JPanel panel = new JPanel(new BorderLayout());
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel contPanel = new JPanel();

        JButton bGoToMain = new JButton("Menu");

        JLabel allSeries = new JLabel("These are all our Series!");

        JList<Media> list = new JList<>(stream.getSeries().toArray(new Series[0]));

        JPanel bPanel = new JPanel(new GridLayout(1, 2));

        JButton bPlay = new JButton("Play");
        JButton bSave = new JButton("Save");

        JButton bViewMedia = new JButton("Series details");

        JScrollPane titlesSPanel = new JScrollPane(list);
        titlesSPanel.setPreferredSize(new Dimension(300,500));

        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        list.addListSelectionListener(e -> {
            boolean b = e.getFirstIndex() != -1;
            bPlay.setEnabled(b);
            bSave.setEnabled(b);
            bViewMedia.setEnabled(b);

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

        bSave.addActionListener(e -> {
            Media m = list.getSelectedValue();
            boolean b = stream.getCurrentUser().addToSaved(m);
            if (!b) {
                JOptionPane.showMessageDialog(frame, "Media is already saved");
            } else {
                JOptionPane.showMessageDialog(frame, m + " is saved");
            }
        });

        bViewMedia.addActionListener(e -> {
            stream.setCurrentMedia(list.getSelectedValue());

            swapPanel(createViewPanel());
        });

        bPlay.setEnabled(false);
        bSave.setEnabled(false);
        bViewMedia.setEnabled(false);


        contPanel.setLayout(new BorderLayout());

        bPanel.add(bPlay);
        bPanel.add(bSave);
        bPanel.add(bViewMedia);
        topPanel.add(bGoToMain);
        contPanel.add(allSeries, NORTH);
        contPanel.add(titlesSPanel, CENTER);
        contPanel.add(bPanel, SOUTH);
        panel.add(topPanel, NORTH);
        panel.add(contPanel, CENTER);

        allSeriesPanel.add(panel, CENTER);

        return allSeriesPanel;

    }

    private JPanel createViewPanel() {

        viewMediaPanel = new JPanel(new BorderLayout());

        Streaming stream = Streaming.getInstance();

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));

        JButton bToMainMenu = new JButton("Main Menu");

        BufferedImage imageMedia;
        JLabel imageLabel = new JLabel();

        //get mediaName, mediaRating and mediasGenre
        JLabel mediaName = new JLabel(stream.getCurrentMedia().getName());
        JLabel mediaRating = new JLabel(String.valueOf(stream.getCurrentMedia().getRating()));
        JLabel mediaGenre = new JLabel(String.valueOf(stream.getCurrentMedia().getGenre()));

        JLabel mediaYear = new JLabel();
        JLabel seriesSeason = null;

        Media media = stream.getCurrentMedia();


        if (media instanceof Movie) {
            mediaYear = new JLabel(String.valueOf((((Movie) media).getYear())));
            mediaYear.setHorizontalAlignment(JLabel.CENTER);
        } else if (media instanceof Series) {
            mediaYear = new JLabel(String.valueOf(((Series) media).getYearStart()) + " - " + String.valueOf(((Series) media).getYearEnd()));
            seriesSeason = new JLabel(String.valueOf(((Series) media).getSeasons()));
            mediaYear.setHorizontalAlignment(JLabel.CENTER);
            seriesSeason.setHorizontalAlignment(JLabel.CENTER);
        }

        bToMainMenu.addActionListener(e -> {
            stream.setCurrentMedia(null);
            swapPanel(createMainPanel());
        });


            if (media instanceof Series) {
                String imageName = "data/seriesImages/" + media.getName() + ".jpg";
                imageLabel = new JLabel(new ImageIcon(imageName));

            } else if (media instanceof Movie) {
                String imageName = "data/moviesImages/" + media.getName() + ".jpg";
                imageLabel = new JLabel(new ImageIcon(imageName));
            }


        topPanel.add(bToMainMenu);

            mediaName.setHorizontalAlignment(JLabel.CENTER);
            mediaRating.setHorizontalAlignment(JLabel.CENTER);
            mediaGenre.setHorizontalAlignment(JLabel.CENTER);


        {
            Font font = mediaName.getFont();
            font = font.deriveFont(font.getSize2D()*2);
            mediaName.setFont(font);
            mediaRating.setFont(font);
            mediaGenre.setFont(font);
            mediaYear.setFont(font);
            if(seriesSeason != null)
            seriesSeason.setFont(font);
        }

        {

            infoPanel.add(Box.createVerticalGlue());

            infoPanel.add(mediaName);
            infoPanel.add(mediaRating);
            infoPanel.add(mediaYear);
            infoPanel.add(mediaGenre);
            if(seriesSeason != null){
                //TODO: make seasons appear beautyfully
                infoPanel.add(seriesSeason);
            }
            infoPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 100));


            infoPanel.add(Box.createVerticalGlue());
        }

        viewMediaPanel.add(infoPanel, EAST);
        viewMediaPanel.add(imageLabel, CENTER);
        viewMediaPanel.add(topPanel, NORTH);

        return viewMediaPanel;
    }

    private JPanel createSearchPanel() {
        searchFilter.genre = "";
        searchFilter.name = "";
        searchFilter.type = "movie";

        if (searchPanel != null)
            return searchPanel;

        searchPanel= new JPanel();

        Streaming stream = Streaming.getInstance();

        JPanel contentPanel = new JPanel();
        JPanel showPanel = new JPanel();
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel mediaTypePanel = new JPanel();
        final DefaultListModel<Media> listModel = new DefaultListModel<>();
        listModel.addAll(stream.getMovies());
        JList<Media> list = new JList<>(listModel);
        JScrollPane titlesSPanel = new JScrollPane(list);
        JPanel bPanel = new JPanel();

        mediaTypePanel.setLayout(new GridLayout(1,2));
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        searchPanel.setLayout(new BorderLayout());
        showPanel.setLayout(new BoxLayout(showPanel, BoxLayout.Y_AXIS));
        bPanel.setLayout(new GridLayout(1,3));

        ArrayList<String> genres = new ArrayList<>(Arrays.asList("Please select a genre","Crime","Drama","Mystery","Action","Adventure",
                "Thriller","Comedy","Fantasy","History","Animation","Sci fi","Biography","Family","Western",
                "Documentary","Romance","Sport","War","Horror","Film noir","Musical"));

        JComboBox genreSelect = new JComboBox(genres.toArray());
        genreSelect.setSelectedIndex(0);
        ((JLabel)genreSelect.getRenderer()).setHorizontalAlignment(JLabel.CENTER);

        genreSelect.addItemListener(e -> {
            String genre = (String)e.getItem();
            if (!genres.get(0).equals(genre)){
                searchFilter.genre = genre;
            } else {
                searchFilter.genre = "";
            }
        });

        JButton bGoToMain = new JButton("Main Menu");
        JButton bMovies = new JButton("Movies");
        JButton bSeries = new JButton("Series");
        JButton bSearch = new JButton("Search");
        JButton bPlay = new JButton("Play");
        JButton bSave = new JButton("Save");
        JButton bViewMedia = new JButton("Details");

        JLabel lName = new JLabel("Search for title");
        JTextField name = new JTextField();

        titlesSPanel.setPreferredSize(new Dimension(300,600));
        name.setColumns(5);
        lName.setAlignmentX(Component.CENTER_ALIGNMENT);
        bSearch.setAlignmentX(Component.CENTER_ALIGNMENT);



        list.addListSelectionListener(e -> {
            boolean b = e.getFirstIndex() != -1;
            bPlay.setEnabled(b);
            bSave.setEnabled(b);
            bViewMedia.setEnabled(b);
        });

        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        bGoToMain.addActionListener(e -> {
            //goto mainpanel
            swapPanel(createMainPanel());
        });

        bMovies.addActionListener(e -> {
            searchFilter.type = "movie";
        });

        bSeries.addActionListener(e -> {
            searchFilter.type = "series";
        });


        bPlay.addActionListener(e -> {
            stream.setCurrentMedia(list.getSelectedValue());
            //goto playPanel
            swapPanel(createPlayPanel(list.getSelectedValue()));
        });

        bSave.addActionListener(e -> {
            Media m = list.getSelectedValue();
            boolean b = stream.getCurrentUser().addToSaved(m);
            if (!b) {
                JOptionPane.showMessageDialog(frame, "Media is already saved");
            } else {
                JOptionPane.showMessageDialog(frame, m + " is saved");
            }
        });

        bViewMedia.addActionListener(e -> {
            stream.setCurrentMedia(list.getSelectedValue());

            swapPanel(createViewPanel());
        });

        bSearch.addActionListener(e -> {
            searchFilter.name = name.getText();

            ArrayList<Movie> movies = stream.getMovies();
            ArrayList<Series> series = stream.getSeries();

            ArrayList<Media> medias= new ArrayList<>();

            if (searchFilter.type.equals("movie")) {
                medias.addAll(stream.getMovies());
            } else {
                medias.addAll(stream.getSeries());
            }

            if (!searchFilter.name.isBlank()) {
                for (int i = medias.size()-1; i >= 0 ; i--) {
                    if (!medias.get(i).getName().contains(searchFilter.name)) {
                        medias.remove(i);
                    }
                }
            }

            if (!searchFilter.genre.isEmpty()) {
                for (int i = medias.size()-1; i >= 0 ; i--) {
                    if (!medias.get(i).getGenre().contains(searchFilter.genre)) {
                        medias.remove(i);
                    }
                }
            }

            listModel.clear();
            listModel.addAll(medias);
        });

        bPlay.setEnabled(false);
        bSave.setEnabled(false);
        bViewMedia.setEnabled(false);

        mediaTypePanel.add(bMovies);
        mediaTypePanel.add(bSeries);
        contentPanel.add(mediaTypePanel);
        contentPanel.add(lName);
        contentPanel.add(name);
        contentPanel.add(genreSelect);
        contentPanel.add(bSearch);
        contentPanel.add(Box.createVerticalStrut(300));
        bPanel.add(bPlay);
        bPanel.add(bSave);
        bPanel.add(bViewMedia);
        showPanel.add(titlesSPanel);
        showPanel.add(bPanel);
        topPanel.add(bGoToMain);


        searchPanel.add(topPanel, NORTH);
        searchPanel.add(contentPanel, CENTER);
        searchPanel.add(showPanel, EAST);

        return searchPanel;
    }

    private void swapPanel(JPanel panel) {
        if (currentPanel != null) {
            frame.remove(currentPanel);
        }
        frame.getContentPane().add(currentPanel = panel, CENTER);
        frame.revalidate();
        frame.repaint();
    }

    private static class SearchFilter {

        public String type;
        public String genre;
        public String name;


    }

}