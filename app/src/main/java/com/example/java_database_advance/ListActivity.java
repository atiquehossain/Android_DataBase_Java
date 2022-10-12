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

import com.example.java_database_advance.database.DatabaseManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {
    FloatingActionButton addBtn;
    ArrayList<String> arrTotal, arrDate, arrProductName, arrProductCost;
    DatabaseManager databaseManager;
    MyAdapter myAdapter;
    Context context;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        addBtn = findViewById(R.id.add_new);

        databaseManager = new DatabaseManager(this);
        arrTotal = new ArrayList<>();
        arrDate = new ArrayList<>();
        arrProductCost = new ArrayList<>();
        arrProductName = new ArrayList<>();
        recyclerView = findViewById(R.id.mRecyclerView);
        myAdapter = new MyAdapter(this,arrDate,arrTotal,arrProductName,arrProductCost);
        
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        
        displayData();

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ListActivity.this,EntryActivity.class);
                startActivity(intent);
            }
        });

    }

    private void displayData() {

        Cursor  cursor =databaseManager.getProductData();
        if (cursor.getCount() == 0){
            Log.d("Atique", "displayData: ");

        }else {
            while (cursor.moveToNext()){
                arrDate.add(cursor.getString(0));
                arrTotal.add(cursor.getString(1));
                arrProductCost.add(cursor.getString(2));
                arrProductName.add(cursor.getString(3));

            }
        }
    }
}