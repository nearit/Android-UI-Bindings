package com.nearit.ui_bindings.coupon.detail;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import com.nearit.ui_bindings.utils.images.ImageDownloadListener;
import com.nearit.ui_bindings.utils.images.NearItImageDownloader;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import it.near.sdk.reactions.couponplugin.model.Coupon;

/**
 * @author Federico Boschini
 */
public class CouponDetailPresenterImpl implements CouponDetailContract.Presenter {

    private final CouponDetailContract.View view;
    private final Coupon coupon;
    private final CouponDetailExtraParams params;
    private final NearItImageDownloader imageDownloader;

    CouponDetailPresenterImpl(CouponDetailContract.View view, Coupon coupon, CouponDetailExtraParams params, NearItImageDownloader imageDownloader) {
        this.view = view;
        this.coupon = coupon;
        this.params = params;
        this.imageDownloader = imageDownloader;
        init();
    }

    private void init() {
        view.injectPresenter(this);
    }

    @Override
    public void reloadImage() {

    }

    @Override
    public void start() {
        if (!params.isNoWakeLock() && isValid(coupon)) {
            view.keepScreenOn();
        }

        if (isNotValidYet(coupon) || isAlreadyRedeemed(coupon)) {
            view.setDisabled();
        }

        if (params.isNoSeparator()) view.hideSeparator();
        else if (params.getSeparatorDrawable() != 0) {
            view.setSeparator(params.getSeparatorDrawable());
        }

        if (coupon.getTitle() != null) view.showTitle(coupon.getTitle());

        if (coupon.value != null) view.showValue(coupon.value);

        if (coupon.description != null) view.showDescription(coupon.description);

        if (params.getIconDrawable() != 0) view.showIcon(params.getIconDrawable());

        if (coupon.getIconSet() != null) {
            view.hideIcon();
            view.showSpinner();
            try {
                imageDownloader.downloadImage(coupon.getIconSet().getFullSize(), new ImageDownloadListener() {
                    @Override
                    public void onSuccess(@NonNull Bitmap bitmap) {
                        view.showIcon(bitmap);
                        view.hideSpinner();
                    }

                    @Override
                    public void onError() {
                        hideSpinnerAndSetDefault();
                    }
                }, 5000);
            } catch (InterruptedException e) {
                hideSpinnerAndSetDefault();
            } catch (ExecutionException e) {
                hideSpinnerAndSetDefault();
            } catch (TimeoutException e) {
                hideSpinnerAndSetDefault();
            }
        }
    }

    @Override
    public void stop() {

    }

    private void hideSpinnerAndSetDefault() {
        view.hideSpinner();
        view.showIcon();
    }

    private boolean isValid(@NonNull Coupon coupon) {
        return coupon.getRedeemedAtDate() == null &&
                (coupon.getExpiresAtDate() == null || coupon.getExpiresAtDate().getTime() > System.currentTimeMillis()) &&
                (coupon.getRedeemableFromDate() == null || coupon.getRedeemableFromDate().getTime() < System.currentTimeMillis());
    }

    private boolean isNotValidYet(@NonNull Coupon coupon) {
        return coupon.getRedeemableFromDate() != null && coupon.getRedeemableFromDate().getTime() > System.currentTimeMillis();
    }

    private boolean isAlreadyRedeemed(@NonNull Coupon coupon) {
        return coupon.getRedeemedAt() != null;
    }
}
