package com.example.java_database_advance;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.java_database_advance.database.DatabaseHelper;
import com.example.java_database_advance.database.DatabaseManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import antonkozyriatskyi.circularprogressindicator.CircularProgressIndicator;

public class ListActivity extends AppCompatActivity {
    FloatingActionButton addBtn;
    ArrayList<CostOfProduct> arrCostOfProducts;
    DatabaseManager databaseManager;
    MyAdapter myAdapter;
    Context context;
    RecyclerView recyclerView;
    CircularProgressIndicator circularProgress;

    static int a = 1;
    Integer total ,totalCost;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        addBtn = findViewById(R.id.add_new);
        databaseManager = new DatabaseManager(this);
        recyclerView = findViewById(R.id.mRecyclerView);
        arrCostOfProducts = new ArrayList<>();
      //  circularProgress = new CircularProgressIndicator(this);
        myAdapter = new MyAdapter(this, arrCostOfProducts);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(myAdapter);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListActivity.this, EntryActivity.class);
                startActivity(intent);
            }
        });

      //  getDashboardData();

    }

    private void getDashboardData(){
        circularProgress = findViewById(R.id.percentage);
        double max = 100.00;
        circularProgress.setMaxProgress(max);

        Thread t1 = new Thread(new Runnable() {
            public void run()
            {
                total = databaseManager.getMaxTotal();
                totalCost = databaseManager.sumOfColumn();
                int percentage = 0;
                if(total!= null){
                    if(totalCost!= null){
                      //  int total1 = total.length, current = lists2.length;
                        percentage = (total*totalCost)/100;
                    }
                }
                circularProgress.setCurrentProgress(percentage);
            }});
        t1.start();


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
        if(total !=null && totalCost !=null ){
            int v =total - totalCost;
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
}
