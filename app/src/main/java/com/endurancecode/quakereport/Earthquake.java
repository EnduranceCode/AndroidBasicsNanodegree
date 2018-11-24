package com.endurancecode.quakereport;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

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

    /* Variable for the time of the earthquake
     * in milliseconds since the epoch (1970-01-01T00:00:00.000Z)
     */
    private long time;

    /* Variable for the date of the earthquake
     * in human readable format
     */
    private String dateHuman;

    /**
     * Constructs a new {@link Earthquake} object
     *
     * @param magnitude float   magnitude of the earthquake
     * @param location  String  location of the earthquaque
     * @param time      Date    time of the earthquake im milliseconds since epoch (1970-01-01T00:00:00.000Z)
     */
    public Earthquake(double magnitude, String location, long time) {
        this.magnitude = magnitude;
        this.location = location;
        this.time = time;
        /* Inspiration from http://envyandroid.com/java-milliseconds-to-readable-date */
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault());
        this.dateHuman = dateFormat.format(new Date(time));
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

    /* Method to get the time of the {@link Earthquake} object */
    public long getTime() {
        return time;
    }

    /* Method to get the time of the {@link Earthquake} object */
    public String getDateHuman() {
        return dateHuman;
    }
}
