package com.example.java_database_advance;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.java_database_advance.database.DatabaseHelper;
import com.example.java_database_advance.database.DatabaseManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class EntryActivity extends AppCompatActivity {

    DatabaseHelper databaseHelper;
    DatabaseManager databaseManager;
    String sTotal, sDate, sProductName, sProductCost;
    int total,cost;
    EditText eTotal, eDate, eProductName, eProductCost;
    FloatingActionButton mBtn;

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
        mBtn = findViewById(R.id.mSaveMaster);

        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sTotal = eTotal.getText().toString().trim();
                sDate = eDate.getText().toString().trim();
                sProductName = eProductName.getText().toString().trim();
                sProductCost = eProductCost.getText().toString().trim();

                total =Integer.parseInt(sTotal);
                cost =Integer.parseInt(sProductCost);

                boolean databaseCheck = databaseManager.insertProductData(sDate,total,sProductName,cost);

                if(databaseCheck == true){
                    Log.d("Atique", "Sucess: ");
                }else
                    Log.d("Atique", "failed: ");

            }
        });
    }
}