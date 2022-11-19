package com.example.java_database_advance;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.java_database_advance.database.DatabaseManager;

import java.util.ArrayList;

import antonkozyriatskyi.circularprogressindicator.CircularProgressIndicator;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<CostOfProduct> arrCostOfProducts;


    public MyAdapter(Context context, ArrayList<CostOfProduct> arrCostOfProducts) {

        this.context = context;
        this.arrCostOfProducts = arrCostOfProducts;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.adapter_group_register_recycler_view, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        CostOfProduct costOfProduct = arrCostOfProducts.get(position);
        holder.total_id.setText(String.valueOf(costOfProduct.total));
        holder.cost_id.setText(String.valueOf(costOfProduct.Cost));
        holder.date_id.setText(costOfProduct.date);
        double max = 100.00;
        holder.circularProgress.setMaxProgress(max);
        holder.circularProgress.setCurrentProgress ((100*costOfProduct.Cost)/ costOfProduct.total );;

    }

    @Override
    public int getItemCount() {
        return arrCostOfProducts.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView date_id, total_id, Product_id, cost_id;
        CircularProgressIndicator circularProgress;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            date_id = itemView.findViewById(R.id.date_id);
            total_id = itemView.findViewById(R.id.total_id);
            cost_id = itemView.findViewById(R.id.cost_id);
            circularProgress = itemView.findViewById(R.id.percentage);
        }
    }

}
