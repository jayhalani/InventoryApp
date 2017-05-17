package com.example.jay.inventoryapp.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.jay.inventoryapp.data.ProductContract.ProductEntry;


public class ProductDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Inventory.db";

    private static final int DATABASE_VERSION = 1;

    public ProductDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String SQL_CREATE_PRODUCTS_TABLE = "CREATE TABLE " + ProductEntry.TABLE_NAME + " ("
                + ProductEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ProductEntry.COLUMN_PRODUCT_NAME + " TEXT NOT NULL, "
                + ProductEntry.COLUMN_PRODUCT_PRICE + " FLOAT NOT NULL, "
                + ProductEntry.COLUMN_PRODUCT_QUANTITY + " INTEGER NOT NULL, "
                + ProductEntry.COLUMN_PROVIDER_NAME + " TEXT NOT NULL, "
                + ProductEntry.COLUMN_PROVIDER_NUMBER + " TEXT NOT NULL, "
                + ProductEntry.COLUMN_PROVIDER_EMAIL + " TEXT NOT NULL, "
                + ProductEntry.COLUMN_PRODUCT_IMAGE + " BLOB);";

        db.execSQL(SQL_CREATE_PRODUCTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
