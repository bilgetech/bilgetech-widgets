package com.bilgetech.libraries;

import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.bilgetech.libraries.sample.Consts;
import com.bilgetech.libraries.sample.SampleAdapter;
import com.bilgetech.libraries.sample.SampleData;
import com.bilgetech.widgets.RecyclerViewLayout;
import com.bilgetech.widgets.ui.alertdialog.CustomAlertDialogBuilder;
import com.bilgetech.widgets.ui.button.ProgressButton;
import com.bilgetech.widgets.ui.toolbar.MainToolbarLayout;
import com.google.gson.Gson;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private MainToolbarLayout mainToolbarLayout;
    private ProgressButton pbSendWithLoading;
    private ProgressButton pbSendWithError;
    private ProgressButton pbSendWithSuccess;
    private RecyclerViewLayout recyclerViewLayout;

    @Bean
    protected SampleAdapter sampleAdapter;

    List<SampleData> items = new ArrayList<>();

    {
        items = Arrays.asList(new Gson().fromJson(Consts.SAMPLE_USER_LAT_LON_JSON, SampleData[].class));

        Log.d(TAG, "instance initializer: "+ items.size());

    }

    private static int notificationCount = 0;

    @AfterViews
    protected void afterViews() {

        new CustomAlertDialogBuilder(MainActivity.this, "Hello world!");

        mainToolbarLayout = findViewById(R.id.mainToolbarLayout);
        notificationCounter();

        pbSendWithLoading = findViewById(R.id.pbSendWithLoading);
        pbSendWithError = findViewById(R.id.pbSendWithError);
        pbSendWithSuccess = findViewById(R.id.pbSendWithSuccess);

        prepareSendClick(pbSendWithLoading, ProgressButton.State.LOADING);
        prepareSendClick(pbSendWithError, ProgressButton.State.ERROR);
        prepareSendClick(pbSendWithSuccess, ProgressButton.State.SUCCESS);

        // https://gist.github.com/Damra/8ee436c1ad2ac58211eecd49ebbaca6b
        recyclerViewLayout = findViewById(R.id.recyclerViewLayout);
        recyclerViewLayout.setSwipeable(true);
        recyclerViewLayout.setDataAdapter(sampleAdapter);
        recyclerViewLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadRecyclerViewItems();
            }
        });

        prepareRecyclerViewItems();

    }

    private void loadRecyclerViewItems() {
        sampleAdapter.setItems(items);
        recyclerViewLayout.setCurrentState(RecyclerViewLayout.State.DATA);
    }

    private void prepareRecyclerViewItems() {

        // 1. Empty view display
        // 2. Error view display
        // 3. Adapter Items view display

        Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                recyclerViewLayout.setCurrentState(RecyclerViewLayout.State.ERROR);
            }
        }, 5000);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                sampleAdapter.setItems(items);
                recyclerViewLayout.setCurrentState(RecyclerViewLayout.State.DATA);
            }
        }, 10000);

    }

    private void prepareSendClick(final ProgressButton pbSend, final ProgressButton.State state) {
        pbSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!pbSend.isLoading()) { // spam control ^^
                    pbSend.showLoader();

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (pbSend.isLoading()) {
                                if (state == ProgressButton.State.LOADING) pbSend.hideLoader();
                                if (state == ProgressButton.State.ERROR) pbSend.showError();
                                if (state == ProgressButton.State.SUCCESS) pbSend.showSuccess();
                            }
                        }
                    }, 1500);
                }
            }
        });
    }

    private void notificationCounter() {

        final Handler notificationHandler = new Handler();
        notificationHandler.postDelayed(new Runnable() {
            @Override
            public void run() {

                if (notificationCount == Integer.MAX_VALUE) { // overkill ^
                    notificationCount = 0;
                }

                mainToolbarLayout.setNotificationCount(notificationCount);
                notificationCount++;

                notificationHandler.postDelayed(this, 500);
            }
        }, 1000);
    }
}