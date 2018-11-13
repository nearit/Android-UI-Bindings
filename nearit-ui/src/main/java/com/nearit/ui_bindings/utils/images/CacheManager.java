package com.nearit.ui_bindings.utils.images;

import android.graphics.Bitmap;
import android.util.LruCache;

/**
 * @author Federico Boschini
 */
public class CacheManager {

    private LruCache<String, Bitmap> mMemoryCache;

    public CacheManager() {
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

    public void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if (getBitmapFromMemCache(key) == null) {
            mMemoryCache.put(key, bitmap);
        }
    }

    public Bitmap getBitmapFromMemCache(String key) {
        return mMemoryCache.get(key);
    }

}
