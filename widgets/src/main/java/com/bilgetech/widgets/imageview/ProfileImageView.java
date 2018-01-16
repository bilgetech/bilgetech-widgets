package com.bilgetech.widgets.imageview;

import android.content.Context;
import android.util.AttributeSet;

import com.bilgetech.widgets.R;

public class ProfileImageView extends CircularImageView {

    public ProfileImageView(Context context) {
        super(context);
    }

    public ProfileImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ProfileImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void loadImage(String picturePath) {
        super.loadImage(picturePath, R.drawable.ic_sample_user);
    }
}