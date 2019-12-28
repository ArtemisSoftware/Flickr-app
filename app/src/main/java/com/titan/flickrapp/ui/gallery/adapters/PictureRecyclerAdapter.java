package com.titan.flickrapp.ui.gallery.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.titan.flickrapp.R;
import com.titan.flickrapp.models.Picture;

import java.util.List;


public class PictureRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int REGISTER_TYPE = 1;

    private List<Picture> results;


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = null;

        switch (viewType){

            default:{
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.picture_list_item, parent, false);
                return new PictureViewHolder(view);
            }
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        int itemViewType = getItemViewType(position);

        if(itemViewType == REGISTER_TYPE) {

            ((PictureViewHolder) holder).onBind(results.get(position));
        }
    }

    @Override
    public int getItemViewType(int position) {
        return REGISTER_TYPE;
    }


    @Override
    public int getItemCount() {

        if(results != null) {
            return results.size();
        }

        return 0;
    }


    public void setResults(List<Picture> results){
        this.results = results;
        notifyDataSetChanged();
    }
}
