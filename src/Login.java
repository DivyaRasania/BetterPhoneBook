import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import java.util.List;

/**
 * A login screen for the PhoneBook application.
 *
 * @author Divya Rasania, David Komiyama
 */
public class Login extends JFrame implements ActionListener {
    private static final String DEFAULT_USERNAME = "admin";
    private static final String DEFAULT_PASSWORD = "admin";

    private JLabel welcomeLabel, welcomeLabel2, usernameLabel, passwordLabel, iconLabel;
    private JTextField usernameTextField;
    private JPasswordField passwordTextField;
    private JPanel loginPanel, headingPanel, iconPanel;
    private JButton loginButton, cancelButton;
    private Font headingFont30, headingFont35, regularFont;

    public Login() {
        initializeComponents();
        layoutComponents();
        addListeners();
    }

    private void initializeComponents() {
        welcomeLabel = new JLabel("Welcome to PhoneBook");
        welcomeLabel2 = new JLabel("Please login to proceed");
        usernameLabel = new JLabel("Username");
        passwordLabel = new JLabel("Password");
        iconLabel = new JLabel(new ImageIcon(ClassLoader.getSystemResource("resources/icons/user.png")));
        usernameTextField = new JTextField();
        passwordTextField = new JPasswordField();
        loginPanel = new JPanel();
        headingPanel = new JPanel();
        iconPanel = new JPanel();
        loginButton = new JButton("Login");
        cancelButton = new JButton("Cancel & Exit");

        List<URL> customFonts = List.of(
                ClassLoader.getSystemResource("resources/fonts/MirandaSans/MirandaSans-Bold.ttf"),
                ClassLoader.getSystemResource("resources/fonts/MirandaSans/MirandaSans-Regular.ttf")
        );
        loadCustomFonts(customFonts);

        headingFont30 = new Font("Miranda Sans", Font.BOLD, 30);
        headingFont35 = new Font("Miranda Sans", Font.BOLD, 35);
        regularFont = new Font("Miranda Sans", Font.PLAIN, 20);
    }

    private void loadCustomFonts(List<URL> fontFiles) {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        List<String> availableFonts = List.of(ge.getAvailableFontFamilyNames());

        for (URL url : fontFiles) {
            if (url != null) {
                try {
                    Font f = Font.createFont(Font.TRUETYPE_FONT, url.openStream());
                    if (!availableFonts.contains(f.getFontName()))
                        ge.registerFont(f);
                } catch (FontFormatException | IOException e) {
                    JOptionPane.showMessageDialog(null, e.getMessage());
                }
            }
        }
    }

    private void layoutComponents() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setIconImage(new ImageIcon(ClassLoader.getSystemResource("resources/icons/login.png")).getImage());
        setTitle("Login");
        setLayout(new BorderLayout(10, 20));

        welcomeLabel.setFont(headingFont35);
        welcomeLabel2.setFont(headingFont30);
        usernameLabel.setFont(regularFont);
        usernameTextField.setFont(regularFont);
        passwordLabel.setFont(regularFont);
        passwordTextField.setFont(regularFont);
        loginButton.setFont(regularFont);
        cancelButton.setFont(regularFont);

        loginButton.setPreferredSize(new Dimension(200, 40));
        cancelButton.setPreferredSize(new Dimension(200, 40));

        // Heading panel
        headingPanel.setLayout(new GridLayout(2, 1, 10, 10));
        headingPanel.add(welcomeLabel);
        headingPanel.add(welcomeLabel2);

        // Icon panel
        iconPanel.add(iconLabel);

        // Login panel
        loginPanel.setLayout(new GridLayout(3, 2, 10, 10));
        loginPanel.add(usernameLabel);
        loginPanel.add(usernameTextField);
        loginPanel.add(passwordLabel);
        loginPanel.add(passwordTextField);
        loginPanel.add(loginButton);
        loginPanel.add(cancelButton);

        // Add panels to the main frame
        ((JPanel) getContentPane()).setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20)); // padding for all sides so text dont stick to edges
        add(headingPanel, BorderLayout.NORTH);
        add(iconPanel, BorderLayout.EAST);
        add(loginPanel, BorderLayout.CENTER);
        pack();
    }

    private void addListeners() {
        loginButton.addActionListener(this);
        cancelButton.addActionListener(this);
        passwordTextField.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton || e.getSource() == passwordTextField) {
            // Get the username and password from the text fields
            String username = usernameTextField.getText();
            String password = passwordTextField.getText();

            // Validate the username and password
            if (username.equals(DEFAULT_USERNAME) && password.equals(DEFAULT_PASSWORD)) {
                // Login successful
                this.setVisible(false);
                new Contacts().setVisible(true);
            } else {
                // Login failed
                JOptionPane.showMessageDialog(null, "Invalid username or password.");
            }
        } else if (e.getSource() == cancelButton) {
            // Exit the application
            this.dispose();
        }
    }
}