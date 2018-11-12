package com.nearit.ui_bindings;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.nearit.ui_bindings.utils.LoadImageFromURL;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author Federico Boschini
 */
public class NearItImageDownloader {

    private static final Object SINGLETON_LOCK = new Object();

    @Nullable
    private volatile static NearItImageDownloader sInstance = null;

    public static NearItImageDownloader getInstance() {
        if (sInstance == null) {
            synchronized (SINGLETON_LOCK) {
                if (sInstance == null) {
                    sInstance = new NearItImageDownloader();
                }
            }
        }
        return sInstance;
    }

    private NearItImageDownloader() {

    }

    public void downloadImage(@NonNull String url, @NonNull ImageDownloadListener listener) {
        new LoadImageFromURL(listener).execute(url);
    }

    public void downloadImage(@NonNull String url, @NonNull ImageDownloadListener listener, int timeout) throws InterruptedException, ExecutionException, TimeoutException {
        new LoadImageFromURL(listener).execute(url).get(timeout, TimeUnit.MILLISECONDS);
    }
}
