package com.example.java_database_advance;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.java_database_advance.database.DatabaseHelper;
import com.example.java_database_advance.database.DatabaseManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

import java.util.ArrayList;

import antonkozyriatskyi.circularprogressindicator.CircularProgressIndicator;

public class ListActivity extends AppCompatActivity {
    ArrayList<CostOfProduct> arrCostOfProducts;
    DatabaseManager databaseManager;
    MyAdapter myAdapter;
    Context context;
    RecyclerView recyclerView;
    CircularProgressIndicator circularProgress;
    PopUpAlert popUpAlert;

    static int a = 1;
    Integer total, totalCost;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        context = this;
        databaseManager = new DatabaseManager(this);
        recyclerView = findViewById(R.id.mRecyclerView);
        arrCostOfProducts = new ArrayList<>();
        circularProgress = new CircularProgressIndicator(this);
        myAdapter = new MyAdapter(this, arrCostOfProducts);
        popUpAlert = new PopUpAlert(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(myAdapter);

    }


    private void displayData() {
        arrCostOfProducts.clear();
        arrCostOfProducts.addAll(databaseManager.getProductData());
        total = databaseManager.getMaxTotal();
        if (total != null) {
            ((TextView) findViewById(R.id.totalValue)).setText(total + " ");
        } else {
            ((TextView) findViewById(R.id.totalValue)).setText(R.string.no_data_message);
        }
        totalCost = databaseManager.sumOfColumn();
        if (totalCost != null) {
            ((TextView) findViewById(R.id.costValue)).setText(totalCost + " ");
        } else {
            ((TextView) findViewById(R.id.costValue)).setText(R.string.no_data_message);
        }
        if (total != null && totalCost != null) {
            int v = total - totalCost;
            ((TextView) findViewById(R.id.balance)).setText(v + " ");

        }

        myAdapter.notifyDataSetChanged();

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (a == 1) {
            displayData();
        }

    }

    public void totalBtn(View view) {
        Intent intent = new Intent(ListActivity.this, TotalBalanceActivity.class);
        startActivity(intent);
    }

    public void addNewRecord(View view) {
        Intent intent = new Intent(ListActivity.this, EntryActivity.class);
        startActivity(intent);
    }

    public void deleteAllData(View view) {
        popUpAlert.showWarning(context.getString(R.string.yes));
        popUpAlert.setAlertListener(new PopUpAlert.AlertListener() {
            @Override
            public void onAlertClick(boolean isCancel) {
                if (!isCancel) {
                    /*SweetAlertDialog pDialog = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
                    pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                    pDialog.setTitleText("Loading");
                    pDialog.setCancelable(false);
                    pDialog.show();*/
                    databaseManager.deleteAllData();
                    finish();
                    overridePendingTransition(0, 0);
                    startActivity(getIntent());
                    overridePendingTransition(0, 0);
                } else {
                    Log.d("Atique", "onAlertClick: 1 ");
                }
            }
        });

    }
}
