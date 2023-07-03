package com.HelloWay.HelloWay.DistanceLogic;

import com.HelloWay.HelloWay.entities.Space;

public class DistanceCalculator {
    private  static final double EARTH_RADIUS = 6371; // Earth's radius in kilometers

    public static double calculateDistance(double userLatitude, double userLongitude, double cafeLatitude, double cafeLongitude) {
        // Convert latitude and longitude to radians
        double userLatRad = Math.toRadians(userLatitude);
        double userLonRad = Math.toRadians(userLongitude);
        double cafeLatRad = Math.toRadians(cafeLatitude);
        double cafeLonRad = Math.toRadians(cafeLongitude);

        // Calculate the differences between the latitude and longitude coordinates
        double latDiff = cafeLatRad - userLatRad;
        double lonDiff = cafeLonRad - userLonRad;

        // Apply the Haversine formula
        double a = Math.pow(Math.sin(latDiff / 2), 2) +
                Math.cos(userLatRad) * Math.cos(cafeLatRad) *
                        Math.pow(Math.sin(lonDiff / 2), 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = EARTH_RADIUS * c;

        return distance;
    }


    /**
     *
     * @param userLatitude :
     * @param userLongitude
     * @param space
     * @return Boolean to say if the user in the space or not
     */
    public static  Boolean isTheUserInTheSpaCe(String userLatitude,
                                               String userLongitude,
                                               Space space
                                               ){
        String spaceLatitude = space.getLatitude();
        String spaceLongitude = space.getLongitude();

        // double cast = space.getSurfaceEnM2()/Math.pow(10,6) from m2 to km2 for the Rayon
        // then threshold = sqrt(cast/Math.Pi)
        double threshold = 5.0; // Threshold distance in kilometers
        double distance = calculateDistance(Double.parseDouble(userLatitude), Double.parseDouble(userLongitude), Double.parseDouble(spaceLatitude), Double.parseDouble(spaceLongitude));
        if (distance <= threshold) {
            System.out.println("User is near the cafe.");
            return true;
        }
        else {
            System.out.println("User is not near the cafe.");
            return false ;
        }
    }
}
