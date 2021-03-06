package com.nearit.ui_bindings.utils.images;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.LruCache;

/**
 * @author Federico Boschini
 */
class CacheManager {

    private LruCache<String, Bitmap> mMemoryCache;

    CacheManager() {
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

    void addBitmapToMemoryCache(@NonNull String key, @NonNull Bitmap bitmap) {
        mMemoryCache.put(key, bitmap);
    }

    @Nullable
    Bitmap getBitmapFromMemCache(@NonNull String key) {
        return mMemoryCache.get(key);
    }

}
