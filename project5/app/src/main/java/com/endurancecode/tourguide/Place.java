package com.endurancecode.tourguide;

/**
 * Class to store the information about the places listed in the app
 */
public class Place {
    /**
     * Variable declarations
     */
    /* Variable for the address of the place */
    private String mAddress;

    /* Variable for the image resource ID of the place */
    private int mCardImageResourceID;

    /* Variable for the category of the place */
    private String mCategory;

    /* Variable for the gps coordinates of the place */
    private double[] mCoordinates;

    /* Variable for the description of the place */
    private String mDescription;

    /* Variable for the e-mail of the place */
    private String mEmail;

    /* Variable for the opening hour of the place */
    private String[] mOpeningHours;

    /* Variable for the telephone number of the place */
    private String mPhone;

    /* Variable for the subtitle or tag line of the place*/
    private String mSubtitle;

    /* Variable for the title or designation of the place */
    private String mTitle;

    /* Variable for the website url of the place */
    private String mWebsiteURL;

    /**
     * Constructs a new {@link Place} object
     *
     * @param address             String      address of the {@link Place} object.
     * @param cardImageResourceID int         card image resource ID of the {@link Place} object.
     * @param category            String      category of the {@link Place} object.
     * @param coordinates         double[]    coordinates of the {@link Place} object in decimal degrees.
     * @param description         String      description of the {@link Place} object.
     * @param email               String      e-mail of the {@link Place} object. Can be null.
     * @param openingHours        String[]    opening hours of the {@link Place} object. Can be null.
     * @param phone               String      phone number of the {@link Place} object. Can be null.
     * @param subtitle            String      subtitle of the {@link Place} object. Can be null.
     * @param title               String      title of the {@link Place} object.
     * @param websiteURL          String      URL for the website of the {@link Place} object. Can be null.
     */
    public Place(String address, int cardImageResourceID, String category, double[] coordinates, String description, String email, String[] openingHours, String phone, String subtitle, String title, String websiteURL) {
        mAddress = address;
        mCardImageResourceID = cardImageResourceID;
        mCategory = category;
        mCoordinates = coordinates;
        mDescription = description;
        mEmail = email;
        mOpeningHours = openingHours;
        mPhone = phone;
        mSubtitle = subtitle;
        mTitle = title;
        mWebsiteURL = websiteURL;
    }

    /**
     * Methods to get the properties of the {@link Place} objects
     */
    /* Method to get the address of the Place object */
    public String getAddress() {
        return mAddress;
    }

    /* Method to get the card image resource ID of the Place object */
    public int getCardImageResourceID() {
        return mCardImageResourceID;
    }

    /* Method to get the category of the Place object */
    public String getCategory() {
        return mCategory;
    }

    /* Method to get the coordinates of the Place object */
    public double[] getCoordinates() {
        return mCoordinates;
    }

    /* Method to get the description of the Place object */
    public String getDescription() {
        return mDescription;
    }

    /* Method to get the e-mail of the Place object */
    public String getEmail() {
        return mEmail;
    }

    /* Method to get the opening hours of the Place object */
    public String[] getOpeningHours() {
        return mOpeningHours;
    }

    /* Method to get the phone number of the Place object */
    public String getPhone() {
        return mPhone;
    }

    /* Method to get the subtitle of the Place object */
    public String getSubtitle() {
        return mSubtitle;
    }

    /* Method to get the title of the Place object */
    public String getTitle() {
        return mTitle;
    }

    /* Method to get the website URL of the Place object */
    public String getWebsiteURL() {
        return mWebsiteURL;
    }
}
