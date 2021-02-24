package test;

import java.sql.SQLException;

class Search {

    /**
     * Returns Array of matching Physicians for the Patients symptoms
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public static Physician[] findPhysician(String emailPat) throws SQLException, ClassNotFoundException {
        Databaseconnection databaseconnection = new Databaseconnection();
        Patient pat = databaseconnection.getPatient(emailPat);

        return databaseconnection.searchPhysician(pat.getSymptoms());
    }

    /**
     * Returns km for all physicians with a matching specialization for the patients symptoms and prints them
     */
    public static void distantSearch(String emailPat) throws SQLException, ClassNotFoundException {

        Databaseconnection databaseconnection = new Databaseconnection();
        Patient pat = databaseconnection.getPatient(emailPat);

        Physician [] phy = databaseconnection.searchPhysician(pat.getSymptoms());

        for (Physician phy1 : phy) {
            System.out.print(phy1.getFirstName()+ " " + phy1.getLastName()+ " " +LatLong.distanceInKm(pat.getGeolocation(), phy1.getGeolocation())+"km \n");

        }

    }


}
