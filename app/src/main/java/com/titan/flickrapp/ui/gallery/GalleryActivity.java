package com.titan.flickrapp.ui.gallery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.integration.recyclerview.RecyclerViewPreloader;
import com.bumptech.glide.util.ViewPreloadSizeProvider;
import com.google.android.material.textfield.TextInputLayout;
import com.titan.flickrapp.App;
import com.titan.flickrapp.R;
import com.titan.flickrapp.models.Picture;
import com.titan.flickrapp.ui.BaseActivity;
import com.titan.flickrapp.ui.gallery.adapters.OnPictureListener;
import com.titan.flickrapp.ui.gallery.adapters.PictureRecyclerAdapter;
import com.titan.flickrapp.util.ApiConstants;
import com.titan.flickrapp.util.ApiResponse;
import com.titan.flickrapp.util.AppConstants;
import com.titan.flickrapp.util.ViewModelFactory;
import com.titan.flickrapp.viewmodels.GalleryViewModel;
import com.titan.flickrapp.viewmodels.LoginViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import timber.log.Timber;

public class GalleryActivity extends BaseActivity implements OnPictureListener {

    @Inject
    ViewModelFactory viewModelFactory;

    @Inject
    RequestManager requestManager;

    private GalleryViewModel galleryViewModel;


    @BindView(R.id.picture_list)
    RecyclerView recyclerView;

    private PictureRecyclerAdapter pictureRecyclerAdapter;

    private String nsid;

    private final String GALLERY = "gallery";

    private Parcelable listState;
    private final String GALLERY_STATE = "galleryState";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        Timber.d("Gallery");

        ((App) getApplication()).getAppComponent().doInjection(this);

        galleryViewModel = ViewModelProviders.of(this, viewModelFactory).get(GalleryViewModel.class);

        initRecyclerView();

        getIncomingIntent();


        if(savedInstanceState == null) {
            galleryViewModel.searchGallery(nsid);
        }
        else{
            pictureRecyclerAdapter.setResults(savedInstanceState.getParcelableArrayList(GALLERY));
        }
    }


    private void initRecyclerView(){

        ViewPreloadSizeProvider<String> viewPreloader = new ViewPreloadSizeProvider<>();

        pictureRecyclerAdapter = new PictureRecyclerAdapter(this, requestManager, viewPreloader);
        recyclerView.setAdapter(pictureRecyclerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if(!recyclerView.canScrollVertically(1)){

                    galleryViewModel.searchNextGallery(nsid);
                }
            }
        });
    }


    private void getIncomingIntent(){
        if(getIntent().hasExtra(AppConstants.NSID)){
            nsid = getIntent().getStringExtra(AppConstants.NSID);
            subscribeObservers();
        }
    }


    private void subscribeObservers(){

        galleryViewModel.observeGallery().observe(this, new Observer<ApiResponse>() {
            @Override
            public void onChanged(ApiResponse apiResponse) {

                Timber.d("onChanged: " + apiResponse.toString());

                switch (apiResponse.status){


                    case LOADING:

                        if(galleryViewModel.getPageNumber() > 1){
                            pictureRecyclerAdapter.displayLoading();
                        }
                        else {
                            showProgressBar(true);
                        }
                        break;

                    case SUCCESS:

                        pictureRecyclerAdapter.hideLoading();
                        pictureRecyclerAdapter.setResults((List<Picture>) apiResponse.data);
                        showProgressBar(false);
                        break;

                    case ERROR:

                        if(apiResponse.message.equals(GalleryViewModel.QUERY_EXHAUSTED)){
                            pictureRecyclerAdapter.setQueryExhausted();
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "Error: " + apiResponse.message, Toast.LENGTH_SHORT).show();
                        }
                        showProgressBar(false);
                        break;
                }
            }
        });
    }


    @Override
    public void onPictureClick(int position) {
        Intent intent = new Intent(this, PictureActivity.class);
        intent.putExtra(AppConstants.PICTURE, pictureRecyclerAdapter.getSelectedPicture(position).getId());
        startActivity(intent);
    }



    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        listState = recyclerView.getLayoutManager().onSaveInstanceState();
        outState.putParcelable(GALLERY_STATE, listState);
        outState.putParcelableArrayList(GALLERY, (ArrayList<Picture>) pictureRecyclerAdapter.getResults());
    }

    @Override
    protected void onRestoreInstanceState(Bundle state) {
        super.onRestoreInstanceState(state);
        // Retrieve list state and list/item positions
        if(state != null)
            listState = state.getParcelable(GALLERY_STATE);
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (listState != null) {
            recyclerView.getLayoutManager().onRestoreInstanceState(listState);
        }
    }
}
