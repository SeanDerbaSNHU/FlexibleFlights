package com.example.flexibleflights;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyViewHolder extends RecyclerView.ViewHolder {

    TextView priceText;
    TextView currencyText;
    TextView nameText;
    TextView aircraftText;
    TextView originText;

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        priceText = itemView.findViewById(R.id.priceText);
        currencyText = itemView.findViewById(R.id.currencyTextView);
        nameText = itemView.findViewById(R.id.nameTextView);
        aircraftText = itemView.findViewById(R.id.aircraftTextView);
        originText = itemView.findViewById(R.id.originTextView);
    }
}
