package com.bilgetech.widgets.ui.imageview;

import android.content.Context;
import android.util.AttributeSet;

import com.squareup.picasso.Picasso;

import java.io.File;

/**
 * Created by damra on 16/01/2018.
 */

public class SquareImageView extends android.support.v7.widget.AppCompatImageView {

    public SquareImageView(Context context) {
        super(context);
    }

    public SquareImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SquareImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void loadImage(File pictureFile) {
        Picasso.with(getContext())
                .load(pictureFile)
                .fit().centerCrop()
                .into(this);
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
                .centerCrop().fit()
                .placeholder(placeHolder)
                .into(this);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);

        int width = getMeasuredWidth();
        int height = getMeasuredHeight();

        if (width != height) {
            setMeasuredDimension(width, width);
        }
    }
}