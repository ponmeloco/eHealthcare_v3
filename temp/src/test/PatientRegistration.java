package test;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.Arrays;


public class PatientRegistration implements ActionListener {


    private static JFrame registerFrame;

    private static JTextField patientRegistrationEmailAddress;
    private static JTextField patientRegistrationPhoneNumber;
    private static JTextField patientRegistrationTitle;
    private static JTextField patientRegistrationSurname;
    private static JTextField patientRegistrationLastName;
    private static JTextField patientRegistrationStreet;
    private static JTextField patientRegistrationHouseNumber;
    private static JTextField patientRegistrationPLZ;
    private static JTextField patientRegistrationCity;
    private static JPasswordField patientRegistrationPassword1;
    private static JPasswordField patientRegistrationPassword2;

    public static void userRegistration() {

        Login.loginFrame.setEnabled(false);

        JPanel registerPanel = new JPanel();
        registerPanel.setLayout(null);

        registerFrame = new JFrame();

        registerFrame.setSize(470, 550);
        registerFrame.setLocation(Login.dim.width / 2 - registerFrame.getSize().width / 2, Login.dim.height / 2 - registerFrame.getSize().height / 2);
        registerFrame.setResizable(false);
        registerFrame.setTitle("Register");
        registerFrame.setIconImage(Login.titleIcon.getImage());
        registerFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Login.loginFrame.setEnabled(true);
            }
        });


        JLabel userEmailAddress = new JLabel("Email Address:");
        userEmailAddress.setBounds(40, 50, 120, 25);
        registerPanel.add(userEmailAddress);
        patientRegistrationEmailAddress = new JTextField();
        patientRegistrationEmailAddress.setBounds(250, 50, 165, 25);
        registerPanel.add(patientRegistrationEmailAddress);



        JLabel userTitle = new JLabel("Title:");
        userTitle.setBounds(40, 110, 120, 25);
        registerPanel.add(userTitle);
        patientRegistrationTitle = new JTextField();
        patientRegistrationTitle.setBounds(250, 110, 165, 25);
        registerPanel.add(patientRegistrationTitle);

        JLabel userSurName = new JLabel("Name:");
        userSurName.setBounds(40, 140, 120, 25);
        registerPanel.add(userSurName);
        patientRegistrationSurname = new JTextField();
        patientRegistrationSurname.setBounds(250, 140, 165, 25);
        registerPanel.add(patientRegistrationSurname);

        JLabel userLastName = new JLabel("Last name:");
        userLastName.setBounds(40, 170, 120, 25);
        registerPanel.add(userLastName);
        patientRegistrationLastName = new JTextField();
        patientRegistrationLastName.setBounds(250, 170, 165, 25);
        registerPanel.add(patientRegistrationLastName);

        JLabel userPhoneNumber = new JLabel("Phone number:");
        userPhoneNumber.setBounds(40, 200, 120, 25);
        registerPanel.add(userPhoneNumber);
        patientRegistrationPhoneNumber = new JTextField();
        patientRegistrationPhoneNumber.setBounds(250, 200, 165, 25);
        registerPanel.add(patientRegistrationPhoneNumber);

        JLabel userStreet = new JLabel("Street:");
        userStreet.setBounds(40, 230, 120, 25);
        registerPanel.add(userStreet);
        patientRegistrationStreet = new JTextField();
        patientRegistrationStreet.setBounds(250, 230, 165, 25);
        registerPanel.add(patientRegistrationStreet);

        JLabel userHouseNumber = new JLabel("House number:");
        userHouseNumber.setBounds(40, 260, 120, 25);
        registerPanel.add(userHouseNumber);
        patientRegistrationHouseNumber = new JTextField();
        patientRegistrationHouseNumber.setBounds(250, 260, 165, 25);
        registerPanel.add(patientRegistrationHouseNumber);

        JLabel userPLZ = new JLabel("Postal code:");
        userPLZ.setBounds(40, 290, 120, 25);
        registerPanel.add(userPLZ);
        patientRegistrationPLZ = new JTextField();
        patientRegistrationPLZ.setBounds(250, 290, 165, 25);
        registerPanel.add(patientRegistrationPLZ);

        JLabel userCity = new JLabel("City:");
        userCity.setBounds(40, 320, 120, 25);
        registerPanel.add(userCity);
        patientRegistrationCity = new JTextField();
        patientRegistrationCity.setBounds(250, 320, 165, 25);
        registerPanel.add(patientRegistrationCity);

        JLabel userPassword = new JLabel("Password");
        userPassword.setBounds(40, 350, 120, 25);
        registerPanel.add(userPassword);
        patientRegistrationPassword1 = new JPasswordField();
        patientRegistrationPassword1.setBounds(250, 350, 165, 25);
        registerPanel.add(patientRegistrationPassword1);

        JLabel userPasswordValidation = new JLabel("Password again:");
        userPasswordValidation.setBounds(40, 380, 120, 25);
        registerPanel.add(userPasswordValidation);
        patientRegistrationPassword2 = new JPasswordField();
        patientRegistrationPassword2.setBounds(250, 380, 165, 25);
        registerPanel.add(patientRegistrationPassword2);

        JButton acceptRegisterButton = new JButton("Register");
        acceptRegisterButton.setBounds(20, 500, 100, 25);
        acceptRegisterButton.setLocation(registerFrame.getWidth() / 2 - acceptRegisterButton.getWidth() / 2 - 5,
                registerFrame.getHeight()-100);
        acceptRegisterButton.addActionListener(new PatientRegistration());


        registerFrame.add(acceptRegisterButton);
        registerFrame.add(registerPanel);
        registerFrame.setVisible(true);

    }



    @Override
    public void actionPerformed(ActionEvent e) {
        if( patientRegistrationSurname.getText().equals("") ||
                patientRegistrationLastName.getText().equals("") ||
                patientRegistrationEmailAddress.getText().equals("") ||
                patientRegistrationTitle.getText().equals("") ||
                patientRegistrationPhoneNumber.getText().equals("") ||
                patientRegistrationHouseNumber.getText().equals("") ||
                patientRegistrationStreet.getText().equals("") ||
                patientRegistrationPLZ.getText().equals("") ||
                patientRegistrationCity.getText().equals("") ||
                patientRegistrationPassword1.getPassword().length == 0 ||
                patientRegistrationPassword2.getPassword().length == 0) {
            JOptionPane.showMessageDialog(PatientRegistration.registerFrame, "Every field has to be filled out!");
        } else if(!Arrays.equals(patientRegistrationPassword1.getPassword(),patientRegistrationPassword2.getPassword())) {
            JOptionPane.showMessageDialog(PatientRegistration.registerFrame, "Passwords do not match!");
        } else {
            Patient patientRegister = new Patient(
                    patientRegistrationEmailAddress.getText(),
                    patientRegistrationSurname.getText(),
                    patientRegistrationLastName.getText(),
                    patientRegistrationCity.getText(),
                    patientRegistrationStreet.getText(),
                    patientRegistrationHouseNumber.getText(),
                    patientRegistrationPLZ.getText(),
                    patientRegistrationPhoneNumber.getText(),
                    patientRegistrationTitle.getText(),
                    String.valueOf(patientRegistrationPassword1.getPassword()));
            try {
                Databaseconnection databaseconnection = new Databaseconnection();
                databaseconnection.addUser(patientRegister);
                databaseconnection.displayUsers();
            } catch(SQLException | ClassNotFoundException penis){
                System.out.println(penis.getMessage());
            }
            JOptionPane.showMessageDialog(registerFrame, "Registration successful!\nYou are now registered");
            registerFrame.dispatchEvent(new WindowEvent(registerFrame, WindowEvent.WINDOW_CLOSING));
        }

    }

}