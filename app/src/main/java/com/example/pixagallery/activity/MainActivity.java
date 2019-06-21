package com.example.pixagallery.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.example.pixagallery.R;
import com.example.pixagallery.contract.MainActivityContract;
import com.example.pixagallery.model.Hit;
import com.example.pixagallery.presenter.MainActPresenterImp;
import com.example.pixagallery.utils.ApiHelper;
import com.example.pixagallery.view.RecAdapter;
import com.example.pixagallery.view.ZoomImgFragment;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wang.avi.AVLoadingIndicatorView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MainActivityContract.View {

    private AVLoadingIndicatorView loadingIndicatorView;
    private RecyclerView recyclerView;
    private RecAdapter recAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private MainActPresenterImp presenterImp;

    public interface ClickListener {
        void onClick(View view, int position);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadingIndicatorView = findViewById(R.id.main_loader);
        getSupportActionBar().setTitle("Pixabay Gallery");
        showLoader();
        presenterImp = new MainActPresenterImp(this, this);
        presenterImp.initData();
    }

    @Override
    public void initView() {
        recyclerView = findViewById(R.id.mainRecyclerView);
        layoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(false);
        recAdapter = new RecAdapter(presenterImp);
        recyclerView.setAdapter(recAdapter);
        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {


            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
                View child = rv.findChildViewUnder(e.getX(), e.getY());
                presenterImp.startActvity(rv.getChildLayoutPosition(child));
                return false;
            }

            @Override
            public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {


            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });
        recAdapter.notifyDataSetChanged();
    }

    @Override
    public void showErr() {
        Toast.makeText(this,"Unable to fetch data, Please try later", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void startIntentActivity(List<Hit> hits, int currPosition) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        //add
        ZoomImgFragment zoomImgFragment = new ZoomImgFragment().newInstance((Serializable) hits, currPosition);


    }


    @Override
    public void showLoader() {
        loadingIndicatorView.show();

    }

    @Override
    public void hideLoader() {
        loadingIndicatorView.hide();
    }

    @Override
    public void dataHasChanged() {
        initView();
        hideLoader();
        if (recyclerView.getLayoutManager() != null) {
            recyclerView.getLayoutManager().smoothScrollToPosition(recyclerView, new RecyclerView.State(), 0);
        }
    }
}
