package com.bilgetech.libraries;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.bilgetech.libraries.sample.Consts;
import com.bilgetech.libraries.sample.SampleAdapter;
import com.bilgetech.libraries.sample.SampleData;
import com.bilgetech.widgets.RecyclerViewLayout;
import com.bilgetech.widgets.ui.alertdialog.CustomAlertDialogBuilder;
import com.bilgetech.widgets.ui.button.ProgressButton;
import com.bilgetech.widgets.ui.toolbar.MainToolbarLayout;
import com.google.gson.Gson;

import org.androidannotations.annotations.Bean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private MainToolbarLayout mainToolbarLayout;
    private ProgressButton pbSendWithLoading;
    private ProgressButton pbSendWithError;
    private ProgressButton pbSendWithSuccess;
    private RecyclerViewLayout recyclerViewLayout;

    @Bean
    protected SampleAdapter sampleAdapter;

    final List<SampleData> items = Arrays.asList(new Gson().fromJson(Consts.SAMPLE_USER_LAT_LON_JSON, SampleData[].class));

    private static int notificationCount = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        // 2. 5 seconds after try load data with failure.

        Handler handler = new Handler();

        sampleAdapter.setItems(new ArrayList<SampleData>());

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

            }
        }, 5000);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                sampleAdapter.setItems(items);
                recyclerViewLayout.setCurrentState(RecyclerViewLayout.State.DATA);
            }
        }, 2000);

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