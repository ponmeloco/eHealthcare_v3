package test;


import java.io.IOException;

class Physician extends User {
    private String [] specialization;


    Physician(String _email, String _first, String _last){
        setFirstName(_first);
        setEmailAddress(_email);
        setLastName(_last);
        setUserType("Physician");
    }
    Physician(String _emailAddress, String _firstName, String _lastName, String _city,
              String _street, String _houseNumber, String _postalCode, String _phoneNumber, String _title, String _password) throws IOException, InterruptedException {
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
        setGeolocation(Geocoder.decode(_city +" "+ _street +" "+ _houseNumber +" "+ _postalCode));
        setUserType("Physician");

    }


    Physician(String _emailAddress, String _firstName, String _lastName, String _city,
              String _street, String _houseNumber, String _postalCode, String _phoneNumber, String _title, String _password, String[] _specialization) throws IOException, InterruptedException {
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
        setGeolocation(Geocoder.decode(_city+" "+_street+" "+_houseNumber+" "+_postalCode));
    }

    Physician(String _emailAddress, String _firstName, String _lastName, String _city,
              String _street, String _houseNumber, String _postalCode, String _phoneNumber, String [] _specialization, String _password) throws IOException, InterruptedException {
        setEmailAddress(_emailAddress);
        setFirstName(_firstName);
        setLastName(_lastName);
        setCity(_city);
        setStreet(_street);
        setHouseNumber(_houseNumber);
        setPostalCode(_postalCode);
        setPhoneNUmber(_phoneNumber);
        setSpecialization(_specialization);
        setPasswordhash(_password);
        setUserType("Physician");
        setGeolocation(Geocoder.decode(_city+" "+_street+" "+_houseNumber+" "+_postalCode));
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





