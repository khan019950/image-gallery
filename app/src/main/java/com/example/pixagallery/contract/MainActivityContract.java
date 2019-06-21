package com.example.pixagallery.contract;

import com.android.volley.toolbox.StringRequest;
import com.example.pixagallery.model.Hit;
import com.example.pixagallery.view.RecAdapter;

import java.util.List;

public interface MainActivityContract {
    interface View {

        void initView();
        void showErr();
        void startIntentActivity(List<Hit> hits, int currPosition);
        void showLoader();
        void hideLoader();
        void dataHasChanged();

    }

    interface Presenter {
        void initData();
        void fetchFailed();
        void fetchSuccess(List<Hit> changedHits);
        void onBindViewAtPosition(RecAdapter.MyViewHolder holder, int position);
        int getHitsCount();
        void intentActivity(List<Hit> hits, int currPosition);
    }

    interface Model {
        void fetchData();
    }

    interface RecyclerRowView {
        void setThumbnailView(String url);
        void showLoader();
        void hideLoader();
        void setItemClickListener(List<Hit> hits, int currPosition);
    }


}
