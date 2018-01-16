package com.bilgetech.widgets.ui.toolbar;


import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.bilgetech.widgets.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

@EViewGroup
public abstract class BaseToolbarLayout extends FrameLayout {

    protected static final int NOT_SET = -1;

    protected @DrawableRes
    int leftIcon;
    protected @DrawableRes
    int rightIcon;

    @ViewById

    protected ButtonClickListener buttonClickListener;

    public BaseToolbarLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        resolveBaseAttrs(context, attrs);
    }

    public BaseToolbarLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        resolveBaseAttrs(context, attrs);
    }

    @AfterViews
    void afterBaseViews() {
        if (leftIcon != NOT_SET) {
            leftButton.setImageResource(leftIcon);
            leftButton.setVisibility(View.VISIBLE);
        }

        if (rightIcon != NOT_SET) {
            rightButton.setImageResource(rightIcon);
            rightButton.setVisibility(View.VISIBLE);
        }
    }

    final void resolveBaseAttrs(@NonNull Context context, @Nullable AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.BaseToolbarLayout);

        try {
            leftIcon = typedArray.getResourceId(R.styleable.BaseToolbarLayout_leftIcon, NOT_SET);
            rightIcon = typedArray.getResourceId(R.styleable.BaseToolbarLayout_rightIcon, NOT_SET);
        } finally {
            typedArray.recycle();
        }
    }

    @Click(R.id.leftButton)
    void onLeftButtonClick() {
        if (buttonClickListener != null) {
            buttonClickListener.onLeftButtonClick();
        }
    }

    @Click(R.id.rightButton)
    void onRightButtonClick() {
        if (buttonClickListener != null) {
            buttonClickListener.onRightButtonClick();
        }
    }

    public int getLeftIcon() {
        return leftIcon;
    }

    public void setLeftIcon(int leftIcon) {
        this.leftIcon = leftIcon;
        afterBaseViews();
    }

    public int getRightIcon() {
        return rightIcon;
    }

    public void setRightIcon(int rightIcon) {
        this.rightIcon = rightIcon;
        afterBaseViews();
    }

    public ButtonClickListener getButtonClickListener() {
        return buttonClickListener;
    }

    public void setButtonClickListener(ButtonClickListener buttonClickListener) {
        this.buttonClickListener = buttonClickListener;
    }

    public interface ButtonClickListener {
        void onLeftButtonClick();

        void onRightButtonClick();
    }
}
