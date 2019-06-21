package com.example.pixagallery.utils;

import android.content.Context;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ApiHelper {

    private final String ENDPOINT_URL = "https://pixabay.com/api/?key=12809799-c69ee2dede5a2d9c69622d9db&image_type=photo";
    private Gson gson;
    private GsonBuilder gsonBuilder;
    public StringRequest request;
    Context context;

    public ApiHelper(Context context) {
        this.context = context;
        this.gsonBuilder = new GsonBuilder();
        this.gson = gsonBuilder.create();
    }

    public String getENDPOINT_URL() {
        return ENDPOINT_URL;
    }

    public Gson getGson() {
        return gson;
    }
}
