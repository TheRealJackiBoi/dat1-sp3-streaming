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
    private JPanel loginPanel;



    public JFrame createFrame() {

        if (frame != null)
            throw new IllegalArgumentException("Frame already created");


        frame = new JFrame("Streamy");
        JPanel contentPane = new JPanel(new BorderLayout());
        contentPane.add(createLoginPanel(), BorderLayout.CENTER);
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

        bCreate.addActionListener(e -> {

        });

        bLogin.addActionListener(e -> {

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



}
