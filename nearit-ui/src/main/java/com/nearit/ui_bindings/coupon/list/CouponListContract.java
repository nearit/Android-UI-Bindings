package com.nearit.ui_bindings.coupon.list;

import com.nearit.ui_bindings.base.BasePresenter;
import com.nearit.ui_bindings.base.BaseView;

import java.util.List;

import it.near.sdk.reactions.couponplugin.model.Coupon;

/**
 * @author Federico Boschini
 */
class CouponListContract {
    interface View extends BaseView<Presenter> {
        void showCouponList(List<Coupon> couponList);
        void showEmptyLayout();
        void hideEmptyLayout();
        void showRefreshError(String error);
        void openDetail(Coupon coupon);
    }

    interface Presenter extends BasePresenter {
        void requestRefresh();
        void couponClicked(Coupon coupon);
    }
}
