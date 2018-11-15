package com.nearit.ui_bindings.utils.images;

import android.graphics.Bitmap;

/**
 * @author Federico Boschini
 */
public class Image {

    private Bitmap bitmap;
    private String url;

    Image(Bitmap bitmap, String url) {
        this.bitmap = bitmap;
        this.url = url;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public String getUrl() {
        return url;
    }
}
