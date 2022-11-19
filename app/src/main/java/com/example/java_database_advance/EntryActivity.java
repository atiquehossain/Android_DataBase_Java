package com.example.java_database_advance;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.example.java_database_advance.database.DatabaseHelper;
import com.example.java_database_advance.database.DatabaseManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class EntryActivity extends AppCompatActivity {

    DatabaseHelper databaseHelper;
    DatabaseManager databaseManager;
    String sTotal, sDate, sProductName, sProductCost;
    int total, cost, year, month, day;
    EditText eTotal, eDate, eProductName, eProductCost;

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
        final Calendar calendar = Calendar.getInstance();

        eDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        EntryActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int month, int day) {
                                String mSelectedActualDate = day + "-" + month + "-" + year;
                                eDate.setText(mSelectedActualDate);
                            }
                        },
                        year, month, day);
                datePickerDialog.show();

            }
        });


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