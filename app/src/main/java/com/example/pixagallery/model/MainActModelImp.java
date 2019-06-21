package com.example.pixagallery.model;

import android.content.Context;


import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.pixagallery.contract.MainActivityContract;
import com.example.pixagallery.presenter.MainActPresenterImp;
import com.example.pixagallery.utils.ApiHelper;

import java.util.ArrayList;
import java.util.List;

public class MainActModelImp implements MainActivityContract.Model {

    private Context context;
    private ApiHelper apiHelper;
    private MainActPresenterImp presenterImp;
    private List<Hit> hits = new ArrayList<Hit>();

    public MainActModelImp(Context context, MainActPresenterImp presenterImp) {
        this.context = context;
        this.presenterImp = presenterImp;
        this.apiHelper = new ApiHelper(context);
    }

    public List<Hit> getHits() {
        return hits;
    }

    @Override
    public void fetchData() {
        apiHelper.request = new StringRequest(apiHelper.getENDPOINT_URL(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null) {
                    if (!hits.isEmpty()){
                        hits.clear();
                    }
                    hits = apiHelper.getGson().fromJson(response, Result.class).getHits();
                    presenterImp.fetchSuccess(hits);
                }else {
                    presenterImp.fetchFailed();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                presenterImp.fetchFailed();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(apiHelper.request);
    }


}
