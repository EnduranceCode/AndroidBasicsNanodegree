package com.endurancecode.inventoryappstagetwo.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import com.endurancecode.inventoryappstagetwo.data.InventoryContract.Products;

/**
 * {@link ContentProvider} for Inventory App
 * <p>
 * METHODS INDEX
 * -------------
 * - onCreate()
 * - query()
 * - getType()
 * - insert()
 * - insertProduct()
 * - delete()
 * - update()
 */
public class ProductProvider extends ContentProvider {

    /**
     * Tag for the log messages
     */
    public static final String LOG_TAG = ProductProvider.class.getSimpleName();

    /**
     * URI matcher code for the content URI for the products table
     */
    private static final int PRODUCTS = 1000;

    /**
     * URI matcher code for the content URI for a sigle product in the products table
     */
    private static final int PRODUCT_ID = 1001;

    /**
     * UriMatcher object to match a content URI to a corresponding code.
     * The input passed into the constructor represents the code to return for the root URI.
     * It's common to use NO_MATCH as the input for this case.
     */
    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    /* Static initializer. This is run the first time anything is called from this class */
    static {
        /*
         * The calls to addURI() go here, for all of the content URI patterns that the provider
         * should recognize. All paths added to the UriMatcher have a corresponding code to return
         * when a match is found.
         */
        uriMatcher.addURI(InventoryContract.CONTENT_AUTHORITY, InventoryContract.PATH_PRODUCTS, PRODUCTS);
        uriMatcher.addURI(InventoryContract.CONTENT_AUTHORITY, InventoryContract.PATH_PRODUCTS + "/#", PRODUCT_ID);
    }

    /* Database Helper Object */
    ProductDatabaseHelper productDatabaseHelper;

    @Override
    public boolean onCreate() {
        productDatabaseHelper = new ProductDatabaseHelper(getContext());
        return true;
    }

