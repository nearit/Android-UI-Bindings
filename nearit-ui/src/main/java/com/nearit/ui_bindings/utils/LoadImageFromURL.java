package com.nearit.ui_bindings.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.nearit.ui_bindings.ImageDownloadListener;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashSet;
import java.util.Iterator;

/**
 * @author Federico Boschini
 */

public final class LoadImageFromURL extends AsyncTask<String, Void, Bitmap> {

    private final HashSet<ImageDownloadListener> listeners = new HashSet<>();

    public LoadImageFromURL(@NonNull ImageDownloadListener listener) {
        listeners.add(listener);
    }

    public void addListener(@NonNull ImageDownloadListener listener) {
        listeners.add(listener);
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
        Iterator<ImageDownloadListener> iterator = listeners.iterator();
        while (iterator.hasNext()) {
            ImageDownloadListener listener = iterator.next();
            if (icon != null) {
                listener.onSuccess(icon);
            } else {
                listener.onError();
            }
            iterator.remove();
        }
    }
}
