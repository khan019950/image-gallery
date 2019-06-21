package com.example.pixagallery.view;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.example.pixagallery.R;
import com.example.pixagallery.contract.MainActivityContract;
import com.example.pixagallery.model.Hit;
import com.example.pixagallery.presenter.MainActPresenterImp;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.List;

public class RecAdapter extends RecyclerView.Adapter<RecAdapter.MyViewHolder> {


    private MainActPresenterImp presenterImp;

    public RecAdapter(MainActPresenterImp presenterImp) {
        this.presenterImp = presenterImp;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rec_row_cell, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        presenterImp.onBindViewAtPosition(holder,position);
    }

    @Override
    public int getItemCount() {
        return presenterImp.getHitsCount();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements MainActivityContract.RecyclerRowView {

        private AVLoadingIndicatorView avLoadingIndicatorView;
        private ImageView imageView;
        private CardView cardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.cardView = itemView.findViewById(R.id.row_card);
            this.avLoadingIndicatorView = itemView.findViewById(R.id.row_loader);
            this.imageView = itemView.findViewById(R.id.row_img);
        }

        @Override
        public void setThumbnailView(String url) {
            RequestOptions requestOptions = new RequestOptions();
            requestOptions.placeholder(R.color.cardview_shadow_start_color);
            requestOptions.error(R.color.colorPrimaryDark);
            requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);
            requestOptions.centerCrop();

            Glide.with(itemView.getContext()).load(url).apply(requestOptions).listener(new RequestListener<Drawable>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                    hideLoader();
                    return false;
                }

                @Override
                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                    hideLoader();
                    return false;
                }
            }).transition(DrawableTransitionOptions.withCrossFade()).into(this.imageView);
        }

        @Override
        public void showLoader() {
            this.avLoadingIndicatorView.show();
        }

        @Override
        public void hideLoader() {
            this.avLoadingIndicatorView.hide();
        }

        @Override
        public void setItemClickListener(final List<Hit> hits, final int currPosition) {
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    presenterImp.intentActivity(hits, currPosition);
                }
            });
        }
    }
}
