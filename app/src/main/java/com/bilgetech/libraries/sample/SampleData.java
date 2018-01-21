package com.bilgetech.libraries.sample;

/**
 * Created by damra on 18/01/2018.
 */

public class SampleData {
    private String title;
    private String text;

    public SampleData() {
    }

    public SampleData(String title, String text) {
        this.title = title;
        this.text = text;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setText(String text) {
        this.text = text;
    }
}
