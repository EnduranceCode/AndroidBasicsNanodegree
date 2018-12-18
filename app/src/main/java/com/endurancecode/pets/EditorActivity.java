/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.endurancecode.pets;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.NavUtils;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.endurancecode.pets.data.PetContract.PetEntry;

/**
 * Allows user to create a new pet or edit an existing one.
 */
public class EditorActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    /**
     * EditText field to enter the pet's name
     */
    private EditText mNameEditText;

    /**
     * EditText field to enter the pet's breed
     */
    private EditText mBreedEditText;

    /**
     * EditText field to enter the pet's weight
     */
    private EditText mWeightEditText;

    /**
     * EditText field to enter the pet's gender
     */
    private Spinner mGenderSpinner;

    /**
     * Gender of the pet. The possible values are:
     * 0 for unknown gender, 1 for male, 2 for female.
     */
    private int mGender = 0;

    /**
     * Identifier for the pet data loader
     */
    private static final int EXISTING_PET_LOADER = 0;

    /**
     * Content URI for the existing pet (null if it's a new pet)
     */
    private Uri mCurrentPetUri;

    /**
     * Boolean flag that keeps track of whether the pet has been edited (true) or not (false)
     */
    private boolean mPetHasChanged = false;

    /**
     * OnTouchListener that listens for any user touches on a View, implying that they are modifying
     * the view, and we change the mPetHasChanged boolean to true.
     */
    private View.OnTouchListener mTouchLister = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            mPetHasChanged = true;
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        /*
         * Examine the intent that was used to launch this activity,
         * in order to figure out if we're creating a new pet or editing an existing one.
         */
        Intent editorActivityIntent = getIntent();
        mCurrentPetUri = editorActivityIntent.getData();

        /*
         * If the intent DOES NOT contain a pet content URI, then we know that we are
         * creating a new pet
         */
        if (mCurrentPetUri == null) {
            /* This is a new pet, so change the app bar to say "Add a Pet" */
            setTitle(R.string.editor_activity_title_new_pet);

            /*
             * Invalidate the options menu, so the "Delete" menu option can be hidden.
             * (It doesn't make sense to delete a pet that hasn't been created yet.)
             */
            invalidateOptionsMenu();
        } else {
            /* Otherwise this is an existing pet, so change app bar to say "Edit Pet" */
            setTitle(R.string.editor_activity_title_edit_pet);

            /* And we only initialize the loader when we are editing a existing pet */
            //noinspection deprecation
            getSupportLoaderManager().initLoader(EXISTING_PET_LOADER, null, this);
        }

        /* Find all relevant views that we will need to read user input from */
        mNameEditText = (EditText) findViewById(R.id.edit_pet_name);
        mBreedEditText = (EditText) findViewById(R.id.edit_pet_breed);
        mWeightEditText = (EditText) findViewById(R.id.edit_pet_weight);
        mGenderSpinner = (Spinner) findViewById(R.id.spinner_gender);

        /*
         * Setup OnTouchListeners on all the input fields, so we can determine if the user
         * has touched or modified them. This will let us know if there are unsaved changes
         * or not, if the user tries to leave the editor without saving.
         */
        mNameEditText.setOnTouchListener(mTouchLister);
        mBreedEditText.setOnTouchListener(mTouchLister);
        mWeightEditText.setOnTouchListener(mTouchLister);
        mGenderSpinner.setOnTouchListener(mTouchLister);

        setupSpinner();
    }

    /**
     * Setup the dropdown spinner that allows the user to select the gender of the pet.
     */
    private void setupSpinner() {
        // Create adapter for spinner. The list options are from the String array it will use
        // the spinner will use the default layout
        ArrayAdapter genderSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_gender_options, android.R.layout.simple_spinner_item);

        // Specify dropdown layout style - simple list view with 1 item per line
        genderSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        // Apply the adapter to the spinner
        mGenderSpinner.setAdapter(genderSpinnerAdapter);

        // Set the integer mSelected to the constant values
        mGenderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.gender_male))) {
                        mGender = PetEntry.GENDER_MALE; // Male
                    } else if (selection.equals(getString(R.string.gender_female))) {
                        mGender = PetEntry.GENDER_FEMALE; // Female
                    } else {
                        mGender = PetEntry.GENDER_UNKNOWN; // Unknown
                    }
                }
            }

            // Because AdapterView is an abstract class, onNothingSelected must be defined
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mGender = 0; // Unknown
            }
        });
    }

    /**
     * Get user input from editor and save new pet into database.
     */
    private void savePet() {
        String stringName = mNameEditText.getText().toString().trim();
        String stringBreed = mBreedEditText.getText().toString().trim();
        String stringWeight = mWeightEditText.getText().toString().trim();

        /*
         * Check if this is supposed to be a new pet
         * and check if all the fields in the editor are blank
         */
        if (mCurrentPetUri == null &&
                TextUtils.isEmpty(stringName) &&
                TextUtils.isEmpty(stringBreed) &&
                mGender == PetEntry.GENDER_UNKNOWN &&
                TextUtils.isEmpty(stringWeight)) {
            return;
        }

        /*
         * Create a ContentValues object where column names are the keys,
         * and pet attributes from the editor are the values.
         */
        ContentValues values = new ContentValues();
        values.put(PetEntry.COLUMN_PET_NAME, stringName);
        values.put(PetEntry.COLUMN_PET_BREED, stringBreed);
        values.put(PetEntry.COLUMN_PET_GENDER, mGender);

        /*
         * If the weight is not provided by the user, don't try to parse the string into an
         * integer value. Use 0 by default
         */
        int weight = 0;
        if (!TextUtils.isEmpty(stringWeight)) {
            weight = Integer.parseInt(stringWeight);
        }
        values.put(PetEntry.COLUMN_PET_WEIGHT, weight);


        if (mCurrentPetUri != null) {
            /*
             * This is an EXISTING pet, so update the pet with content URI: mCurrentPetUri
             * and pass in the new ContentValues. Pass in null for the selection and selection args
             * because mCurrentPetUri will already identify the correct row in the database that
             * we want to modify.
             */
            int rowUpdated = getContentResolver().update(mCurrentPetUri, values, null, null);

            /* Show a toast message depending on whether or not the update was successful */
            if (rowUpdated == 0) {
                /* If no rows were affected, then there was an error with the update */
                Toast.makeText(this, getString(R.string.editor_update_pet_failed), Toast.LENGTH_SHORT).show();
            } else {
                /* Otherwise, the update was successful and we can display a toast */
                Toast.makeText(this, getString(R.string.editor_update_pet_successful), Toast.LENGTH_SHORT).show();
            }
        } else {
            /*
             * This is a NEW pet, so insert a new pet into the provider,
             * returning the content URI for the new pet.
             */
            Uri newUri = getContentResolver().insert(PetEntry.CONTENT_URI, values);

            /* Show a toast message depending on whether or not the insertion was successful */
            if (newUri == null) {
                /* If the new content URI is null, then there was an error with insertion */
                Toast.makeText(this, getString(R.string.editor_insert_pet_failed), Toast.LENGTH_SHORT).show();
            } else {
                /* Otherwise, the insertion was successful and we can display a toast */
                Toast.makeText(this, getString(R.string.editor_insert_pet_successful), Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * This method is called after invalidateOptionsMenu(), so that the
     * menu can be updated (some menu items can be hidden or made visible).
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        /* If this is a new pet, hide the "Delete" menu item */
        if (mCurrentPetUri == null) {
            MenuItem menuItem = menu.findItem(R.id.action_delete);
            menuItem.setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /*
         * Inflate the menu options from the res/menu/menu_editor.xml file.
         * This adds menu items to the app bar.
         */
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            /* Respond to a click on the "Save" menu option */
            case R.id.action_save:
                /* Save pet to database */
                savePet();
                /* Exit activity */
                finish();
                return true;
            /* Respond to a click on the "Delete" menu option */
            case R.id.action_delete:
                /* Pop up confirmation dialog for deletion */
                showDeleteConfirmationDialog();
                return true;
            /* Respond to a click on the "Up" arrow button in the app bar */
            case android.R.id.home:
                /*
                 * If the pet hasn't changed, continue with navigating up to parent activity
                 * which is the {@link CatalogActivity}.
                 */
                if (!mPetHasChanged) {
                    NavUtils.navigateUpFromSameTask(EditorActivity.this);
                    return true;
                }

                /*
                 * Otherwise if there are unsaved changes, setup a dialog to warn the user.
                 * Create a click listener to handle the user confirming that
                 * changes should be discarded.
                 */
                DialogInterface.OnClickListener discardButtonClickListener =
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                /* User clicked "Discard" button, navigate to parent activity */
                                NavUtils.navigateUpFromSameTask(EditorActivity.this);
                            }
                        };

                /* Show a dialog that notifies the user they have unsaved changes */
                showUnsavedChangesDialog(discardButtonClickListener);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * This method is called when the back button is pressed.
     */
    @Override
    public void onBackPressed() {
        /* If the pet hasn't changed, continue with handling back button press */
        if (!mPetHasChanged) {
            super.onBackPressed();
            return;
        }

        /*
         * Otherwise if there are unsaved changes, setup a dialog to warn the user.
         * Create a click listener to handle the user confirming that changes should be discarded.
         */
        DialogInterface.OnClickListener discardButtonClickListener =
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        /* User clicked "Discard" button, close the current activity. */
                        finish();
                    }
                };

        /* Show dialog that there are unsaved changes */
        showUnsavedChangesDialog(discardButtonClickListener);
    }

    /**
     * Prompt the user to confirm that they want to delete this pet.
     */
    private void showDeleteConfirmationDialog() {
        /*
         * Create an AlertDialog.Builder and set the message, and click listeners
         * for the positive and negative buttons on the dialog.
         */
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.delete_dialog_msg);
        builder.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                /* User clicked the "Delete" button, so delete the pet */
                deletePet();
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked the "Cancel" button, so dismiss the dialog
                // and continue editing the pet.
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });

        /* Create and show the AlertDialog */
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    /**
     * Perform the deletion of the pet in the database.
     */
    private void deletePet() {
        /* Only perform the delete if this is an existing pet */
        if (mCurrentPetUri != null) {

            int rowsDeleted = getContentResolver().delete(mCurrentPetUri, null, null);

            /* Show a toast message depending on whether or not the update was successful */
            if (rowsDeleted == 0) {
                Toast.makeText(this, getString(R.string.editor_delete_pet_failed), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, getString(R.string.editor_delete_pet_successful), Toast.LENGTH_SHORT).show();
            }
        }

        /* Close the activity */
        finish();
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int i, @Nullable Bundle bundle) {

        /*
         * Since the editor shows all pet attributes, define a projection that contains
         * all columns from the pet table
         */
        String[] projection = {
                PetEntry._ID,
                PetEntry.COLUMN_PET_NAME,
                PetEntry.COLUMN_PET_BREED,
                PetEntry.COLUMN_PET_GENDER,
                PetEntry.COLUMN_PET_WEIGHT
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
                mCurrentPetUri,
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
        }

        /*
         * Proceed with moving to the first row of the cursor and reading data from it
         * (This should be the only row in the cursor)
         */
        if (cursor.moveToFirst()) {
            /* Find the columns of pet attributes that we're interested in */
            int nameColumnIndex = cursor.getColumnIndex(PetEntry.COLUMN_PET_NAME);
            int breedColumnIndex = cursor.getColumnIndex(PetEntry.COLUMN_PET_BREED);
            int genderColumnIndex = cursor.getColumnIndex(PetEntry.COLUMN_PET_GENDER);
            int weightColumnIndex = cursor.getColumnIndex(PetEntry.COLUMN_PET_WEIGHT);

            /* Extract out the value from the Cursor for the given column index */
            String name = cursor.getString(nameColumnIndex);
            String breed = cursor.getString(breedColumnIndex);
            int gender = cursor.getInt(genderColumnIndex);
            int weight = cursor.getInt(weightColumnIndex);

            /* Update the name's EditText with the value from the database */
            mNameEditText.setText(name);

            /* Update the breed's EditText with the value from the database */
            mBreedEditText.setText(breed);

            /*
             * Gender is a dropdown spinner, so map the constant value from the database
             * into one of the dropdown options (0 is Unknown, 1 is Male, 2 is Female).
             * Then call setSelection() so that option is displayed on screen as the current selection.
             */
            switch (gender) {
                case PetEntry.GENDER_MALE:
                    mGenderSpinner.setSelection(PetEntry.GENDER_MALE);
                    break;
                case PetEntry.GENDER_FEMALE:
                    mGenderSpinner.setSelection(PetEntry.GENDER_FEMALE);
                    break;
                default:
                    mGenderSpinner.setSelection(PetEntry.GENDER_UNKNOWN);
                    break;
            }

            /* Update the weight's EditText with the value from the database */
            mWeightEditText.setText(Integer.toString(weight));
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        /* If the loader is invalidated, clear out all the data from the input fields */
        mNameEditText.getText().clear();
        mBreedEditText.getText().clear();
        mGenderSpinner.setSelection(0);
        mWeightEditText.getText().clear();
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
        builder.setPositiveButton(R.string.discard, discardButtonClickListener);
        builder.setNegativeButton(R.string.keep_editing, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
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
}
