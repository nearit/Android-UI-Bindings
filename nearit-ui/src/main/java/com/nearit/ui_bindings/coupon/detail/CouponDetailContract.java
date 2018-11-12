package com.nearit.ui_bindings.coupon.detail;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import com.nearit.ui_bindings.base.BasePresenter;
import com.nearit.ui_bindings.base.BaseView;

/**
 * @author Federico Boschini
 */
class CouponDetailContract {

    interface View extends BaseView<Presenter> {
        void keepScreenOn();
        void hideSeparator();
        void setSeparator(int separatorDrawable);
        void showTitle(@NonNull String title);
        void showValue(@NonNull String value);
        void showDescription(@NonNull String description);
        void showIcon(@NonNull Bitmap bitmap);
        void showIcon(int placeholderIcon);
        void hideIcon();
        void showIcon();
        void showSpinner();
        void hideSpinner();
        void setDisabled();
    }

    interface Presenter extends BasePresenter{
        void reloadImage();
    }

}
