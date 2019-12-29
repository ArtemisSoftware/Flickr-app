package com.titan.flickrapp.ui.gallery.adapters;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.titan.flickrapp.R;
import com.titan.flickrapp.models.Picture;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PictureViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    @BindView(R.id.txt_title)
    TextView txt_title;


    private OnPictureListener onPictureListener;

    public PictureViewHolder(@NonNull View itemView, OnPictureListener onPictureListener) {
        super(itemView);

        ButterKnife.bind(this, itemView);
        this.onPictureListener = onPictureListener;
        itemView.setOnClickListener(this);
    }

    public void onBind(Picture picture){

        // set the image for the future

        txt_title.setText(picture.getTitle());

    }

    @Override
    public void onClick(View v) {
        onPictureListener.onPictureClick(getAdapterPosition());
    }
}
