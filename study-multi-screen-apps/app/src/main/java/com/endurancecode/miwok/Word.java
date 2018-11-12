package com.endurancecode.miwok;

/**
 * {@link Word} represents a vocabulary word that the user wants to learn.
 * It contains a default translation and a Miwok translation for that word.
 */
public class Word {
    /* Constant value that represents no image was provided for this word */
    private static final int NO_IMAGE_PROVIDED = -1;
    /**
     * Define the string variables that store the different languages translations
     */
    /* Default translation word */
    private String mDefaultTranslation;
    /* Miwok translation word */
    private String mMiwokTranslation;
    /* Image resource id of the image representing the word */
    private int mImageResourceId = NO_IMAGE_PROVIDED;
    /* Audio resource id of the word's pronunciation */
    private int mAudioResourceId;

    /**
     * Constructs a new Word object
     *
     * @param defaultTranslation    word in the user's default language (English in this project)
     * @param miwokTranslation      word in the Miwok language
     * @param audioResourceId       audio with the Miwok word pronunciation
     */
    public Word(String defaultTranslation, String miwokTranslation, int audioResourceId) {
        mDefaultTranslation = defaultTranslation;
        mMiwokTranslation = miwokTranslation;
        mAudioResourceId = audioResourceId;
    }

    /**
     * Constructs a new Word object
     *
     * @param defaultTranslation    word in the user's default language (English in this project)
     * @param miwokTranslation      word in the Miwok language
     * @param imageResourceId       drawable resource ID for the image associated with the word
     * @param audioResourceId       audio resource ID for the Miwok word pronunciation
     */
    public Word(String defaultTranslation, String miwokTranslation, int imageResourceId, int audioResourceId) {
        mDefaultTranslation = defaultTranslation;
        mMiwokTranslation = miwokTranslation;
        mImageResourceId = imageResourceId;
        mAudioResourceId = audioResourceId;
    }

    /**
     * Methods to get the string variables that store the different languages translations
     */
    /* Method to get the default translation */
    public String getDefaultTranslation() {
        return mDefaultTranslation;
    }

    /* Method to get the Miwok translation */
    public String getMiwokTranslation() {
        return mMiwokTranslation;
    }

    /* Method to get the image resource ID */
    public int getImageResourceId() {
        return mImageResourceId;
    }

    /* Method to get the existence or not of an image */
    public boolean hasImage() {
        return mImageResourceId != NO_IMAGE_PROVIDED;
    }

    /* Method to get the audio resource ID */
    public int getAudioResourceId() {
        return mAudioResourceId;
    }

    /* Print out all the variables in the Word class as a string */
    @Override
    public String toString() {
        return "Word{" +
                "mDefaultTranslation = '" + mDefaultTranslation + "\'" +
                ", mMiwokTranslation = '" + mMiwokTranslation + "\'" +
                ", mAudioResourceID = '" + mAudioResourceId + "\'" +
                ", mImageResourceID = '" + mImageResourceId +
                "}";
    }
}