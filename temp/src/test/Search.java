package test;

import java.sql.SQLException;

public class Search {

    public static Physician findPhysician(){
        return null;
    }

    public static void distantSearch(String emailPat) throws SQLException, ClassNotFoundException {

        Databaseconnection databaseconnection = new Databaseconnection();
        Patient pat = databaseconnection.getPatient(emailPat);

        Physician [] phy = databaseconnection.searchPhysician(pat.getSymptoms());

        for (Physician phy1 : phy) {
            System.out.print(LatLong.distanceInKm(pat.getGeolocation(), phy1.getGeolocation())+"km ");

        }

    }


}
