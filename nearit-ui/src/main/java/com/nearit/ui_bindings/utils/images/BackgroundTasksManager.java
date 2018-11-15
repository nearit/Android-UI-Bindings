package com.nearit.ui_bindings.utils.images;

import android.support.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Federico Boschini
 */
class BackgroundTasksManager {

    private final Map<String, LoadImageFromURL> backgroundTasks = new HashMap<>();

    boolean isAlreadyRunning(@NonNull String url) {
        return backgroundTasks.containsKey(url);
    }

    LoadImageFromURL getTask(@NonNull String url) {
        return backgroundTasks.get(url);
    }

    void removeTask(@NonNull String url) {
        backgroundTasks.remove(url);
    }

    void addTask(@NonNull String url, @NonNull LoadImageFromURL task) {
        backgroundTasks.put(url, task);
    }

    LoadImageFromURL createNewTask(@NonNull ImageDownloadListener listener) {
        return new LoadImageFromURL(listener);
    }

}
