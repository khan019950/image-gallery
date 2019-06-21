package com.example.pixagallery.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.example.pixagallery.R;
import com.example.pixagallery.model.Hit;
import com.example.pixagallery.presenter.MainActPresenterImp;
import com.google.gson.annotations.SerializedName;
import com.wang.avi.AVLoadingIndicatorView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class ZoomImgFragment extends Fragment {

    private String TAG = ZoomImgFragment.class.getSimpleName();
    private MainActPresenterImp presenterImp;
    private ViewPager viewPager;
    private SubsamplingScaleImageView scaleImageView;
    private AVLoadingIndicatorView fragLoder;
    private ViewPagerAdapter viewPagerAdapter;
    private List<Hit> hitList;
    private int currPos = 0;



    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    public ZoomImgFragment() {
        //presenterImp = new MainActPresenterImp();
    }


    public static ZoomImgFragment newInstance(Serializable hits, int position) {
        ZoomImgFragment fragment = new ZoomImgFragment();
        Bundle args = new Bundle();
        args.putSerializable("hits", hits);
        args.putInt("position", position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_zoom_img, container, false);
        viewPager = (ViewPager) view.findViewById(R.id.f_view_pager);
        scaleImageView = (SubsamplingScaleImageView) view.findViewById(R.id.f_zoom_ssimgview);
        fragLoder = (AVLoadingIndicatorView) view.findViewById(R.id.f_loader);
        hitList = (ArrayList<Hit>) getArguments().getSerializable("hits");
        currPos = getArguments().getInt("position");
        viewPagerAdapter = new ViewPagerAdapter();
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);
        setCurrentImg(currPos);
        return view;
    }

    private void setCurrentImg(int pos){
        viewPager.setCurrentItem(pos, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    public class ViewPagerAdapter extends PagerAdapter {

        private LayoutInflater layoutInflater;
        private AVLoadingIndicatorView loadingIndicatorView;
        private SubsamplingScaleImageView imageView;

        public ViewPagerAdapter() {
        }

        @Override
        public int getCount() {
            return hitList.size();
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View view = layoutInflater.inflate(R.layout.fragment_zoom_img, container, false);
            loadingIndicatorView = view.findViewById(R.id.f_loader);
            loadingIndicatorView.show();
            imageView = view.findViewById(R.id.f_zoom_ssimgview);
            Hit hit = hitList.get(position);
            RequestOptions requestOptions = new RequestOptions();
            requestOptions.placeholder(R.color.cardview_shadow_start_color);
            requestOptions.error(R.color.colorPrimaryDark);
            requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);
            requestOptions.centerCrop();

            Glide.with(getActivity()).
                    asBitmap().load(hit.getLargeImageURL()).
                    apply(requestOptions).
                    into(new CustomTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                            loadingIndicatorView.hide();
                            imageView.setImage(ImageSource.bitmap(resource));
                        }

                        @Override
                        public void onLoadCleared(@Nullable Drawable placeholder) {
                            loadingIndicatorView.hide();
                        }
                    });

            container.addView(view);
            return view;

        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == ((View) object);
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }
    }


}
