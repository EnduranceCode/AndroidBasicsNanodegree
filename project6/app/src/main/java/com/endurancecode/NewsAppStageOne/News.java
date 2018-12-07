package com.endurancecode.NewsAppStageOne;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Class to store the information about the news listed in the app
 */
public class News {

    /* Tag for log messages */
    private static final String LOG_TAG = News.class.getName();

    /**
     * Object's variable declarations
     */
    /* Variable for the result's section name */
    private String sectionName;

    /* Variable for the result's day */
    private String day;

    /* Variable for the result's month */
    private String month;

    /* Variable for the result's web title */
    private String webTitle;

    /* Variable for the result's web url */
    private String webUrl;

    /* Variable for the result's byline field (author) */
    private String byline;

    /**
     * Constructs a new {@link News} object
     *
     * @param sectionName        section name of the {@link News} object.
     * @param webPublicationDate web publication date of the {@link News} object. Can be null.
     * @param webTitle           web publication title of the {@link News} object.
     * @param webUrl             web url of the {@link News} object.
     * @param byline             byline field (author) of the {@link News} object.  Can be null.
     */
    public News(String sectionName, String webPublicationDate, String webTitle, String webUrl, String byline) {
        this.sectionName = sectionName;
        this.webTitle = webTitle;
        this.webUrl = webUrl;
        this.byline = byline;

        /* Variable for the result's date */
        Date publicationDateObject;

        /*
         * The following date conversions are inspired in TestDateExample5.java published in
         * https://www.mkyong.com/java/how-to-convert-string-to-date-java
         */
        if (webPublicationDate != null) {
            try {
                publicationDateObject = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.getDefault())
                        .parse(webPublicationDate.replaceAll("Z$", "+0000"));
            } catch (ParseException e) {
                Log.e(LOG_TAG, "Error parsing the web publication date (webPublicationDate)", e);
                publicationDateObject = null;
            }
        } else {
            publicationDateObject = null;
        }

        if (publicationDateObject != null) {
            /* Get day of webPublicationDate as a String */
            day = new SimpleDateFormat("d", Locale.getDefault())
                    .format(publicationDateObject);

            /* Get mont of webPublicationDate as a String */
            month = new SimpleDateFormat("MMM", Locale.getDefault())
                    .format(publicationDateObject);
        } else {
            day = null;
            month = null;
        }
    }

    /**
     * Methods to get the properties of the {@link News} objects
     */
    /* Method to get the {@link News} object's section name*/
    public String getSectionName() {
        return sectionName;
    }

    /* Method to get the {@link News} object's day */
    public String getDay() {
        return day;
    }

    /* Method to get the {@link News} object's month */
    public String getMonth() {
        return month;
    }

    /* Method to get the {@link News} object's webTitle */
    public String getWebTitle() {
        return webTitle;
    }

    /* Method to get the {@link News} object's webUrl */
    public String getWebUrl() {
        return webUrl;
    }

    /* Method to get the {@link News} object's byline */
    public String getByline() {
        return byline;
    }
}
