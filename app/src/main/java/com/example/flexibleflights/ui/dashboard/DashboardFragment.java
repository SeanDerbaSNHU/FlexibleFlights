package com.example.flexibleflights.ui.dashboard;


import static java.lang.String.valueOf;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.NumberPicker;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.flexibleflights.AirportModel;
import com.example.flexibleflights.Item;
import com.example.flexibleflights.MyAdapter;
import com.example.flexibleflights.R;
import com.example.flexibleflights.databinding.FragmentDashboardBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Scanner;

import ir.mirrajabi.searchdialog.SimpleSearchDialogCompat;
import ir.mirrajabi.searchdialog.core.BaseSearchDialogCompat;
import ir.mirrajabi.searchdialog.core.SearchResultListener;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;
    List<Item> items = new ArrayList<Item>();
    final String url = "http://54.163.192.205:3000/search";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //////
        //Buttons
        /////
        Button date = root.findViewById(R.id.buttonDate);
        Button passengers = root.findViewById(R.id.buttonNumPassengers);
        Button search = root.findViewById(R.id.buttonSearch);
        Button orgin = root.findViewById(R.id.buttonOrgin);
        Button destination = root.findViewById(R.id.buttonDestination);

        /////
        //RecyclerView
        /////
        //items = makeDummyList();
        RecyclerView recyclerView = root.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new MyAdapter(items));


        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //DatePickerFragment newFragment = new DatePickerFragment();
                //newFragment.show(getParentFragmentManager(), "datePicker");
                // on below line we are getting
                // the instance of our calendar.
                final Calendar c = Calendar.getInstance();

                // on below line we are getting
                // our day, month and year.
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                // on below line we are creating a variable for date picker dialog.
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        // on below line we are passing context.
                        view.getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int month, int day) {
                                // on below line we are setting date to our text view.
                                month++; //Month returns month-1 for some reason, this fixes it
                                if(month > 9) {
                                    date.setText(year + "-" + (month) + "-" + day);
                                }
                                else{
                                    date.setText(year + "-0" + (month) + "-" + day);
                                }
                            }
                        },
                        // on below line we are passing year,
                        // month and day for selected date in our date picker.
                        year, month, day);
                // at last we are calling show to
                // display our date picker dialog.
                datePickerDialog.show();

            }
        });

        passengers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder d = new AlertDialog.Builder(root.getContext());

                View dialogView = inflater.inflate(R.layout.number_picker_dialog, null);
                d.setTitle("Title");
                d.setMessage("Message");
                d.setView(dialogView);
                final NumberPicker numberPicker = (NumberPicker) dialogView.findViewById(R.id.dialog_number_picker);
                numberPicker.setMaxValue(10);
                numberPicker.setMinValue(1);
                numberPicker.setWrapSelectorWheel(false);
                d.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        passengers.setText(numberPicker.getValue() +"");
                    }
                });
                d.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                AlertDialog alertDialog = d.create();
                alertDialog.show();
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("HELP", "Button pushed");
                RequestQueue queue = Volley.newRequestQueue(root.getContext());

                JSONArray array = new JSONArray();
                JSONObject jsonParam = new JSONObject();
                JSONObject paramPassenger = new JSONObject();
                try {
                    jsonParam.put("origin", orgin.getText());
                    jsonParam.put("destination", destination.getText());
                    jsonParam.put("departure_date", date.getText());
                    jsonParam.put("cabin_class", "economy");
                    paramPassenger.put("type", "adult");
                } catch (JSONException e){
                    e.printStackTrace();
                }

                JSONArray listPassengers = new JSONArray();
                for(int i = 0; i < Integer.valueOf((String) passengers.getText()); i++){
                    listPassengers.put(paramPassenger);
                }
                array.put(jsonParam);
                array.put(listPassengers);

                JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, url, array, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("RESPONSE", response.toString());
                        for(int i = 0; i < response.length(); i++){
                            try {
                                JSONObject object = response.getJSONObject(i);
                                items.add(makeItem(object));
                                Log.d("ITEMS", valueOf(items.size()));
                                recyclerView.getAdapter().notifyDataSetChanged();
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Error", error.toString());
//                        throw new RuntimeException(error);
                    }
                });
                jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(
                        50000,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                queue.add(jsonArrayRequest);
            }
        });

        orgin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    getAirport(orgin, root);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        destination.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    getAirport(destination, root);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        return root;
    }



    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

    }

    public List<Item> makeDummyList(){
        List<Item> items = new ArrayList<Item>();

        for(int i = 0; i < 20; i++){
            items.add(new Item(("Test" + valueOf(i))));
        }

        return items;
    }

    public Item makeItem(JSONObject object) throws JSONException {
        //Fetch necessary nested JSON objects

        JSONObject slices = object.getJSONArray("slices").getJSONObject(0);
        JSONObject origin = slices.getJSONObject("origin");
        JSONObject owner = object.getJSONObject("owner");
        JSONObject segments = slices.getJSONArray("segments").getJSONObject(0);
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
        String arrivalTime = segments.getString("arriving_at");
        String departTime = segments.getString("departing_at");
        String durationTime = slices.getString("duration");

        //Make and return Item object
        Item item = new Item(price, currency, name, total_emissions, aircraftName, destinationName, originName);
        item.setArrive_time(arrivalTime);
        item.setDepart_time(departTime);
        item.setDuration(durationTime);
        return item;
    }

    public ArrayList<AirportModel> createAirportList() throws IOException {
        ArrayList<AirportModel> result = new ArrayList<>();
        AssetManager assets = this.getContext().getAssets();
        String path = this.getContext().getFilesDir().getAbsolutePath();
        //File file = new File(path + "/app/src/main/java/airports.txt");
        try (Scanner s = new Scanner(assets.open("airports.txt"))){
            while(s.hasNext()){
                result.add(new AirportModel(s.nextLine()));
            }
        }
        return result;
    }

    public void getAirport(Button button, View root) throws IOException {

        new SimpleSearchDialogCompat<AirportModel>(root.getContext(), "Search...", "What are you looking for?", null, createAirportList(), new SearchResultListener<AirportModel>() {
            @Override
            public void onSelected(BaseSearchDialogCompat dialog, AirportModel item, int position) {
                // If filtering is enabled, [position] is the index of the item in the filtered result, not in the unfiltered source
                button.setText(item.getIATA());
                dialog.dismiss();
            }
        }).show();


    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
