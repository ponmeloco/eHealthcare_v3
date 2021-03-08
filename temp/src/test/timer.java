package test;
import javax.swing.*;
import java.util.*;
import java.util.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Timer;


class timer {

    /**
     * Entered Year,Month,Hour,Minute will make a reminder displayed minutes before the entered minutesBefore int
     */
    public void reminder(int y, int m, int d, int hh, int mm, int minutesBefore) {
        final long ONE_MINUTE_IN_MILLIS=60000;  //milliSec
        Calendar reminderCalendar = new GregorianCalendar(y, m, d, hh, mm);
        long t= reminderCalendar.getTimeInMillis();
        Date afterSubtractingMin = new Date(t - (minutesBefore * ONE_MINUTE_IN_MILLIS));
        System.out.println("Your reminder Date is: "+ afterSubtractingMin);

        Timer timer = new Timer();
        TimerTask tt = new TimerTask() {

            public void run() {
                Date now = new Date();
                if (afterSubtractingMin.before(now)) {
                    JOptionPane.showMessageDialog(null,"Appointment in " + minutesBefore +" minutes. E-Mail has been sent as a reminder too" +
                            "", "Appointment", JOptionPane.INFORMATION_MESSAGE);  // when timer true execute
                    timer.cancel(); // stop timer instantly
                }
            }
        };
        timer.schedule(tt, 1000, 1000);
    }
}





