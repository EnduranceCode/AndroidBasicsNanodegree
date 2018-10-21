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
     * Constructs a new Word with initial values for default translation and Miwok translation
     */
    public Word (String defaultTranslation, String miwokTranslation) {
        mDefaultTranslation = defaultTranslation;
        mMiwokTranslation = miwokTranslation;
    }

    /**
     * Methods to get the string variables that store the different languages translations
     */

    // Method to get the default translation
    public String getDefaultTranslation () {
        return mDefaultTranslation;
    }

    // Method to get the Miwok translation
    public String getMiwokTranslation () {
        return mMiwokTranslation;
    }
}