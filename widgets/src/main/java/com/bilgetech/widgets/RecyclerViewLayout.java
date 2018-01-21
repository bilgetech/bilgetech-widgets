package com.bilgetech.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.AttrRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

@EViewGroup(resName = "recycler_view_layout")
public class RecyclerViewLayout extends LinearLayout {

    @ViewById
    FrameLayout frameLayout;

    @ViewById
    RecyclerView recyclerView;

    @ViewById
    SwipeRefreshLayout swipeRefreshLayout;

    @ViewById
    ProgressBar progressBar;

    private LayoutInflater layoutInflater;
    private View errorView;
    private View emptyView;

    private boolean isSwipeable;
    private int errorViewResId;
    private int emptyViewResId;

    private State currentState;

    public RecyclerViewLayout(@NonNull Context context) {
        super(context);
        init(context, null);
    }

    public RecyclerViewLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public RecyclerViewLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @AfterViews
    protected void afterViews() {
        swipeRefreshLayout.setRefreshing(false);
        inflateErrorView();
        inflateEmptyView();

        setCurrentState(State.LOADING);



    }

    private void init(Context context, AttributeSet attrs) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RecyclerViewLayout);

        isSwipeable = typedArray.getBoolean(R.styleable.RecyclerViewLayout_is_swipeable, false);
        emptyViewResId = typedArray.getResourceId(R.styleable.RecyclerViewLayout_empty_view, R.layout.default_empty_view);
        errorViewResId = typedArray.getResourceId(R.styleable.RecyclerViewLayout_error_view, R.layout.default_error_view);

        typedArray.recycle();
    }

    private void inflateErrorView() {
        if (errorView != null) {
            frameLayout.removeView(errorView);
        }
        errorView = layoutInflater.inflate(errorViewResId, frameLayout, false);
        frameLayout.addView(errorView);
    }

    private void inflateEmptyView() {
        if (emptyView != null) {
            frameLayout.removeView(emptyView);
        }
        emptyView = layoutInflater.inflate(emptyViewResId, frameLayout, false);
        frameLayout.addView(emptyView);
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    public void setErrorViewResId(@LayoutRes int errorViewResId) {
        this.errorViewResId = errorViewResId;
        inflateErrorView();
    }

    public void setEmptyViewResId(@LayoutRes int emptyViewResId) {
        this.emptyViewResId = emptyViewResId;
        inflateEmptyView();
    }

    public View getErrorView() {
        return errorView;
    }

    public View getEmptyView() {
        return emptyView;
    }

    public boolean isSwipeable() {
        return isSwipeable;
    }

    public void setSwipeable(boolean isSwipeable) {
        this.isSwipeable = isSwipeable;
        swipeRefreshLayout.setEnabled(isSwipeable);
    }

    public RecyclerView.Adapter getDataAdapter() {
        return recyclerView.getAdapter();
    }

    public void setDataAdapter(RecyclerView.Adapter adapter) {
        swipeRefreshLayout.setRefreshing(false);
        recyclerView.setAdapter(adapter);
        notifyAdapterChanged();

        if (adapter != null) {
            adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
                @Override
                public void onChanged() {
                    notifyAdapterChanged();
                }
            });
        }
    }

    private void notifyAdapterChanged() {
        RecyclerView.Adapter adapter = recyclerView.getAdapter();
        boolean isAdapterValid = adapter != null && adapter.getItemCount() > 0;
        if (isAdapterValid) {
            setCurrentState(State.DATA);
        } else {
            setCurrentState(State.EMPTY);
        }
    }

    public void setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener onRefreshListener) {
        if (isSwipeable && onRefreshListener == null) {
            throw new IllegalStateException("If you want to swipe to refresh, onRefreshListener should not be empty. Or remove row app:is_swipeable");
        }

        swipeRefreshLayout.setEnabled(isSwipeable);
        swipeRefreshLayout.setOnRefreshListener(isSwipeable ? onRefreshListener : null);
    }


    public State getCurrentState() {
        return currentState;
    }

    public void setCurrentState(State currentState) {
        this.currentState = currentState;
        notifyStateChanged();
    }

    public void showError() {
        setCurrentState(State.ERROR);
    }

    private void notifyStateChanged() {
        errorView.setVisibility(currentState == State.ERROR ? VISIBLE : GONE);
        emptyView.setVisibility(currentState == State.EMPTY ? VISIBLE : GONE);
        recyclerView.setVisibility(currentState == State.DATA || currentState == State.SWIPE ? VISIBLE : GONE);
        progressBar.setVisibility(currentState == State.LOADING ? VISIBLE : GONE);
        swipeRefreshLayout.setRefreshing(currentState == State.SWIPE);
    }

    public enum State {
        LOADING, ERROR, EMPTY, DATA, SWIPE
    }
}