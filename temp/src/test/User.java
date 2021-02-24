package test;

/**
 * Abstract class for Physician and Patient fo sharing attributes and methods
 */
abstract class User {
    private LatLong geolocation;
    private String UserType;
    private String passwordhash;
    private String emailAddress;
    private String firstName;
    private String lastName;
    private String city;
    private String street;
    private String houseNumber;
    private String postalCode;
    private String phoneNUmber;
    private String title;// = {"Prof.","Dr.","Dipl-Ing","B.Sc.","B.A.","B.Eng.","B.F.A.","B.Mus.","M.Sc.","M.A.","M.Eng.","Magister"};

    public LatLong getGeolocation() {
        return geolocation;
    }

    public void setGeolocation(LatLong geolocation) {
        this.geolocation = geolocation;
    }

    public String getUserType() {
        return UserType;
    }

    public void setUserType(String userType) {
        UserType = userType;
    }



    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getPhoneNUmber() {
        return phoneNUmber;
    }

    public void setPhoneNUmber(String phoneNumber) {
        this.phoneNUmber = phoneNumber;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPasswordhash(String password) {
        try {
            this.passwordhash = hashPassword(password);

        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * Hashes a entered password with the BCrypt algorithm open-source from the Internet
     */
    public static String hashPassword(String pw) {
        pw = org.mindrot.jbcrypt.BCrypt.hashpw(pw, org.mindrot.jbcrypt.BCrypt.gensalt());
        return pw;
    }

    public String getPasswordhash() {
        return passwordhash;
    }

    public void printAttr() {
        System.out.print(this.emailAddress + " " + this.firstName + " " + this.lastName + " " + this.title + " " + this.city + " " + this.street + " " + this.houseNumber + " " + this.postalCode + " " + this.phoneNUmber);
    }
}
