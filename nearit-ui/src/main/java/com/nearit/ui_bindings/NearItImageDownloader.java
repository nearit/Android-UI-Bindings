package com.nearit.ui_bindings;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.util.LruCache;

import com.nearit.ui_bindings.utils.LoadImageFromURL;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author Federico Boschini
 */
public class NearItImageDownloader {

    private static final String TAG = "NearItImgDownloader";

    private static final Object SINGLETON_LOCK = new Object();

    private LruCache<String, Bitmap> mMemoryCache;

    private final Map<String, LoadImageFromURL> backgroundTasks = new HashMap<>();

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
        // TODO: maybe fine-tune values
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        final int cacheSize = maxMemory / 8;
        mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                return bitmap.getByteCount() / 1024;
            }
        };
    }

    public void downloadImage(@NonNull final String url, @NonNull final ImageDownloadListener listener) {
        Bitmap cached = getBitmapFromMemCache(url);
        if (cached == null) {
            if (backgroundTasks.containsKey(url)) {
                Log.e(TAG, String.format("A background task for %s is already running", url));
                backgroundTasks.get(url).addListener(listener);
            } else {
                final LoadImageFromURL task = new LoadImageFromURL(new ImageDownloadListener() {
                    @Override
                    public void onSuccess(@NonNull Bitmap bitmap) {
                        addBitmapToMemoryCache(url, bitmap);
                        listener.onSuccess(bitmap);
                        backgroundTasks.remove(url);
                    }

                    @Override
                    public void onError() {
                        listener.onError();
                        backgroundTasks.remove(url);
                    }
                });

                task.execute(url);
                backgroundTasks.put(url, task);
            }
        } else listener.onSuccess(cached);
    }

    public void downloadImage(@NonNull final String url, @NonNull final ImageDownloadListener listener, int timeout) throws InterruptedException, ExecutionException, TimeoutException {
        Bitmap cached = getBitmapFromMemCache(url);
        if (cached == null) {
            if (backgroundTasks.containsKey(url)) {
                Log.e(TAG, String.format("A background task for %s is already running", url));
                backgroundTasks.get(url).addListener(listener);
            } else {
                final LoadImageFromURL task = new LoadImageFromURL(new ImageDownloadListener() {
                    @Override
                    public void onSuccess(@NonNull Bitmap bitmap) {
                        addBitmapToMemoryCache(url, bitmap);
                        listener.onSuccess(bitmap);
                        backgroundTasks.remove(url);
                    }

                    @Override
                    public void onError() {
                        listener.onError();
                        backgroundTasks.remove(url);
                    }
                });

                task.execute(url).get(timeout, TimeUnit.MILLISECONDS);
                backgroundTasks.put(url, task);
            }
        } else listener.onSuccess(cached);
    }

    private void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if (getBitmapFromMemCache(key) == null) {
            mMemoryCache.put(key, bitmap);
        }
    }

    private Bitmap getBitmapFromMemCache(String key) {
        return mMemoryCache.get(key);
    }
}
