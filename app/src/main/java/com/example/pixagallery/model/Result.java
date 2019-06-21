package com.example.pixagallery.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Result {

    @SerializedName("totalHits")
    @Expose
    private int numOfresults;

    @SerializedName("hits")
    @Expose
    private List<Hit> hits;

    public Result() { }

    public int getNumOfresults() {
        return numOfresults;
    }

    public List<Hit> getHits() {
        return hits;
    }
}
