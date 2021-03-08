package test;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.Year;

//source https://dev.mysql.com/doc/connector-j/5.1/en/connector-j-usagenotes-connect-drivermanager.html
//source https://www.youtube.com/watch?v=JPsWaI5Z3gs

/**
 * Class to establish and connect to a database persistently saving the data provided by the Software.
 * @author Archy
 */
class Databaseconnection {
    private static Connection connection;
    private static boolean hasData = false;

    public void displayUsers() throws SQLException, ClassNotFoundException {
        if (connection == null) {
            connect();
        }
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT firstName, lastName FROM User;");
        while (resultSet.next()) {
            System.out.println(resultSet.getString(1) + " " + resultSet.getString(2));
        }
    }

    /**
     * Fetches the hashedpassword from an user account.
     * @param email The registered emailaddress of the User.
     * @return The hashed passwort saved in the database.
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public String getUserPw(String email) throws SQLException, ClassNotFoundException {
        if(connection == null){
            connect();
        }
        Statement statement = connection.createStatement();
        return statement.executeQuery("SELECT password FROM User WHERE emailAddress ='" + email + "';").getString(1);
    }

    /**
     * Save all relevant attributes of a patient not yet within the database.
     * @param patient The Instance of the Patient class resembling the Information about the User.
     * @throws SQLException
     * @throws ClassNotFoundException
     * @author Archy
     */
    public void addUser(Patient patient) throws SQLException, ClassNotFoundException {
        if (connection == null) {
            connect();
        }
        Statement statement = connection.createStatement();
        ResultSet checkIfNew = statement.executeQuery("SELECT * FROM User WHERE emailAddress='" + patient.getEmailAddress() + "'");

        if (!checkIfNew.next()) {

            //Insert Data into Usertable
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO User (emailAddress, password, firstName, lastName, city, street, houseNumber, postalCode, phoneNumber, title, longitude, latitude) VALUES (?,?,?,?,?,?,?,?,?,?,?,?);");
            preparedStatement.setString(1, patient.getEmailAddress());
            preparedStatement.setString(2, patient.getPasswordhash());
            preparedStatement.setString(3, patient.getFirstName());
            preparedStatement.setString(4, patient.getLastName());
            preparedStatement.setString(5, patient.getCity());
            preparedStatement.setString(6, patient.getStreet());
            preparedStatement.setString(7, patient.getHouseNumber());
            preparedStatement.setString(8, patient.getPostalCode());
            preparedStatement.setString(9, patient.getPhoneNUmber());
            preparedStatement.setString(10, patient.getTitle());
            preparedStatement.setDouble (11, patient.getGeolocation().getLongitude());
            preparedStatement.setDouble(12, patient.getGeolocation().getLatitude());
            preparedStatement.execute();


            //Insert Data into Patienttable
            ResultSet res = statement.executeQuery("SELECT ID FROM User WHERE emailAddress='" + patient.getEmailAddress() + "';");
            int patientID = res.getInt(1);
            res = statement.executeQuery("SELECT ID FROM Insurance WHERE name='"+ patient.getInsuranceName() +"';");
            int insuranceID = res.getInt(1);
            statement.execute("INSERT INTO Patient (ID,InsuranceID, dateOfBirth, weight) VALUES (" + patientID + "," +  insuranceID + "," + patient.getBirthday() + "," + patient.getWeight() +");");

            //Insert Data into SymptomPatient
            int symptomID;
            int severenessID;

            for(int i = 0; i < patient.getSymptoms().length; i++){
                res = statement.executeQuery("SELECT ID FROM Symptom WHERE name='" + patient.getSymptoms()[i].getName() + "';");
                symptomID = res.getInt(1);
                res = statement.executeQuery("SELECT ID FROM Severeness WHERE severeness='" + patient.getSymptoms()[i].getSevereness() + "';");
                severenessID = res.getInt(1);
                statement.execute("INSERT INTO SymptomPatient (PatientID, SymptomID, SeverenessID) VALUES (" + patientID + "," +  symptomID + "," + severenessID + ");");
            }
        }else{
            throw new SQLException("User already registered.");
        }
    }

    /**
     * Save all relevant attributes of a physician not yet within the database.
     * @param physician The Instance of the Physician class resembling the Information about the User.
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public void addUser(Physician physician) throws SQLException, ClassNotFoundException{
        if(connection == null){
            connect();
        }
        int ID;
        int specID;
        Statement statement = connection.createStatement();
        ResultSet checkIfNew = statement.executeQuery("SELECT * FROM User WHERE emailAddress='"+physician.getEmailAddress()+"';");

        if(!checkIfNew.next()) {
            /*Insert into user table*/
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO User(emailAddress, password, firstName, lastName, city, street, houseNumber, postalCode, phoneNumber, title,longitude,latitude) VALUES (?,?,?,?,?,?,?,?,?,?,?,?);");
            preparedStatement.setString(1, physician.getEmailAddress());
            preparedStatement.setString(2, physician.getPasswordhash());
            preparedStatement.setString(3, physician.getFirstName());
            preparedStatement.setString(4, physician.getLastName());
            preparedStatement.setString(5, physician.getCity());
            preparedStatement.setString(6, physician.getStreet());
            preparedStatement.setString(7, physician.getHouseNumber());
            preparedStatement.setString(8, physician.getPostalCode());
            preparedStatement.setString(9, physician.getPhoneNUmber());
            preparedStatement.setString(10, physician.getTitle());
            preparedStatement.setDouble(11, physician.getGeolocation().getLongitude());
            preparedStatement.setDouble(12, physician.getGeolocation().getLatitude());
            preparedStatement.execute();

            /* Insert Physician and SpezializationPhysisician  table*/

