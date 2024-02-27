package com.example.flexibleflights;

import static java.lang.String.valueOf;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
        /////
        //RecyclerView
        /////
        List<Item> items = makeDummyList();
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new MyAdapter(getApplicationContext(), items));
    }

    public List<Item> makeDummyList(){
        List<Item> items = new ArrayList<Item>();

        for(int i = 0; i < 3; i++){
            items.add(new Item(valueOf(i)));
        }
        items.add(new Item("test"));

        return items;
    }
    
}