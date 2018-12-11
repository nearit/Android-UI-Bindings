package com.nearit.ui_bindings.utils.images;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;

/**
 * @author Federico Boschini
 */
public class Image {

    @NonNull
    private Bitmap bitmap;
    @NonNull
    private String url;

    Image(@NonNull Bitmap bitmap, @NonNull String url) {
        this.bitmap = bitmap;
        this.url = url;
    }

    @NonNull
    public Bitmap getBitmap() {
        return bitmap;
    }

    @NonNull
    public String getUrl() {
        return url;
    }
}
