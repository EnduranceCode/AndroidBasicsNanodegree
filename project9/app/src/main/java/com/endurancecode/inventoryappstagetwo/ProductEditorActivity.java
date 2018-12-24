package com.endurancecode.inventoryappstagetwo;

import android.annotation.SuppressLint;
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
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
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
 * - onPrepareOptionsMenu()
 * - onCreateOptionsMenu()
 * - onOptionsItemSelected()
 * - saveProduct()
 * - isInputProductDataValid()
 * - showDeleteConfirmationDialog()
 * - deleteProduct()
 * - onBackPressed()
 * - showUnsavedChangesDialog()
 * - onCreateLoader()
 * - onLoadFinished()
 * - onLoaderReset()
 * - isCursorValid()
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

        @SuppressLint("ClickableViewAccessibility")
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            productHasChanged = true;
            return false;
        }
    };

    @SuppressLint("ClickableViewAccessibility")
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

        Log.e(LOG_TAG, "Existing Content URI: " + existingProductUri);

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

            /*
             * And we also invalidate the options menu, so the "Delete product" menu option can be hidden.
             * (It doesn't make sense to delete a product that hasn't been created yet.)
             */
            invalidateOptionsMenu();
        } else {

            /*
             * We are editing a new product
             *
             * Therefore we change the app bar label accordingly
             */
            setTitle(R.string.editor_activity_title_edit_product);

            /* And we only initialize the loader when we are editing a existing product */
            //noinspection deprecation
            getSupportLoaderManager().initLoader(EDITOR_PRODUCT_LOADER, null, this);

            /* As this is an existing product, we can set an onClickListener method on the FAB delete button */
            FloatingActionButton deleteProductButton = findViewById(R.id.floatingDeleteButton);
            deleteProductButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDeleteConfirmationDialog();
                }
            });
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

        /* Set an onClickListener method on the FAB save button */
        FloatingActionButton saveProductButton = findViewById(R.id.floatingSaveButton);
        saveProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveProduct();
            }
        });
    }

    /**
     * This method is called after invalidateOptionsMenu(), so that the
     * menu can be updated (some menu items can be hidden or made visible).
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);

        /* If this is a new product, hide the "Delete product" menu item */
        if (existingProductUri == null) {

            MenuItem menuItem = menu.findItem(R.id.delete_product);
            menuItem.setVisible(false);
        }

        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        /*
         * Inflate the menu options from the res/menu/product_editor_menu.xml file.
         * This adds menu items to the app bar.
         */
        getMenuInflater().inflate(R.menu.product_editor_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        /* User clicked on a menu option in the app bar overflow menu */
        switch (item.getItemId()) {

            /* Respond to a click on the "Save product" menu option */
            case R.id.save_product:
                saveProduct();
                return true;

            /* Respond to a click on the "Delete product" menu option */
            case R.id.delete_product:
                showDeleteConfirmationDialog();
                return true;

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
                     * If the new content URI is null, there was an error with insertion
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
            } else {

                /*
                 * This is a EXISTING product, so insert the edited exiting product into the provider,
                 * returning the content URI for the new pet.
                 */
                int rowsUpdated = getContentResolver().update(existingProductUri, contentValues, null, null);

                /* Show a toast message depending on whether or not the insertion was successful */
                if (rowsUpdated == 0) {

                    /*
                     * If the returned number of updated rows is zero, the data insertion was successful
                     * and we let the user know about the success, there was an error with insertion
                     * and we let the user know about the failure
                     */
                    Toast.makeText(this, getString(R.string.save_product_failure), Toast.LENGTH_SHORT).show();
                } else {

                    /*
                     * If the returned number of updated rows is NOT zero, the data insertion was successful
                     * and we let the user know about the success
                     */
                    Toast.makeText(this, getString(R.string.save_product_success), Toast.LENGTH_SHORT).show();

                    /* We now show to the user the Details Product view with the new product's data */
                    Intent detailsActivityIntent = new Intent(ProductEditorActivity.this, ProductDetailsActivity.class);

                    /* Set the URI on the data field of the intent */
                    detailsActivityIntent.setData(existingProductUri);
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

    /**
     * Prompt the user to confirm product's deletion
     */
    private void showDeleteConfirmationDialog() {

        /*
         * Create an AlertDialog.Builder and set the message, and click listeners
         * for the positive and negative buttons on the dialog.
         */
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setMessage(R.string.delete_dialog_single_product_msg);
        builder.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                /* User clicked the "Delete" button, so delete the product */
                deleteProduct();
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                /*
                 * User clicked the "Cancel" button, so dismiss the dialog
                 * and continue editing the pet.
                 */
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });

        /* Create and show the AlertDialog */
        android.app.AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void deleteProduct() {

        /* Only perform the delete if this is an existing product */
        if (existingProductUri != null) {

            int rowsDeleted = getContentResolver().delete(existingProductUri, null, null);

            /* Show a toast message depending on whether or not the deletion was successful */
            if (rowsDeleted == 0) {
                Toast.makeText(this, getString(R.string.editor_delete_product_failed), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, getString(R.string.editor_delete_product_successful), Toast.LENGTH_SHORT).show();

                /* Close the activity */
                finish();

                Intent mainActivityIntent = new Intent(ProductEditorActivity.this, MainActivity.class);
                startActivity(mainActivityIntent);
            }
        }
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

        /*
         * The editor shows all product'a attributes,
         * so we need to define a projection that contains all columns from the product table
         */
        String[] projection = {
                Products._ID,
                Products.PRODUCT_NAME,
                Products.PRICE,
                Products.QUANTITY,
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
                existingProductUri,
                projection,
                null,
                null,
                null
        );
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor cursor) {

        if (isCursorValid(cursor)) {

            /*
             * Proceed with moving to the first row of the cursor and reading data from it
             * (This should be the only row in the cursor)
             */
            if (cursor.moveToFirst()) {

                /* Find the columns of product's attributes that we're interested in */
                int nameColumnIndex = cursor.getColumnIndex(Products.PRODUCT_NAME);
                int priceColumnIndex = cursor.getColumnIndex(Products.PRICE);
                int quantityColumnIndex = cursor.getColumnIndex(Products.QUANTITY);
                int supplierColumnIndex = cursor.getColumnIndex(Products.SUPPLIER);
                int supplierPhoneColumnIndex = cursor.getColumnIndex(Products.SUPPLIER_PHONE);

                /* Extract out the value from the Cursor for the given column index */
                String name = cursor.getString(nameColumnIndex);
                double price = cursor.getDouble(priceColumnIndex);
                int quantity = cursor.getInt(quantityColumnIndex);
                String supplier = cursor.getString(supplierColumnIndex);
                String supplierPhone = cursor.getString(supplierPhoneColumnIndex);

                /* Update the EditText fields in the layout with the extracted values from the cursor */
                nameEditText.setText(name);
                priceEditText.setText(Double.toString(price));
                quantityEditText.setText(Integer.toString(quantity));
                supplierEditText.setText(supplier);
                supplierPhoneEditText.setText(supplierPhone);
            }
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

        /* If the loader is invalidated, clear out all the data from the input fields */
        nameEditText.getText().clear();
        priceEditText.getText().clear();
        quantityEditText.getText().clear();
        supplierEditText.getText().clear();
        supplierPhoneEditText.getText().clear();
    }

    /**
     * Check if the cursor is not null and if it has at least one row
     *
     * @param cursor The cursor to be checked
     */
    private boolean isCursorValid(Cursor cursor) {
        return cursor != null && cursor.getCount() >= 1;
    }
}
