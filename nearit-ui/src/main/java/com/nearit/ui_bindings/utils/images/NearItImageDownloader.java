package com.nearit.ui_bindings.utils.images;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import it.near.sdk.logging.NearLog;

/**
 * @author Federico Boschini
 */
public class NearItImageDownloader {

    private static final String TAG = "NearItImgDownloader";

    private static final Object SINGLETON_LOCK = new Object();

    private CacheManager cacheManager;
    private BackgroundTasksManager tasksManager;

    @Nullable
    private volatile static NearItImageDownloader sInstance = null;

    public static NearItImageDownloader getInstance() {
        if (sInstance == null) {
            synchronized (SINGLETON_LOCK) {
                if (sInstance == null) {
                    sInstance = new NearItImageDownloader(new CacheManager(), new BackgroundTasksManager());
                }
            }
        }
        return sInstance;
    }

    NearItImageDownloader(CacheManager cacheManager, BackgroundTasksManager tasksManager) {
        this.cacheManager = cacheManager;
        this.tasksManager = tasksManager;
    }

    public void downloadImage(@NonNull final String url, @NonNull final ImageDownloadListener listener) {
        Bitmap cached = cacheManager.getBitmapFromMemCache(url);
        if (cached == null) {
            if (tasksManager.isAlreadyRunning(url)) {
                NearLog.d(TAG, String.format("A background task for %s is already running", url));
                tasksManager.getTask(url).addListener(listener);
            } else {
                LoadImageFromURL task = tasksManager.createNewTask(new ImageDownloadListener() {
                    @Override
                    public void onSuccess(@NonNull Bitmap bitmap) {
                        cacheManager.addBitmapToMemoryCache(url, bitmap);
                        listener.onSuccess(bitmap);
                        tasksManager.removeTask(url);
                    }

                    @Override
                    public void onError() {
                        listener.onError();
                        tasksManager.removeTask(url);
                    }
                });

                task.execute(url);
                tasksManager.addTask(url, task);
            }
        } else listener.onSuccess(cached);
    }

    public void downloadImage(@NonNull final String url, @NonNull final ImageDownloadListener listener, int timeout) throws InterruptedException, ExecutionException, TimeoutException {
        Bitmap cached = cacheManager.getBitmapFromMemCache(url);
        if (cached == null) {
            if (tasksManager.isAlreadyRunning(url)) {
                NearLog.d(TAG, String.format("A background task for %s is already running", url));
                tasksManager.getTask(url).addListener(listener);
            } else {
                final LoadImageFromURL task = tasksManager.createNewTask(new ImageDownloadListener() {
                    @Override
                    public void onSuccess(@NonNull Bitmap bitmap) {
                        cacheManager.addBitmapToMemoryCache(url, bitmap);
                        listener.onSuccess(bitmap);
                        tasksManager.removeTask(url);
                    }

                    @Override
                    public void onError() {
                        listener.onError();
                        tasksManager.removeTask(url);
                    }
                });

                task.execute(url).get(timeout, TimeUnit.MILLISECONDS);
                tasksManager.addTask(url, task);
            }
        } else listener.onSuccess(cached);
    }
}
