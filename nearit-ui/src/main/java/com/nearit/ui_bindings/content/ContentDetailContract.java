package com.nearit.ui_bindings.content;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import com.nearit.ui_bindings.base.BasePresenter;
import com.nearit.ui_bindings.base.BaseView;

/**
 * @author Federico Boschini
 */
class ContentDetailContract {

    interface View extends BaseView<Presenter> {
        void showTitle(@NonNull String title);

        void showContent(@NonNull String content);

        void showImageContainer();
        void showImageSpinner();
        void hideImageSpinner();
        void showImage(@NonNull Bitmap bitmap);
        void showImageRetry();
        void hideImage();

        void showCtaButton(@NonNull String label);

        void openLink(@NonNull String url);
        void openLinkInWebView(@NonNull String url);
    }

    interface Presenter extends BasePresenter {
        void handleLinkTap(@NonNull String link);
        void handleCtaTap();
        void reloadImage();
    }

}
