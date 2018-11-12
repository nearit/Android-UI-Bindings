package com.nearit.ui_bindings;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;

/**
 * @author Federico Boschini
 */
public interface ImageDownloadListener {
    void onSuccess(@NonNull Bitmap bitmap);
    void onError();
}
