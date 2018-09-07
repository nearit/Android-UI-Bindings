package com.nearit.ui_bindings.content;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import com.nearit.ui_bindings.ExtraConstants;

/**
 * @author Federico Boschini
 */

class ContentDetailExtraParams implements Parcelable {

    private final boolean enableTapOutsideToClose;
    private final boolean openLinksInWebView;

    ContentDetailExtraParams(boolean enableTapOutsideToClose, boolean openLinksInWebView) {
        this.enableTapOutsideToClose = enableTapOutsideToClose;
        this.openLinksInWebView = openLinksInWebView;
    }

    ContentDetailExtraParams() {
        this.enableTapOutsideToClose = false;
        this.openLinksInWebView = false;
    }

    /**
     * Extract ContentDetailExtraParams from an Intent.
     */
    static ContentDetailExtraParams fromIntent(Intent intent) {
        return intent.getParcelableExtra(ExtraConstants.EXTRA_FLOW_PARAMS);
    }

    /**
     * Extract ContentDetailExtraParams from a Bundle.
     */
    public static ContentDetailExtraParams fromBundle(Bundle bundle) {
        return bundle.getParcelable(ExtraConstants.EXTRA_FLOW_PARAMS);
    }

    /**
     * Create a bundle containing this ContentDetailExtraParams object as {@link
     * ExtraConstants#EXTRA_FLOW_PARAMS}.
     */
    public Bundle toBundle() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(ExtraConstants.EXTRA_FLOW_PARAMS, this);
        return bundle;
    }

    public static final Creator<ContentDetailExtraParams> CREATOR = new Creator<ContentDetailExtraParams>() {
        @Override
        public ContentDetailExtraParams createFromParcel(Parcel in) {
            boolean enableTapToClose = in.readInt() != 0;
            boolean openLinksInWebView = in.readInt() != 0;

            return new ContentDetailExtraParams(
                    enableTapToClose,
                    openLinksInWebView
            );
        }

        @Override
        public ContentDetailExtraParams[] newArray(int size) {
            return new ContentDetailExtraParams[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(enableTapOutsideToClose ? 1 : 0);
        dest.writeInt(openLinksInWebView ? 1 : 0);
    }

    boolean isEnableTapOutsideToClose() {
        return enableTapOutsideToClose;
    }

    boolean isOpenLinksInWebView() {
        return openLinksInWebView;
    }

}
