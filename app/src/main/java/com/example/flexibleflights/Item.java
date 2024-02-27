package com.example.flexibleflights;

public class Item {
    String total_amount; //Price
    String base_currency; //USD, GBP, EURO, etc
    String name; //Name of airline (owner.name)
    String total_emissions_kg; //Total emissions made by flight
    String aircraft_name; //Name of the aircraft ex. Airbus Industries A380
    String destination_name;
    String origin_name;

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

}
