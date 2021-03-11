package test;
import java.io.*;


class Patient extends User {

    private String birthday;
    private String insuranceType;
    private String insuranceName;
    private Symptom[] symptoms;
    private Medication[] medications;
    private int weight;

    Patient() {}
    Patient(String _street, String _housenumber, String _city){
        setStreet(_street);
        setHouseNumber(_housenumber);
        setCity(_city);
    }
    Patient(String _emailAddress, String _firstName, String _lastName, String _city,
            String _street, String _houseNumber, String _postalCode, String _phoneNumber, String _title, String _password, LatLong _geolocation) {
        setEmailAddress(_emailAddress);
        setFirstName(_firstName);
        setLastName(_lastName);
        setCity(_city);
        setStreet(_street);
        setHouseNumber(_houseNumber);
        setPostalCode(_postalCode);
        setPhoneNUmber(_phoneNumber);
        setTitle(_title);
        setPasswordhash(_password);
        setGeolocation(_geolocation);
    }
    Patient(String _emailAddress, String _firstName, String _lastName, String _city,
            String _street, String _houseNumber, String _postalCode, String _phoneNumber, String _dateOfBirth, String _title, String _insuranceName,String _password, int _weight, LatLong _geolocation) throws IOException, InterruptedException {
        setEmailAddress(_emailAddress);
        setFirstName(_firstName);
        setLastName(_lastName);
        setCity(_city);
        setStreet(_street);
        setHouseNumber(_houseNumber);
        setPostalCode(_postalCode);
        setPhoneNUmber(_phoneNumber);
        setBirthday(_dateOfBirth);
        setTitle(_title);
        setInsuranceName(_insuranceName);
        setPasswordhash(_password);
        setWeight(_weight);
        setGeolocation(_geolocation);
    }
    Patient(String _emailAddress, String _firstName, String _lastName, String _city,
            String _street, String _houseNumber, String _postalCode, String _phoneNumber, String _dateOfBirth, String _title, String _insuranceName, int _weight) throws IOException, InterruptedException {
        setEmailAddress(_emailAddress);
        setFirstName(_firstName);
        setLastName(_lastName);
        setCity(_city);
        setStreet(_street);
        setHouseNumber(_houseNumber);
        setPostalCode(_postalCode);
        setPhoneNUmber(_phoneNumber);
        setBirthday(_dateOfBirth);
        setTitle(_title);
        setInsuranceName(_insuranceName);
        setWeight(_weight);
        setGeolocation(Geocoder.decode(_city+" "+_street+" "+_houseNumber+" "+_postalCode));
    }

    Patient(String _emailAddress, String _firstName, String _lastName, String _city,
            String _street, String _houseNumber, String _postalCode, String _phoneNumber, String _dateOfBirth, String _title, String _password, String _insuranceName, int _weight) throws IOException, InterruptedException {
        setEmailAddress(_emailAddress);
        setFirstName(_firstName);
        setLastName(_lastName);
        setCity(_city);
        setStreet(_street);
        setHouseNumber(_houseNumber);
        setPostalCode(_postalCode);
        setPhoneNUmber(_phoneNumber);
        setBirthday(_dateOfBirth);
        setTitle(_title);
        setPasswordhash(_password);
        setInsuranceName(_insuranceName);
        setWeight(_weight);
        setGeolocation(Geocoder.decode(_city+" "+_street+" "+_houseNumber+" "+_postalCode));
    }
    Patient(String _emailAddress, String _firstName, String _lastName, String _city,
            String _street, String _houseNumber, String _postalCode, String _phoneNumber, String _title, String _password,
            String _birthday, String _insuranceName, Symptom[] _healthinformation, Medication[] _medications, int _weight, LatLong _geolocation) {
        setEmailAddress(_emailAddress);
        setFirstName(_firstName);
        setLastName(_lastName);
        setCity(_city);
        setStreet(_street);
        setHouseNumber(_houseNumber);
        setPostalCode(_postalCode);
        setPhoneNUmber(_phoneNumber);
        setTitle(_title);
        setPasswordhash(_password);
        setBirthday(_birthday);
        setInsuranceName(_insuranceName);
        setSymptoms(_healthinformation);
        setMedications(_medications);
        setWeight(_weight);
        setGeolocation(_geolocation);
    }


    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getInsuranceType() {
        return insuranceType;
    }

    public void setInsuranceType(String insuranceType) {
        this.insuranceType = insuranceType;
    }

    public String getInsuranceName() {
        return insuranceName;
    }

    public void setInsuranceName(String insuranceName) {
        this.insuranceName = insuranceName;
    }

    public Symptom[] getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(Symptom[] symptoms) {
        this.symptoms = symptoms;
    }

    public Medication[] getMedications() {
        return medications;
    }

    public void setMedications(Medication[] medications) {
        this.medications = medications;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    /***
     * Copying and exporting the symptoms in form of a text file
     * @throws IOException = test
     */
    public void exportHealthInf() throws IOException{

        File file=new File("Health information.txt");
        FileWriter fw= new FileWriter(file);
        PrintWriter pw = new PrintWriter(fw);
        for (Symptom symptom : symptoms) {
            pw.print(symptom.getDescription() + "\n" + symptom.getName() + "\n" + symptom.getSevereness() + "\n" + "\n");

        }

        pw.close();
    }

}