package com.titan.flickrapp.ui.gallery;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.titan.flickrapp.App;
import com.titan.flickrapp.R;
import com.titan.flickrapp.models.Picture;
import com.titan.flickrapp.ui.BaseActivity;
import com.titan.flickrapp.ui.gallery.adapters.OnPictureListener;
import com.titan.flickrapp.ui.gallery.adapters.PictureRecyclerAdapter;
import com.titan.flickrapp.util.ApiResponse;
import com.titan.flickrapp.util.ViewModelFactory;
import com.titan.flickrapp.viewmodels.GalleryViewModel;
import com.titan.flickrapp.viewmodels.LoginViewModel;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import timber.log.Timber;

public class GalleryActivity extends BaseActivity implements OnPictureListener {

    @Inject
    ViewModelFactory viewModelFactory;

    private GalleryViewModel galleryViewModel;


    @BindView(R.id.picture_list)
    RecyclerView recyclerView;

    private PictureRecyclerAdapter pictureRecyclerAdapter;

    private String nsid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        Timber.d("Gallery");

        ((App) getApplication()).getAppComponent().doInjection(this);

        galleryViewModel = ViewModelProviders.of(this, viewModelFactory).get(GalleryViewModel.class);

        initRecyclerView();

        getIncomingIntent();
        galleryViewModel.searchGallery(nsid);
    }


    private void initRecyclerView(){

        pictureRecyclerAdapter = new PictureRecyclerAdapter(this);
        recyclerView.setAdapter(pictureRecyclerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }


    private void getIncomingIntent(){
        if(getIntent().hasExtra("nsid")){
            nsid = getIntent().getStringExtra("nsid");
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

                        showProgressBar(true);
                        break;

                    case SUCCESS:

                        pictureRecyclerAdapter.setResults((List<Picture>) apiResponse.data);
                        showProgressBar(false);
                        break;

                    case ERROR:

                        Toast.makeText(getApplicationContext(),"Error: " + apiResponse.message,Toast.LENGTH_SHORT).show();
                        showProgressBar(false);
                        break;
                }
            }
        });
    }


    @Override
    public void onPictureClick(int position) {
        Intent intent = new Intent(this, PictureActivity.class);
        intent.putExtra("picture", pictureRecyclerAdapter.getSelectedPicture(position));
        startActivity(intent);
    }
}
