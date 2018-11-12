package com.nearit.ui_bindings.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.nearit.ui_bindings.ImageDownloadListener;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * @author Federico Boschini
 */

public final class LoadImageFromURL extends AsyncTask<String, Void, Bitmap> {

    private final ImageDownloadListener listener;

    public LoadImageFromURL(@NonNull ImageDownloadListener listener) {
        this.listener = listener;
    }

    @Override
    protected Bitmap doInBackground(String... urls) {
        String url = urls[0];
        Bitmap icon = null;
        InputStream in;
        try {
            in = new URL(url).openStream();
            icon = BitmapFactory.decodeStream(in);
        } catch (IOException ignored) { }
        return icon;
    }

    @Override
    protected void onPostExecute(Bitmap icon) {
        if (icon != null) listener.onSuccess(icon);
        else listener.onError();
    }
}
