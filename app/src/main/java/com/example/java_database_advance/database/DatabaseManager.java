package com.example.java_database_advance.database;

import static com.example.java_database_advance.database.DatabaseHelper.TABLE_NAME;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.java_database_advance.CostOfProduct;

import java.util.ArrayList;

/*
 *** DB Operation ****
 */
public class DatabaseManager {
    private Context context;
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;
    public Integer total, temp, tempRemaining, sum, remainingValue, lastRemainingId = null;

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
        contentValues.put(DatabaseHelper.totalBalance, total);
        contentValues.put(DatabaseHelper.productName, name);
        contentValues.put(DatabaseHelper.productCost, cost);
        remainingValue = RemainingCash(cost, total);

        contentValues.put(DatabaseHelper.RemainingBalance, remainingValue);

        //   Update Budget SET remainingBalance = total - Cost WHERE id =1 ;


        long insertCheck = database.insert(TABLE_NAME, null, contentValues);

        close();
        if (insertCheck > 0) {
            return true;
        } else
            return false;
    }

    private Integer RemainingCash(int cost, int total) {
        int tempValue = 0;
        String selectQuery = "SELECT " + DatabaseHelper.RemainingBalance + " FROM " + TABLE_NAME + " " + DatabaseHelper.orderBy + "ID DESC LIMIT 1 ";
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor != null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                tempValue = cursor.getInt(0);
                tempRemaining = tempValue - cost;
                //  ;

            }
        } else {
            tempRemaining = total - cost;
            // contentValues.put(DatabaseHelper.RemainingBalance, tempValue);
        }
        return tempRemaining;

    }

    public void deleteAllData() {
        open();
        database.execSQL("Delete FROM " + TABLE_NAME);
        close();
    }

    public ArrayList<CostOfProduct> getProductData() {
        open();
        ArrayList<CostOfProduct> modelList = new ArrayList<>();
        String selectQuery = "SELECT " + DatabaseHelper.preClause + " FROM " + TABLE_NAME + " " + DatabaseHelper.orderBy + "ID DESC";
        Cursor cursor = database.rawQuery(selectQuery, null);

        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            for (int i = 0; i < cursor.getCount(); i++) {
                CostOfProduct costOfProduct = new CostOfProduct();
                costOfProduct.setCost(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.productCost)));
                costOfProduct.setTotal(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.totalBalance)));
                costOfProduct.setDate(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.date)));
                costOfProduct.setName(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.productName)));
                modelList.add(costOfProduct);
                cursor.moveToNext();
            }

        }
        cursor.close();
        close();
        return modelList;

    }

    public Integer lastTotalBalance() {
        open();
        String selectQuery = "SELECT " + DatabaseHelper.totalBalance + " FROM " + TABLE_NAME + " " + DatabaseHelper.orderBy + "ID DESC LIMIT 1 ";
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor != null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                total = cursor.getInt(0);
            }
        }
        cursor.close();
        close();
        return total;
    }

    public Integer lastId() {
        open();
        String selectQuery = "SELECT " + DatabaseHelper.id + " FROM " + TABLE_NAME + " " + DatabaseHelper.orderBy + "ID DESC LIMIT 1 ";
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor != null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                temp = cursor.getInt(0);
            }
        }
        cursor.close();
        close();
        return temp;
    }

    public Integer lastRemainingBalance() {
        open();
        String selectQuery = "SELECT " + DatabaseHelper.RemainingBalance + " FROM " + TABLE_NAME + " " + DatabaseHelper.orderBy + "ID DESC LIMIT 1 ";
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor != null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                temp = cursor.getInt(0);
            }
        }
        cursor.close();
        close();
        return temp;
    }

    public  void BalanceUpdate(Integer BalanceUpdate){
        Integer lastid = lastId();
        Integer lastRemainingbalance = lastRemainingBalance();
        if(lastid != null && lastid >0){
            temp = lastRemainingbalance + BalanceUpdate;
            String tempStr =temp.toString();
            open();
                    String selectQuery = "UPDATE "  + TABLE_NAME + " SET "+ DatabaseHelper.RemainingBalance +" = "+ tempStr + " WHERE " + DatabaseHelper.id +" = " + lastid ;
                    Cursor cursor = database.rawQuery(selectQuery, null);
                    if (cursor != null && cursor.getCount() > 0) {
                        if (cursor.moveToFirst()) {
                            lastRemainingId = cursor.getInt(0);
                        }
                    }
            cursor.close();
            close();
        }
    }

    public Integer sumOfColumn() {
        open();
        String selectQuery = "SELECT " + "SUM(" + DatabaseHelper.productCost + ")" + " FROM " + TABLE_NAME + " " + DatabaseHelper.orderBy + "ID DESC ";
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor != null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                sum = cursor.getInt(0);
            }
        }
        cursor.close();
        close();
        return sum;
    }

}
