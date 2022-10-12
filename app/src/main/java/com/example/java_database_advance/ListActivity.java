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
        Log.d("totalArraySize", "displayData: "+databaseManager.getProductData().size());
        Log.d("totalArraySize", "displayData: "+databaseManager.getProductData().get(1).getDate());
        arrCostOfProducts.clear();
        arrCostOfProducts.addAll(databaseManager.getProductData()) ;
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
