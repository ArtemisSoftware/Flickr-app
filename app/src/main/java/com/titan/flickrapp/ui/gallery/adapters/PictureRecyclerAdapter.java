package com.titan.flickrapp.ui.gallery.adapters;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.ListPreloader;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.util.ViewPreloadSizeProvider;
import com.titan.flickrapp.adapters.LoadingViewHolder;
import com.titan.flickrapp.R;
import com.titan.flickrapp.models.Picture;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class PictureRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements ListPreloader.PreloadModelProvider<String>{

    public static final int REGISTER_TYPE = 1;
    public static final int LOADING_TYPE = 2;
    public static final int EXHAUSTED_TYPE = 3;

    private List<Picture> results;

    private OnPictureListener onPictureListener;

    private RequestManager requestManager;

    private ViewPreloadSizeProvider<String> preloadSizeProvider;


    public PictureRecyclerAdapter(OnPictureListener onPictureListener, RequestManager requestManager, ViewPreloadSizeProvider<String> preloadSizeProvider) {
        this.onPictureListener = onPictureListener;
        this.requestManager = requestManager;
        this.results = new ArrayList<>();

        this.preloadSizeProvider = preloadSizeProvider;
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
                return new PictureViewHolder(view, this.onPictureListener, this.requestManager, preloadSizeProvider);
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

    public void setQueryExhausted(){

        hideLoading();

        Picture picture = new Picture(EXHAUSTED_TYPE);
        this.results.add(picture);
        notifyDataSetChanged();
    }



    @NonNull
    @Override
    public List<String> getPreloadItems(int position) {
        String url = this.results.get(position).getUrl();
        if(TextUtils.isEmpty(url)){
            return Collections.emptyList();
        }

        return Collections.singletonList(url);
    }

    @Nullable
    @Override
    public RequestBuilder<?> getPreloadRequestBuilder(@NonNull String item) {
        return requestManager.load(item);
    }
}