    /**
     * Perform the query for the given URI.
     * Use the given projection, selection, selection arguments, and sort order.
     *
     * @param uri           URI to use in the query
     * @param projection    Columns to include in the resulting Cursor
     * @param selection     Selection clause
     * @param selectionArgs Selection arguments
     * @param sortOrder     Sort order
     */
    @Override
    public Cursor query(Uri uri,
                        String[] projection,
                        String selection,
                        String[] selectionArgs,
                        String sortOrder) {

        /* Get readable database */
        SQLiteDatabase database = productDatabaseHelper.getReadableDatabase();

        /* This cursor will hold the result of the query */
        Cursor cursor;

        /* Figure out if the URI matcher can match the URI to a specific code */
        final int match = uriMatcher.match(uri);

        switch (match) {
            case PRODUCTS:
                /*
                 * For the PRODUCTS code, query the pets table directly with the given
                 * projection, selection, selection arguments, and sort order.
                 * The cursor could contain multiple rows of the pets table.
                 */
                cursor = database.query(Products.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            case PRODUCT_ID:
                /*
                 * For the PRODUCT_ID code, extract out the ID from the URI.
                 * For an example URI such as
                 * "content://com.endurancecode.inventoryappstagetwo/products/3", the selection
                 * will be "_id=?" and the selection argument will be a String array containing
                 * the actual ID of 3 in this case.
                 *
                 * For every "?" in the selection, we need to have an element in the selection
                 * arguments that will fill in the "?". Since we have 1 question mark in the
                 * selection, we have 1 String in the selection arguments' String array.
                 */
                selection = Products._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                cursor = database.query(Products.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            default:
                throw new IllegalArgumentException(LOG_TAG + ": Cannot query unknown URI " + uri);
        }

        /*
         * Set notification URI on the Cursor,
         * so we know what content URI the Cursor was created for.
         * If the data at this URI changes, then we know we need to update the Cursor
         */
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        /* Return the cursor */
        return cursor;
    }

    /**
     * Returns the MIME type of data for the content URI.
     */
    @Override
    public String getType(Uri uri) {

        /* Figure out if the URI matcher can match the URI to a specific code */
        final int match = uriMatcher.match(uri);

        switch (match) {
            case PRODUCTS:
                return Products.CONTENT_LIST_TYPE;
            case PRODUCT_ID:
                return Products.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalArgumentException(
                        LOG_TAG + ": Unknown URI " +
                                uri + " with match " +
                                match);
        }
    }

    /**
     * Insert new data into the provider with the given ContentValues
     *
     * @param uri           URI to use in the data insertion
     * @param contentValues Values to insert in the database
     */
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {

        /* Figure out if the URI matcher can match the URI to a specific code */
        final int match = uriMatcher.match(uri);

        switch (match) {
            case PRODUCTS:
                return insertProduct(uri, contentValues);
            default:
                throw new IllegalArgumentException(LOG_TAG + ": Insertion is not supported for " + uri);
        }
    }

    /**
     * Insert a product into the database with the given content values.
     * Return the new content URI for that specific row in the database.
     *
     * @param uri           URI to use in the data insertion
     * @param contentValues Values to insert in the database
     */
    private Uri insertProduct(Uri uri, ContentValues contentValues) {

        /*
         * We don't want to insert invalid data in the database,
         * so we have to validate the given data before insert it in the database
         *
         * To validate the given data we have to get it first
         */
        String name = contentValues.getAsString(Products.PRODUCT_NAME);
        double price = contentValues.getAsDouble(Products.PRICE);
        int quantity = contentValues.getAsInteger(Products.QUANTITY);
        String supplier = contentValues.getAsString(Products.SUPPLIER);
        String supplierPhone = contentValues.getAsString(Products.SUPPLIER_PHONE);

        /* Check that the name is invalid */
        if (Products.isInvalidProductName(name)) {
            throw new IllegalArgumentException(LOG_TAG + ": Product requires a valid name");
        }

        /* Check that the price is invalid */
        if (Products.isInvalidPrice(price)) {
            throw new IllegalArgumentException(LOG_TAG + ": Product requires a valid price");
        }

        /* Check that the quantity is invalid */
        if (Products.isInvalidQuantity(quantity)) {
            throw new IllegalArgumentException(LOG_TAG + ": Product requires a valid quantity");
        }

        /* Check that the supplier is invalid */
        if (Products.isInvalidSupplier(supplier)) {
            throw new IllegalArgumentException(LOG_TAG + ": Product requires a valid supplier");
        }

        /* Check that the supplier's phone is invalid */
        if (Products.isInvalidSupplierPhone(supplierPhone)) {
            throw new IllegalArgumentException(LOG_TAG + ": Product requires a valid supplier's phone");
        }

        /* Get writable database */
        SQLiteDatabase database = productDatabaseHelper.getWritableDatabase();

        /* Insert the new product with the given values */
        long id = database.insert(Products.TABLE_NAME, null, contentValues);

        /* If the ID is -1, then the insertion failed. Log an error and return null. */
        if (id == -1) {
            Log.e(LOG_TAG, "Failed to insert data for " + uri);
            return null;
        } else {
            /* Notify all listeners that the data has changed for the pet content URI */
            getContext().getContentResolver().notifyChange(uri, null);

            /* Return the new URI with the ID appended to the end of it */
            return ContentUris.withAppendedId(uri, id);
        }
    }

    /**
     * Delete the data at the given selection and selection arguments.
     *
     * @param uri           URI to use in the query
     * @param selection     Selection clause
     * @param selectionArgs Selection arguments
     */
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        /* Get writable database */
        SQLiteDatabase database = productDatabaseHelper.getWritableDatabase();

        /* Track the number of deleted rows */
        int rowsDeleted;

        /* Figure out if the URI matcher can match the URI to a specific code */
        final int match = uriMatcher.match(uri);

        switch (match) {
            case PRODUCTS:
                /* Delete all rows that match the selection and selection args */
                rowsDeleted = database.delete(Products.TABLE_NAME, selection, selectionArgs);
                break;
            case PRODUCT_ID:
                /* Delete a single row given by the ID in the URI */
                selection = Products._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                rowsDeleted = database.delete(Products.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException(LOG_TAG + ": Deletion is no supported for " + uri);
        }


        /*
         * If 1 or more rows were deleted, then notify all listeners that the data at the
         * given URI has changed
         */
        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        /* Return the number of deletes rows */
        return rowsDeleted;
    }

    /**
     * Updates the data at the given selection and selection arguments, with the new ContentValues.
     *
     * @param uri           URI to use in the query
     * @param contentValues Values to insert in the database
     * @param selection     Selection clause
     * @param selectionArgs Selection arguments
     */
    @Override
    public int update(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {

        /* Figure out if the URI matcher can match the URI to a specific code */
        int match = uriMatcher.match(uri);

        switch (match) {
            case PRODUCTS:
                return updateProduct(uri, contentValues, selection, selectionArgs);
            case PRODUCT_ID:
                selection = Products._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                return updateProduct(uri, contentValues, selection, selectionArgs);
            default:
                throw new IllegalArgumentException(LOG_TAG + ": Update is not supported for " + uri);
        }
    }

    /**
     * Update pets in the database with the given content values. Apply the changes to the rows
     * specified in the selection and selection arguments (which could be 0 or 1 or more pets).
     * Return the number of rows that were successfully updated.
     */
    private int updateProduct(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {

        /*
         * We don't want to insert invalid data in the database,
         * so we have to validate the given data before insert it in the database
         *
         * To validate the given data we have to check what data is present and then
         * we must get it
         *
         * If the {@link Product#PRODUCT_NAME} key is present,
         * check that the name is invalid
         */
        if (contentValues.containsKey(Products.PRODUCT_NAME)) {
            String name = contentValues.getAsString(Products.PRODUCT_NAME);

            if (Products.isInvalidProductName(name)) {
                throw new IllegalArgumentException(LOG_TAG + ": Product requires a valid name");
            }
        }

        /*
         * If the {@Link Product#PRICE} key is present,
         * check that the name is invalid
         */
        if (contentValues.containsKey(Products.PRICE)) {
            double price = contentValues.getAsDouble(Products.PRICE);

            if (Products.isInvalidPrice(price)) {
                throw new IllegalArgumentException(LOG_TAG + ": Product requires a valid price");
            }
        }

        /*
         * If the {@Link Product#QUANTITY} key is present,
         * check that the quantity is invalid
         */
        if (contentValues.containsKey(Products.QUANTITY)) {
            int quantity = contentValues.getAsInteger(Products.QUANTITY);

            if (Products.isInvalidQuantity(quantity)) {
                throw new IllegalArgumentException(LOG_TAG + ": Product requires a valid quantity");
            }
        }

        /*
         * If the {@Link Product#SUPPLIER} key is present,
         * check that the supplier is invalid
         */
        if (contentValues.containsKey(Products.SUPPLIER)) {
            String supplier = contentValues.getAsString(Products.SUPPLIER);

            if (Products.isInvalidSupplier(supplier)) {
                throw new IllegalArgumentException(LOG_TAG + ": Product requires a valid supplier");
            }
        }

        /*
         * If the {@Link Product#SUPPLIER_PHONE} key is present,
         * check that the supplier's phone is invalid
         */
        if (contentValues.containsKey(Products.SUPPLIER_PHONE)) {
            String supplierPhone = contentValues.getAsString(Products.SUPPLIER_PHONE);

            if (Products.isInvalidSupplierPhone(supplierPhone)) {
                throw new IllegalArgumentException(LOG_TAG + ": Product requires a valid suppliers phone");
            }
        }

        /* Track the number of updated rows */
        int rowsUpdated;

        /* If there are no values to update, then don't try to update the database */
        if (contentValues.size() == 0) {
            return 0;
        } else {

            /* Otherwise, get writable database to update the data */
            SQLiteDatabase database = productDatabaseHelper.getWritableDatabase();

            /* Perform the update on the database and get the number of rows affected */
            rowsUpdated = database.update(Products.TABLE_NAME, contentValues, selection, selectionArgs);

            /*
             * If 1 or more rows were updated, then notify all listeners that the data at the
             * given URI has changed
             */
            if (rowsUpdated != 0) {
                getContext().getContentResolver().notifyChange(uri, null);
            }

            /* Returns the number of database rows affected by the update statement */
            return rowsUpdated;
        }
    }
}
