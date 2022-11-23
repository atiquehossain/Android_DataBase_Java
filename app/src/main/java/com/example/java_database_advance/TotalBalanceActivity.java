package com.example.java_database_advance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.java_database_advance.database.DatabaseManager;

public class TotalBalanceActivity extends AppCompatActivity {

    private EditText editText;
    private DatabaseManager databaseManager;
    Integer totalRemainingBalance;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total_balance);
        databaseManager = new DatabaseManager(this);
        displayData();

        final Button button = findViewById(R.id.mLoginBtn);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                editText = findViewById(R.id.totalBalanceUpdate);
                String updateRemaining = editText.getText().toString();
                databaseManager.BalanceUpdate(Integer.parseInt(updateRemaining));
                displayData();
                editText.getText().clear();

            }

        });
    }

    private void displayData() {

        totalRemainingBalance = databaseManager.lastRemainingBalance();
        // total = databaseManager.BalanceUpdate();
        if (totalRemainingBalance != null) {
            ((TextView) findViewById(R.id.balance)).setText(totalRemainingBalance + " ");
        } else {
            ((TextView) findViewById(R.id.balance)).setText(R.string.no_data_message);
        }


    }
}