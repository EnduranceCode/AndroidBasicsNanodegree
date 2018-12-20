package com.endurancecode.inventoryappstagetwo;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.endurancecode.inventoryappstagetwo.data.InventoryContract.Products;

/**
 * {@link ProductAdapter} is an adapter for a list or grid view
 * that uses a {@link Cursor} of pet data as its data source. This adapter knows
 * how to create list items for each row of pet data in the {@link Cursor}.
 */
public class ProductAdapter extends CursorAdapter {

    /**
     * Constructs a new {@link ProductAdapter}.
     *
     * @param context The context
     * @param cursor  The cursor from which to get the data.
     */
    public ProductAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0 /* flags */);
    }

    /**
     * Makes a new blank list item view. No data is set (or bound) to the views yet.
     *
     * @param context app context
     * @param cursor  The cursor from which to get the data. The cursor is already
     *                moved to the correct position.
     * @param parent  The parent to which the new view is attached to
     * @return the newly created list item view.
     */
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.products_list_item, parent, false);
    }

    /**
     * This method binds the pet data (in the current row pointed to by cursor) to the given
     * list item layout. For example, the name for the current pet can be set on the name TextView
     * in the list item layout.
     *
     * @param view    Existing view, returned earlier by newView() method
     * @param context app context
     * @param cursor  The cursor from which to get the data. The cursor is already moved to the
     *                correct row.
     */
    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        /* Find views to populate in inflated template */
        TextView nameTextView = view.findViewById(R.id.name);
        TextView priceTextView = view.findViewById(R.id.price);
        TextView quantityTextView = view.findViewById(R.id.quantity);
        Button saleButton = view.findViewById(R.id.sale_button);

        /* Extract properties from cursor */
        String name = cursor.getString(cursor.getColumnIndex(Products.PRODUCT_NAME));
        String priceString = String.valueOf(cursor.getDouble(cursor.getColumnIndex(Products.PRICE)));
        String quantityString = String.valueOf(cursor.getInt(cursor.getColumnIndex(Products.QUANTITY)));

        /* Populate fields with extracted properties */
        nameTextView.setText(name);
        priceTextView.setText(priceString);
        quantityTextView.setText(quantityString);

        /* TODO: Add onClick event on the sale Button to decrease {@Products.#QUANTIY} by one */
    }
}
