package test;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;

public class MainInterface extends JFrame {

    private JPanel rootPanel;
    public JLabel _name;
    public JLabel _lastname;
    public JLabel _email;
    public JLabel _user;
    public JTabbedPane tabbedPane1;
    public JTable medicationTable;
    private JScrollPane scPane;
    private JPanel tab_specialization;
    private JPanel tab_appointment;
    private JPanel tab_search;
    private JPanel tab_rate;
    private JPanel tab_medication;
    private JPanel tab_settings;
    private JPanel tab_manage;


    public MainInterface(Patient _patient){

        commonInterface();
        _name.setText(_patient.getFirstName());
        _lastname.setText(_patient.getLastName());
        _email.setText(_patient.getEmailAddress());
        _user.setText(_patient.getUserType());
        tabbedPane1.remove(5);
        tabbedPane1.remove(4);

    }

    public MainInterface( Physician _physician){
        
        _lastname.setText(_physician.getLastName());
        _email.setText(_physician.getEmailAddress());
        _user.setText(_physician.getUserType());
       
        
        

    }

    public MainInterface(Admin _admin){

        commonInterface();
        _name.setText(_admin.getFirstName());
        _lastname.setText(_admin.getLastName());
        _email.setText(_admin.getEmailAddress());
        _user.setText(_admin.getUserType());
        tabbedPane1.remove(4);
        tabbedPane1.remove(3);
        tabbedPane1.remove(2);
        tabbedPane1.remove(1);
        tabbedPane1.remove(0);
    }

    private void createUIComponents() {

        Object[] columns = new Object[]{"Name Med.","Morgens","Mittags","Abends","Nachts"};
        Object[][] data = new Object[][]{{"Allopurinol 100mg","1","0","0","0"},{"Aspirin 100mg","0","1","0","0"},
                {"Bisoprolol 5mg","1","0","0","0"},{"Pantozol 40mg","1","0","0","0"}};
        medicationTable = new JTable(data, columns);
        medicationTable.getTableHeader().setReorderingAllowed(false);
    }

    private void commonInterface(){

        add(rootPanel);
        setTitle("eHealthcare");
        setSize(1280,720);
        rootPanel.setBackground(Color.lightGray);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}


