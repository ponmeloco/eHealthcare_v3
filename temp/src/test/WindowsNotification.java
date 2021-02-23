package test;
import javax.swing.*;
import java.awt.*;
import java.awt.TrayIcon.MessageType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class WindowsNotification implements ActionListener{
    private static TrayIcon trayIcon;

    public static void showNotification(String caption, String text) throws AWTException {
        displayTray();
        trayIcon.displayMessage(caption, text, MessageType.INFO);
        trayIcon.addActionListener(e -> { System.out.println(JOptionPane.showConfirmDialog
                (null,"You successfully performed an action on the bubble!","Test Dialog",
                    JOptionPane.DEFAULT_OPTION)); //add action clicking notification bubble
        });

    }

    public static void displayTray() throws AWTException {
        //Obtain only one instance of the SystemTray object
        SystemTray tray = SystemTray.getSystemTray();

        //If the icon is a file
        Image image = Toolkit.getDefaultToolkit().createImage("icon.png");
        trayIcon = new TrayIcon(image, "eHealthcare");
        //Let the system resize the image if needed
        trayIcon.setImageAutoSize(true);
        //Set tooltip text for the tray icon
        trayIcon.setToolTip("eHealthcare");
        tray.add(trayIcon);


    }

}


