package com.nearit.ui_bindings.utils.images;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.Iterator;

/**
 * @author Federico Boschini
 */

public class LoadImageFromURL extends AsyncTask<String, Void, Image> {

    @SuppressWarnings("unused")
    private static final String TAG = "LoadImageFromURL";

    private final HashSet<ImageDownloadListener> listeners = new HashSet<>();

    private final static int MAX_HEIGHT = 2880;
    private final static int MAX_WIDTH = 1440;

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
        if (url != null) {
            try {
                @Nullable Bitmap icon = getScaledBitmapFromUrl(url);
                if (icon != null) image = new Image(icon, url);
            } catch (IOException ignored) {

            }
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

    @Nullable
    private Bitmap getScaledBitmapFromUrl(String imageUrl) throws IOException {
        URL url = new URL(imageUrl);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(url.openConnection().getInputStream(), null, options);
        options.inSampleSize = calculateInSampleSize(options);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeStream(url.openConnection().getInputStream(), null, options);
    }


    private int calculateInSampleSize(BitmapFactory.Options options) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > MAX_HEIGHT || width > MAX_WIDTH) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= MAX_HEIGHT
                    || (halfWidth / inSampleSize) >= MAX_WIDTH) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }
}
