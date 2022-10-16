package com.example.java_database_advance;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

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
        holder.total_id.setText(String.valueOf(arrCostOfProducts.get(position).total));
        holder.cost_id.setText(String.valueOf(arrCostOfProducts.get(position).Cost));
        holder.date_id.setText(arrCostOfProducts.get(position).date);


    }

    @Override
    public int getItemCount() {
        return arrCostOfProducts.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView date_id, total_id, Product_id, cost_id;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            date_id = itemView.findViewById(R.id.date_id);
            total_id = itemView.findViewById(R.id.total_id);
            cost_id = itemView.findViewById(R.id.cost_id);
        }
    }
}
