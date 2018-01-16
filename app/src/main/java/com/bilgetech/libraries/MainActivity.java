package com.bilgetech.libraries;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.bilgetech.widgets.ui.alertdialog.CustomAlertDialogBuilder;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new CustomAlertDialogBuilder(MainActivity.this, "Hello world!");
    }
}