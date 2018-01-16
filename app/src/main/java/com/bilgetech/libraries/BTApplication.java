package com.bilgetech.libraries;

import com.bilgetech.BTInitializer;

/**
 * Created by damra on 16/01/2018.
 */

public class BTApplication extends android.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();
        BTInitializer.init(this);
    }

}