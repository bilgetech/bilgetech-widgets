package com.bilgetech.widgets.linkdispatcher;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.bilgetech.widgets.multilanguageview.MultiLanguageHelper;

/**
 * Manifest.xml customization sample
 * https://gist.github.com/Damra/b58741c7d86b702ce1183fe41bf95751
 *
 * USAGE:
 */

public class LinkDispatcherActivity extends AppCompatActivity {

    private final UriToIntentMapper mMapper = new UriToIntentMapper(this, new IntentHelper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            mMapper.dispatchIntent(getIntent());
        } catch (IllegalArgumentException iae) {
            String errString = MultiLanguageHelper.getInstance().translate("Unexpected error has occured. ");
            Toast.makeText(this, errString, Toast.LENGTH_SHORT).show();
            iae.printStackTrace();
        } finally {
            // Always finish the activity so that it doesn't stay in our history
            finish();
        }
    }
}