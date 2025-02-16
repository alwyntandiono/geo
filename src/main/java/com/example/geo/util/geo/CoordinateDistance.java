package com.example.geo.util.geo;

public class CoordinateDistance {

    /**
     * Calculate distance between two points in latitude and longitude, using
     * Haversine formula.
     *
     * Reference: https://stackoverflow.com/a/16794680
     *
     * The distance is then scaled/normalized from 0 to 1, with 0 representing
     * 40075017 / 2 = 20037508.5 km, and 1 representing 0 km. The normalization is
     * needed as part of the score calculation.
     *
     * Rationale:
     * The earth's circumference is 40075017 km, that is the distance that needs to
     * be traveled if you were to start from one point, circle around the earth, and
     * return to the starting point. As you are travelling in circle, the furthest
     * point from your starting point would be exactly half of the travelled
     * distance (the circumference). Therefore, you can start from anywhere on the
     * earth
     * and reach anywhere else, by travelling at most 40075017 / 2 = 20037508.5 km.
     *
     * @returns distance in km, scaled from 0 to 1
     */
    public static double calculate(double lat1, double lon1, double lat2, double lon2) {
        final int EARTH_CIRCUMFERENCE = 40075017;
        return 1 - (haversineDistance(lat1, lat2, lon1, lon2) * 0.5 / EARTH_CIRCUMFERENCE);
    }

    private static double haversineDistance(double lat1, double lat2, double lon1, double lon2) {
        final int EARTH_RADIUS = 6371;
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                        * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return EARTH_RADIUS * c;
    }
}
