package com.example.flexibleflights;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {
//TODO : CHANGE FROM <Item> TO DIRECT JSON OBJECTS

    List<Item> items;
    private OnClickListener onClickListener;


    public MyAdapter(List<Item> items) {

        this.items = items;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.nameText.setText(items.get(position).getName());
        holder.priceText.setText(items.get(position).getTotal_amount());
        holder.currencyText.setText(items.get(position).getBase_currency());
        holder.timeText.setText((items.get(position).getDepart_time()) + " - " + ((items.get(position).getArrive_time())));
        holder.originText.setText(items.get(position).getOrigin_name() + " -> " + items.get(position).getDestination_name());
        holder.durationText.setText(items.get(position).getDuration());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onClickListener != null) {
                    onClickListener.onClick(position, items.get(position));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }
    public interface OnClickListener {
        void onClick(int position, Item model);
    }

    public void delete(int position) {
        items.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, items.size());
    }
}
