package com.endurancecode.miwok;

/**
 * {@link Word} represents a vocabulary word that the user wants to learn.
 * It contains a default translation and a Miwok translation for that word.
 */
public class Word {
    /**
     *  Define the string variables that store the different languages translations
     */

    // Variable that stores the default translation word
    private String mDefaultTranslation;

    // Variable that stores Miwok translation word
    private String mMiwokTranslation;

    /**
     * Constant value that represents no image was provided for this word
     */
    private static final int NO_IMAGE_PROVIDED = -1;
    /**
     * Variable that stores the image resource id of the image representing the word
     */
    private int mImageResourceId = NO_IMAGE_PROVIDED;

    /* Constructs a new Word with initial values for default translation and Miwok translation
     */
    public Word (String defaultTranslation, String miwokTranslation) {
        mDefaultTranslation = defaultTranslation;
        mMiwokTranslation = miwokTranslation;
    }


    /* Constructs a new Word with initial values for default translation, Miwok translation and
     * imageResourceId
     */
    public Word(String defaultTranslation, String miwokTranslation, int imageResourceId) {
        mDefaultTranslation = defaultTranslation;
        mMiwokTranslation = miwokTranslation;
        mImageResourceId = imageResourceId;
    }

    /**
     * Methods to get the string variables that store the different languages translations
     */

    // Method to get the default translation
    public String getDefaultTranslation() {
        return mDefaultTranslation;
    }

    // Method to get the Miwok translation
    public String getMiwokTranslation() {
        return mMiwokTranslation;
    }

    // Method to get the image resource id
    public int getImageResourceId() {
        return mImageResourceId;
    }

    // Method to get the existence or not of an image
    public boolean hasImage() {
        return mImageResourceId != NO_IMAGE_PROVIDED;
    }
}