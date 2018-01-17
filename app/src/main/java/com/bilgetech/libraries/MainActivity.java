package com.bilgetech.libraries;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.bilgetech.widgets.ui.alertdialog.CustomAlertDialogBuilder;
import com.bilgetech.widgets.ui.button.ProgressButton;
import com.bilgetech.widgets.ui.toolbar.MainToolbarLayout;

public class MainActivity extends AppCompatActivity {

    private MainToolbarLayout mainToolbarLayout;
    private ProgressButton pbSendWithLoading;
    private ProgressButton pbSendWithError;
    private ProgressButton pbSendWithSuccess;

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