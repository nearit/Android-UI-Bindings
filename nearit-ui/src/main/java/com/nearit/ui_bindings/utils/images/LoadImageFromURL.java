package com.nearit.ui_bindings.utils.images;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashSet;
import java.util.Iterator;

/**
 * @author Federico Boschini
 */

public class LoadImageFromURL extends AsyncTask<String, Void, Image> {

    private final HashSet<ImageDownloadListener> listeners = new HashSet<>();

    LoadImageFromURL(@NonNull ImageDownloadListener listener) {
        listeners.add(listener);
    }

    void addListener(@NonNull ImageDownloadListener listener) {
        listeners.add(listener);
    }

    @Override
    protected Image doInBackground(@NonNull String... urls) {
        String url = urls[0];
        Image image = null;
        InputStream in;
        if (url != null) {
            try {
                in = new URL(url).openStream();
                @Nullable Bitmap icon = BitmapFactory.decodeStream(in);
                if (icon != null) image = new Image(icon, url);
            } catch (IOException ignored) { }
        }
        return image;
    }

    @Override
    protected void onPostExecute(@Nullable Image image) {
        Iterator<ImageDownloadListener> iterator = listeners.iterator();
        while (iterator.hasNext()) {
            @Nullable
            ImageDownloadListener listener = iterator.next();
            if (listener != null) {
                if (image != null) {
                    listener.onSuccess(image);
                } else {
                    listener.onError();
                }
            }
            iterator.remove();
        }
    }
}
