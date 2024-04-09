package com.example.flexibleflights;

import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Item {
    String total_amount; //Price
    String base_currency; //USD, GBP, EURO, etc
    String name; //Name of airline (owner.name)
    String total_emissions_kg; //Total emissions made by flight
    String aircraft_name; //Name of the aircraft ex. Airbus Industries A380
    String destination_name;
    String origin_name;
    String arrive_time;
    String depart_time;
    String duration;

    public String getDepart_time() {
        return depart_time;
    }

    public void setDepart_time(String depart_time) {
        this.depart_time = formatDate(depart_time);
    }
    public void setDepart_time_nf(String depart_time) {
        this.depart_time = depart_time;
    }


    public String getArrive_time() {
        return arrive_time;
    }

    public void setArrive_time(String arrive_time) {
        this.arrive_time = formatDate(arrive_time);
    }
    //No format
    public void setArrive_time_nf(String arrive_time) {
        this.arrive_time = arrive_time;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        //this.duration = formatTime(duration);
        String time = duration.substring(2);
        this.duration = time;
    }
    public void setDuration_nf(String duration) {
        this.duration = duration;
    }



    public String getTotal_amount() {
        return total_amount;
    }

    public String getBase_currency() {
        return base_currency;
    }

    public String getName() {
        return name;
    }

    public String getTotal_emissions_kg() {
        return total_emissions_kg;
    }

    public String getAircraft_name() {
        return aircraft_name;
    }

    public String getDestination_name() {
        return destination_name;
    }

    public String getOrigin_name() {
        return origin_name;
    }

    public Item(String total_amount, String base_currency, String name, String total_emissions_kg, String aircraft_name, String destination_name, String origin_name) {
        this.total_amount = total_amount;
        this.base_currency = base_currency;
        this.name = name;
        this.total_emissions_kg = total_emissions_kg;
        this.aircraft_name = aircraft_name;
        this.destination_name = destination_name;
        this.origin_name = origin_name;
    }
    public Item(){
        this.total_amount = "test";
        this.base_currency = "test";
        this.name = "test";
        this.total_emissions_kg = "test";
        this.aircraft_name = "test";
        this.destination_name = "test";
        this.origin_name = "test";
    }

    public Item(String sample){
        this.total_amount = sample;
        this.base_currency = sample;
        this.name = sample;
        this.total_emissions_kg = sample;
        this.aircraft_name = sample;
        this.destination_name = sample;
        this.origin_name = sample;
    }

    private String formatDate(String d){
        DateFormat dateFormat = new SimpleDateFormat("hh:mm a");
        String time = "";
        String hour = d.substring(11, 13);
        String minute = d.substring(13, 16);
        int int_hour = Integer.parseInt(hour);
        if(int_hour > 12){time = (int_hour - 12) + minute + " PM";}
        else if (int_hour == 12){time = hour + minute + " PM";}
        else{time = hour + minute + " AM";}
        /*try {
            Date date = dateFormat.parse(time);
            date_s = date.toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }*/
        return time;
    }

    private String formatTime(String t){
        DateFormat dateFormat = new SimpleDateFormat("hh:mm a");
        String time = "";
        try {
            Date date = dateFormat.parse(t);
            time = date.toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }
    public void saveToDB(String email){
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Map<String, Object> item = new HashMap<>();
        item.put("total_amount", this.total_amount);
        item.put("base_currency", this.base_currency);
        item.put("name", this.name);
        item.put("total_emissions_kg", this.total_emissions_kg);
        item.put("aircraft_name", this.aircraft_name);
        item.put("destination_name", this.destination_name);
        item.put("origin_name", this.origin_name);
        item.put("arrive_time", this.arrive_time);
        item.put("depart_time", this.depart_time);
        item.put("duration", this.duration);

        db.collection("users").document(email).collection("SavedFlights").add(item);

    }

}
