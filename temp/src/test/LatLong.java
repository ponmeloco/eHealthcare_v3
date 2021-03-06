package test;


/**
 * Class with two parameters (latitude, longitude) for easier handling the distances for patient and physician
 */
class LatLong {

    private double latitude;
    private double longitude;


    LatLong(double latitude, double longitude){
        setLatitude(latitude);
        setLongitude(longitude);
    }

    /**
     * Calculates the distance in kilometer from two given latitudes and longitudes as double
     */
    public static double distanceInKm(LatLong patient, LatLong physician) {
        int radius = 6371;

        double lat = Math.toRadians(physician.latitude - patient.latitude);
        double lon = Math.toRadians(physician.longitude - patient.longitude);

        double a = Math.sin(lat / 2) * Math.sin(lat / 2) + Math.cos(Math.toRadians(patient.latitude)) * Math.cos(Math.toRadians(physician.latitude)) * Math.sin(lon / 2) * Math.sin(lon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double d = radius * c;
        return Math.floor(Math.abs(d) *100)/100;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

}
