package com.example.pixagallery.presenter;

import android.content.Context;

import com.example.pixagallery.contract.MainActivityContract;
import com.example.pixagallery.model.Hit;
import com.example.pixagallery.model.MainActModelImp;
import com.example.pixagallery.view.RecAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActPresenterImp implements MainActivityContract.Presenter {

    List<Hit> hitList = new ArrayList<>();
    MainActivityContract.View mView;
    Context context;
    MainActModelImp modelImp;
    Hit currHit;

    public MainActPresenterImp(MainActivityContract.View mView, Context context) {
        this.mView = mView;
        this.context = context;
        modelImp =  new MainActModelImp(context,this);
    }

    @Override
    public void initData() {
        mView.showLoader();
        modelImp.fetchData();
    }

    @Override
    public void onBindViewAtPosition(RecAdapter.MyViewHolder holder, int position) {
        Hit hit = hitList.get(position);
        currHit = hit;
        holder.showLoader();
        holder.setThumbnailView(hit.getPreviewURL());
        holder.hideLoader();
    }

    @Override
    public void intentActivity(List<Hit> hits, int currPosition) {
        mView.startIntentActivity(hits,currPosition);
    }

    public void startActvity(int pos){
        mView.startIntentActivity(hitList,pos);
    }
    @Override
    public void fetchFailed() {
        mView.hideLoader();
        mView.showErr();
    }

    @Override
    public void fetchSuccess(List<Hit> changedHits) {
        hitList = changedHits;
        mView.dataHasChanged();
        mView.hideLoader();
    }

    @Override
    public int getHitsCount() {
        return modelImp.getHits().size();
    }
}
