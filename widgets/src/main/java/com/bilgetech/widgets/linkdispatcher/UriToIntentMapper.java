package com.bilgetech.widgets.linkdispatcher;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * Created by damra on 16/01/2018.
 */


public class UriToIntentMapper {
    private final String APP_SCHEME = "bilgetech";
    private final String APP_HOST = "app";
    private final String WEB_HOST = "bilgetech.com.tr";
    private Context mContext;
    private IntentHelper mIntents;

    public static final String TAG = UriToIntentMapper.class.getSimpleName();

    public UriToIntentMapper(Context context, IntentHelper intentHelper) {
        mContext = context;
        mIntents = intentHelper;

        /*
        APP_SCHEME =  App_.getInstance().getResources().getString(R.string.app_scheme);
        APP_HOST = App_.getInstance().getResources().getString(R.string.app_host);
        WEB_HOST = App_.getInstance().getResources().getString(R.string.web_host);
        */
    }

    public void dispatchIntent(Intent intent) {
        final Uri uri = intent.getData();

        if (uri == null) throw new IllegalArgumentException("Uri cannot be null");

        final String scheme = uri.getScheme().toLowerCase();
        final String host = uri.getHost().toLowerCase();

        if (APP_SCHEME.equals(scheme)) {
            mapAppLink(uri);
        } else if (("http".equals(scheme) || "https".equals(scheme)) && WEB_HOST.equals(host)) {
            mapWebLink(uri);
        }
    }

    private void mapAppLink(Uri uri) {
        final String host = uri.getHost().toLowerCase();

        if (host.equals(APP_HOST)) {
            mIntents.newSplashActivityIntent(mContext);
        }
    }

    private void mapWebLink(Uri uri) {
        final String path = uri.getPath();

        switch (path) {
            case "/account/resetpasswordsuccess":
                mIntents.newSplashActivityIntent(mContext);
                break;

        }

        mIntents.newSplashActivityIntent(mContext);
    }
}