package com.titan.flickrapp.ui.gallery.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.titan.flickrapp.LoadingViewHolder;
import com.titan.flickrapp.R;
import com.titan.flickrapp.models.Picture;

import java.util.ArrayList;
import java.util.List;


public class PictureRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int REGISTER_TYPE = 1;
    public static final int LOADING_TYPE = 2;

    private List<Picture> results;

    private OnPictureListener onPictureListener;

    private RequestManager requestManager;


    public PictureRecyclerAdapter(OnPictureListener onPictureListener, RequestManager requestManager) {
        this.onPictureListener = onPictureListener;
        this.requestManager = requestManager;
        this.results = new ArrayList<>();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = null;

        switch (viewType){

            case LOADING_TYPE:{
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.loading_list_item, parent, false);
                return new LoadingViewHolder(view);
            }

            default:{
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.picture_list_item, parent, false);
                return new PictureViewHolder(view, this.onPictureListener, this.requestManager);
            }
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        int itemViewType = getItemViewType(position);

        if(itemViewType == REGISTER_TYPE) {

            ((PictureViewHolder) holder).onBind(this.results.get(position));
        }
    }

    @Override
    public int getItemViewType(int position) {

        if(this.results.get(position).getType() == LOADING_TYPE){
            return LOADING_TYPE;
        }
        else return REGISTER_TYPE;
    }


    @Override
    public int getItemCount() {

        if(results != null) {
            return results.size();
        }

        return 0;
    }


    public Picture getSelectedPicture(int position){
        if(this.results != null){
            if(this.results.size() > 0){
                return this.results.get(position);
            }
        }
        return null;
    }


    public void setResults(List<Picture> results){
        this.results.addAll(results);
        notifyDataSetChanged();
    }


    public void displayLoading(){

        if(this.results == null){
            this.results = new ArrayList<>();
        }
        if(!isLoading()){

            Picture picture = new Picture(LOADING_TYPE);
            this.results.add(picture);
            notifyDataSetChanged();
        }
    }


    public void hideLoading(){
        if(isLoading()){

            if (this.results.get(this.results.size() - 1).getType() == LOADING_TYPE) {
                this.results.remove(this.results.size() - 1);
            }

            notifyDataSetChanged();
        }
    }

    private boolean isLoading(){

        if(this.results != null){
            if (this.results.size() > 0) {
                if (this.results.get(this.results.size() - 1).getType() == LOADING_TYPE) {
                    return true;
                }
            }
        }
        return false;
    }
}
