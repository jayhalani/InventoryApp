package com.example.jay.inventoryapp;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jay.inventoryapp.data.ProductContract.ProductEntry;

import static android.content.ContentValues.TAG;

/**
 * Created by Jay on 5/9/2017.
 */

public class ProductCursorAdapter extends CursorAdapter {

    public ProductCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_items, parent, false);
    }

    @Override
    public void bindView(View view, final Context context, final Cursor cursor) {

        TextView nameTextView = (TextView) view.findViewById(R.id.list_product_name_text);
        TextView quantityTextView = (TextView) view.findViewById(R.id.list_product_quantity_text);
        TextView priceTextView = (TextView) view.findViewById(R.id.list_product_price_text);
        ImageView sellImageView = (ImageView) view.findViewById(R.id.btn_sell);
        ImageView productImageView = (ImageView) view.findViewById(R.id.product_image);

        int idColumnIndex = cursor.getColumnIndex(ProductEntry._ID);
        int nameColumnIndex = cursor.getColumnIndex(ProductEntry.COLUMN_PRODUCT_NAME);
        int priceColumnIndex = cursor.getColumnIndex(ProductEntry.COLUMN_PRODUCT_PRICE);
        int quantityColumnIndex = cursor.getColumnIndex(ProductEntry.COLUMN_PRODUCT_QUANTITY);
        int imageColumnIndex = cursor.getColumnIndex(ProductEntry.COLUMN_PRODUCT_IMAGE);

        final int productId = cursor.getInt(idColumnIndex);
        String nameString = cursor.getString(nameColumnIndex);
        Float priceString = cursor.getFloat(priceColumnIndex);
        final int quantityString = cursor.getInt(quantityColumnIndex);
        byte[] imageByteArray = cursor.getBlob(imageColumnIndex);

        nameTextView.setText(nameString);
        quantityTextView.setText(Integer.toString(quantityString));
        priceTextView.setText(Float.toString(priceString));
        Bitmap bmp = BitmapFactory.decodeByteArray(imageByteArray, 0, imageByteArray.length);
        productImageView.setImageBitmap(bmp);

        sellImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri itemUri = ContentUris.withAppendedId(ProductEntry.CONTENT_URI, productId);
                buyProduct(context, itemUri, quantityString);
            }
        });
    }

    private void buyProduct(Context context, Uri itemUri, int currentQuantity) {
        int newCount = (currentQuantity >= 1) ? currentQuantity - 1 : 0;
        ContentValues values = new ContentValues();
        values.put(ProductEntry.COLUMN_PRODUCT_QUANTITY, newCount );
        int numRowsUpdated = context.getContentResolver().update(itemUri, values, null, null);

        if (numRowsUpdated > 0) {
            Log.i(TAG, "Buy product successful");
        } else {
            Log.i(TAG, "Could not update buy product");
        }
    }
}
