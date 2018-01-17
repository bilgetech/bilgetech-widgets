package com.bilgetech.widgets.ui.button;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.Transformation;

import com.bilgetech.widgets.R;
import com.bilgetech.widgets.multilanguageview.MultiLanguageButton;

import java.util.Arrays;

public class ProgressButton extends MultiLanguageButton {
    public static final String TAG = ProgressButton.class.getSimpleName();

    private static final int MAX_LEVEL = 10000;
    private static final int ANIM_DURATION = 500;
    private Animation animation;
    private boolean isClickableWhenLoading;

    private boolean hasAnimation;
    private Drawable loadingDrawable;
    private boolean mShouldStartAnimationDrawable;

    private Drawable[] oldCompoundDrawables;
    private Transformation transformation;

    private CharSequence normalText;
    private CharSequence successText;
    private CharSequence errorText;

    private State state = State.NORMAL;

    public ProgressButton(Context context) {
        super(context);
        init(context);
    }

    public ProgressButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ProgressButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    private void init(Context context) {
        loadingDrawable = null;
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ProgressButton);

        if (a.hasValue(R.styleable.ProgressButton_loadingDrawable)) {
            loadingDrawable = a.getDrawable(R.styleable.ProgressButton_loadingDrawable);
        } else {
            loadingDrawable = ContextCompat.getDrawable(context, R.drawable.default_spinner);
        }

        if (a.hasValue(R.styleable.ProgressButton_successText)) {
            setSuccessText(a.getString(R.styleable.ProgressButton_successText));
        } else {
            setSuccessText(getText());
        }

        if (a.hasValue(R.styleable.ProgressButton_errorText)) {
            setErrorText(a.getString(R.styleable.ProgressButton_errorText));
        } else {
            setErrorText(getText());
        }

        if (a.hasValue(R.styleable.ProgressButton_autoDisableClickable)) {
            isClickableWhenLoading = a.getBoolean(R.styleable.ProgressButton_autoDisableClickable, false);
        }

        a.recycle();

        normalText = getText();

        Log.d(TAG, "init: normalText:"+ normalText);

        if (loadingDrawable != null) {
            loadingDrawable.setBounds(0, 0, loadingDrawable.getIntrinsicWidth(), loadingDrawable.getIntrinsicHeight());
            if (!(loadingDrawable instanceof Animatable)) {
                animation = new AlphaAnimation(0f, 1f);
                animation.setRepeatMode(Animation.RESTART);
                animation.setRepeatCount(Animation.INFINITE);
                animation.setDuration(ANIM_DURATION);
                animation.setInterpolator(new LinearInterpolator());
                animation.setStartTime(Animation.START_ON_FIRST_FRAME);
                hasAnimation = true;
                transformation = new Transformation();
            }
        }

    }

    public boolean isClickableWhenLoading() {
        return isClickableWhenLoading;
    }

    public void setClickableWhenLoading(boolean clickableWhenLoading) {
        isClickableWhenLoading = clickableWhenLoading;
    }

    public void hideLoader() {
        setState(State.NORMAL);
        saveCompoundDrawables();
    }

    public void showLoader() {
        setState(State.LOADING);
        restoreCompoundDrawables();
    }

    public boolean isLoading() {
        return state == State.LOADING;
    }

    private void saveCompoundDrawables() {
        oldCompoundDrawables = Arrays.copyOf(getCompoundDrawables(), 4);
        setCompoundDrawables(null, null, null, null);
    }

    private void restoreCompoundDrawables() {
        if (oldCompoundDrawables != null) {
            setCompoundDrawablesWithIntrinsicBounds(oldCompoundDrawables[0], oldCompoundDrawables[1], oldCompoundDrawables[2], oldCompoundDrawables[3]);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (loadingDrawable != null && isLoading()) {
            canvas.save();
            int left = getWidth() / 2 - loadingDrawable.getMinimumWidth() / 2;
            int top = getHeight() / 2 - loadingDrawable.getMinimumHeight() / 2;
            canvas.translate(left, top);

            loadingDrawable.draw(canvas);
            canvas.restore();

            long time = getDrawingTime();
            if (hasAnimation) {
                animation.getTransformation(time, transformation);
                float scale = transformation.getAlpha();
                loadingDrawable.setLevel((int) (scale * MAX_LEVEL));
                ViewCompat.postInvalidateOnAnimation(this);
            }
            if (mShouldStartAnimationDrawable && loadingDrawable instanceof Animatable) {
                ((Animatable) loadingDrawable).start();
                mShouldStartAnimationDrawable = false;
            }
        }
    }

    public void setSuccessText(CharSequence successText) {
        this.successText = successText;
    }

    public void setErrorText(CharSequence errorText) {
        this.errorText = errorText;
    }

    public void showNormal(){
        setState(State.NORMAL);
    }

    public void showSuccess() {
        setState(State.SUCCESS);
    }

    public void showError() {
        setState(State.ERROR);
    }

    private void setState(State state) {
        this.state = state;
        notifyUI();
    }

    private void notifyUI() {
        setText(getDisplayText());
        setClickable(isClickableWhenLoading || state != State.LOADING);
    }

    private CharSequence getDisplayText() {
        switch (state) {
            default:
            case NORMAL:
                return normalText;
            case LOADING:
                return "";
            case SUCCESS:
                return successText;
            case ERROR:
                return errorText;
        }
    }

    public enum State {
        NORMAL, LOADING, ERROR, SUCCESS
    }

}