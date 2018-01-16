package com.bilgetech.widgets.ui.imageview;

import android.content.Context;
import android.util.AttributeSet;

import com.squareup.picasso.Picasso;

public class CircularImageView extends de.hdodenhof.circleimageview.CircleImageView {

    public CircularImageView(Context context) {
        super(context);
    }

    public CircularImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CircularImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void loadImage(String picturePath) {
        Picasso.with(getContext())
                .load(picturePath)
                .centerCrop().fit()
                .into(this);
    }

    public void loadImage(String picturePath, int placeHolder) {
        Picasso.with(getContext())
                .load(picturePath)
                .placeholder(placeHolder)
                .centerCrop().fit()
                .into(this);
    }
}