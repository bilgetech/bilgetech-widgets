package com.bilgetech.widgets.multilanguageview;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;

import com.bilgetech.widgets.R;

public class MultiLanguageEditText extends AppCompatEditText {
    private String page;
    private String hintKey;
    private String translatedHint;

    private String errorKey;
    private String translatedError;

    public MultiLanguageEditText(Context context) {
        super(context);
    }

    public MultiLanguageEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        resolveAttributes(attrs);
        setHintKey(getHint());
    }

    public MultiLanguageEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        resolveAttributes(attrs);
        setHintKey(getHint().toString());
    }

    public void setHintKey(CharSequence hint) {
        hintKey = hint == null ? null : hint.toString();
        translatedHint = translate(hintKey);
        setHint(translatedHint);
    }

    @Override
    public void setError(@Nullable CharSequence error, Drawable icon) {
        errorKey = error == null ? null : error.toString();
        translatedError = errorKey == null ? null : translate(errorKey);
        super.setError(translatedError, icon);
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getHintKey() {
        return hintKey;
    }

    public String getErrorKey() {
        return errorKey;
    }

    private void resolveAttributes(AttributeSet attrs) {
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.Translatable);
        try {
            page = a.getString(R.styleable.Translatable_page);
        } finally {
            a.recycle();
        }
    }

    private String translate(String key) {
        if (page == null) {
            return MultiLanguageHelper.getInstance().translate(key);
        } else {
            return MultiLanguageHelper.getInstance().translate(page, key);
        }
    }
}