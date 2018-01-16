package com.bilgetech.widgets.helper;

import android.content.Context;
import android.graphics.Bitmap;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.UiThread;

import java.io.File;

@EBean
public class ImageUploadHelper {
    private Context context;
    private int requiredWidth;
    private int requiredHeight;
    private Listener listener;
    private File file;

    public ImageUploadHelper(Context context) {
        this.context = context;
    }

    public ImageUploadHelper config(int requiredWidth, int requiredHeight) {
        this.requiredWidth = requiredWidth;
        this.requiredHeight = requiredHeight;
        return this;
    }

    public void prepareForUpload(File file, Listener listener) {
        this.file = file;
        this.listener = listener;
        prepareInBackground();
    }

    @Background
    void prepareInBackground() {
        try {
            Bitmap bitmap = ImageHelper.rotateAndResizeBitmapIfNeeded(file, requiredWidth, requiredHeight);
            File preparedFile = ImageHelper.saveBitmapToCacheDir(context, bitmap);
            notifyComplete(preparedFile);
        } catch (Exception e) {
            notifyError(e.getMessage());
        }
    }

    @UiThread
    void notifyComplete(File preparedFile) {
        if (listener != null) {
            listener.onComplete(preparedFile);
        }
    }

    @UiThread
    void notifyError(String message) {
        if (listener != null) {
            listener.onError(file, message);
        }
    }

    public interface Listener {
        void onComplete(File file);

        void onError(File file, String message);
    }
}

