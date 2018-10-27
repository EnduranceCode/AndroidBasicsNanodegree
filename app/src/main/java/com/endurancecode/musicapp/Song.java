package com.endurancecode.musicapp;

/**
 * {@link Song} stores all the data about a song
 */

public class Song {

    /**
     * Variable's declaration
     */
    // The song's Title
    private String mSongTitle;

    // The song's Artist
    private String mSongArtist;

    // The song's Album
    private String mSongAlbum;

    // The song's album CoverArt image resource ID
    private int mSongCoverArtID;

    // The song's year of release
    private int mSongYear;

    // The song's Genre
    private String mSongGenre;

    // The song's Length as a string in mm:ss format
    private String mSongLength;

    /**
     * Constructor for a Song object
     *
     * @param songTitle      The song's Tittle
     * @param songArtist     The song's Artist
     * @param songAlbum      The song's Album
     * @param songCoverArtID The song's Cover Art image resource ID
     * @param songYear       The song's Year of release
     * @param songGenre      The song's Genre
     * @param songLength     The song's Length in mm:ss format
     */
    public Song(String songTitle,
                String songArtist,
                String songAlbum,
                int songCoverArtID,
                int songYear,
                String songGenre,
                String songLength) {
        mSongTitle = songTitle;
        mSongArtist = songArtist;
        mSongAlbum = songAlbum;
        mSongCoverArtID = songCoverArtID;
        mSongYear = songYear;
        mSongGenre = songGenre;
        mSongLength = songLength;
    }

    /**
     * Methods to get the values in Song
     */
    // Get the song's Title
    public String getSongTitle() {
        return mSongTitle;
    }

    // Get the song's Artist
    public String getSongArtist() {
        return mSongArtist;
    }

    // Get the song's Album
    public String getSongAlbum() {
        return mSongAlbum;
    }

    // Get the song's album CoverArt image resource ID
    public int getSongCoverArt() {
        return mSongCoverArtID;
    }

    // Get the song's year of release
    public int getSongYear() {
        return mSongYear;
    }

    // Get the song's Genre
    public String getSongGenre() {
        return mSongGenre;
    }

    // Get the song's Length as a string in mm:ss format
    public String getmSongLength() {
        return mSongLength;
    }
}
