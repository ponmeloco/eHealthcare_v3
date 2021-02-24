package test;
import java.util.*;
import java.util.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;


class timer {

    public void reminder(int y, int m, int d, int hh, int mm) {
        Calendar reminderCalendar = new GregorianCalendar(y, m, d, hh, mm);
        Date reminder = reminderCalendar.getTime();
        System.out.println("Your reminder Date is :"+ reminder);

        Timer timer = new Timer();
        TimerTask tt = new TimerTask() {

            public void run() {
                Date now = new Date();
                if (reminder.before(now)) {
                    System.out.print("APPOINTMENT");  // when timer true execute
                    timer.cancel(); // stop timer instantly
                }
            }
        };
        timer.schedule(tt, 1000, 1000);
    }
}





