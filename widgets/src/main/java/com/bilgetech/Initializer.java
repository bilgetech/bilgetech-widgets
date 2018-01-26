package com.bilgetech;

import android.content.Context;

import com.bilgetech.widgets.helper.ThreadSafeSingleton;
import com.bilgetech.widgets.multilanguageview.MultiLanguageHelper;

import io.paperdb.Paper;

/**
 * Created by damra on 16/01/2018.
 */

public final class Initializer extends ThreadSafeSingleton<Initializer> {

    private Initializer() {
    }

    private static Initializer instance;

    public synchronized static void init(Context context) {

        if (instance == null) {
            instance = getInstance(Initializer.class);

            Paper.init(context);
            MultiLanguageHelper.load();

            // FirebaseApp.initializeApp(context);
            // Fabric.with(this, new Crashlytics());
        }
    }

}
