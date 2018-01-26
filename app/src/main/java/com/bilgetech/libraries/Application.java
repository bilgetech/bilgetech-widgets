package com.bilgetech.libraries;

import com.bilgetech.Initializer;

/**
 * Created by damra on 16/01/2018.
 */

public class Application extends android.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Initializer.init(this);
    }

}