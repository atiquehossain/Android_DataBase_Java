package com.example.java_database_advance;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.java_database_advance.database.DatabaseManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {
    FloatingActionButton addBtn;
    ArrayList<CostOfProduct> arrCostOfProducts;
    DatabaseManager databaseManager;
    MyAdapter myAdapter;
    Context context;
    RecyclerView recyclerView;

    static int a = 1 ;
    Integer total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        addBtn = findViewById(R.id.add_new);
        databaseManager = new DatabaseManager(this);
        recyclerView = findViewById(R.id.mRecyclerView);
        arrCostOfProducts = new ArrayList<>();
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

    }

    private void displayData() {
        arrCostOfProducts.clear();
        arrCostOfProducts.addAll(databaseManager.getProductData()) ;
        total = databaseManager.getMaxTotal();
        if(total != null){
            ((TextView)findViewById(R.id.totalValue)).setText(total + " ");
        }else {
            ((TextView)findViewById(R.id.totalValue)).setText(R.string.no_data_message);
        }

        myAdapter.notifyDataSetChanged();

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(a==1) {
            displayData();
        }
    }
}
