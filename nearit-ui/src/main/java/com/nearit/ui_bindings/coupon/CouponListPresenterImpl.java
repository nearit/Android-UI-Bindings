package com.nearit.ui_bindings.coupon;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import it.near.sdk.NearItManager;
import it.near.sdk.reactions.couponplugin.CouponListener;
import it.near.sdk.reactions.couponplugin.model.Coupon;

import static com.nearit.ui_bindings.coupon.CouponUtils.getExpired;
import static com.nearit.ui_bindings.coupon.CouponUtils.getInactive;
import static com.nearit.ui_bindings.coupon.CouponUtils.getRedeemed;
import static com.nearit.ui_bindings.coupon.CouponUtils.getValid;

/**
 * @author Federico Boschini
 */
public class CouponListPresenterImpl implements CouponListContract.Presenter {

    private final NearItManager nearItManager;
    private final CouponListContract.View view;
    private final CouponListExtraParams params;

    private boolean defaultList, valid, expired, inactive, redeemed;

    CouponListPresenterImpl(NearItManager nearItManager, CouponListContract.View view, CouponListExtraParams params) {
        this.nearItManager = nearItManager;
        this.view = view;
        this.params = params;
        init();
    }

    private void init() {
        view.injectPresenter(this);

        if (params != null) {
            defaultList = params.isDefaultList();
            valid = params.isValid();
            expired = params.isExpired();
            inactive = params.isInactive();
            redeemed = params.isRedeemed();
        }
    }

    @Override
    public void start() {
        loadCouponList();
    }

    @Override
    public void stop() {

    }

    @Override
    public void requestRefresh() {
        loadCouponList();
    }

    @Override
    public void couponClicked(Coupon coupon) {
        view.openDetail(coupon);
    }

    private void loadCouponList() {
        nearItManager.getCoupons(new CouponListener() {
            @Override
            public void onCouponsDownloaded(List<Coupon> coupons) {
                List<Coupon> filtered = new ArrayList<>();
                if (defaultList) {
                    filtered.addAll(getValid(coupons));
                    filtered.addAll(getInactive(coupons));
                } else {
                    if (valid) {
                        filtered.addAll(getValid(coupons));
                    }

                    if (inactive) {
                        filtered.addAll(getInactive(coupons));
                    }

                    if (expired) {
                        filtered.addAll(getExpired(coupons));
                    }

                    if (redeemed) {
                        filtered.addAll(getRedeemed(coupons));
                    }
                }

                if (filtered.isEmpty()) {
                    view.showEmptyLayout();
                } else {
                    view.showCouponList(filtered);
                    view.hideEmptyLayout();
                }
            }

            @Override
            public void onCouponDownloadError(String error) {
                view.showCouponList(Collections.<Coupon>emptyList());
                view.showEmptyLayout();
                view.showRefreshError(error);
            }
        });
    }
}
