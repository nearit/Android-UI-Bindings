package com.nearit.ui_bindings.utils.images;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;

/**
 * @author Federico Boschini
 */
public interface ImageDownloadListener {
    void onSuccess(@NonNull Bitmap bitmap);
    void onError();
}
