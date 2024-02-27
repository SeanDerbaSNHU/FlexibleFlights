package com.example.flexibleflights;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {
//TODO : CHANGE FROM <Item> TO DIRECT JSON OBJECTS
    Context context;
    List<Item> items;


    public MyAdapter(Context context, List<Item> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.nameText.setText(items.get(position).getName());
        holder.priceText.setText(items.get(position).getTotal_amount());
        holder.currencyText.setText(items.get(position).getBase_currency());
        holder.aircraftText.setText(items.get(position).getAircraft_name());
        holder.originText.setText(items.get(position).getOrigin_name());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
