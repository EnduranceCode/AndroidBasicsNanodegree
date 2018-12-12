package com.endurancecode.inventoryappstageone.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.endurancecode.inventoryappstageone.data.InventoryContract.Products;

/**
 * Database helper for Inventory App. Manages database creation and version management.
 */
public class ProductDatabaseHelper extends SQLiteOpenHelper {

    /* Tag for log messages */
    public static final String LOG_TAG = ProductDatabaseHelper.class.getSimpleName();

    /* Name of the database file */
    private static final String DATABASE_FILE = "products.db";

    /**
     * Database version.
     * If and when the database schema is changed, the database version must be incremented.
     */
    private static final int DATABASE_VERSION = 1;

    /**
     * Constructs a new instance of {@link ProductDatabaseHelper}.
     *
     * @param context of the app
     */
    public ProductDatabaseHelper(Context context) {
        super(context, DATABASE_FILE, null, DATABASE_VERSION);
    }


    /**
     * This is called when the database is created for the first time.
     */
    @Override
    public void onCreate(SQLiteDatabase database) {

        /* Create a String that contains the SQL statement to create the products table */
        String SQL_CREATE_PRODUCTS_TABLE = "CREATE TABLE " + Products.TABLE_NAME + "("
                + Products._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Products.PRODUCT_NAME + " TEXT NOT NULL, "
                + Products.PRICE + " REAL NOT NULL DEFAULT 0, "
                + Products.QUANTITY + " INTEGER NOT NULL DEFAULT 0, "
                + Products.SUPPLIER + " TEXT, "
                + Products.SUPPLIER_PHONE + " TEXT);";

        /* Logs the SQL statement that creates the product's table */
        Log.e(LOG_TAG, "Table creation's SQL statement: " + SQL_CREATE_PRODUCTS_TABLE);

        /* Execute the SQL statement */
        database.execSQL(SQL_CREATE_PRODUCTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        /* The database is still at version 1, so there's nothing to do be done here */
    }
}
