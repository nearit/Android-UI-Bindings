package com.nearit.ui_bindings.coupon;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import com.nearit.ui_bindings.ExtraConstants;

/**
 * @author Federico Boschini
 */

class CouponListExtraParams implements Parcelable {

    private final int iconDrawable;
    private final int separatorDrawable;
    private final int noCouponLayout;
    private final boolean enableNetErrorDialog;
    private final boolean noSeparator;
    private final boolean noIcon;
    private final boolean enableTapOutsideToClose;
    private final boolean validOnly;
    private final boolean expiredOnly;
    private final boolean inactiveOnly;
    private final boolean redeemedOnly;
    private final boolean includeRedeemed;

    CouponListExtraParams(int iconDrawable, int separatorDrawable, int noCouponLayout, boolean enableNetErrorDialog, boolean noSeparator, boolean noIcon, boolean enableTapOutsideToClose,
                          boolean validOnly, boolean expiredOnly, boolean inactiveOnly, boolean redeemedOnly, boolean includeRedeemed) {
        this.iconDrawable = iconDrawable;
        this.separatorDrawable = separatorDrawable;
        this.noCouponLayout = noCouponLayout;
        this.enableNetErrorDialog = enableNetErrorDialog;
        this.noSeparator = noSeparator;
        this.noIcon = noIcon;
        this.enableTapOutsideToClose = enableTapOutsideToClose;
        this.validOnly = validOnly;
        this.expiredOnly = expiredOnly;
        this.inactiveOnly = inactiveOnly;
        this.redeemedOnly = redeemedOnly;
        this.includeRedeemed = includeRedeemed;
    }

    CouponListExtraParams(int iconDrawable, int separatorDrawable, int noCouponLayout, boolean enableNetErrorDialog, boolean noSeparator, boolean noIcon,
                          boolean validOnly, boolean expiredOnly, boolean inactiveOnly, boolean redeemedOnly, boolean includeRedeemed) {
        this.iconDrawable = iconDrawable;
        this.separatorDrawable = separatorDrawable;
        this.noCouponLayout = noCouponLayout;
        this.enableNetErrorDialog = enableNetErrorDialog;
        this.noSeparator = noSeparator;
        this.noIcon = noIcon;
        this.enableTapOutsideToClose = false;
        this.validOnly = validOnly;
        this.expiredOnly = expiredOnly;
        this.inactiveOnly = inactiveOnly;
        this.redeemedOnly = redeemedOnly;
        this.includeRedeemed = includeRedeemed;
    }

    /**
     * Extract CouponDetailExtraParams from an Intent.
     */
    static CouponListExtraParams fromIntent(Intent intent) {
        return intent.getParcelableExtra(ExtraConstants.EXTRA_FLOW_PARAMS);
    }

    /**
     * Extract CouponDetailExtraParams from a Bundle.
     */
    public static CouponListExtraParams fromBundle(Bundle bundle) {
        return bundle.getParcelable(ExtraConstants.EXTRA_FLOW_PARAMS);
    }

    /**
     * Create a bundle containing this CouponDetailExtraParams object as {@link
     * ExtraConstants#EXTRA_FLOW_PARAMS}.
     */
    public Bundle toBundle() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(ExtraConstants.EXTRA_FLOW_PARAMS, this);
        return bundle;
    }

    public static final Creator<CouponListExtraParams> CREATOR = new Creator<CouponListExtraParams>() {
        @Override
        public CouponListExtraParams createFromParcel(Parcel in) {
            int iconDrawable = in.readInt();
            int separatorDrawable = in.readInt();
            int noCouponLayout = in.readInt();
            boolean enableNetErrorDialog = in.readInt() != 0;
            boolean noSeparator = in.readInt() != 0;
            boolean noIcon = in.readInt() != 0;
            boolean enableTapToClose = in.readInt() != 0;
            boolean validOnly = in.readInt() != 0;
            boolean expiredOnly = in.readInt() != 0;
            boolean inactiveOnly = in.readInt() != 0;
            boolean redeemedOnly = in.readInt() != 0;
            boolean includeRedeemed = in.readInt() != 0;
            return new CouponListExtraParams(
                    iconDrawable,
                    separatorDrawable,
                    noCouponLayout,
                    enableNetErrorDialog,
                    noSeparator,
                    noIcon,
                    enableTapToClose,
                    validOnly,
                    expiredOnly,
                    inactiveOnly,
                    redeemedOnly,
                    includeRedeemed
            );
        }

        @Override
        public CouponListExtraParams[] newArray(int size) {
            return new CouponListExtraParams[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(iconDrawable);
        dest.writeInt(separatorDrawable);
        dest.writeInt(noCouponLayout);
        dest.writeInt(enableNetErrorDialog ? 1 : 0);
        dest.writeInt(noSeparator ? 1 : 0);
        dest.writeInt(noIcon ? 1 : 0);
        dest.writeInt(enableTapOutsideToClose ? 1 : 0);
        dest.writeInt(validOnly ? 1 : 0);
        dest.writeInt(expiredOnly ? 1 : 0);
        dest.writeInt(inactiveOnly ? 1 : 0);
        dest.writeInt(redeemedOnly ? 1 : 0);
        dest.writeInt(includeRedeemed ? 1 : 0);
    }

    int getIconDrawable() {
        return iconDrawable;
    }

    int getSeparatorDrawable() {
        return separatorDrawable;
    }

    int getNoCouponLayout() {
        return noCouponLayout;
    }

    boolean isEnableNetErrorDialog() {
        return enableNetErrorDialog;
    }

    boolean isEnableTapOutsideToClose() {
        return enableTapOutsideToClose;
    }

    boolean isNoSeparator() {
        return noSeparator;
    }

    boolean isNoIcon() {
        return noIcon;
    }

    boolean isValidOnly() {
        return validOnly;
    }

    boolean isExpiredOnly() {
        return expiredOnly;
    }

    boolean isInactiveOnly() {
        return inactiveOnly;
    }

    boolean isRedeemedOnly() {
        return redeemedOnly;
    }

    boolean isIncludeRedeemed() {
        return includeRedeemed;
    }

}
