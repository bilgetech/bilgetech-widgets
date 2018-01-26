package com.bilgetech.libraries.sample;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bilgetech.libraries.R;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

/**
 * Created by damra on 18/01/2018.
 */

interface ItemClickListener {
    void viewClick(View clickedView);
}

@EViewGroup(R.layout.dummy_view)
public class SampleView extends LinearLayout {

    private ItemClickListener itemClickListener;

    @ViewById
    TextView tvTitle, tvText;

    public SampleView(Context context) {
        super(context);
    }

    public SampleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SampleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void bind(SampleData data) {
        tvTitle.setText(data.getTitle());
        tvText.setText(data.getText());
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Click(R.id.llRoot)
    public void viewClick(View clickedView) {
        itemClickListener.viewClick(clickedView);
    }

}

