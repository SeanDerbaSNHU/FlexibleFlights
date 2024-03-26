package com.example.flexibleflights;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyViewHolder extends RecyclerView.ViewHolder {

    TextView priceText;
    TextView currencyText;
    TextView nameText;
    TextView timeText;
    TextView originText;
    TextView durationText;

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        priceText = itemView.findViewById(R.id.priceText);
        currencyText = itemView.findViewById(R.id.currencyTextView);
        nameText = itemView.findViewById(R.id.nameTextView);
        timeText = itemView.findViewById(R.id.timeTextView);
        originText = itemView.findViewById(R.id.originTextView);
        durationText = itemView.findViewById(R.id.durationTextView);
    }
}
