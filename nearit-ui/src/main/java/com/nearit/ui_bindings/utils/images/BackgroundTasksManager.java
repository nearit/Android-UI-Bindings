package com.nearit.ui_bindings.utils.images;

import android.support.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Federico Boschini
 */
public class BackgroundTasksManager {

    private final Map<String, LoadImageFromURL> backgroundTasks = new HashMap<>();

    public boolean isAlreadyRunning(@NonNull String url) {
        return backgroundTasks.containsKey(url);
    }

    public LoadImageFromURL getTask(@NonNull String url) {
        return backgroundTasks.get(url);
    }

    public void removeTask(@NonNull String url) {
        backgroundTasks.remove(url);
    }

    public void addTask(@NonNull String url, @NonNull LoadImageFromURL task) {
        backgroundTasks.put(url, task);
    }

    public LoadImageFromURL createNewTask(@NonNull ImageDownloadListener listener) {
        return new LoadImageFromURL(listener);
    }

}
