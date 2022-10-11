package com.example.java_database_advance.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/*
 *** DB Operation ****
 */
public class DatabaseManager {
    private Context context;
    private  DatabaseHelper databaseHelper;
    private SQLiteDatabase database;

    public  DatabaseManager(Context context) {
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

    public boolean insertProductData(String date,int total, String name, int cost){

        open();
        ContentValues contentValues =new ContentValues();
        contentValues.put(DatabaseHelper.date ,date);
        contentValues.put(DatabaseHelper.total ,total);
        contentValues.put(DatabaseHelper.product ,name);
        contentValues.put(DatabaseHelper.productCost ,cost);
        long  insertCheck = database.insert(DatabaseHelper.TABLE_NAME , null,contentValues);

        close();
        if (insertCheck > 0){
            return  true;
        }return  false;
    }

    public Cursor getProductData(){
        open();
        String selectQuery = "SELECT " + DatabaseHelper.preClause + " FROM " + DatabaseHelper.TABLE_NAME + " " + DatabaseHelper.whereClause + " " + DatabaseHelper.orderBy + "ID DESC";
        Cursor cursor =database.rawQuery(selectQuery,null);
        return  cursor;
    }
}
