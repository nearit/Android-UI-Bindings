package com.nearit.ui_bindings.coupon.detail;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.nearit.htmltextview.NearItMovementMethod;
import com.nearit.ui_bindings.utils.qrcode.QRcodeGenerator;
import com.nearit.ui_bindings.utils.images.Image;
import com.nearit.ui_bindings.utils.images.ImageDownloadListener;
import com.nearit.ui_bindings.utils.images.NearItImageDownloader;
import com.nearit.ui_bindings.utils.qrcode.QRcodeGeneratorProvider;

import it.near.sdk.reactions.couponplugin.model.Coupon;

/**
 * @author Federico Boschini
 */
public class CouponDetailPresenterImpl implements CouponDetailContract.Presenter {

    private final CouponDetailContract.View view;
    private final Coupon coupon;
    private final CouponDetailExtraParams params;
    private final NearItImageDownloader imageDownloader;
    private final QRcodeGeneratorProvider qRcodeGeneratorProvider;

    CouponDetailPresenterImpl(CouponDetailContract.View view, Coupon coupon, CouponDetailExtraParams params, NearItImageDownloader imageDownloader, QRcodeGeneratorProvider qRcodeGeneratorProvider) {
        this.view = view;
        this.coupon = coupon;
        this.params = params;
        this.imageDownloader = imageDownloader;
        this.qRcodeGeneratorProvider = qRcodeGeneratorProvider;
        init();
    }

    private void init() {
        view.injectPresenter(this);
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

        QRcodeGenerator generator = qRcodeGeneratorProvider.getGenerator();
        generator.setListener(new QRcodeGenerator.GeneratorListener() {
            @Override
            public void onComplete(Bitmap qrCode) {
                view.showQrCode(qrCode);
            }

            @Override
            public void onError() {
                view.showQrCodeError();
            }
        });
        generator.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, coupon.getSerial());

        if (coupon.getTitle() != null) view.showTitle(coupon.getTitle());

        if (coupon.value != null) view.showValue(coupon.value);

        if (coupon.description != null) view.showDescription(coupon.description);

        if (params.getIconDrawable() != 0) view.showIcon(params.getIconDrawable());

        if (coupon.getIconSet() != null && coupon.getIconSet().getFullSize() != null) {
            view.hideIcon();
            view.showSpinner();

            imageDownloader.downloadImage(coupon.getIconSet().getFullSize(), new ImageDownloadListener() {
                @Override
                public void onSuccess(@NonNull Image image) {
                    view.showIcon(image.getBitmap());
                    view.hideSpinner();
                }

                @Override
                public void onError() {
                    hideSpinnerAndSetDefault();
                }
            });
        }
    }

    @Override
    public void stop() {

    }

    @Override
    public void handleLinkTap(@NonNull String link, @NonNull NearItMovementMethod.LinkType type) {
        switch (type) {
            case WEB_URL:
                if (params.isOpenLinksInWebView()) {
                    view.openLinkInWebView(link);
                } else {
                    view.openLink(link);
                }
                break;
            case PHONE:
            case EMAIL_ADDRESS:
            case NONE:
                view.openLink(link);
                break;
        }
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
