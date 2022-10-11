package com.example.java_database_advance.database;
/*
 *** Table Structure ****
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

    private final String TAG = getClass().getSimpleName();
    private Context context;
    private static final String DATABASE_NAME = "COST";
    private static final int DATABASE_VERSION = 1;

    //TABLE COLUMN DEFINE
    public static final String TABLE_NAME = "CostInfo";
    public static final String id = "ID";
    public static final String total = "Total";
    public static final String date = "date";
    public static final String product = "ProductName";
    public static final String productAmount = "productAmount";
    public static final String productCost = "Cost";

    public static final String groupBy = " GROUP BY  ";
    public static final String orderBy = " ORDER BY  ";
    public static final String preClause = " * ";
    public static final String whereClause = " ";
    public static final String mainClause = " " ;


    private final String CREATE_USER_INFO_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(" +
            id + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            total + " INTEGER, " +
            date + " VARCHAR, " +
            product + " VARCHAR, " +
            productCost + " INTEGER, " +
            productAmount + " INTEGER " +
            ");";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_INFO_TABLE);
        Log.d("Atique", "onCreate: " + CREATE_USER_INFO_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        try {
            onCreate(db);
        } catch (Exception e) {
            Log.d("Atique", "onUpgrade: ");
        }

    }
}
