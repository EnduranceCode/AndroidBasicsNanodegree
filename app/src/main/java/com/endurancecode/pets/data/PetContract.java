package com.endurancecode.pets.data;

import android.provider.BaseColumns;

/**
 * API Contract for the Pets app.
 */
public final class PetContract {

    /*
     * To prevent someone from accidentally instantiating the contract class,
     * make the constructor private.
     */
    private PetContract() {
    }

    /* Inner class that defines the table contents */
    public static class PetEntry implements BaseColumns {

        /* Name of the database table for pets */
        public static final String TABLE_NAME = "pets";

        /**
         * Unique ID number for the pet (only for use in the database table
         * <p>
         * Type: INTEGER
         */
        public static final String _ID = BaseColumns._ID;

        /**
         * Name of the pet
         * <p>
         * Type: TEXT
         */
        public static final String COLUMN_PET_NAME = "name";

        /**
         * Breed of the pet
         * <p>
         * Type: TEXT
         */
        public static final String COLUMN_PET_BREED = "breed";

        /**
         * Gender of the pet
         * <p>
         * The only possible values are {@link #GENDER_UNKNOWN}, {@link #GENDER_MALE}
         * and {@link #GENDER_FEMALE}
         * <p>
         * Type: INTEGER
         */
        public static final String COLUMN_PET_GENDER = "gender";

        /**
         * Weight of the pet
         * <p>
         * Type: INTEGER
         */
        public static final String COLUMN_PET_WEIGHT = "weight";

        /**
         * Possible values for the gender of the pet
         */
        public static final int GENDER_UNKNOWN = 0;
        public static final int GENDER_MALE = 1;
        public static final int GENDER_FEMALE = 2;
    }
}
