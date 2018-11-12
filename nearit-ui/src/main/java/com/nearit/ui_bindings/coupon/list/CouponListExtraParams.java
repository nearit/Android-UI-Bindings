package com.nearit.ui_bindings.coupon.list;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.nearit.ui_bindings.ExtraConstants;

/**
 * @author Federico Boschini
 */

public class CouponListExtraParams implements Parcelable {

    private static final boolean DEFAULT_ENABLE_TAP_OUTSIDE_TO_CLOSE = false;
    private final int iconDrawable;
    private final int separatorDrawable;
    private final int noCouponLayout;
    private final boolean jaggedBorders;
    private final boolean enableNetErrorDialog;
    private final boolean noSeparator;
    private final boolean noIcon;
    private final boolean enableTapOutsideToClose;
    private final boolean defaultList;
    private final boolean valid;
    private final boolean expired;
    private final boolean inactive;
    private final boolean redeemed;
    @Nullable
    private final String activityTitle;

    public CouponListExtraParams(
            int iconDrawable,
            int separatorDrawable,
            int noCouponLayout,
            boolean enableNetErrorDialog,
            boolean jaggedBorders,
            boolean noSeparator,
            boolean noIcon,
            boolean defaultList,
            boolean valid,
            boolean expired,
            boolean inactive,
            boolean redeemed,
            boolean enableTapOutsideToClose,
            @Nullable String activityTitle) {
        this.iconDrawable = iconDrawable;
        this.separatorDrawable = separatorDrawable;
        this.noCouponLayout = noCouponLayout;
        this.enableNetErrorDialog = enableNetErrorDialog;
        this.jaggedBorders = jaggedBorders;
        this.noSeparator = noSeparator;
        this.noIcon = noIcon;
        this.enableTapOutsideToClose = enableTapOutsideToClose;
        this.defaultList = defaultList;
        this.valid = valid;
        this.expired = expired;
        this.inactive = inactive;
        this.redeemed = redeemed;
        this.activityTitle = activityTitle;
    }

    public CouponListExtraParams(
            int iconDrawable,
            int separatorDrawable,
            int noCouponLayout,
            boolean enableNetErrorDialog,
            boolean jaggedBorders,
            boolean noSeparator,
            boolean noIcon,
            boolean defaultList,
            boolean valid,
            boolean expired,
            boolean inactive,
            boolean redeemed) {
        this(
                iconDrawable,
                separatorDrawable,
                noCouponLayout,
                enableNetErrorDialog,
                jaggedBorders,
                noSeparator,
                noIcon,
                defaultList,
                valid,
                expired,
                inactive,
                redeemed,
                DEFAULT_ENABLE_TAP_OUTSIDE_TO_CLOSE,
                null);
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
            boolean jaggedBorders = in.readInt() != 0;
            boolean noSeparator = in.readInt() != 0;
            boolean noIcon = in.readInt() != 0;
            boolean enableTapToClose = in.readInt() != 0;
            boolean defaultList = in.readInt() != 0;
            boolean valid = in.readInt() != 0;
            boolean expired = in.readInt() != 0;
            boolean inactive = in.readInt() != 0;
            boolean redeemed = in.readInt() != 0;
            String activityTitle = in.readString();
            return new CouponListExtraParams(
                    iconDrawable,
                    separatorDrawable,
                    noCouponLayout,
                    enableNetErrorDialog,
                    jaggedBorders,
                    noSeparator,
                    noIcon,
                    defaultList,
                    valid,
                    expired,
                    inactive,
                    redeemed,
                    enableTapToClose,
                    activityTitle
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
        dest.writeInt(jaggedBorders ? 1 : 0);
        dest.writeInt(noSeparator ? 1 : 0);
        dest.writeInt(noIcon ? 1 : 0);
        dest.writeInt(enableTapOutsideToClose ? 1 : 0);
        dest.writeInt(defaultList ? 1 : 0);
        dest.writeInt(valid ? 1 : 0);
        dest.writeInt(expired ? 1 : 0);
        dest.writeInt(inactive ? 1 : 0);
        dest.writeInt(redeemed ? 1 : 0);
        dest.writeString(activityTitle);
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

    boolean isJaggedBorders() {
        return jaggedBorders;
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

    boolean isDefaultList() {
        return defaultList;
    }

    boolean isValid() {
        return valid;
    }

    boolean isExpired() {
        return expired;
    }

    boolean isInactive() {
        return inactive;
    }

    boolean isRedeemed() {
        return redeemed;
    }

    @Nullable
    public String getActivityTitle() {
        return activityTitle;
    }
}
