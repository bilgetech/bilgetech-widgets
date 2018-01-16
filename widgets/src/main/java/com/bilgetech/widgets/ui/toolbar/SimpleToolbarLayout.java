package com.bilgetech.widgets.ui.toolbar;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.bilgetech.widgets.R;
import com.bilgetech.widgets.multilanguageview.MultiLanguageTextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

@EViewGroup(R.layout.simple_toolbar_layout)
public class SimpleToolbarLayout extends BaseToolbarLayout implements BaseToolbarLayout.ButtonClickListener {

    private String title;

    @ViewById
    MultiLanguageTextView titleText;

    public SimpleToolbarLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        resolveAttrs(context, attrs);
    }

    public SimpleToolbarLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        resolveAttrs(context, attrs);
    }

    private void resolveAttrs(@NonNull Context context, @Nullable AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SimpleToolbarLayout);
        try {
            title = a.getString(R.styleable.SimpleToolbarLayout_title);
        } finally {
            a.recycle();
        }
    }

    @AfterViews
    void afterViews() {
        setBackgroundResource(R.color.colorToolbarBg);
        setButtonClickListener(this);

        if (title != null) {
            titleText.setText(title);
        }
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        titleText.setText(title);
    }

    public void setTitle(int title) {
        titleText.setText(title);
    }

    @Override
    public void onLeftButtonClick() {
        ((Activity) getContext()).onBackPressed();
    }

    @Override
    public void onRightButtonClick() {

    }
}