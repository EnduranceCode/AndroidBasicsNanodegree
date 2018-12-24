package com.endurancecode.inventoryappstagetwo;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.endurancecode.inventoryappstagetwo.data.InventoryContract.Products;

/**
 * {@link ProductCursorAdapter} is an adapter for a list or grid view
 * that uses a {@link Cursor} of product data as its data source. This adapter knows
 * how to create list items for each row of product data in the {@link Cursor}.
 * <p>
 * METHODS INDEX
 * -------------
 * - productCursorAdapter()
 * - newView()
 * - bindView()
 */
public class ProductCursorAdapter extends CursorAdapter {

    /**
     * Tag for the log messages
     */
    private static final String LOG_TAG = ProductCursorAdapter.class.getSimpleName();

    /**
     * Constructs a new {@link ProductCursorAdapter}.
     *
     * @param context The context
     * @param cursor  The cursor from which to get the data.
     */
    ProductCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0 /* flags */);
    }

    /**
     * Makes a new blank list item view. No data is set (or bound) to the views yet.
     *
     * @param context App context
     * @param cursor  The cursor from which to get the data. The cursor is already
     *                moved to the correct position.
     * @param parent  The parent to which the new view is attached to
     * @return the newly created list item view.
     */
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_item_product, parent, false);
    }

    /**
     * This method binds the product data (in the current row pointed to by cursor) to the given
     * list item layout. For example, the name for the current product can be set on the name TextView
     * in the list item layout.
     *
     * @param view    Existing view, returned earlier by newView() method
     * @param context App context
     * @param cursor  The cursor from which to get the data. The cursor is already moved to the
     *                correct row.
     */
    @Override
    public void bindView(View view, final Context context, Cursor cursor) {

        /* Find views to populate in inflated template */
        TextView nameTextView = view.findViewById(R.id.name);
        TextView priceTextView = view.findViewById(R.id.price);
        TextView quantityTextView = view.findViewById(R.id.quantity);
        Button saleButton = view.findViewById(R.id.sale_button);

        /* Extract properties from cursor */
        long id = cursor.getInt(cursor.getColumnIndex(Products._ID));
        String name = cursor.getString(cursor.getColumnIndex(Products.PRODUCT_NAME));
        double price = cursor.getDouble(cursor.getColumnIndex(Products.PRICE));
        final int quantity = cursor.getInt(cursor.getColumnIndex(Products.QUANTITY));

        /* Populate fields with extracted properties */
        nameTextView.setText(name);
        priceTextView.setText(String.valueOf(price));
        quantityTextView.setText(String.valueOf(quantity));

        /* Get the current product URI */
        final Uri currentProductUri = ContentUris.withAppendedId(Products.CONTENT_URI, id);

        /* Set an onClickListener method on the sales button */
        saleButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.e(LOG_TAG, "Current Product URI: " + currentProductUri);
                Log.e(LOG_TAG, "Product's quantity: " + quantity);

                if (quantity <= 0) {

                    /*
                     * We don't allow negative quantities in the database, therefore
                     * we don't update the database and we let the user know
                     */
                    Toast.makeText(context, context.getString(R.string.negative_quantities_warning),
                            Toast.LENGTH_SHORT).show();
                } else {

                    /*
                     * Create a ContentValues object where column names are the keys,
                     * and product attributes from the cursor are the values.
                     * We only want to update the quantity, therefore it is the only pair key/value
                     * that we use
                     *
                     * The inserted quantity value is the current quantity decreased by one
                     */
                    ContentValues contentValues = new ContentValues();
                    contentValues.put(Products.QUANTITY, quantity - 1);

                    /* Update the database with the new product's quantity */
                    context.getContentResolver().update(
                            currentProductUri,
                            contentValues,
                            null,
                            null);
                }
            }
        });
    }
}
