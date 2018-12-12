package com.endurancecode.inventoryappstageone;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.endurancecode.inventoryappstageone.data.InventoryContract.Products;
import com.endurancecode.inventoryappstageone.data.ProductDatabaseHelper;


public class MainActivity extends AppCompatActivity {

    /* Database helper that provides access to the database */
    private ProductDatabaseHelper productDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
            Toast.makeText(this, "Produto inserido na linha " + newRowID, Toast.LENGTH_SHORT).show();
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
