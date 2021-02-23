package test;
import java.util.Date;

public class Physician extends User {
    private String [] specialization;


    Physician(String _email, String _first, String _last){
        setFirstName(_first);
        setEmailAddress(_email);
        setLastName(_last);
        setUserType("Physician");
    }
    Physician(String _emailAddress, String _firstName, String _lastName, String _city,
              String _street, String _houseNumber, String _postalCode, String _phoneNumber, String _title, String _password){
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
        setUserType("Physician");

    }

    Physician(String _emailAddress, String _firstName, String _lastName, String _city,
              String _street, String _houseNumber, String _postalCode, String _phoneNumber, String _title, String _password, String[] _specialization) {
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
        setSpecialization(_specialization);
        setUserType("Physician");
    }

    Physician(String _emailAddress, String _firstName, String _lastName, String _city,
              String _street, String _houseNumber, String _postalCode, String _phoneNumber, String _title, String _password, String[] _specialization, LatLong _geolocation) {
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
        setSpecialization(_specialization);
        setUserType("Physician");
        setGeolocation(_geolocation);
    }

    public String[] getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String[] _specialization) {
        this.specialization = _specialization;
    }
}





