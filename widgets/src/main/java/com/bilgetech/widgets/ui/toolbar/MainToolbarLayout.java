package com.bilgetech.widgets.ui.toolbar;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.bilgetech.widgets.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

@EViewGroup(R.layout.main_toolbar_layout)
public class MainToolbarLayout extends BaseToolbarLayout {

    @ViewById
    TextView badgeView;

    public MainToolbarLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MainToolbarLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @AfterViews
    void afterViews() {
        setBackgroundResource(R.color.colorToolbarBg);
        setNotificationCount(0);
    }

    public void setNotificationCount(int count) {
        badgeView.setText(count > 100 ? "99+" : count + "");
        animateBadge(count);
    }

    private void animateBadge(int count) {
        if (badgeView.getVisibility() == VISIBLE && count == 0) {
            badgeView.animate().alpha(0).scaleX(0).scaleY(0).setDuration(300).withEndAction(new Runnable() {
                @Override
                public void run() {
                    badgeView.setVisibility(GONE);
                }
            }).start();
        } else if (badgeView.getVisibility() != VISIBLE && count > 0) {
            badgeView.setVisibility(VISIBLE);
            badgeView.animate().alpha(1).scaleX(1).scaleY(1).setDuration(300).start();
        }
    }
}
