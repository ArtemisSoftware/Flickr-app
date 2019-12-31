package com.titan.flickrapp.ui.gallery.adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.titan.flickrapp.R;
import com.titan.flickrapp.models.Picture;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PictureViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    @BindView(R.id.txt_title)
    TextView txt_title;

    @BindView(R.id.picture_image)
    ImageView picture_image;


    private OnPictureListener onPictureListener;
    RequestManager requestManager;

    public PictureViewHolder(@NonNull View itemView, OnPictureListener onPictureListener, RequestManager requestManager) {
        super(itemView);

        ButterKnife.bind(this, itemView);
        this.onPictureListener = onPictureListener;
        this.requestManager = requestManager;
        itemView.setOnClickListener(this);
    }

    public void onBind(Picture picture){

        requestManager.load(picture.getUrl()).into(picture_image);
        txt_title.setText(picture.getTitle());
    }

    @Override
    public void onClick(View v) {
        onPictureListener.onPictureClick(getAdapterPosition());
    }
}
