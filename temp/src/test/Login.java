package test;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.*;

public class Login {

    private static JTextField userText;
    private static JPasswordField passwordText;

    private static JButton loginButton;
    private static JLabel registerButton;
    private static JLabel registerPhysicianButton;
    protected static JFrame loginFrame = new JFrame();
    protected static Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
    protected static ImageIcon titleIcon;



    public static void loginFrame()  {


        loginFrame.setSize(450, 220);
        loginFrame.setTitle("eHealthcare Login");
        titleIcon = new ImageIcon("eHealthcareFrameIcon1.png");
        loginFrame.setIconImage(titleIcon.getImage());
        loginFrame.setResizable(false);
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setLocation(dim.width / 2 - loginFrame.getSize().width / 2, dim.height / 2 - loginFrame.getSize().height / 2);
        JPanel panel = new JPanel();
        panel.setBackground(Color.LIGHT_GRAY);
        panel.setLayout(null);

        JLabel userLabel = new JLabel("Username :");
        userLabel.setBounds(20, 20, 80, 25);
        panel.add(userLabel);

        JLabel passwordLabel = new JLabel("Password :");
        passwordLabel.setBounds(20, 50, 80, 25);
        panel.add(passwordLabel);


        userText = new JTextField();
        userText.setBounds(120, 20, 210, 25);
        panel.add(userText);

        passwordText = new JPasswordField();
        passwordText.setBounds(120, 50, 210, 25);
        panel.add(passwordText);

        loginButton = new JButton("Login");
        loginButton.setIcon(new ImageIcon(""));
        loginButton.setBounds(340,20 , 70, 54);
        loginButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Databaseconnection databaseconnection = new Databaseconnection();
                String DatabasePw = null;
                try {
                    DatabasePw = databaseconnection.getUserPw(userText.getText());
                } catch (SQLException | ClassNotFoundException sqlException) {
                    sqlException.printStackTrace();
                }
                if(DatabasePw == null) {
                    JOptionPane.showMessageDialog(loginFrame,"eMail-Address not found", "Error", JOptionPane.ERROR_MESSAGE);
                }else{
                    String password = String.valueOf(passwordText.getPassword());
                    int check = -1;
                    while (check == -1) {
                        if (e.getSource() == Login.loginButton) {
                            if (userText.getText().equals("") || password.equals("")) {
                                JOptionPane.showMessageDialog(loginFrame, "Fields cannot be empty! Please enter your data.");
                                break;
                            } else if ((org.mindrot.jbcrypt.BCrypt.checkpw(password,DatabasePw))) {
                                JOptionPane.showMessageDialog(loginFrame, "Login successful!");
                                check = 0;
                                loginFrame.dispose();
                            } else {
                                System.out.println(User.hashPassword(password));
                                JOptionPane.showMessageDialog(loginFrame, "Login unsuccessful!", "Error", JOptionPane.ERROR_MESSAGE);
                                break;
                            }
                        }
                    }

                }

            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        panel.add(loginButton);


        registerButton = new JLabel("Register");
        registerButton.setBounds(20, 135, 157, 25);
        registerButton.setForeground(Color.blue);
        registerButton.setLocation(loginFrame.getWidth() / 2 - (registerButton.getWidth() / 2)+ 50, passwordText.getY() + 50);
        registerButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                PatientRegistration.userRegistration();
            }

            @Override
            public void mousePressed(MouseEvent e) {


            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                registerButton.setForeground(Color.GRAY);

            }

            @Override
            public void mouseExited(MouseEvent e) {
                registerButton.setForeground(Color.BLUE);

            }
        });
        panel.add(registerButton);

        registerPhysicianButton = new JLabel("Register as Physician");
        registerPhysicianButton.setBounds(20, 135, 157, 25);
        registerPhysicianButton.setForeground(Color.blue);
        registerPhysicianButton.setLocation(loginFrame.getWidth() / 2 - (registerPhysicianButton.getWidth() / 2)+10, registerButton.getY() + 25);
        registerPhysicianButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                PhysicianRegistration.userRegistration();
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                registerPhysicianButton.setForeground(Color.GRAY);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                registerPhysicianButton.setForeground(Color.BLUE);
            }
        });
        panel.add(registerPhysicianButton);


        loginFrame.add(panel);
        loginFrame.setVisible(true);
    }



}
