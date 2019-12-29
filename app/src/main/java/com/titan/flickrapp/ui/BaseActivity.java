package com.titan.flickrapp.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.titan.flickrapp.R;
import com.titan.flickrapp.util.AppConfig;

import butterknife.ButterKnife;


public abstract class BaseActivity extends AppCompatActivity {

    private ProgressBar progress_bar;

    @Override
    public void setContentView(int layoutResID) {

        ConstraintLayout constraintLayout = (ConstraintLayout) getLayoutInflater().inflate(R.layout.activity_base, null);
        FrameLayout frameLayout = constraintLayout.findViewById(R.id.activity_content);
        progress_bar = constraintLayout.findViewById(R.id.progress_bar);

        getLayoutInflater().inflate(layoutResID, frameLayout, true);
        super.setContentView(constraintLayout);
        ButterKnife.bind(this);

    }

    public void showProgressBar(boolean visible) {
        progress_bar.setVisibility(visible ? View.VISIBLE : View.INVISIBLE);
    }


}
