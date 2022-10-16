package com.example.java_database_advance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.java_database_advance.database.DatabaseHelper;
import com.example.java_database_advance.database.DatabaseManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class EntryActivity extends AppCompatActivity {

    DatabaseHelper databaseHelper;
    DatabaseManager databaseManager;
    String sTotal, sDate, sProductName, sProductCost;
    int total, cost;
    EditText eTotal, eDate, eProductName, eProductCost;
    FloatingActionButton mBtn;

    Integer i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);

        databaseHelper = new DatabaseHelper(this);
        databaseManager = new DatabaseManager(this);

        eTotal = findViewById(R.id.total);
        eDate = findViewById(R.id.date);
        eProductName = findViewById(R.id.name);
        eProductCost = findViewById(R.id.Cost);

        i = databaseManager.getMaxTotal();
        if (i != null) {
            ((TextView) findViewById(R.id.total)).setText(String.valueOf(i));
            findViewById(R.id.total).setFocusable(false);
        }

    }

    public void addOrUpdateMaster(View view) {
        sTotal = eTotal.getText().toString().trim();
        sDate = eDate.getText().toString().trim();
        sProductName = eProductName.getText().toString().trim();
        sProductCost = eProductCost.getText().toString().trim();
        total = Integer.parseInt(sTotal);
        cost = Integer.parseInt(sProductCost);

        boolean databaseCheck = databaseManager.insertProductData(sDate, total, sProductName, cost);

        if (databaseCheck == true) {
            finish();
        } else
            Log.d("Atique", "failed: ");

    }
}