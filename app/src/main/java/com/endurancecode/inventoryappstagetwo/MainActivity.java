package com.endurancecode.inventoryappstagetwo;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.endurancecode.inventoryappstagetwo.data.InventoryContract.Products;
import com.endurancecode.inventoryappstagetwo.data.ProductDatabaseHelper;


public class MainActivity extends AppCompatActivity {

    /* Database helper that provides access to the database */
    private ProductDatabaseHelper productDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         * Test the database java code manipulation
         */
        testDatabaseJavaManipulationCode();
    }

    /**
     * Temporary method to test the database java manipulation code
     */
    private void testDatabaseJavaManipulationCode() {

        /* Test the insertData() method */
        insertData("Product", 12.34, 1, "Supplier", "243 000 000");

        /* Test the queryData() method */
        Cursor queryDatabaseCursor = queryData();

        /* Get the temporary TextView to test the App */
        TextView temporaryTestTextView = (TextView) findViewById(R.id.temporary);

        try {
            /*
             * Create a header in the Text View that looks like this:
             *
             * The pets table contains <number of rows in Cursor> pets.
             * _id - name - price - quantity - supplier -- supplier_phone
             *
             * In the while loop below, iterate through the rows of the cursor and display
             * the information from each column in this order.
             */
            temporaryTestTextView.setText("The product's table contains "
                    + queryDatabaseCursor.getCount()
                    + " products \n\n");
            temporaryTestTextView.append(Products._ID + " - "
                    + Products.PRODUCT_NAME + " - "
                    + Products.PRICE + " - "
                    + Products.QUANTITY + " - "
                    + Products.SUPPLIER + " - "
                    + Products.SUPPLIER_PHONE + "\n");

            /* Get the index of the table's columns */
            int idColumnIndex = queryDatabaseCursor.getColumnIndex(Products._ID);
            int nameColumnIndex = queryDatabaseCursor.getColumnIndex(Products.PRODUCT_NAME);
            int priceColumnIndex = queryDatabaseCursor.getColumnIndex(Products.PRICE);
            int quantityColumnIndex = queryDatabaseCursor.getColumnIndex(Products.QUANTITY);
            int supplierColumnIndex = queryDatabaseCursor.getColumnIndex(Products.SUPPLIER);
            int phoneColumnIndex = queryDatabaseCursor.getColumnIndex(Products.SUPPLIER_PHONE);

            /* Iterate through all the returned rows in the cursor */
            while (queryDatabaseCursor.moveToNext()) {

                /*
                 * Use that index to extract the String or Int value of the word
                 * at the current row the cursor is on.
                 */
                int currentID = queryDatabaseCursor.getInt(idColumnIndex);
                String currentName = queryDatabaseCursor.getString(nameColumnIndex);
                double currentPrice = queryDatabaseCursor.getDouble(priceColumnIndex);
                int currentQuantity = queryDatabaseCursor.getInt(quantityColumnIndex);
                String currentSupplier = queryDatabaseCursor.getString(supplierColumnIndex);
                String currentPhone = queryDatabaseCursor.getString(phoneColumnIndex);

                /* Display the values from each column of the current row in the cursor in the TextView */
                temporaryTestTextView.append("\n" + currentID + " - "
                        + currentName + " - "
                        + currentPrice + " - "
                        + currentQuantity + " - "
                        + currentSupplier + " - "
                        + currentPhone);
            }
        } finally {
            queryDatabaseCursor.close();
        }
    }

    /**
     * Method to insert data in the database
     *
     * @param productName   name of the product
     * @param price         price of the product
     * @param quantity      quantity in stock of the product
     * @param supplier      product's supplier
     * @param supplierPhone product's supplier phone number
     */
    private void insertData(String productName, double price, int quantity, String supplier, String supplierPhone) {

        /* Create database helper */
        productDatabase = new ProductDatabaseHelper(this);

        /* Gets the database in write mode */
        SQLiteDatabase sqLiteDatabase = productDatabase.getWritableDatabase();

        /*
         * Create a ContentValues object where column names are the keys,
         * and pet attributes from the editor are the values.
         */
        ContentValues values = new ContentValues();
        values.put(Products.PRODUCT_NAME, productName);
        values.put(Products.PRICE, price);
        values.put(Products.QUANTITY, quantity);
        values.put(Products.SUPPLIER, supplier);
        values.put(Products.SUPPLIER_PHONE, supplierPhone);

        /* Insert a new product in the database, returning the ID of that new row */
        long newRowID = sqLiteDatabase.insert(Products.TABLE_NAME, null, values);

        /* Show a toast message depending on whether or not the insertion was successful */
        if (newRowID == -1) {
            Toast.makeText(this, "Error with inserting the new product", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Product insert at line " + newRowID, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Method to query the database
     */
    private Cursor queryData() {

        /* Create database helper */
        productDatabase = new ProductDatabaseHelper(this);

        /* Create and/or open a database to read from it */
        SQLiteDatabase sqLiteDatabase = productDatabase.getReadableDatabase();

        /*
         * Define a projection that specifies which columns from the database
         * you will actually use after this query.
         */
        String[] projection = {
                Products._ID,
                Products.PRODUCT_NAME,
                Products.PRICE,
                Products.QUANTITY,
                Products.SUPPLIER,
                Products.SUPPLIER_PHONE};

        /*
         * Performs a query to the products table of the inventory database and
         * returns the result
         */
        return sqLiteDatabase.query(
                Products.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null,
                null);
    }
}
