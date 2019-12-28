package com.titan.flickrapp.ui.gallery.adapters;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.titan.flickrapp.R;
import com.titan.flickrapp.models.Picture;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PictureViewHolder extends RecyclerView.ViewHolder{

    @BindView(R.id.txt_title)
    TextView txt_title;

    @BindView(R.id.txt_picture_id)
    TextView txt_picture_id;


    public PictureViewHolder(@NonNull View itemView) {
        super(itemView);

        ButterKnife.bind(this, itemView);
    }

    public void onBind(Picture picture){

        // set the image

        txt_title.setText(picture.getTitle());
        txt_picture_id.setText(picture.getId());

    }

}
