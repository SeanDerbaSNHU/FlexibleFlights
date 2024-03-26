package com.example.flexibleflights;

import ir.mirrajabi.searchdialog.core.Searchable;

public class AirportModel implements Searchable {
    private String title;
    private String IATA;

    public AirportModel(String t){
        title = t;
        IATA = t.substring(0,3);
    }
    @Override
    public String getTitle() {
        return title;
    }

    public String getIATA() {
        return IATA;
    }


}
