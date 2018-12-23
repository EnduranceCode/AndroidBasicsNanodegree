package com.endurancecode.inventoryappstagetwo;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.endurancecode.inventoryappstagetwo.data.InventoryContract.Products;

/**
 * Displays the list of products stored in the database
 * <p>
 * METHODS INDEX
 * -------------
 * - onCreate()
 * - insertDummyData()
 * - onCreateLoader()
 * - onLoadFinished()
 * - onLoaderReset()
 */
public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    /**
     * Identifier for the product's data loader
     */
    private static final int PRODUCT_LOADER = 1;

    /**
     * Adapter for to populate the ListView
     */
    private ProductCursorAdapter productCursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* Temporary call for debugging purposes only */
        insertDummyData();

        /* Set an onClickListener method on the FAB add product button */
        FloatingActionButton addProductButton = findViewById(R.id.floatingAddProductButton);
        addProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent productEditorActivityIntent = new Intent(MainActivity.this, ProductEditorActivity.class);
                startActivity(productEditorActivityIntent);
            }
        });

        /* Find the ListView which will be populated with the product data */
        ListView productListView = findViewById(R.id.products_list);

        /* Find and set empty view on the ListView, so that it only shows when the list has 0 items */
        View productEmptyView = findViewById(R.id.empty_view);
        productListView.setEmptyView(productEmptyView);

        /*
         * Setup an Adapter to create a list item for each row of product data in the Cursor
         *
         * There is no product data yet (until the loader finishes) so pass in null for the Cursor
         */
        productCursorAdapter = new ProductCursorAdapter(this, null);

        /* Attach the CursorAdapter to the ListView */
        productListView.setAdapter(productCursorAdapter);

        /* Setup item click listener */
        productListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent detailsActivityIntent = new Intent(MainActivity.this, ProductDetailsActivity.class);

                /*
                 * Form the content URI that represents the specific product that was clicked on,
                 * by appending the "id" (passed as input to this method) onto the
                 * {@link ProductEntry#CONTENT_URI}.
                 * For example, the URI would be
                 * "content://com.endurancecode.inventoryappstagetwo/products/2" if the pet with
                 * ID 2 was clicked on.
                 */
                Uri currentProductUri = ContentUris.withAppendedId(Products.CONTENT_URI, id);

                /* Set the URI on the data field of the intent */
                detailsActivityIntent.setData(currentProductUri);

                /* Launch the {@link ProductDetailsActivity} to display the data for the current product */
                startActivity(detailsActivityIntent);
            }
        });

        /* Kick off the loader */
        getSupportLoaderManager().initLoader(PRODUCT_LOADER, null, this);
    }

    /* Temporary helper method for debugging the app
     * Inserts dummy data in the database
     */
    private void insertDummyData() {
        /*
         * Create a ContentValues object where column name are the keys,
         * and Dummy's product attributes are the values
         */
        ContentValues contentValues = new ContentValues();
        contentValues.put(Products.PRODUCT_NAME, "Dummy product");
        contentValues.put(Products.PRICE, 12.34);
        contentValues.put(Products.QUANTITY, 3);
        contentValues.put(Products.SUPPLIER, "Dummy supplier");
        contentValues.put(Products.SUPPLIER_PHONE, "123 456 789");

        /*
         * Insert a new row for Dummy product into the provider using the ContentResolver.
         * Use the {@link ProductEntry#CONTENT_URI} to indicate that we want to insert
         * into the product database table.
         * Receive the new content URI that will allow us
         * to access Dummy's product data in the future.
         */
        Uri newUri = getContentResolver().insert(Products.CONTENT_URI, contentValues);
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int i, @Nullable Bundle bundle) {

        /* Define a projection that specifies the columns from the table we care about */
        String[] projection = {
                Products._ID,
                Products.PRODUCT_NAME,
                Products.PRICE,
                Products.QUANTITY
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
                Products.CONTENT_URI,
                projection,
                null,
                null,
                null
        );
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor cursor) {
        productCursorAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        productCursorAdapter.swapCursor(null);
    }
}
