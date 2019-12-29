package com.titan.flickrapp.ui.gallery;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
import com.titan.flickrapp.R;
import com.titan.flickrapp.models.Picture;
import com.titan.flickrapp.ui.BaseActivity;

import butterknife.BindView;
import timber.log.Timber;

public class PictureActivity extends BaseActivity {


    @BindView(R.id.txt_title)
    TextView txt_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);

        Timber.d("Picture");
        getIncomingIntent();
    }

    private void getIncomingIntent(){
        if(getIntent().hasExtra("picture")){
            Picture picture = getIntent().getParcelableExtra("picture");
            setPictureProperties(picture);
        }
    }

    private void setPictureProperties(Picture picture) {

        if(picture != null){

            txt_title.setText(picture.getTitle());

        }
    }
}
