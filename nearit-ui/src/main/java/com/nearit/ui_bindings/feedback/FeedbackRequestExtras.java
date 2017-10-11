package com.nearit.ui_bindings.feedback;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import com.nearit.ui_bindings.ExtraConstants;

/**
 * Created by Federico Boschini on 29/08/17.
 */

class FeedbackRequestExtras implements Parcelable {
    private final boolean hideTextResponse;
    private final boolean enableTapOutsideToClose;
    private final boolean noSuccessIcon;
    private boolean autoClose;
    private boolean showCloseButton;
    private final int iconResId;

    FeedbackRequestExtras(boolean hideTextResponse, int iconResId, boolean noSuccessIcon, boolean enableTapOutsideToClose, boolean autoClose, boolean showCloseButton) {
        this.hideTextResponse = hideTextResponse;
        this.iconResId = iconResId;
        this.noSuccessIcon = noSuccessIcon;
        this.enableTapOutsideToClose = enableTapOutsideToClose;
        this.autoClose = autoClose;
        this.showCloseButton = showCloseButton;
    }

    FeedbackRequestExtras(boolean hideTextResponse, int iconResId, boolean noSuccessIcon) {
        this.hideTextResponse = hideTextResponse;
        this.iconResId = iconResId;
        this.noSuccessIcon = noSuccessIcon;
        this.enableTapOutsideToClose = false;
        this.autoClose = false;
        this.showCloseButton = false;
    }


    /**
     * Extract FeedbackRequestExtras from an Intent.
     */
    static FeedbackRequestExtras fromIntent(Intent intent) {
        return intent.getParcelableExtra(ExtraConstants.EXTRA_FLOW_PARAMS);
    }

    /**
     * Extract FeedbackRequestExtras from a Bundle.
     */
    public static FeedbackRequestExtras fromBundle(Bundle bundle) {
        return bundle.getParcelable(ExtraConstants.EXTRA_FLOW_PARAMS);
    }

    /**
     * Create a bundle containing this FeedbackRequestExtras object as {@link
     * ExtraConstants#EXTRA_FLOW_PARAMS}.
     */
    public Bundle toBundle() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(ExtraConstants.EXTRA_FLOW_PARAMS, this);
        return bundle;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(hideTextResponse ? 1 : 0);
        dest.writeInt(iconResId);
        dest.writeInt(noSuccessIcon ? 1 : 0);
        dest.writeInt(enableTapOutsideToClose ? 1 : 0);
        dest.writeInt(autoClose ? 1 : 0);
        dest.writeInt(showCloseButton ? 1 : 0);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<FeedbackRequestExtras> CREATOR = new Creator<FeedbackRequestExtras>() {
        @Override
        public FeedbackRequestExtras createFromParcel(Parcel in) {
            boolean hideTextResponse = in.readInt() != 0;
            int iconResId = in.readInt();
            boolean noSuccessIcon = in.readInt() != 0;
            boolean enableTapOutsideToClose = in.readInt() != 0;
            boolean autoClose = in.readInt() != 0;
            boolean showCloseButton = in.readInt() != 0;
            return new FeedbackRequestExtras(
                    hideTextResponse,
                    iconResId,
                    noSuccessIcon,
                    enableTapOutsideToClose,
                    autoClose,
                    showCloseButton
            );
        }

        @Override
        public FeedbackRequestExtras[] newArray(int size) {
            return new FeedbackRequestExtras[size];
        }
    };

    boolean isHideTextResponse() {
        return hideTextResponse;
    }

    boolean isEnableTapOutsideToClose() {
        return enableTapOutsideToClose;
    }

    int getIconResId() {
        return iconResId;
    }

    boolean isNoSuccessIcon() {
        return noSuccessIcon;
    }

    boolean isAutoClose() {
        return autoClose;
    }

    void setAutoClose(boolean autoClose) {
        this.autoClose = autoClose;
    }

    boolean isShowCloseButton() {
        return showCloseButton;
    }

    void setShowCloseButton(boolean showCloseButton) {
        this.showCloseButton = showCloseButton;
    }
}
