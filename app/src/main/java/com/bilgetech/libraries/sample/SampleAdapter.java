package com.bilgetech.libraries.sample;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.bilgetech.base.BaseRecyclerViewAdapter;
import com.bilgetech.base.ViewWrapper;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

/**
 * Created by damra on 18/01/2018.
 */

@EBean
public class SampleAdapter extends BaseRecyclerViewAdapter<SampleData, SampleView> implements ItemClickListener {

    public static final String TAG = SampleAdapter.class.getSimpleName();

    @RootContext
    Context context;

    @Override
    protected SampleView onCreateItemView(ViewGroup parent, int viewType) {
        // return DummyViev_.build(context);
        return null;
    }

    @Override
    public int getItemCount() {
        return this.items.size();
    }

    @Override
    public void onBindViewHolder(ViewWrapper<SampleView> viewHolder, int position) {
        SampleData data = items.get(position);
        SampleView view = viewHolder.getView();
        view.setItemClickListener(this);
        view.bind(data);
    }

    @Override
    public void viewClick(View clickedView) {
        Log.d(TAG, "viewClick: ");
    }
}