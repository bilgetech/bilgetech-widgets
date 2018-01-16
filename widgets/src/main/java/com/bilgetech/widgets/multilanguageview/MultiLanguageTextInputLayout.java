package com.bilgetech.widgets.multilanguageview;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.util.AttributeSet;

import com.bilgetech.widgets.R;

/**
 * Created by damra on 16/01/2018.
 */

public class MultiLanguageTextInputLayout extends TextInputLayout {
    private String page;
    private String errorKey;
    private String translatedError;

    private String hintKey;
    private String translatedHint;

    public MultiLanguageTextInputLayout(Context context) {
        super(context);
    }

    public MultiLanguageTextInputLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        resolveAttributes(attrs);
        setHint(getHint());
    }

    public MultiLanguageTextInputLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        resolveAttributes(attrs);
        setHint(getHint());
    }

    private void resolveAttributes(AttributeSet attrs) {
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.Translatable);
        try {
            page = a.getString(R.styleable.Translatable_page);
        } finally {
            a.recycle();
        }
    }

    @Override
    public void setHint(@Nullable CharSequence hint) {
        hintKey = hint == null ? null : hint.toString();
        translatedHint = hintKey == null ? null : translate(hintKey);
        super.setHint(translatedHint);
    }

    @Override
    public void setError(@Nullable CharSequence error) {
        errorKey = error == null ? null : error.toString();
        translatedError = errorKey == null ? null : translate(errorKey);
        super.setError(translatedError);
    }

    private String translate(String key) {
        if (page == null) {
            return MultiLanguageHelper.getInstance().translate(key);
        } else {
            return MultiLanguageHelper.getInstance().translate(page, key);
        }
    }
}
