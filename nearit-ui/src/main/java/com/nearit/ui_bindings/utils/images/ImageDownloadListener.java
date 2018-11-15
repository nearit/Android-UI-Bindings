package com.nearit.ui_bindings.utils.images;

import android.support.annotation.NonNull;

/**
 * @author Federico Boschini
 */
public interface ImageDownloadListener {
    void onSuccess(@NonNull Image image);
    void onError();
}
