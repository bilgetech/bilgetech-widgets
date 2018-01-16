package com.bilgetech.widgets.ui.alertdialog;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AlertDialog;

public class CustomAlertDialogBuilder implements CustomAlertDialogView.DialogListener {

    private AlertDialog mAlertDialog;
    private Activity mActivty;

    public CustomAlertDialogBuilder(Context context, final String message) {
        populateView(context, message);
    }

    public CustomAlertDialogBuilder(Context context, final String message, Activity activity) {
        populateView(context, message);
        mActivty = activity;
    }

    private void populateView(Context context, final String message) {

        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setCancelable(false);

        CustomAlertDialogView alertDialogView = new CustomAlertDialogView(context) {
            @Override
            String getDialogMessage() {
                return message;
            }
        };

        alertDialogView.setDialogListener(this);
        alert.setView(alertDialogView);

        mAlertDialog = alert.create();
        mAlertDialog.setCanceledOnTouchOutside(false);
        mAlertDialog.show();
    }

    @Override
    public void performDoneClick() {
        mAlertDialog.dismiss();

        if (mActivty != null) {
            mActivty.finish();
        }
    }

}