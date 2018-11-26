package com.endurancecode.quakereport;

/**
 * Class to store the information about the places listed in the app
 */
public class Earthquake {
    /**
     * Variable declarations
     */
    /* Variable for the magnitude of the earthquake */
    private double magnitude;

    /* Variable for the location of the earthquake */
    private String location;

    /* Variable for the timeMilliseconds of the earthquake
     * in milliseconds since the epoch (1970-01-01T00:00:00.000Z)
     */
    private long timeMilliseconds;

    /**
     * Constructs a new {@link Earthquake} object
     *
     * @param magnitude       float   magnitude of the earthquake
     * @param location        String  location of the earthquaque
     * @param timeMiliseconds Date    timeMilliseconds of the earthquake im milliseconds since epoch (1970-01-01T00:00:00.000Z)
     */
    public Earthquake(double magnitude, String location, long timeMiliseconds) {
        this.magnitude = magnitude;
        this.location = location;
        this.timeMilliseconds = timeMiliseconds;
    }

    /**
     * Methods to get the properties of the {@link Earthquake} objects
     */
    /* Method to get the magnitude of the {@link Earthquake} object */
    public double getMagnitude() {
        return magnitude;
    }

    /* Method to get the location of the {@link Earthquake} object */
    public String getLocation() {
        return location;
    }

    /* Method to get the timeMilliseconds of the {@link Earthquake} object */
    public long getTimeMilliseconds() {
        return timeMilliseconds;
    }
}
