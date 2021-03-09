package test;
import javax.swing.*;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.Timer;
import java.util.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Random;



class Appointment {

    private Patient patient;
    private Physician physician;
    private LocalDateTime date;
    private int identifier;
    private Random rnd = new Random();


    Appointment(Patient _patient, Physician _physician, LocalDateTime _date) throws SQLException, ClassNotFoundException{
        this.patient = _patient;
        this.physician = _physician;
        this.date = _date;
        this.identifier = rnd.nextInt(2147483647);
        Databaseconnection databaseconnection = new Databaseconnection();
        databaseconnection.addAppointment(this);
    }

    Appointment(Patient _patient, Physician _physician, int year, int month, int day, int hour, int minute) throws SQLException, ClassNotFoundException{
        this.patient = _patient;
        this.physician = _physician;
        this.date = LocalDateTime.of(year,month,day,hour,minute);
        this.identifier = rnd.nextInt(2147483647);
        Databaseconnection databaseconnection = new Databaseconnection();
        databaseconnection.addAppointment(this);
    }

    Appointment(Patient _patient, Physician _physician, int year, int month, int day, int hour, int minute, int _identifier) throws SQLException,ClassNotFoundException {
        this.patient = _patient;
        this.physician = _physician;
        this.date = LocalDateTime.of(year,month,day,hour,minute);
        this.identifier = _identifier;


    }
    Appointment(Patient _patient, Physician _physician, LocalDateTime _date, int _identifier) throws SQLException,ClassNotFoundException{
        this.patient = _patient;
        this.physician = _physician;
        this.date = _date;
        this.identifier = _identifier;
    }

    public int getIdentifier() {
        return identifier;
    }

    public void raiseIdentifier(){
        this.identifier++;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
        Databaseconnection databaseconnection = new Databaseconnection();

    }


    public LocalDateTime getDate() {
        return date;
    }


    public void setTimeAndDate(LocalDateTime _date) {
        this.date = _date;

    }

    public Physician getPhysician() {
        return physician;
    }

    public void setPhysician(Physician physician) {
        this.physician = physician;
    }

    /**
     * reminds the user with entered variables at a certain time
     * @param y     = year
     * @param m     = month
     * @param d     = day
     * @param hh    = hour
     * @param mm    = minute
     * @param minutesBefore = Time before the appointment, where the reminder shall message the user
     */
    public static void reminder(int y, int m, int d, int hh, int mm, int minutesBefore) {
        final long ONE_MINUTE_IN_MILLIS=60000;  //milliSec
        Calendar reminderCalendar = new GregorianCalendar(y, m, d, hh, mm);
        long t= reminderCalendar.getTimeInMillis();
        Date afterAddingTenMin = new Date(t - (minutesBefore * ONE_MINUTE_IN_MILLIS));
        System.out.println("Your reminder Date is: "+ afterAddingTenMin);

        Timer timer = new Timer();
        TimerTask tt = new TimerTask() {

            public void run() {
                Date now = new Date();
                if (afterAddingTenMin.before(now)) {
                    JOptionPane.showMessageDialog(null,"Appointment in " + minutesBefore +" minutes. E-Mail has been sent as a reminder too.", "Appointment", JOptionPane.INFORMATION_MESSAGE);  // when timer true execute
                    timer.cancel(); // stop timer instantly
                }
            }
        };
        timer.schedule(tt, 1000, 1000);
    }

}
