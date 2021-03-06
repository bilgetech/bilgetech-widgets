package com.bilgetech.widgets.multilanguageview;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;

import com.bilgetech.widgets.R;

/**
 * Created by damra on 16/01/2018.
 */

public class MultiLanguageButton extends AppCompatButton {
    private String page;
    private String key;
    private String translatedText;

    public MultiLanguageButton(Context context) {
        super(context);
    }

    public MultiLanguageButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        resolveAttributes(attrs);
        setText(getText());
    }

    public MultiLanguageButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        resolveAttributes(attrs);
        setText(getText());
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        key = text == null ? null : text.toString();
        translatedText = translate();
        super.setText(translatedText, type);
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getKey() {
        return key;
    }

    private void resolveAttributes(AttributeSet attrs) {
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.Translatable);
        try {
            page = a.getString(R.styleable.Translatable_page);
        } finally {
            a.recycle();
        }
    }

    private String translate() {
        if (page == null) {
            return MultiLanguageHelper.getInstance().translate(key);
        } else {
            return MultiLanguageHelper.getInstance().translate(page, key);
        }
    }
}