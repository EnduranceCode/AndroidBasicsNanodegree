package com.endurancecode.inventoryappstagetwo;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.endurancecode.inventoryappstagetwo.data.InventoryContract.Products;
import com.endurancecode.inventoryappstagetwo.data.ProductProvider;

/**
 * Display the products details
 */
public class DetailsActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    /**
     * Tag for the log messages
     */
    public static final String LOG_TAG = ProductProvider.class.getSimpleName();
    /**
     * Identifier for the product's data loader
     */
    private static final int DETAILS_PRODUCT_LOADER = 1;
    /**
     * Track the product's name
     */
    private TextView nameTextView;
    /**
     * Track the product's quantity
     */
    private TextView quantityTextView;
    /**
     * Track the product's price
     */
    private TextView priceTextView;
    /**
     * Track the product's supplier
     */
    private TextView supplierTextView;
    /**
     * Track the product's supplier phone neumber
     */
    private TextView supplierPhoneTextView;
    /**
     * Content URI for the existing pet
     */
    private Uri currentProductUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        /* Get the current's product URI */
        Intent detailsActivityIntent = getIntent();
        currentProductUri = detailsActivityIntent.getData();

        Log.e(LOG_TAG, "Current Product URI: " + currentProductUri);

        /* Initialize the loader to get the current's product data */
        getSupportLoaderManager().initLoader(DETAILS_PRODUCT_LOADER, null, this);

        /* Find all relevant TextViews that we will need update with the data from the Cursor */
        nameTextView = findViewById(R.id.name);
        quantityTextView = findViewById(R.id.quantity_value);
        priceTextView = findViewById(R.id.price_value);
        supplierTextView = findViewById(R.id.supplier_value);
        supplierPhoneTextView = findViewById(R.id.supplier_phone_value);
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int i, @Nullable Bundle bundle) {
        /*
         * Since we want to show all products attributes, define a projection that contains
         * all columns from the products table
         */
        String[] projection = {
                Products._ID,
                Products.PRODUCT_NAME,
                Products.QUANTITY,
                Products.PRICE,
                Products.SUPPLIER,
                Products.SUPPLIER_PHONE
        };

        /*
         * This loader will execute the ContentProvider's query method on a background thread
         *
         * Provided input parameters:
         *  - Parent activity context
         *  - Provider content URI to query
         *  - Columns to include in the resulting Cursor
         *  - No selection clause
         *  - No selection arguments
         *  - Default sort order
         */
        return new CursorLoader(
                this,
                currentProductUri,
                projection,
                null,
                null,
                null
        );
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor cursor) {

        /* Bail early if the cursor is null or there is less than 1 row in the cursor */
        if (cursor == null || cursor.getCount() < 1) {
            return;
        } else {

            /*
             * Proceed with moving to the first row of the cursor and reading data from it
             * (This should be the only row in the cursor)
             */
            if (cursor.moveToFirst()) {

                /* Find index of the columns of product's attributes that we're interested in */
                int nameColumnIndex = cursor.getColumnIndex(Products.PRODUCT_NAME);
                int quantityColumnIndex = cursor.getColumnIndex(Products.QUANTITY);
                int priceQuantityColumnIndex = cursor.getColumnIndex(Products.PRICE);
                int supplierColumnIndex = cursor.getColumnIndex(Products.SUPPLIER);
                int supplierPhoneColumnIndex = cursor.getColumnIndex(Products.SUPPLIER_PHONE);

                Log.e(LOG_TAG, "Name Column Index: " + nameColumnIndex);

                /* Extract out the value from the Cursor for the given column index */
                String name = cursor.getString(nameColumnIndex);
                int quantity = cursor.getInt(quantityColumnIndex);
                double price = cursor.getDouble(priceQuantityColumnIndex);
                String supplier = cursor.getString(supplierColumnIndex);
                String supplierPhone = cursor.getString(supplierPhoneColumnIndex);

                Log.e(LOG_TAG, "Name: " + name);

                /* Update the TextViews in the layout with the extracted values from the cursor */
                nameTextView.setText(name);
                quantityTextView.setText(Integer.toString(quantity));
                priceTextView.setText(Double.toString(price));
                supplierTextView.setText(supplier);
                supplierPhoneTextView.setText(supplierPhone);
            }
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

        /* If the loader is invalidated, clear out all the data from the input fields */
        nameTextView.setText(null);
        quantityTextView.setText(null);
        priceTextView.setText(null);
        supplierTextView.setText(null);
        supplierPhoneTextView.setText(null);
    }
}
