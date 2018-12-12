package com.endurancecode.inventoryappstageone;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

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
