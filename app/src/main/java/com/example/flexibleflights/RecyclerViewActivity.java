package com.example.flexibleflights;

import static java.lang.String.valueOf;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RecyclerViewActivity extends AppCompatActivity {

    List<Item> items = new ArrayList<Item>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
        final String url = "http://54.163.192.205:3000";
        try {
            JsonTest((url + "/search"));
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        items = makeDummyList();
        /////
        //RecyclerView
        /////
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new MyAdapter(getApplicationContext(), items));
    }

    public List<Item> makeDummyList(){
        List<Item> items = new ArrayList<Item>();

        for(int i = 0; i < 20; i++){
            items.add(new Item(("Test" + valueOf(i))));
        }

        return items;
    }

    public void JsonTest(String url) throws JSONException {
        RequestQueue queue = Volley.newRequestQueue(RecyclerViewActivity.this);
        HashMap<String, String> params = new HashMap<String, String>();
        JSONArray array = new JSONArray();
        JSONObject jsonParam = new JSONObject();
        try {
            jsonParam.put("origin", "LHR");
            jsonParam.put("destination", "JFK");
            jsonParam.put("departure_date", "2024-03-01");
            jsonParam.put("cabin_class", "economy");
        } catch (JSONException e){
            e.printStackTrace();
        }
        array.put(jsonParam);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonParam, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                // below are the strings which we
                // extract from our json object.
                Log.d("RESPONSE", response.toString());
                try {
                   JSONArray offers = response.getJSONArray("offers");
                    for (int i = 0; i < offers.length(); i++){
                        JSONObject object = offers.getJSONObject(i);
                        items.add(makeItem(object));
                        Log.d("ITEMS", valueOf(items.size()));
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("ERROR", "No response");
            }
        });
        //JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, url, array, )
        queue.add(jsonObjectRequest);
    }

public Item makeItem(JSONObject object) throws JSONException {
        //Fetch necessary nested JSON objects

        JSONObject slices = object.getJSONArray("slices").getJSONObject(0);
        JSONObject origin = slices.getJSONObject("origin");
        JSONObject owner = object.getJSONObject("owner");
        JSONObject segments = slices.getJSONObject("segments");
        JSONObject aircraft = segments.getJSONObject("aircraft");
        JSONObject destination = slices.getJSONObject("destination");

        //Get necessary variables from JSON
        String price = object.getString("total_amount");
        String currency = object.getString("total_currency");
        String name = owner.getString("name");
        String total_emissions = object.getString("total_emissions_kg");
        String originName = origin.getString("city_name");
        String destinationName = destination.getString("city_name");
        String aircraftName = aircraft.getString("name");

        //Make and return Item object
        Item item = new Item(price, currency, name, total_emissions, aircraftName, destinationName, originName);
        return item;
}


}