package com.example.java_database_advance.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.java_database_advance.CostOfProduct;

import java.util.ArrayList;

/*
 *** DB Operation ****
 */
public class DatabaseManager {
    private Context context;
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;

    public DatabaseManager(Context context) {
        this.context = context;
        databaseHelper = new DatabaseHelper(context);
    }

    private void open() {
        database = databaseHelper.getWritableDatabase();
        database.beginTransaction();
    }

    private void close() {
        database.setTransactionSuccessful();
        database.endTransaction();
        database.close();
        databaseHelper.close();
    }

    public boolean insertProductData(String date, int total, String name, int cost) {

        open();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.date, date);
        contentValues.put(DatabaseHelper.total, total);
        contentValues.put(DatabaseHelper.product, name);
        contentValues.put(DatabaseHelper.productCost, cost);
        long insertCheck = database.insert(DatabaseHelper.TABLE_NAME_COST_INFO, null, contentValues);

        close();
        if (insertCheck > 0) {
            return true;
        } else
            return false;
    }

    public ArrayList<CostOfProduct> getProductData() {
        open();
        ArrayList<CostOfProduct> modelList = new ArrayList<>();
        String selectQuery = "SELECT " + DatabaseHelper.preClause + " FROM " + DatabaseHelper.TABLE_NAME_COST_INFO + " " + DatabaseHelper.orderBy + "ID DESC";
        Cursor cursor = database.rawQuery(selectQuery, null);

        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            for (int i = 0; i < cursor.getCount(); i++) {
                CostOfProduct costOfProduct = new CostOfProduct();
                costOfProduct.setCost(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.productCost)));
                costOfProduct.setTotal(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.total)));
                costOfProduct.setDate(String.valueOf(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.date))));
                costOfProduct.setName(String.valueOf(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.product))));
                modelList.add(costOfProduct);
                cursor.moveToNext();
            }

        }
        cursor.close();
        close();
        return modelList;

    }

    public  int  getMaxTotal(){
        open();
        int total = 0 ;
        String selectQuery = "SELECT " + DatabaseHelper.total + " FROM " + DatabaseHelper.TABLE_NAME_COST_INFO + " " + DatabaseHelper.orderBy + "ID DESC LIMIT 1 ";
        Cursor cursor = database.rawQuery(selectQuery, null);


        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            for (int i = 0; i < cursor.getCount(); i++) {
                total =cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.total));
            }

        }
        cursor.close();
        close();
        return  total;


    }
}
