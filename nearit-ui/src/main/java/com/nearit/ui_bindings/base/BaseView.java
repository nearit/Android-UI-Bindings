package com.nearit.ui_bindings.base;

/**
 * @author Federico Boschini
 */

import android.support.annotation.NonNull;

public interface BaseView<T> {
    void injectPresenter(@NonNull T presenter);
}
