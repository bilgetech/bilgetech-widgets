package com.bilgetech.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by damra on 18/01/2018.
 */

public abstract class BaseRecyclerViewAdapter<T, V extends View> extends RecyclerView.Adapter<ViewWrapper<V>> {

    protected List<T> items = new ArrayList<T>();

    @Override
    public int getItemCount() {
        return items.size();
    }

    public List<T> getItems() {
        return items;
    }

    @Override
    public final ViewWrapper<V> onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewWrapper<V>(onCreateItemView(parent, viewType));
    }

    protected abstract V onCreateItemView(ViewGroup parent, int viewType);

    public void setItems(List<T> items) {
        this.items = items;
        this.notifyDataSetChanged();
    }

    public void clearItems() {
        items.clear();
    }

    public void insertItem(T item) {
        this.items.add(item);
        int index = this.items.size() - 1;
        this.notifyItemInserted(index);
    }

    public void insertItem(int index, T item) {
        this.items.add(index, item);
        this.notifyItemInserted(index);
    }

    public synchronized boolean insertItemIfNotExist(T item, int index) {
        boolean isItemNotExists = this.items.indexOf(item) == -1;
        if (this.items.indexOf(item) == -1) {
            insertItem(index, item);
        }
        return isItemNotExists;
    }

    public boolean removeItem(int position) {
        if (items.size() >= position + 1) {
            items.remove(position);
            return true;
        }
        return false;
    }

    public void removeItem(T item) {
        int index = this.items.indexOf(item);

        if (removeItem(index)) {
            this.notifyItemRemoved(index);
            this.notifyItemRangeChanged(index, getItemCount());
        }
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

// additional methods to manipulate the items
}