            ResultSet res = statement.executeQuery("SELECT ID FROM User WHERE emailAddress='" + physician.getEmailAddress() + "';");
            ID = res.getInt(1);
            statement.execute("INSERT INTO Physician VALUES (" + ID + ")");
            res = statement.executeQuery("SELECT ID FROM Specialization WHERE Specialization='" + physician.getSpecialization()[1] + "';");
            specID = res.getInt(1);
            statement.execute("INSERT INTO SpecializationPhysician(PhysicianID, SpecializationID) VALUES (" + ID + "," + specID + ");");
        }else{
            throw new SQLException("User already registered.");
        }
    }

    /**
     * Save all relevant attributes of a admin not yet within the database.
     * @param admin The Instance of the Admin class resembling the Information about the User.
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public void addUser(Admin admin) throws SQLException, ClassNotFoundException {
        if (connection == null) {
            connect();
        }
        Statement statement = connection.createStatement();
        ResultSet checkIfNew = statement.executeQuery("SELECT * FROM USER WHERE emailAddress='" + admin.getEmailAddress() + "')");

        if (!checkIfNew.next()) {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO User(emailAddress, password, firstName, lastName, city, street, houseNumber, postalCode, phoneNumber, title,longitude,latitude) VALUES (?,?,?,?,?,?,?,?,?,?);");
            preparedStatement.setString(1, admin.getEmailAddress());
            preparedStatement.setString(2, admin.getPasswordhash());
            preparedStatement.setString(3, admin.getFirstName());
            preparedStatement.setString(4, admin.getLastName());
            preparedStatement.setString(5, admin.getCity());
            preparedStatement.setString(6, admin.getStreet());
            preparedStatement.setString(7, admin.getHouseNumber());
            preparedStatement.setString(8, admin.getPostalCode());
            preparedStatement.setString(9, admin.getPhoneNUmber());
            preparedStatement.setString(10, admin.getTitle());
            preparedStatement.setDouble (11, admin.getGeolocation().getLongitude());
            preparedStatement.setDouble(12, admin.getGeolocation().getLatitude());
            preparedStatement.execute();


            ResultSet res = statement.executeQuery("SELECT ID FROM User WHERE emailAddress='" + admin.getEmailAddress() + "';");
            int ID = res.getInt(1);
            statement.execute("INSERT INTO Admin VALUES (" + ID + ")");
        }
    }

    /**
     * Update a patient already existing within the database.
     * @param patient The Instance of the Patient class resembling the Information about the User.
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public void updateUser(Patient patient) throws SQLException, ClassNotFoundException{
        if (connection == null) {
            connect();
        }
        Statement statement = connection.createStatement();
        ResultSet checkIfNew = statement.executeQuery("SELECT * FROM User WHERE emailAddress='" + patient.getEmailAddress() + "'");

        if(checkIfNew.next()){

            //update User Table
            try {
                statement.execute("UPDATE User SET lastName = '"+patient.getLastName()+"' WHERE emailAddress='" + patient.getEmailAddress() + "'");
            }catch(Exception e) {
                System.out.println(e.getMessage());
            }
            try {
                statement.execute("UPDATE User SET firstName = '"+patient.getFirstName()+"' WHERE emailAddress='" + patient.getEmailAddress() + "'");
            }catch(Exception e) {
                System.out.println(e.getMessage());
            }
            try {
                statement.execute("UPDATE User SET lastName = '"+patient.getLastName()+"' WHERE emailAddress='" + patient.getEmailAddress() + "'");
            }catch(Exception e) {
                System.out.println(e.getMessage());
            }
            try {
                statement.execute("UPDATE User SET city = '"+patient.getCity()+"' WHERE emailAddress='" + patient.getEmailAddress() + "'");
            }catch(Exception e) {
                System.out.println(e.getMessage());
            }
            try {
                statement.execute("UPDATE User SET houseNumber = '"+patient.getHouseNumber()+"' WHERE emailAddress='" + patient.getEmailAddress() + "'");
            }catch(Exception e) {
                System.out.println(e.getMessage());
            }
            try {
                statement.execute("UPDATE User SET street = '"+patient.getStreet()+"' WHERE emailAddress='" + patient.getEmailAddress() + "'");
            }catch(Exception e) {
                System.out.println(e.getMessage());
            }
            try {
                statement.execute("UPDATE User SET postalCode = '"+patient.getPostalCode()+"' WHERE emailAddress='" + patient.getEmailAddress() + "'");
            }catch(Exception e) {
                System.out.println(e.getMessage());
            }
            try {
                statement.execute("UPDATE User SET phoneNumber = '"+patient.getPhoneNUmber()+"' WHERE emailAddress='" + patient.getEmailAddress() + "'");
            }catch(Exception e) {
                System.out.println(e.getMessage());
            }
            try {
                statement.execute("UPDATE User SET title = '"+patient.getTitle()+"' WHERE emailAddress='" + patient.getEmailAddress() + "'");
            }catch(Exception e) {
                System.out.println(e.getMessage());
            }

            try {
                statement.execute("UPDATE User SET latitude = '"+ patient.getGeolocation().getLatitude() +"' WHERE emailAddress='" + patient.getEmailAddress() + "'");
            }catch(Exception e) {
                System.out.println(e.getMessage());
            }

            try {
                statement.execute("UPDATE User SET longitude = '"+ patient.getGeolocation().getLongitude() +"' WHERE emailAddress='" + patient.getEmailAddress() + "'");
            }catch(Exception e) {
                System.out.println(e.getMessage());
            }

            //Update patient table
            try {
                statement.execute("UPDATE Patient SET dateOfBirth = '"+patient.getBirthday()+"' WHERE ID = (SELECT ID FROM User WHERE emailAddress='"+patient.getEmailAddress()+"');");
            }catch(Exception e) {
                System.out.println(e.getMessage());
            }
            try {
                statement.execute("UPDATE Patient SET weight = '"+patient.getWeight()+"' WHERE ID = (SELECT ID FROM User WHERE emailAddress='"+patient.getEmailAddress()+"');");
            }catch(Exception e) {
                System.out.println(e.getMessage());
            }
            try {
                statement.execute("UPDATE Patient SET insuranceID = (SELECT ID FROM Insurance WHERE name ='"+patient.getInsuranceName()+"')  WHERE ID = (SELECT ID FROM User WHERE emailAddress='"+patient.getEmailAddress()+"');");
            }catch(Exception e) {
                System.out.println(e.getMessage());
            }

            setSymptoms(patient.getEmailAddress(), patient.getSymptoms());
            setMedications(patient.getEmailAddress(), patient.getMedications());

        }else{
            throw new SQLException("User not registered yet. If you tried to change the Emailadress please note, \n  this address is used to uniquely identify each user.\n Please use the method changeEmail not updateuser.");
        }
    }

    /**
     * Updates a physician already existing within the database.
     * @param physician The Instance of the Physician class resembling the Information about the User.
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public void updateUser(Physician physician) throws SQLException, ClassNotFoundException{
        if (connection == null) {
            connect();
        }
        Statement statement = connection.createStatement();
        ResultSet checkIfNew = statement.executeQuery("SELECT * FROM User WHERE emailAddress='" + physician.getEmailAddress() + "'");

        if(checkIfNew.next()){

            //update User Table
            try {
                statement.execute("UPDATE User SET lastName = '"+physician.getLastName()+"' WHERE emailAddress='" + physician.getEmailAddress() + "'");
            }catch(Exception e) {
                System.out.println(e.getMessage());
            }
            try {
                statement.execute("UPDATE User SET firstName = '"+physician.getFirstName()+"' WHERE emailAddress='" + physician.getEmailAddress() + "'");
            }catch(Exception e) {
                System.out.println(e.getMessage());
            }
            try {
                statement.execute("UPDATE User SET lastName = '"+physician.getLastName()+"' WHERE emailAddress='" + physician.getEmailAddress() + "'");
            }catch(Exception e) {
                System.out.println(e.getMessage());
            }
            try {
                statement.execute("UPDATE User SET city = '"+physician.getCity()+"' WHERE emailAddress='" + physician.getEmailAddress() + "'");
            }catch(Exception e) {
                System.out.println(e.getMessage());
            }
            try {
                statement.execute("UPDATE User SET houseNumber = '"+physician.getHouseNumber()+"' WHERE emailAddress='" + physician.getEmailAddress() + "'");
            }catch(Exception e) {
                System.out.println(e.getMessage());
            }
            try {
                statement.execute("UPDATE User SET street = '"+physician.getStreet()+"' WHERE emailAddress='" + physician.getEmailAddress() + "'");
            }catch(Exception e) {
                System.out.println(e.getMessage());
            }
            try {
                statement.execute("UPDATE User SET postalCode = '"+physician.getPostalCode()+"' WHERE emailAddress='" + physician.getEmailAddress() + "'");
            }catch(Exception e) {
                System.out.println(e.getMessage());
            }
            try {
                statement.execute("UPDATE User SET phoneNumber = '"+physician.getPhoneNUmber()+"' WHERE emailAddress='" + physician.getEmailAddress() + "'");
            }catch(Exception e) {
                System.out.println(e.getMessage());
            }
            try {
                statement.execute("UPDATE User SET title = '"+physician.getTitle()+"' WHERE emailAddress='" + physician.getEmailAddress() + "'");
            }catch(Exception e) {
                System.out.println(e.getMessage());
            }

            try {
                statement.execute("UPDATE User SET latitude = '"+ physician.getGeolocation().getLatitude() +"' WHERE emailAddress='" + physician.getEmailAddress() + "'");
            }catch(Exception e) {
                System.out.println(e.getMessage());
            }

            try {
                statement.execute("UPDATE User SET longitude = '"+ physician.getGeolocation().getLongitude() +"' WHERE emailAddress='" + physician.getEmailAddress() + "'");
            }catch(Exception e) {
                System.out.println(e.getMessage());
            }

            setSpecialization(physician.getEmailAddress(), physician.getSpecialization());

        }else{
            throw new SQLException("User not registered yet. If you tried to change the Emailadress please note, \n  this address is used to uniquely identify each user.\n Please use the method changeEmail not updateuser.");
        }
    }

    /**
     * Updates an admin already existing within the database.
     * @param admin The Instance of the Admin class resembling the Information about the User.
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public void updateUser(Admin admin) throws SQLException, ClassNotFoundException{
        if (connection == null) {
            connect();
        }
        Statement statement = connection.createStatement();
        ResultSet checkIfNew = statement.executeQuery("SELECT * FROM User WHERE emailAddress='" + admin.getEmailAddress() + "'");

        if(checkIfNew.next()){

            //update User Table
            try {
                statement.execute("UPDATE User SET lastName = '"+admin.getLastName()+"' WHERE emailAddress='" + admin.getEmailAddress() + "'");
            }catch(Exception e) {
                System.out.println(e.getMessage());
            }
            try {
                statement.execute("UPDATE User SET firstName = '"+admin.getFirstName()+"' WHERE emailAddress='" + admin.getEmailAddress() + "'");
            }catch(Exception e) {
                System.out.println(e.getMessage());
            }
            try {
                statement.execute("UPDATE User SET lastName = '"+admin.getLastName()+"' WHERE emailAddress='" + admin.getEmailAddress() + "'");
            }catch(Exception e) {
                System.out.println(e.getMessage());
            }
            try {
                statement.execute("UPDATE User SET city = '"+admin.getCity()+"' WHERE emailAddress='" + admin.getEmailAddress() + "'");
            }catch(Exception e) {
                System.out.println(e.getMessage());
            }
            try {
                statement.execute("UPDATE User SET houseNumber = '"+admin.getHouseNumber()+"' WHERE emailAddress='" + admin.getEmailAddress() + "'");
            }catch(Exception e) {
                System.out.println(e.getMessage());
            }
            try {
                statement.execute("UPDATE User SET street = '"+admin.getStreet()+"' WHERE emailAddress='" + admin.getEmailAddress() + "'");
            }catch(Exception e) {
                System.out.println(e.getMessage());
            }
            try {
                statement.execute("UPDATE User SET postalCode = '"+admin.getPostalCode()+"' WHERE emailAddress='" + admin.getEmailAddress() + "'");
            }catch(Exception e) {
                System.out.println(e.getMessage());
            }
            try {
                statement.execute("UPDATE User SET phoneNumber = '"+admin.getPhoneNUmber()+"' WHERE emailAddress='" + admin.getEmailAddress() + "'");
            }catch(Exception e) {
                System.out.println(e.getMessage());
            }
            try {
                statement.execute("UPDATE User SET title = '"+admin.getTitle()+"' WHERE emailAddress='" + admin.getEmailAddress() + "'");
            }catch(Exception e) {
                System.out.println(e.getMessage());
            }
            try {
                statement.execute("UPDATE User SET latitude = '"+ admin.getGeolocation().getLatitude() +"' WHERE emailAddress='" + admin.getEmailAddress() + "'");
            }catch(Exception e) {
                System.out.println(e.getMessage());
            }

            try {
                statement.execute("UPDATE User SET longitude = '"+ admin.getGeolocation().getLongitude() +"' WHERE emailAddress='" + admin.getEmailAddress() + "'");
            }catch(Exception e) {
                System.out.println(e.getMessage());
            }

        }else{
            throw new SQLException("User not registered yet. If you tried to change the Emailadress please note, \n  this address is used to uniquely identify each user.\n Please use the method changeEmail not updateuser.");
        }
    }

    /**
     * Fetches an instance of the Patient class from the database uniquely identified by its email.
     * @param email The registered emailaddress of the User.
     * @return Instance of a Patient from the database
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public Patient      getPatient(String email) throws SQLException, ClassNotFoundException{
        if(connection == null){
            connect();
        }

        Statement statement = connection.createStatement();
        ResultSet res = statement.executeQuery("SELECT u.*,p.DateOfBirth,p.weight,i.name FROM User AS u JOIN (Patient AS p JOIN Insurance as i ON p.InsuranceID = i.ID) ON u.ID = p.ID WHERE emailAddress ='" + email + "';");

        String patientID =          res.getString(1);
        String pwhash =             res.getString(3);
        String firstName =          res.getString(4);
        String lastName =           res.getString(5);
        String city =                   res.getString(6);
        String street =                 res.getString(7);
        String houseNumber =      res.getString(8);
        String postalCode =         res.getString(9);
        String phoneNumber =     res.getString(10);
        String title =                  res.getString(11);
        String dateOfBirth =        res.getString(12);
        LatLong latlong =           new LatLong(res.getDouble(14), res.getDouble(13));
        int weight =                       res.getInt(15);
        String insurancename =        res.getString(16);
        Medication[] medications =  getMedication(email);
        Symptom[] symptoms =        getSymptoms(email);


        return new Patient(email,firstName,lastName,city,street,houseNumber,postalCode,
                phoneNumber, title, pwhash, dateOfBirth, insurancename, symptoms, medications, weight, latlong);
    }

    /**
     * Fetches an instance of the Physician class from the database uniquely identified by its email.
     * @param email The registered emailaddress of the User.
     * @return  Instance of a Physician from the database
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public Physician    getPhysician(String email) throws SQLException, ClassNotFoundException{
        if(connection == null){
            connect();
        }
        Statement statement = connection.createStatement();
        Statement statement2 = connection.createStatement();
        ResultSet res = statement.executeQuery("SELECT User.*, Physician.ID FROM (User JOIN Physician ON User.ID = Physician.ID) WHERE emailAddress ='" + email + "';");
        int PhysicianID;

        if (res.next()) {
            PhysicianID =               res.getInt(1);
            String pwhash =             res.getString(3);
            String firstName =          res.getString(4);
            String lastName =           res.getString(5);
            String city =               res.getString(6);
            String street =             res.getString(7);
            String houseNumber =        res.getString(8);
            String postalCode =         res.getString(9);
            String phoneNumber =        res.getString(10);
            String title =              res.getString(11);
            LatLong latlong =           new LatLong(res.getDouble(13), res.getDouble(12));
            String[] specialization =   getSpecialization(email);


            return new Physician(email, firstName, lastName, city, street, houseNumber, postalCode,
                    phoneNumber, title, pwhash, specialization, latlong);

        }else{
            throw new SQLException("User not found");
        }
    }

    /**
     * Fetches an instance of the Admin class from the database uniquely identified by its email.
     * @param email The registered emailaddress of the User.
     * @return Instance of an Admin from the database
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public Admin        getAdmin(String email) throws SQLException, ClassNotFoundException{
        if(connection == null){
            connect();
        }
        Statement statement = connection.createStatement();
        ResultSet res = statement.executeQuery("SELECT User.* FROM User WHERE emailAddress ='" + email + "';");

        String pwhash =         res.getString(3);
        String firstName =      res.getString(4);
        String lastName =       res.getString(5);
        String city =           res.getString(6);
        String street =         res.getString(7);
        String houseNumber =    res.getString(8);
        String postalCode =     res.getString(9);
        String phoneNumber =    res.getString(10);
        String title =          res.getString(11);
        LatLong latlong =           new LatLong(res.getDouble(13), res.getDouble(12));

        return new Admin(email,firstName,lastName,city,street,houseNumber,postalCode,
                phoneNumber, title, pwhash, latlong);
    }

    /**
     * Fetches an instance of the Patient class from the database uniquely identified by its UserID.
     * @param patientID The UserID of the patient within the database.
     * @return Instance of a Patient from the database
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public Patient      getPatient(int patientID) throws SQLException, ClassNotFoundException{
        if(connection == null){
            connect();
        }

        Statement statement = connection.createStatement();
        ResultSet res = statement.executeQuery("SELECT u.*,p.DateOfBirth,p.weight,i.name FROM User AS u JOIN (Patient AS p JOIN Insurance as i ON p.InsuranceID = i.ID) ON u.ID = p.ID WHERE u.ID ='" + patientID + "';");

        //String patientID =          res.getString(1);
        String email =              res.getString(2);
        String pwhash =             res.getString(3);
        String firstName =          res.getString(4);
        String lastName =           res.getString(5);
        String city =               res.getString(6);
        String street =             res.getString(7);
        String houseNumber =        res.getString(8);
        String postalCode =         res.getString(9);
        String phoneNumber =        res.getString(10);
        String title =              res.getString(11);
        String dateOfBirth =        res.getString(12);
        int weight =                res.getInt(13);
        LatLong latlong =           new LatLong(res.getDouble(14), res.getDouble(13));
        String insurancename =      res.getString(14);
        Medication[] medications =  getMedication(email);
        Symptom[] symptoms =        getSymptoms(email);


        return new Patient(email,firstName,lastName,city,street,houseNumber,postalCode,
                phoneNumber, title, pwhash, dateOfBirth, insurancename, symptoms, medications, weight,latlong);
    }

    /**
     * Fetches an instance of the Physician class from the database uniquely identified by its UserID.
     * @param physicianID the UserID of the physician within the database.
     * @return Instance of a Phyisician from the database.
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public Physician    getPhysician(int physicianID) throws SQLException, ClassNotFoundException{
        if(connection == null){
            connect();
        }
        Statement statement = connection.createStatement();
        Statement statement2 = connection.createStatement();
        ResultSet res = statement.executeQuery("SELECT User.*, Physician.ID FROM (User JOIN Physician ON User.ID = Physician.ID) WHERE User.ID ='" + physicianID + "';");


        if (res.next()) {
            //PhysicianID =               res.getInt(1);
            String email =              res.getString(2);
            String pwhash =             res.getString(3);
            String firstName =          res.getString(4);
            String lastName =           res.getString(5);
            String city =               res.getString(6);
            String street =             res.getString(7);
            String houseNumber =        res.getString(8);
            String postalCode =         res.getString(9);
            String phoneNumber =        res.getString(10);
            String title =              res.getString(11);
            LatLong latlong =           new LatLong(res.getDouble(13), res.getDouble(12));
            String[] specialization =   getSpecialization(email);


            return new Physician(email, firstName, lastName, city, street, houseNumber, postalCode,
                    phoneNumber, title, pwhash, specialization, latlong);

        }else{
            throw new SQLException("User not found");
        }
    }

    /**
     * Fetches the Symptoms of a user from the database. User is uniquely identified by his emailaddress.
     * @param email The registered emailaddress of the Patient.
     * @return An array containing the Instances of the Symptomclass related to the patient identified by the emailaddress
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public Symptom[]       getSymptoms(String email) throws SQLException,ClassNotFoundException{
        if(connection == null){
            connect();
        }
        Statement statement = connection.createStatement();
        int patientID = statement.executeQuery("SELECT ID FROM User Where emailAddress='"+email+"';" ).getInt(1);
        int countOfSymptoms = 0;
        ResultSet res = statement.executeQuery("SELECT s.name,s.description,sv.severeness FROM (Symptom AS s JOIN SymptomPatient AS sp " +
                "ON (SELECT sp.SymptomID FROM SymptomPatient WHERE sp.PatientID = "+patientID+") = s.ID) " +
                "JOIN Severeness AS sv " +
                "ON(SELECT sp.SeverenessID FROM SymptomPatient as sp WHERE sp.PatientID = "+patientID+")=sv.ID");

        while(res.next()){
            countOfSymptoms++;
        }

        Symptom[] symptoms = new Symptom[countOfSymptoms];
        res = statement.executeQuery("SELECT s.name,s.description,sv.severeness FROM (Symptom AS s JOIN SymptomPatient AS sp " +
                "ON (SELECT sp.SymptomID FROM SymptomPatient WHERE sp.PatientID = "+patientID+") = s.ID) " +
                "JOIN Severeness AS sv " +
                "ON(SELECT sp.SeverenessID FROM SymptomPatient as sp WHERE sp.PatientID = "+patientID+")=sv.ID");

        for (int i = 0; res.next(); i++){
            symptoms[i] = new Symptom(res.getString(1), res.getString(2), res.getString(3));

        }
        return symptoms;
    }

    /**
     * Fetches the Symptoms of a user from the database. User is uniquely identified by his emailaddress.
     * @param email The registered emailaddress of the Patient.
     * @return An array containing the Instances of the Medication class related to the patient identified by the emailaddress.
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public Medication[]    getMedication(String email) throws SQLException,ClassNotFoundException{
        if(connection == null){
            connect();
        }
        int countOfMedication = 0;
        int patientID;
        Statement statement = connection.createStatement();
        patientID = statement.executeQuery("SELECT ID FROM User Where emailAddress='"+email+"';" ).getInt(1);
        ResultSet res = statement.executeQuery("SELECT Dosis, TimesPerDay, DrugID FROM Medication Where PatientID="+patientID+";");


        while(res.next()){
            countOfMedication++;
        }

        Medication[] medication = new Medication[countOfMedication];
        res = statement.executeQuery("SELECT DrugID, Dosis, TimesPerDay FROM Medication Where PatientID="+patientID+";");

        for (int i = 0; res.next(); i++){
            medication[i] = new Medication(getDrug(res.getInt(1)), res.getDouble(2), res.getInt(3));
        }
        return medication;
    }

    /**
     * Fetches the Specializations of a physician from the database. The Physician is uniquely identified by the emailaddress.
     * @param email The registered emailaddress of the Physician.
     * @return An array containing the Strings of the Specializations related to the physician identified by the emailaddress.
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public String[]        getSpecialization(String email) throws SQLException,ClassNotFoundException{
        if(connection == null){
            connect();
        }

        int countOfSpecializations = 0;
        Statement statement = connection.createStatement();
        Statement statement2 = connection.createStatement();
        int PhysicianID = statement.executeQuery("SELECT ID FROM User WHERE emailAddress='"+email+"';").getInt(1);
        ResultSet res = statement.executeQuery("SELECT * FROM SpecializationPhysician WHERE PhysicianID='" + PhysicianID + "';");
        while (res.next()) {
            countOfSpecializations++;
        }

        String[] specialization = new String[countOfSpecializations];
        if (countOfSpecializations > 0) {
            res = statement.executeQuery("SELECT * FROM SpecializationPhysician WHERE PhysicianID='" + PhysicianID + "';");

            for (int i = 0;res.next();i++) {
                specialization[i] = statement2.executeQuery("SELECT Specialization FROM Specialization WHERE ID='" +res.getInt(3)+ "';").getString(1);
            }

        }

        return specialization;
    }

    /**
     * Fetches the instance of a Drug from the database. The drug is uniquely identified by its name.
     * @param drugName A String containing the name of the drug.
     * @return An Instance of the Drug Class identified by its name.
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public Drug            getDrug(String drugName)throws SQLException,ClassNotFoundException{
        if(connection == null){
            connect();
        }

        Statement statement = connection.createStatement();
        ResultSet res = statement.executeQuery("SELECT Drug.name, Drug.description FROM Drug Where name ='"+ drugName +"';");
        return new Drug(res.getString(1),res.getString(2));
    }

    /**
     * Fetches the instance of a Drug from the database. The drug is uniquely identified by its name.
     * @param drugID An intger containing the unique ID of the drug within the database.
     * @return An Instance of the Drug Class identified by its ID.
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public Drug            getDrug(int drugID) throws SQLException,ClassNotFoundException{
        if(connection == null){
            connect();
        }

        Statement statement = connection.createStatement();
        ResultSet res = statement.executeQuery("SELECT Drug.name, Drug.description FROM Drug Where ID ="+ drugID +";");
        return new Drug(res.getString(1),res.getString(2));

    }

    /**
     * Fetches all Appointments of an user identified by the emailaddress.
     * @param email The registered emailaddress of the User.
     * @return An array of all Instances of the Appointment class related to the User uniquely identified by the emailaddress.
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public Appointment[]   getAppointment(String email) throws SQLException,ClassNotFoundException{
        if(connection == null){
            connect();
        }
        Statement statement = connection.createStatement();
        int userID = statement.executeQuery("SELECT ID FROM User WHERE emailaddress='"+email+"';").getInt(1);

        ResultSet res = statement.executeQuery("SELECT * FROM Appointment WHERE PatientID = "+userID+" OR PhysicianID ="+userID+";");
        if(!res.next()){
            throw new SQLException("No Appointments found.");
        }
        else{
            int countAppointments = 0;
            while(res.next()){
                countAppointments++;
            }
            Appointment[] appointments = new Appointment[countAppointments];
            res = statement.executeQuery("SELECT * FROM Appointment WHERE PatientID = "+userID+" OR PhysicianID ="+userID+";");

            for (int i = 0; i<countAppointments; i++){
                int patientID =     res.getInt(2);
                int physicianID =   res.getInt(3);
                appointments[i] =   new Appointment(getPatient(patientID), getPhysician(physicianID), LocalDateTime.of(res.getInt(4), res.getInt(5), res.getInt(6), res.getInt(7), res.getInt(8)));
                res.next();
            }

            return appointments;
        }
    }

    /** Fetches all Appointments from the Database to ensure easy access to appointments for admins. (Only viable for small scale database, Prototype only method)
     *
     * @return An array of all Instances of the Appointment class registered within the database.
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public Appointment[]   getAllAppointments() throws SQLException, ClassNotFoundException{
        if(connection == null){
            connect();
        }
        Statement statement = connection.createStatement();

        ResultSet res = statement.executeQuery("SELECT * FROM Appointment ;");
        if(!res.next()){
            throw new SQLException("No Appointments found.");
        }
        else{
            int countAppointments = 0;
            while(res.next()){
                countAppointments++;
            }
            Appointment[] appointments = new Appointment[countAppointments];
            res = statement.executeQuery("SELECT * FROM Appointment;");

            for (int i = 0; i<countAppointments; i++){
                int patientID =     res.getInt(2);
                int physicianID =   res.getInt(3);
                appointments[i] =   new Appointment(getPatient(patientID), getPhysician(physicianID), LocalDateTime.of(res.getInt(4), res.getInt(5), res.getInt(6), res.getInt(7), res.getInt(8)));
                res.next();
            }

            return appointments;
        }

    }

    /**
     * Fetches an Array of all Physicians that are specialized on the treatment of the Symptoms defined as parameter.
     * @param symptoms An array
     * @return An array containing Instances of the Symptomclass.
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public Physician[] searchPhysician(Symptom[] symptoms) throws SQLException,ClassNotFoundException{
        if(connection == null){
            connect();
        }
        int[] specializationIDs = new int[4];
        Physician[] physicians = new Physician[3];
        int j = 0;
        int k;

        Statement statement = connection.createStatement();

        for (int i = 0; i < symptoms.length; i++){
            specializationIDs[i] = statement.executeQuery("SELECT SpecializationID FROM SymptomSpecialization WHERE SymptomID = " +
                    "(SELECT Symptom.ID FROM Symptom WHERE Name='"+symptoms[i].getName()+"');").getInt(1);
        }
        if(specializationIDs != null){
            for(int i= 0; i<specializationIDs.length; i++){
                ResultSet res = statement.executeQuery("SELECT PhysicianID FROM SpecializationPhysician WHERE SpecializationID = "+specializationIDs[i]+";");
                k = 1;

                while (res.next()){
                    physicians[j] = getPhysician(res.getInt(k));
                    j++;
                    k++;
                }
            }
        }else{
            System.out.print("No Physician Found");
            return null;
        }
        if(physicians == null){
            System.out.print("No Physician Found");
            return null;
        }
        return physicians;
    }


    /**
     * Sets the Symptoms of the patient uniquely identified by the emailaddress to the Symptoms conatained in the Symptom[] Parameter.
     * @param email The registered emailaddress of the User.
     * @param symptoms An array containing Instances of the Symptomclass.
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    private void setSymptoms(String email,Symptom[] symptoms)throws SQLException,ClassNotFoundException{
        if (connection == null) {
            connect();
        }
        Symptom[] oldsymptoms = getSymptoms(email);
        Statement statement = connection.createStatement();
        int patientID = statement.executeQuery("SELECT ID FROM User WHERE emailAddress='"+email+"';").getInt(1);

        try {
            /* delete oldsymptoms */
            for (test.Symptom oldsymptom : oldsymptoms) {
                statement.execute("DELETE FROM SymptomPatient WHERE" +
                        " PatientID = " + patientID + " AND" +
                        " SymptomID = (SELECT ID FROM Symptom WHERE name = '" + oldsymptom.getName() + "') AND" +
                        " SeverenessID = (SELECT ID FROM Severeness WHERE severeness ='" + oldsymptom.getSevereness() + "');");
            }

            /* insert new symptoms into database */

            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO SymptomPatient (PatientID, SymptomID,SeverenessID) VALUES (?,?,?)");
            for (int i = 0; i < symptoms.length; i++) {

                preparedStatement.setInt(1, patientID);
                preparedStatement.setInt(2, statement.executeQuery("SELECT ID FROM Symptom where name = '" + symptoms[i].getName() + "'").getInt(1));
                preparedStatement.setInt(3, statement.executeQuery("SELECT ID FROM Severeness WHERE severeness ='" + symptoms[i].getSevereness() + "';").getInt(1));
                preparedStatement.execute();

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("Error while updating Symptoms.");
        }
    }

    /**
     * Sets the Medication of the patient uniquely identified by the emailaddress to the Medication conatained in the medications[] Parameter.
     * @param email The registered emailaddress of the User.
     * @param medications An array containing Instances of the Medication class.
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    private void setMedications(String email,Medication[] medications)throws SQLException,ClassNotFoundException{
        if (connection == null) {
            connect();
        }
        Medication[] oldmedication = getMedication(email);
        Statement statement = connection.createStatement();
        PreparedStatement preparedStatement;
        int patientID = statement.executeQuery("SELECT ID FROM User WHERE emailAddress='"+email+"';").getInt(1);

        try{
            /* delete oldmedications */
            for(int i = 0; i < oldmedication.length; i++) { //could just delete everything with the patientID since its unique. Update later?
                preparedStatement = connection.prepareStatement("DELETE FROM Medication WHERE" +
                        " PatientID = " + patientID + " AND" +
                        " DrugID = (SELECT ID FROM Drug WHERE name = '"+ oldmedication[i].getDrug().getName()+"');");
                preparedStatement.execute();
            }

            /* insert new medications into database */

            preparedStatement = connection.prepareStatement("INSERT INTO Medication (PatientID, DrugID, Dosis, TimesPerDay) VALUES (?,?,?,?)");
            for(int i = 0; i < medications.length; i++) {

                preparedStatement.setInt(1, patientID);
                preparedStatement.setInt(2, statement.executeQuery("SELECT ID FROM Drug where name = '" + medications[i].getDrug().getName() + "')").getInt(1));
                preparedStatement.setDouble(3, medications[i].getDosis());
                preparedStatement.setInt(3, medications[i].getTimesperDay());

                preparedStatement.execute();

            }
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
            System.out.println("Error while updating medications");
            setMedications(email, oldmedication); //rollback
        }

    }

    /**
     * Sets the Specializations of the physician uniquely identified by the emailaddress to the specializations conatained in the specialization[] Parameter.
     * @param email The registered emailaddress of the User.
     * @param specialization An array containing Strings of the specialization.
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    private void setSpecialization(String email, String[] specialization) throws SQLException, ClassNotFoundException{
        if (connection == null) {
            connect();
        }
        String[] oldspecialization = getSpecialization(email);
        try {
            Statement statement = connection.createStatement();

            int physicianID = statement.executeQuery("SELECT ID FROM User WHERE emailAddress='" + email + "';").getInt(1);
            //DELETE OLD Specializations
            for (int i = 0; i < oldspecialization.length; i++) {
                statement.execute("DELETE FROM SpecializationPhysician WHERE SpecializationID = (SELECT ID FROM Specialization WHERE Specialization = '" + oldspecialization[i] + "');");//could just delete everything with the patientID since its unique. Update later?
            }
            //INSERT NEW Specializations
            for (int i = 0; i < specialization.length; i++) {
                statement.execute("INSERT INTO SpecializationPhysician (PhysicianID,SpecializationID) VALUES (" + physicianID + ",(SELECT ID FROM Specialization WHERE Specialization = '" + specialization[i] + "'));");
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
            System.out.println("Error while updating Specialization");
            setSpecialization(email, oldspecialization);
        }

    }

    /**
     * Adds an appointment to the database. The attributes are provided by an Instance of the Appointment class.
     * @param appointment An instance of the Appointment Class. Representing a scheduled meeting between physician and patient.
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public void addAppointment(Appointment appointment) throws SQLException, ClassNotFoundException{
        if(connection == null){
            connect();
        }
        Statement statement = connection.createStatement();
        int PatientID = statement.executeQuery("SELECT ID FROM User WHERE emailAddress= '" + appointment.getPatient().getEmailAddress() + "'").getInt(1);
        int PhysicianID = statement.executeQuery("SELECT ID FROM User WHERE emailAddress = '" + appointment.getPhysician().getEmailAddress() + "'").getInt(1);
        LocalDateTime dateTime = appointment.getDate();
        int year = dateTime.getYear();
        int month = dateTime.getMonth().getValue();
        int day = dateTime.getDayOfMonth();
        int hour = dateTime.getHour();
        int min = dateTime.getMinute();
        statement.execute("INSERT INTO Appointment (PhysicianID, PatientID, Year, Month, Day, Hour, Minute) " +
                "VALUES (" + PhysicianID + "," + PatientID + "," + year + "," + month + "," + day + "," + hour + "," + min + ");");
    }


    /**
     * Establishes an connection to the database. No Parameters given, as the database path and structure are constant.
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    private void connect() throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection("jdbc:sqlite:eHealthcareUsers.db");
        initialise();
    }

    /**
     * Initialises a Database if no database is existent within the file. The Database is created at the same location as the executable.
     * @throws SQLException
     */
    private void initialise() throws SQLException {
        if(!hasData){
            hasData = true;
            Statement state = connection.createStatement();
            ResultSet res = state.executeQuery("SELECT name FROM sqlite_master WHERE type ='table' AND name='User'");

            if (!res.next()) {
                System.out.println("Building database...");

                buildUserTable();
                buildInsuranceTypeTable();
                buildInsuranceTable();
                buildPhysicianTable();
                buildPatientTable();
                buildAdminTable();
                buildSpecializationTable();
                buildSpecializationPhysicianTable();
                buildSymptomTable();
                buildSeverenessTable();
                buildSymptomPatientTable();
                buildDrugTable();
                buildMedicationTable();
                buildAppointmentTable();
                buildSymptomSpecializationTable();

                System.out.println("Database build. \n\n\n");

            }

        }
    }

    /**
     * creates the Table User within the database and defines its attributes.
     * @throws SQLException
     */
    private void buildUserTable() throws SQLException{
        System.out.println("Building User table...");
        Statement state = connection.createStatement();
        state.execute( "CREATE TABLE User (" +
                "ID INTEGER PRIMARY KEY," +
                "emailAddress VARCHAR(255) NOT NULL UNIQUE," +
                "password VARCHAR(255) NOT NULL," +
                "firstName VARCHAR(255) NOT NULL," +
                "lastName VARCHAR (255) NOT NULL," +
                "city VARCHAR(255) NOT NULL," +
                "street VARCHAR(255) NOT NULL," +
                "houseNumber VARCHAR(255) NOT NULL," +
                "postalCode VARCHAR(5) NOT NULL," +
                "phoneNumber VARCHAR(20) NOT NULL," +
                "title VARCHAR (255) NOT NULL,"
                + "longitude DOUBLE NOT NULL,"
                + "latitude DOUBLE NOT NULL" +
                ")");

        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO User (" +
                "emailAddress," +
                "password," +
                "firstName," +
                "lastName," +
                "city," +
                "street," +
                "houseNumber," +
                "postalCode," +
                "phoneNumber," +
                "title,"
                + "longitude,"
                + "latitude)" +
                "VALUES (?,?,?,?,?,?,?,?,?,?,?,?);");

        preparedStatement.setString(1, "Patient");
        preparedStatement.setString(2, User.hashPassword("asd"));
        preparedStatement.setString(3, "Test");
        preparedStatement.setString(4, "Patient");
        preparedStatement.setString(5, "Frankfurt");
        preparedStatement.setString(6, "Ben-Gurion-Ring");
        preparedStatement.setString(7, "48C");
        preparedStatement.setString(8, "60437");
        preparedStatement.setString(9, "015738340183");
        preparedStatement.setString(10,"hobo");
        preparedStatement.setString(11,"50.19161");
        preparedStatement.setString(12,"8.66039");
        preparedStatement.execute();

        preparedStatement.setString(1, "Doctor");
        preparedStatement.setString(2, User.hashPassword("asd"));
        preparedStatement.setString(3, "Test");
        preparedStatement.setString(4, "Doktor");
        preparedStatement.setString(5, "Frankfurt");
        preparedStatement.setString(6, "Ben-Gurion-Ring");
        preparedStatement.setString(7, "48C");
        preparedStatement.setString(8, "60437");
        preparedStatement.setString(9, "015738340183");
        preparedStatement.setString(10,"Dr.");
        preparedStatement.setString(11,"50.19161");
        preparedStatement.setString(12,"8.86039");
        preparedStatement.execute();

        System.out.println("complete.");
    }

    /**
     * creates the Table InsuranceType within the database and defines its attributes.
     * @throws SQLException
     */
    private void buildInsuranceTypeTable() throws SQLException{
        System.out.println("Building InsuranceType table...");
        Statement state = connection.createStatement();
        state.execute( "CREATE TABLE InsuranceType (" +
                "ID INTEGER PRIMARY KEY," +
                "Type VARCHAR(255) NOT NULL UNIQUE" +
                ")");

        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO InsuranceType (" +
                "Type) " +
                "VALUES (?);");

        preparedStatement.setString(1, "public");
        preparedStatement.execute();
        preparedStatement.setString(1, "private");
        preparedStatement.execute();


        System.out.println("complete.");
    }

    /**
     * creates the Table Insurance within the database and defines its attributes.
     * @throws SQLException
     */
    private void buildInsuranceTable() throws SQLException{
        System.out.println("Building Insurance table...");
        Statement state = connection.createStatement();
        state.execute( "CREATE TABLE Insurance (" +
                "ID INTEGER PRIMARY KEY," +
                "name VARCHAR(255) NOT NULL UNIQUE," +
                "insuranceTypeID INT NOT NULL," +
                "FOREIGN KEY(insuranceTypeID) REFERENCES InsuranceType(ID) ON DELETE RESTRICT ON UPDATE CASCADE" +
                ")");

        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Insurance (" +
                "name," +
                "insuranceTypeID)" +
                "VALUES (?,?);");

        preparedStatement.setString(1, "AOK");
        preparedStatement.setInt(2, 1);
        preparedStatement.execute();
        preparedStatement.setString(1, "Techniker Krankenkasse");
        preparedStatement.setInt(2, 1);
        preparedStatement.execute();
        preparedStatement.setString(1, "Allianz");
        preparedStatement.setInt(2, 2);
        preparedStatement.execute();


        System.out.println("complete.");
    }

    /**
     * creates the Table Physician within the database and defines its attributes.
     * @throws SQLException
     */
    private void buildPhysicianTable() throws SQLException {
        System.out.println("Building Physician table...");

        Statement state = connection.createStatement();
        state.execute( "CREATE TABLE Physician (" +
                "ID int NOT NULL," +
                "PRIMARY KEY(ID), " +
                "FOREIGN KEY(ID) REFERENCES User(ID) ON DELETE CASCADE ON UPDATE CASCADE" +
                ")");
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Physician (" +
                "ID) VALUES (?)");

        /* Insert Test Physician */
        preparedStatement.setString(1, "2");
        preparedStatement.execute();

        System.out.println("complete.");
    }

    /**
     * creates the Table Patient within the database and defines its attributes.
     * @throws SQLException
     */
    private void buildPatientTable() throws SQLException {
        System.out.println("Building Patient table...");

        Statement state = connection.createStatement();
        state.execute( "CREATE TABLE Patient (" +
                "ID int NOT NULL," +
                "insuranceID int NOT NULL," +
                "dateOfBirth DATE NOT NULL," +
                "weight INT NOT NULL," +
                "PRIMARY KEY(ID), " +
                "FOREIGN KEY(ID) REFERENCES User(ID) ON DELETE RESTRICT ON UPDATE CASCADE," +
                "FOREIGN KEY(insuranceID) REFERENCES Insurance(ID) ON DELETE RESTRICT ON UPDATE CASCADE" +
                ")");

        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Patient (" +
                "ID, dateOfBirth,weight, insuranceID) VALUES (?,?,?,?)");

        preparedStatement.setInt(1, 1);
        preparedStatement.setString(2, "2020-01-15");
        preparedStatement.setInt(3, 93);
        preparedStatement.setInt(4, 1);
        preparedStatement.execute();

        System.out.println("complete.");
    }

    /**
     * creates the Table Admin within the database and defines its attributes.
     * @throws SQLException
     */
    private void buildAdminTable() throws SQLException {
        System.out.println("Building Admin table...");

        Statement state = connection.createStatement();
        state.execute( "CREATE TABLE Admin (" +
                "ID int NOT NULL," +
                "PRIMARY KEY(ID), " +
                "FOREIGN KEY(ID) REFERENCES User(ID) ON DELETE CASCADE ON UPDATE CASCADE" +
                ")");

        System.out.println("complete.");
    }

    /**
     * creates the Table Specialization within the database and defines its attributes.
     * @throws SQLException
     */
    private void buildSpecializationTable() throws SQLException{
        System.out.println("Building Specialization table...");

        Statement state = connection.createStatement();
        state.execute( "CREATE TABLE Specialization (" +
                "ID INTEGER PRIMARY KEY," +
                "Specialization VARCHAR(255) NOT NULL UNIQUE"+
                ")");
        //Populating Table
        {/* Insert Specialization */
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Specialization (" +
                    "Specialization) VALUES (?)");
            preparedStatement.setString(1, "Allgemeinmedizin");
            preparedStatement.execute();
            preparedStatement.setString(1, "Anästhesiologie");
            preparedStatement.execute();
            preparedStatement.setString(1, "Anatomie");
            preparedStatement.execute();
            preparedStatement.setString(1, "Arbeitsmedizin");
            preparedStatement.execute();
            preparedStatement.setString(1, "Augenheilkunde");
            preparedStatement.execute();
            preparedStatement.setString(1, "Biochemie");
            preparedStatement.execute();
            preparedStatement.setString(1, "Chirurgie");
            preparedStatement.execute();
            preparedStatement.setString(1, "Chirurgie_Allgemeine Chirurgie");
            preparedStatement.execute();
            preparedStatement.setString(1, "Forensische Psychiatrie");
            preparedStatement.execute();
            preparedStatement.setString(1, "Frauenheilkunde und Geburtshilfe");
            preparedStatement.execute();
            preparedStatement.setString(1, "Gefäßchirurgie");
            preparedStatement.execute();
            preparedStatement.setString(1, "Gynäkologische Onkologie");
            preparedStatement.execute();
            preparedStatement.setString(1, "Hals-Nasen-Ohrenheilkunde");
            preparedStatement.execute();
            preparedStatement.setString(1, "Haut- und Geschlechtskrankheiten");
            preparedStatement.execute();
            preparedStatement.setString(1, "Herzchirurgie");
            preparedStatement.execute();
            preparedStatement.setString(1, "Humangenetik");
            preparedStatement.execute();
            preparedStatement.setString(1, "Hygiene und Umweltmedizin");
            preparedStatement.execute();
            preparedStatement.setString(1, "Innere Medizin ");
            preparedStatement.execute();
            preparedStatement.setString(1, "Innere Medizin und Endokrinologie und Diabetologie");
            preparedStatement.execute();
            preparedStatement.setString(1, "Innere Medizin und Gastroenterologie");
            preparedStatement.execute();
            preparedStatement.setString(1, "Innere Medizin und Hämatologie und Onkologie");
            preparedStatement.execute();
            preparedStatement.setString(1, "Innere Medizin und Kardiologie");
            preparedStatement.execute();
            preparedStatement.setString(1, "Innere Medizin und Nephrologie");
            preparedStatement.execute();
            preparedStatement.setString(1, "Innere Medizin und Pneumologie");
            preparedStatement.execute();
            preparedStatement.setString(1, "Innere Medizin und Rheumatologie");
            preparedStatement.execute();
            preparedStatement.setString(1, "Kinder- und Jugendmedizin");
            preparedStatement.execute();
            preparedStatement.setString(1, "Kinder- und Jugendpsychiatrie und -psychotherapie");
            preparedStatement.execute();
            preparedStatement.setString(1, "Kinderchirurgie");
            preparedStatement.execute();
            preparedStatement.setString(1, "Kinderradiologie");
            preparedStatement.execute();
            preparedStatement.setString(1, "Klinische Pharmakologie");
            preparedStatement.execute();
            preparedStatement.setString(1, "Laboratoriumsmedizin");
            preparedStatement.execute();
            preparedStatement.setString(1, "Mikrobiologie, Virologie und Infektionsepidemiologie");
            preparedStatement.execute();
            preparedStatement.setString(1, "Mund-Kiefer-Gesichtschirurgie");
            preparedStatement.execute();
            preparedStatement.setString(1, "Neurochirurgie");
            preparedStatement.execute();
            preparedStatement.setString(1, "Neurologie");
            preparedStatement.execute();
            preparedStatement.setString(1, "Neuroradiologie");
            preparedStatement.execute();
            preparedStatement.setString(1, "Nuklearmedizin");
            preparedStatement.execute();
            preparedStatement.setString(1, "Orthopädie und Unfallchirurgie");
            preparedStatement.execute();
            preparedStatement.setString(1, "Pathologie ");
            preparedStatement.execute();
            preparedStatement.setString(1, "Pharmakologie und Toxikologie");
            preparedStatement.execute();
            preparedStatement.setString(1, "Phoniatrie und Pädaudiologie");
            preparedStatement.execute();
            preparedStatement.setString(1, "Physikalische und Rehabilitative Medizin");
            preparedStatement.execute();
            preparedStatement.setString(1, "Physiologie");
            preparedStatement.execute();
            preparedStatement.setString(1, "Plastische, Rekonstruktive und Ästhetische Chirurgie");
            preparedStatement.execute();
            preparedStatement.setString(1, "Psychiatrie und Psychotherapie");
            preparedStatement.execute();
            preparedStatement.setString(1, "Psychosomatische Medizin und Psychotherapie");
            preparedStatement.execute();
            preparedStatement.setString(1, "Radiologie");
            preparedStatement.execute();
            preparedStatement.setString(1, "Rechtsmedizin");
            preparedStatement.execute();
            preparedStatement.setString(1, "SP Gynäkologische Endokrinologie und Reproduktionsmedizin");
            preparedStatement.execute();
            preparedStatement.setString(1, "SP Kinder-Hämatologie und -Onkologie");
            preparedStatement.execute();
            preparedStatement.setString(1, "SP Kinder-Kardiologie");
            preparedStatement.execute();
            preparedStatement.setString(1, "SP Neonatologie");
            preparedStatement.execute();
            preparedStatement.setString(1, "SP Neuropädiatrie");
            preparedStatement.execute();
            preparedStatement.setString(1, "Spezielle Geburtshilfe und Perinatalmedizin");
            preparedStatement.execute();
            preparedStatement.setString(1, "Strahlentherapie");
            preparedStatement.execute();
            preparedStatement.setString(1, "Thoraxchirurgie");
            preparedStatement.execute();
            preparedStatement.setString(1, "Transfusionsmedizin");
            preparedStatement.execute();
            preparedStatement.setString(1, "Urologie");
            preparedStatement.execute();
            preparedStatement.setString(1, "Viszeralchirurgie");
            preparedStatement.execute();
            preparedStatement.setString(1, "Innere Medizin und Angiologie");
            preparedStatement.execute();
            preparedStatement.setString(1, "Öffentliches Gesundheitswesen");
            preparedStatement.execute();


        }

        System.out.println("complete.");
    }

    /**
     * creates the table SpecializationPhysician within the database and defines its attributes.
     * @throws SQLException
     */
    private void buildSpecializationPhysicianTable() throws SQLException {
        System.out.println("Building SpecializationPhysician table...");

        Statement state = connection.createStatement();
        state.execute( "CREATE TABLE SpecializationPhysician (" +
                "ID INTEGER PRIMARY KEY," +
                "PhysicianID INT," +
                "SpecializationID INT," +
                "FOREIGN KEY (SpecializationID) REFERENCES Specialization(ID) ON DELETE CASCADE ON UPDATE CASCADE," +
                "FOREIGN KEY (PhysicianID) REFERENCES Physician(ID) ON DELETE CASCADE ON UPDATE CASCADE" +
                ")");

        {/* Insert Test SpecializationPhysician */
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO SpecializationPhysician(PhysicianID,SpecializationID) VALUES (?,?);");
            preparedStatement.setInt(1, 2);
            preparedStatement.setInt(2, 1);
            preparedStatement.execute();
            preparedStatement.setInt(1, 2);
            preparedStatement.setInt(2, 2);
            preparedStatement.execute();
            preparedStatement.setInt(1, 2);
            preparedStatement.setInt(2, 7);
            preparedStatement.execute();
            preparedStatement.setInt(1, 2);
            preparedStatement.setInt(2, 24);
            preparedStatement.execute();}

        System.out.println("complete.");
    }

    /**
     * creates the table Symptom within the database and defines its attributes.
     * @throws SQLException
     */
    private void buildSymptomTable() throws SQLException{
        System.out.println("Building Symptom table...");
        Statement state = connection.createStatement();
        state.execute( "CREATE TABLE Symptom (" +
                "ID INTEGER PRIMARY KEY," +
                "name VARCHAR(255) NOT NULL," +
                "description VARCHAR(255) NOT NULL" +
                ");");


        {//Add Teststubs
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Symptom (" +
                    " name,description) VALUES (?,?)");

            preparedStatement.setString(1, "Cough");
            preparedStatement.setString(2, "Immune Reaction to foreign particles within the respiratory System.");
            preparedStatement.execute();
            preparedStatement.setString(1, "Rash");
            preparedStatement.setString(2, "an area of redness and spots on a person's skin, appearing especially as a result of allergy or illness.");
            preparedStatement.execute();
            preparedStatement.setString(1, "Fever");
            preparedStatement.setString(2, "an abnormally high body temperature, usually accompanied by shivering, headache, and in severe instances, delirium.");
            preparedStatement.execute();
            preparedStatement.setString(1, "Chills");
            preparedStatement.setString(2, "The feeling of being cold, though not necessarily in a cold environment, often accompanied by shivering or shaking.");
            preparedStatement.execute();
            preparedStatement.setString(1, "Shortness of breath");
            preparedStatement.setString(2, "a shortness of breath.");
            preparedStatement.execute();
            preparedStatement.setString(1, "Breathing Difficulties");
            preparedStatement.setString(2, "difficulties breathing.");
            preparedStatement.execute();
            preparedStatement.setString(1, "Fatigue");
            preparedStatement.setString(2, "Feeling overtired, with low energy and a strong desire to sleep that interferes with normal daily activities.");
            preparedStatement.execute();
            preparedStatement.setString(1, "Muscle aches");
            preparedStatement.setString(2, "Dumb pain within the muscles.");
            preparedStatement.execute();
            preparedStatement.setString(1, "Headache");
            preparedStatement.setString(2, "A painful sensation in any part of the head, ranging from sharp to dull, that may occur with other symptoms.");
            preparedStatement.execute();
            preparedStatement.setString(1, "Loss of Taste");
            preparedStatement.setString(2, "Partial or complete loss of the sense of taste.");
            preparedStatement.execute();
            preparedStatement.setString(1, "Loss of Smell");
            preparedStatement.setString(2, "Partial or complete loss of the sense of smell.");
            preparedStatement.execute();
            preparedStatement.setString(1, "Sore Throat");
            preparedStatement.setString(2, "Pain or irritation in the throat that can occur with or without swallowing, often accompanies infections, such as a cold or flu.");
            preparedStatement.execute();
            preparedStatement.setString(1, "Runny Nose");
            preparedStatement.setString(2, "Excess drainage, ranging from a clear fluid to thick mucus, from the nose and nasal passages.");
            preparedStatement.execute();
            preparedStatement.setString(1, "Nausea");
            preparedStatement.setString(2, "Immune Reaction to foreign particles within the respiratory System.");
            preparedStatement.execute();
            preparedStatement.setString(1, "Diarrhea");
            preparedStatement.setString(2, "Immune Reaction to foreign particles within the respiratory System.");
            preparedStatement.execute();
            preparedStatement.setString(1, "Depression");
            preparedStatement.setString(2, "Depression is a state of low mood and aversion to activity. It can affect a person's thoughts, behavior, motivation, feelings, and sense of well-being.");
            preparedStatement.execute();
            preparedStatement.setString(1, "Vertigo");
            preparedStatement.setString(2, "Vertigo is a condition where a person has the sensation of moving or of surrounding objects moving when they are not.");
            preparedStatement.execute();
            preparedStatement.setString(1, "Visual impairment");
            preparedStatement.setString(2, "Visual sight is decreased. e.g. double Vision.");
            preparedStatement.execute();
            preparedStatement.setString(1, "Cardiac arrhythmias");
            preparedStatement.setString(2, "Arrhythmia, also known as cardiac arrhythmia or heart arrhythmia, is a group of conditions in which the heartbeat is irregular, too fast, or too slow.");
            preparedStatement.execute();
            preparedStatement.setString(1, "Swelling");
            preparedStatement.setString(2, "Edema, also known as fluid retention, dropsy, hydropsy or swelling, is the buildup of fluid in the body's tissue.");
            preparedStatement.execute();
            preparedStatement.setString(1, "Tinnitus");
            preparedStatement.setString(2, "Tinnitus is the perception of sound when no corresponding external sound is present.");
            preparedStatement.execute();
            preparedStatement.setString(1, "Hallucination");
            preparedStatement.setString(2, "A hallucination is a perception in the absence of external stimulus that has qualities of real perceptions.");
            preparedStatement.execute();
            preparedStatement.setString(1, "Abnormal vaginal bleeding");
            preparedStatement.setString(2, "Vaginal bleeding is any bleeding from the vagina.");
            preparedStatement.execute();
            preparedStatement.setString(1, "Anxiety");
            preparedStatement.setString(2, "Anxiety is an emotion characterized by an unpleasant state of inner turmoil, often accompanied by nervous behavior such as pacing back and forth, somatic complaints, and rumination.");
            preparedStatement.execute();
            preparedStatement.setString(1, "Phobia");
            preparedStatement.setString(2, "A phobia is a type of anxiety disorder defined by a persistent and excessive fear of an object or situation.");
            preparedStatement.execute();
            preparedStatement.setString(1, "Dysuria");
            preparedStatement.setString(2, "Dysuria refers to difficult urination. Painful urination is also sometimes, but rarely, described as dysuria.");
            preparedStatement.execute();

            System.out.println("complete.");

        }
    }

    /**
     * creates the table Severeness within the database and defines its attributes.
     * @throws SQLException
     */
    private void buildSeverenessTable() throws  SQLException{
        System.out.println("Building Severeness table...");

        Statement state = connection.createStatement();
        state.execute( "CREATE TABLE Severeness (" +
                "ID INTEGER PRIMARY KEY," +
                "severeness VARCHAR(255) NOT NULL" +
                ")");

        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Severeness (" +
                "severeness) " +
                "VALUES (?);");

        preparedStatement.setString(1, "deadly");
        preparedStatement.execute();

        preparedStatement.setString(1, "heavy");
        preparedStatement.execute();

        preparedStatement.setString(1, "medium");
        preparedStatement.execute();

        preparedStatement.setString(1, "light");
        preparedStatement.execute();

        System.out.println("complete.");
    }

    /**
     * creates the table SymptomPatient within the database and defines its attributes.
     * @throws SQLException
     */
    private void buildSymptomPatientTable() throws SQLException{
        System.out.println("Building SymptomPatient table...");
        Statement state = connection.createStatement();
        state.execute( "CREATE TABLE SymptomPatient (" +
                "ID INTEGER PRIMARY KEY," +
                "PatientID INT," +
                "SymptomID INT," +
                "SeverenessID INT DEFAULT(3)," +
                "FOREIGN KEY (PatientID) REFERENCES Patient(ID) ON DELETE CASCADE ON UPDATE CASCADE," +
                "FOREIGN KEY (SymptomID) REFERENCES Symptom(ID) ON DELETE CASCADE ON UPDATE CASCADE," +
                "FOREIGN KEY (SeverenessID) REFERENCES Severeness(ID) ON DELETE CASCADE ON UPDATE CASCADE" +
                ")");
        System.out.println("complete.");

        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO SymptomPatient (" +
                " PatientID,SymptomID) VALUES (?,?)");

        preparedStatement.setInt(1, 1);
        preparedStatement.setInt(2, 1);
        preparedStatement.execute();
        preparedStatement.setInt(1, 1);
        preparedStatement.setInt(2, 2);
        preparedStatement.execute();
        preparedStatement.setInt(1, 1);
        preparedStatement.setInt(2, 3);
        preparedStatement.execute();
        preparedStatement.setInt(1, 1);
        preparedStatement.setInt(2, 6);
        preparedStatement.execute();
    }

    /**
     * creates the table Drug within the database and defines its attributes.
     * @throws SQLException
     */
    private void buildDrugTable() throws SQLException{
        System.out.println("Building Drug table...");

        Statement state = connection.createStatement();
        state.execute( "CREATE TABLE Drug (" +
                "ID INTEGER PRIMARY KEY," +
                "name VARCHAR(255) NOT NULL," +
                "description VARCHAR(255) NOT NULL" +
                ")");
        System.out.println("complete.");


        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Drug (" +
                " name,description) VALUES (?,?)");

        preparedStatement.setString(1, "Ibuprofen");
        preparedStatement.setString(2, "nonsteroidal anti-inflammatory drug (NSAID) class that is used for treating pain, fever, and inflammation.");
        preparedStatement.execute();
        preparedStatement.setString(1, "Levothyroxin-Natrium");
        preparedStatement.setString(2, "Thyroid hormone thyroxine used to treat thyroid hormone deficiency, including the severe form known as myxedema coma.");
        preparedStatement.execute();
        preparedStatement.setString(1, "Metoprolol");
        preparedStatement.setString(2, "Selective β1 receptor blocker medication. Used to treat high blood pressure, chest pain due to poor blood flow to the heart, and a number of conditions involving an abnormally fast heart rate.");
        preparedStatement.execute();
        preparedStatement.setString(1, "Diclofenac");
        preparedStatement.setString(2, " Nonsteroidal anti-inflammatory drug (NSAID) used to treat pain and inflammatory diseases such as gout.");
        preparedStatement.execute();
        preparedStatement.setString(1, "Ramipril");
        preparedStatement.setString(2, "ACE inhibitor used to treat high blood pressure, heart failure, diabetic kidney disease and to prevent cardiovascular disease in those at high risk.");
        preparedStatement.execute();
        preparedStatement.setString(1, "Simvastatin");
        preparedStatement.setString(2, "lipid-lowering medication used along with exercise, diet, and weight loss to decrease elevated lipid levels also used to to prevent cardiovascular disease in those at high risk.");
        preparedStatement.execute();
        preparedStatement.setString(1, "Metamizol-Natrium");
        preparedStatement.setString(2, "Painkiller, spasm reliever, and fever reliever that also has anti-inflammatory effects.");
        preparedStatement.execute();
        preparedStatement.setString(1, "Omeprazol");
        preparedStatement.setString(2, "Proton-pump inhibitor used in the treatment of gastroesophageal reflux disease (GERD), peptic ulcer disease, Zollinger–Ellison syndrome and to prevent upper gastrointestinal bleeding in people who are at high risk.");
        preparedStatement.execute();
        preparedStatement.setString(1, "Bisoprolol");
        preparedStatement.setString(2, "Beta blocker most commonly used for heart diseases especially high blood pressure, chest pain from not enough blood flow to the heart, and heart failure.");
        preparedStatement.execute();
        preparedStatement.setString(1, "Pantoprazol");
        preparedStatement.setString(2, "ATP pump inhibitor used for the treatment of stomach ulcers, short-term treatment of erosive esophagitis due to gastroesophageal reflux disease (GERD), maintenance of healing of erosive esophagitis, and pathological hypersecretory conditions.");
        preparedStatement.execute();


    }

    /**
     * creates the table Medication within the database and defines its attributes.
     * @throws SQLException
     */
    private void buildMedicationTable() throws SQLException{
        System.out.println("Building Medication table...");
        Statement state = connection.createStatement();
        state.execute( "CREATE TABLE Medication (" +
                "ID INTEGER PRIMARY KEY," +
                "PatientID INT," +
                "DrugID INT," +
                "Dosis DOUBLE," +
                "TimesPerDay INT," +
                "FOREIGN KEY (PatientID) REFERENCES Patient(ID) ON DELETE CASCADE ON UPDATE CASCADE," +
                "FOREIGN KEY (DrugID) REFERENCES Drug(ID) ON DELETE CASCADE ON UPDATE CASCADE" +
                ")");
        System.out.println("complete.");

        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Medication (" +
                " PatientID,DrugID, Dosis, TimesPerDay) VALUES (?,?,?,?)");

        preparedStatement.setInt(1, 1);
        preparedStatement.setInt(2, 1);
        preparedStatement.setDouble(3, 50.3);
        preparedStatement.setInt(4, 3);
        preparedStatement.execute();
        preparedStatement.setInt(1, 1);
        preparedStatement.setInt(2, 2);
        preparedStatement.setDouble(3, 100.75);
        preparedStatement.setInt(4, 1);
        preparedStatement.execute();
        preparedStatement.setInt(1, 1);
        preparedStatement.setInt(2, 9);
        preparedStatement.setDouble(3, 20);
        preparedStatement.setInt(4, 15);
        preparedStatement.execute();
    }

    /**
     * creates the table Appointment within the database and defines its attributes.
     * @throws SQLException
     */
    private void buildAppointmentTable() throws SQLException{
        System.out.println("Building Appointment table...");

        Statement state = connection.createStatement();
        state.execute( "CREATE TABLE Appointment (" +
                "ID INTEGER PRIMARY KEY," +
                "PatientID int," +
                "PhysicianID int," +
                "Year INTEGER," +
                "Month INTEGER," +
                "Day INTEGER," +
                "Hour INTEGER," +
                "Minute INTEGER,"+
                "FOREIGN KEY (PatientID) REFERENCES User (UserID) ON DELETE CASCADE ON UPDATE CASCADE," +
                "FOREIGN KEY (PhysicianID) REFERENCES User (UserID) ON DELETE CASCADE ON UPDATE CASCADE" +
                " )");
        state.execute("INSERT INTO Appointment(" +
                "PatientID,PhysicianID,Year, Month, Day, Hour, Minute) VALUES (1,2, 2020,02,22,18,35);");
        state.execute("INSERT INTO Appointment(" +
                "PatientID,PhysicianID,Year, Month, Day, Hour, Minute) VALUES (1,2, 2020,02,22,09,35);");
        state.execute("INSERT INTO Appointment(" +
                "PatientID,PhysicianID,Year, Month, Day, Hour, Minute) VALUES (1,2, 2020,02,25,09,35);");
        state.execute("INSERT INTO Appointment(" +
                "PatientID,PhysicianID,Year, Month, Day, Hour, Minute) VALUES (1,2, 2020,03,25,09,35);");
        state.execute("INSERT INTO Appointment(" +
                "PatientID,PhysicianID,Year, Month, Day, Hour, Minute) VALUES (1,4, 2020,03,25,15,35);");
        state.execute("INSERT INTO Appointment(" +
                "PatientID,PhysicianID,Year, Month, Day, Hour, Minute) VALUES (3,2, 2020,03,21,22,35);");
        state.execute("INSERT INTO Appointment(" +
                "PatientID,PhysicianID,Year, Month, Day, Hour, Minute) VALUES (3,4, 2020,03,29,06,35);");
        state.execute("INSERT INTO Appointment(" +
                "PatientID,PhysicianID,Year, Month, Day, Hour, Minute) VALUES (3,2, 2020,05,29,09,35);");
        state.execute("INSERT INTO Appointment(" +
                "PatientID,PhysicianID,Year, Month, Day, Hour, Minute) VALUES (3,4, 2020,06,29,11,35);");



        System.out.println("complete.");
    }

    /**
     * creates the table SymptomSpecialization within the database and defines its attributes.
     * @throws SQLException
     */
    private void buildSymptomSpecializationTable() throws SQLException{
        System.out.println("Building SymptomSpecializationTable table...");

        Statement state = connection.createStatement();
        state.execute( "CREATE TABLE SymptomSpecialization (" +
                "ID INTEGER PRIMARY KEY," +
                "SymptomID int," +
                "SpecializationID int," +

                "FOREIGN KEY (SymptomID) REFERENCES Symptom (ID) ON DELETE RESTRICT ON UPDATE RESTRICT," +
                "FOREIGN KEY (SpecializationID) REFERENCES Specialization (ID) ON DELETE RESTRICT ON UPDATE RESTRICT" +
                " )");
        state.execute("INSERT INTO SymptomSpecialization(" +
                "SymptomID,SpecializationID) VALUES (1,24);");
        state.execute("INSERT INTO SymptomSpecialization(" +
                "SymptomID,SpecializationID) VALUES (2,14);");
        state.execute("INSERT INTO SymptomSpecialization(" +
                "SymptomID,SpecializationID) VALUES (3,1);");
        state.execute("INSERT INTO SymptomSpecialization(" +
                "SymptomID,SpecializationID) VALUES (4,1);");
        state.execute("INSERT INTO SymptomSpecialization(" +
                "SymptomID,SpecializationID) VALUES (5,24);");
        state.execute("INSERT INTO SymptomSpecialization(" +
                "SymptomID,SpecializationID) VALUES (6,24);");
        state.execute("INSERT INTO SymptomSpecialization(" +
                "SymptomID,SpecializationID) VALUES (7,1);");
        state.execute("INSERT INTO SymptomSpecialization(" +
                "SymptomID,SpecializationID) VALUES (8,38);");
        state.execute("INSERT INTO SymptomSpecialization(" +
                "SymptomID,SpecializationID) VALUES (9,34);");
        state.execute("INSERT INTO SymptomSpecialization(" +
                "SymptomID,SpecializationID) VALUES (10,1);");
        state.execute("INSERT INTO SymptomSpecialization(" +
                "SymptomID,SpecializationID) VALUES (11,1);");
        state.execute("INSERT INTO SymptomSpecialization(" +
                "SymptomID,SpecializationID) VALUES (12,1);");
        state.execute("INSERT INTO SymptomSpecialization(" +
                "SymptomID,SpecializationID) VALUES (13,13);");
        state.execute("INSERT INTO SymptomSpecialization(" +
                "SymptomID,SpecializationID) VALUES (14,20);");
        state.execute("INSERT INTO SymptomSpecialization(" +
                "SymptomID,SpecializationID) VALUES (15,20);");
        state.execute("INSERT INTO SymptomSpecialization(" +
                "SymptomID,SpecializationID) VALUES (16,45);");
        state.execute("INSERT INTO SymptomSpecialization(" +
                "SymptomID,SpecializationID) VALUES (17,35);");
        state.execute("INSERT INTO SymptomSpecialization(" +
                "SymptomID,SpecializationID) VALUES (18,35);");
        state.execute("INSERT INTO SymptomSpecialization(" +
                "SymptomID,SpecializationID) VALUES (19,22);");
        state.execute("INSERT INTO SymptomSpecialization(" +
                "SymptomID,SpecializationID) VALUES (20,59);");
        state.execute("INSERT INTO SymptomSpecialization(" +
                "SymptomID,SpecializationID) VALUES (21,13);");
        state.execute("INSERT INTO SymptomSpecialization(" +
                "SymptomID,SpecializationID) VALUES (22,45);");
        state.execute("INSERT INTO SymptomSpecialization(" +
                "SymptomID,SpecializationID) VALUES (23,10);");
        state.execute("INSERT INTO SymptomSpecialization(" +
                "SymptomID,SpecializationID) VALUES (24,45);");
        state.execute("INSERT INTO SymptomSpecialization(" +
                "SymptomID,SpecializationID) VALUES (25,45);");
        state.execute("INSERT INTO SymptomSpecialization(" +
                "SymptomID,SpecializationID) VALUES (26,58);");

    }

    /**
     * checks if user is physician
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public boolean checkPhysician(String email) throws SQLException, ClassNotFoundException {
        if(connection == null){
            connect();
        }
        Statement statement = connection.createStatement();
        ResultSet res = statement.executeQuery("SELECT User.*, Physician.ID FROM (User JOIN Physician ON User.ID = Physician.ID) WHERE emailAddress ='" + email + "';");


        return res.next();
    }

    /**
     * checks if user is patient
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public boolean checkPatient(String email) throws SQLException, ClassNotFoundException {
        if(connection == null){
            connect();
        }
        Statement statement = connection.createStatement();
        ResultSet res = statement.executeQuery("SELECT User.*, Patient.ID FROM (User JOIN Patient ON User.ID = Patient.ID) WHERE emailAddress ='" + email + "';");


        return res.next();
    }

}



