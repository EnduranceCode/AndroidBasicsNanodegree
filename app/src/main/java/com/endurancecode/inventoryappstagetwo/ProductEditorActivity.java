package com.endurancecode.inventoryappstagetwo;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.NavUtils;
import android.support.v4.content.Loader;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.endurancecode.inventoryappstagetwo.data.InventoryContract.Products;

/**
 * Allows user to create a new product or edit an existing one.
 * <p>
 * METHODS INDEX
 * -------------
 * - onCreate()
 * - saveProduct()
 * - isInputProductDataValid()
 * - onOptionsItemSelected()
 * - onBackPressed()
 * - showUnsavedChangesDialog()
 * - onCreateLoader()
 * - onLoadFinished()
 * - onLoaderReset()
 */
public class ProductEditorActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    /**
     * Tag for the log messages
     */
    private static final String LOG_TAG = ProductEditorActivity.class.getSimpleName();
    /**
     * Identifier for the product's data loader
     */
    private static final int EDITOR_PRODUCT_LOADER = 1;
    /**
     * EditText field for the product's name
     */
    private EditText nameEditText;
    /**
     * EditText field for the product's quantity
     */
    private EditText quantityEditText;
    /**
     * EditText field for the product's price
     */
    private EditText priceEditText;
    /**
     * EditText field for the product's supplier
     */
    private EditText supplierEditText;
    /**
     * EditText field for the product's supplier phone number
     */
    private EditText supplierPhoneEditText;
    /**
     * Content URI for an existing product
     * <p>
     * When we are inserting a new product it will be null
     */
    private Uri existingProductUri;

    /**
     * Boolean flag that keeps track of whether the product has been edit or not:
     * - If the product has been edited it takes the value true;
     * - If the product hasn't been edited it takes the value false
     */
    private boolean productHasChanged = false;

    /**
     * OnTouchListener that listens for any user touches on a View, implying that they are modifying
     * the view, and we change the productHasChanged boolean to true.
     */
    private View.OnTouchListener editorTouchListener = new View.OnTouchListener() {

        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            productHasChanged = true;
            return false;
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_editor);

        /*
         * Examine the intent that was used to launch this activity,
         * in order to figure out if we're creating a new product or editing an existing one.
         */
        Intent editorActivityIntent = getIntent();
        existingProductUri = editorActivityIntent.getData();

        /*
         * If the intent DOES NOT contain a product content URI, then we know that we are
         * creating a new product, otherwise we are editing an existing product
         */
        if (existingProductUri == null) {

            /* We are creating a new product, therefore we change the app bar label accordingly */
            setTitle(R.string.editor_activity_title_insert_product);

            /* When inserting a new product the delete button is useless,
             * therefore we remove from the UI
             */
            FloatingActionButton deleteProductButton = findViewById(R.id.floatingDeleteButton);
            deleteProductButton.hide();
        } else {

            /*
             * We are editing a new product
             *
             * Therefore we change the app bar label accordingly
             */
            setTitle(R.string.editor_activity_title_edit_product);
        }

        /* Find all relevant views that we will need to read user input from */
        nameEditText = findViewById(R.id.name);
        quantityEditText = findViewById(R.id.quantity);
        priceEditText = findViewById(R.id.price);
        supplierEditText = findViewById(R.id.supplier);
        supplierPhoneEditText = findViewById(R.id.supplier_phone);

        /*
         * Setup OnTouchListeners on all the input fields, so we can determine if the user
         * has touched or modified them. This will let us know if there are unsaved changes
         * or not, if the user tries to leave the editor without saving.
         */
        nameEditText.setOnTouchListener(editorTouchListener);
        quantityEditText.setOnTouchListener(editorTouchListener);
        priceEditText.setOnTouchListener(editorTouchListener);
        supplierEditText.setOnTouchListener(editorTouchListener);
        supplierPhoneEditText.setOnTouchListener(editorTouchListener);

        /* Set an onClickListener method on the FAB delete button */
        FloatingActionButton saveProductButton = findViewById(R.id.floatingSaveButton);
        saveProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveProduct();
            }
        });
    }

    private void saveProduct() {

        /* Get the values inserted in the EditText fields */
        String name = nameEditText.getText().toString().trim();
        int quantity;
        if (TextUtils.isEmpty(quantityEditText.getText().toString().trim())) {
            /*
             * We set the quantity to a negative number when the user leaves the quantity
             * EditText field blank to trigger the Products.isInvalidQuantity method and
             * avoiding trying to parse to Integer an empty string
             */
            quantity = -1;
        } else {
            quantity = Integer.parseInt(quantityEditText.getText().toString().trim());
        }
        double price;
        if (TextUtils.isEmpty(priceEditText.getText().toString().trim())) {
            /*
             * We set the price to zero when the user leaves the price
             * EditText field blank to trigger the Products.isInvalidPrice method and
             * avoiding trying to parse to Integer an empty string
             */
            price = 0;
        } else {
            price = Double.parseDouble(priceEditText.getText().toString().trim());
        }
        String supplier = supplierEditText.getText().toString().trim();
        String supplierPhone = supplierPhoneEditText.getText().toString().trim();

        /* Check if the input data is valid */
        if (isInputProductDataValid(name, quantity, price, supplier, supplierPhone)) {

            /*
             * The data retrieved from the EditText fields is valid,
             * so we create a ContentValues object where column names are the keys,
             * and product attributes retrieved from the EditText fields are the values.
             */
            ContentValues contentValues = new ContentValues();
            contentValues.put(Products.PRODUCT_NAME, name);
            contentValues.put(Products.QUANTITY, quantity);
            contentValues.put(Products.PRICE, price);
            contentValues.put(Products.SUPPLIER, supplier);
            contentValues.put(Products.SUPPLIER_PHONE, supplierPhone);

            if (existingProductUri == null) {

                /*
                 * This is a NEW product, so insert a new product into the provider,
                 * returning the content URI for the new pet.
                 */
                Uri newUri = getContentResolver().insert(Products.CONTENT_URI, contentValues);

                /* Show a toast message depending on whether or not the insertion was successful */
                if (newUri == null) {

                    /*
                     * If the new content URI is null there was an error with insertion
                     * and we let the user know about the failure
                     */
                    Toast.makeText(this, getString(R.string.save_product_failure), Toast.LENGTH_SHORT).show();
                } else {

                    /*
                     * If the new content URI is not null, the data insertion was successful
                     * and we let the user know about the success
                     */
                    Toast.makeText(this, getString(R.string.save_product_success), Toast.LENGTH_SHORT).show();

                    /* We now show to the user the Details Product view with the new product's data */
                    Intent detailsActivityIntent = new Intent(ProductEditorActivity.this, ProductDetailsActivity.class);

                    /* Set the URI on the data field of the intent */
                    detailsActivityIntent.setData(newUri);
                    startActivity(detailsActivityIntent);
                }
            }
        }
    }

    /**
     * Checks if the product's data is valid
     *
     * @param name          Product's name
     * @param quantity      Product's quantity
     * @param price         Product's price
     * @param supplier      Product's supplier
     * @param supplierPhone Product's supplier phone number
     */
    private boolean isInputProductDataValid(
            String name,
            int quantity,
            double price,
            String supplier,
            String supplierPhone) {

        boolean isInputProductDataValid = true;

        /* Check if the product's name data is invalid */
        if (Products.isInvalidProductName(name)) {

            isInputProductDataValid = false;

            Toast.makeText(this, getString(R.string.invalid_product_name_msg), Toast.LENGTH_SHORT).show();
        }

        /* Check if the product's quantity data is invalid */
        if (Products.isInvalidQuantity(quantity)) {

            isInputProductDataValid = false;

            Toast.makeText(this, getString(R.string.invalid_product_quantity_msg), Toast.LENGTH_SHORT).show();
        }

        /* Check if the product's price data is invalid */
        if (Products.isInvalidPrice(price)) {

            isInputProductDataValid = false;

            Toast.makeText(this, getString(R.string.invalid_product_price_msg), Toast.LENGTH_SHORT).show();
        }

        /* Check if the product's supplier data is invalid */
        if (Products.isInvalidSupplier(supplier)) {

            isInputProductDataValid = false;

            Toast.makeText(this, getString(R.string.invalid_product_supplier_msg), Toast.LENGTH_SHORT).show();
        }

        /* Check if the product's supplier phone data is invalid */
        if (Products.isInvalidSupplierPhone(supplierPhone)) {

            isInputProductDataValid = false;

            Toast.makeText(this, getString(R.string.invalid_product_supplier_phone_msg), Toast.LENGTH_SHORT).show();
        }

        return isInputProductDataValid;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            /* Respond to the action bar's Up/Home button */
            case android.R.id.home:

                if (productHasChanged) {

                    /*
                     * The user has changed something in the EditText fields
                     * so we have unsaved changes, therefore we setup a dialog to warn the user.
                     *
                     * Create a click listener to handle the user confirming
                     * that changes should be discarded
                     */
                    DialogInterface.OnClickListener discardButtonClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int which) {

                            /* User clicked "Discard" button, navigate to parent activity */
                            NavUtils.navigateUpFromSameTask(ProductEditorActivity.this);
                        }
                    };

                    /* Show a dialog that notifies the user they have unsaved changes */
                    showUnsavedChangesDialog(discardButtonClickListener);
                    return true;
                } else {

                    /* If the product hasn't changed, continue with navigating up to parent activity */
                    NavUtils.navigateUpFromSameTask(ProductEditorActivity.this);
                    return true;
                }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

        if (productHasChanged) {

            /*
             * The user has changed something in the EditText fields so we have unsaved changes,
             * therefore we setup a dialog to warn the user.
             *
             * Create a click listener to handle the user confirming that changes should be discarded
             */
            DialogInterface.OnClickListener discardButtonClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int which) {

                    /* User clicked "Discard" button, close the current activity */
                    finish();
                }
            };

            showUnsavedChangesDialog(discardButtonClickListener);
        } else {

            /*
             * When the user hasn't changed anything in the EditText fields,
             * continue with handling back button press
             */
            super.onBackPressed();
        }
    }

    /**
     * Show a dialog that warns the user there are unsaved changes that will be lost
     * if they continue leaving the editor.
     *
     * @param discardButtonClickListener is the click listener for what to do when
     *                                   the user confirms they want to discard their changes
     */
    private void showUnsavedChangesDialog(DialogInterface.OnClickListener discardButtonClickListener) {

        /*
         * Create an AlertDialog.Builder and set the message, and click listeners
         * for the positive and negative buttons on the dialog.
         */
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.unsaved_changes_dialog_msg);
        builder.setPositiveButton(R.string.unsaved_changes_dialog_discard, discardButtonClickListener);
        builder.setNegativeButton(R.string.unsaved_changes_dialog_keep_editing, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                /*
                 * User clicked the "Keep editing" button, so dismiss the dialog
                 * and continue editing the pet.
                 */
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });

        /* Create and show the AlertDialog */
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int i, @Nullable Bundle bundle) {
        return null;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor cursor) {

    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

    }
}
