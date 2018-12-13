package com.endurancecode.inventoryappstageone.data;

import android.provider.BaseColumns;

public final class InventoryContract {

    /*
     * To prevent someone from accidentally instantiating the contract class,
     * make the constructor private.
     */
    private InventoryContract() {
    }

    /* Inner class that defines the table contents */
    public static class Products implements BaseColumns {

        /* Name of database table for products */
        public static final String TABLE_NAME = "products";

        /**
         * Unique ID number for the product
         * <p>
         * Type: INTEGER
         */
        public static final String _ID = BaseColumns._ID;

        /**
         * Name of the product
         * <p>
         * Type: TEXT
         */
        public static final String PRODUCT_NAME = "name";

        /**
         * Price of the product
         * <p>
         * Type: REAL
         */
        public static final String PRICE = "price";

        /**
         * Quantity of the product
         * <p>
         * Type: INTEGER
         */
        public static final String QUANTITY = "quantity";

        /**
         * Name of the product's supplier
         * <p>
         * Type: TEXT
         */
        public static final String SUPPLIER = "supplier";

        /**
         * Phone number of the product's supplier
         * <p>
         * Type: TEXT
         */
        public static final String SUPPLIER_PHONE = "supplier_phone";
    }
}
