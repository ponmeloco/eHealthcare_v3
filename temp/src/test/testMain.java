package test;

import java.io.IOException;
import java.sql.SQLException;

import java.text.ParseException;
import java.time.LocalDateTime;



public class testMain {

    public static void main(String[] args){
/*
        Databaseconnection databaseconnection = new Databaseconnection();

        //test database!
        //test patientFunctions

        try{
            Patient patient = databaseconnection.getPatient("Patient");
            Patient test = new Patient("testmail", "Tessy",  "Test", "Berlin","Hauptstraße",  "1",  "60001",  "112",  "Dr.", "_password",
                    "2020-01-01",  "AOK", patient.getSymptoms(), databaseconnection.getMedication("Patient"), 12, new LatLong(50.22222,8.88888));


            databaseconnection.addUser(test);
            Patient test2 = databaseconnection.getPatient("testmail");
            test2.setLastName("Penis");
            databaseconnection.updateUser(test2); //this still needs work
            test2 = databaseconnection.getPatient("testmail");
            System.out.println(test2.getLastName());

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        try{
            Physician physician = databaseconnection.getPhysician("Doctor");
            Physician test = new Physician("dtestmail", "Bruce", "Banner", "Berlin", "Hauptstraße", "2", "60001", "110", "Dr.", "abcd", physician.getSpecialization(), new LatLong(50.00000,8.00000));
            databaseconnection.addUser(test);
            Physician test2 = databaseconnection.getPhysician(test.getEmailAddress());
            test2.setLastName("Penis");
            databaseconnection.updateUser(test2); //SQLERROR
            test2 = databaseconnection.getPhysician(test2.getEmailAddress());
            System.out.println(test2.getLastName());

        }catch(Exception e){
            System.out.println(e.getMessage());
        }





        try{
           Physician[] physicians = databaseconnection.getAllPhysicians();
           String[] test = physicians[0].getSpecialization();
            System.out.println(test[0]);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        try{
            Patient[] patients = databaseconnection.getAllPatients();
            Medication[] test = patients[0].getMedications();
            System.out.println(test[0].getDrug().getDescription());
        }catch(Exception e){
            System.out.println(e.getMessage());
        }


 //ERROR
        try{
            Admin testadmin = new Admin("testadmin@gmx.de","Gabe","Newell", "Auckland", "Dominion Road", "1", "1041"
                    , "425-889-9642", "Our Lord" ,"penis", new LatLong(-36.87087339448538, 174.7523596984722));
            databaseconnection.addUser(testadmin);
            testadmin.setPhoneNUmber("0800 5 46 46");
            databaseconnection.updateUser(testadmin);
            Admin testadmin2 = databaseconnection.getAdmin(testadmin.getEmailAddress());
            System.out.println(testadmin2.getPhoneNUmber());
        }catch(Exception e){
            System.out.println(e.getMessage());
        }

        try{
            Drug testdrug = databaseconnection.getDrug("Ibuprofen");
            System.out.println(testdrug.getDescription());
        }catch(Exception e){
            System.out.println(e.getMessage());
        }



        try{
            Appointment[] allAppointments = databaseconnection.getAllAppointments();
            System.out.println(allAppointments[0].getDate() + allAppointments[0].getPatient().getLastName());
        }catch (Exception e){
            System.out.println(e.getMessage());
        }


        try{
            Appointment appointment = new Appointment(databaseconnection.getPatient(1), databaseconnection.getPhysician(2), LocalDateTime.of(2021,2,1,18,30));
            databaseconnection.addAppointment(appointment);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }

        try{
            Appointment[] test = databaseconnection.getAppointment("Doctor");
            System.out.println(test[0].getPatient().getLastName());
            System.out.println(test[0].getPhysician().getSpecialization()[0]);
            System.out.println(test[1].getDate());

        }catch(Exception e){
            System.out.println(e.getMessage());
        }

      */

        //END test database

        //Appointment.reminder(2021,1,24,13,4,0);





        LoginGUI guiMain = new LoginGUI();
        guiMain.setVisible(true);





    }
}
