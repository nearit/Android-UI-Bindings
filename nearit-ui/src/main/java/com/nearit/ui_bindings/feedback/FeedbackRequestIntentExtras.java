package com.nearit.ui_bindings.feedback;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.RestrictTo;

import com.nearit.ui_bindings.ExtraConstants;

/**
 * Created by Federico Boschini on 29/08/17.
 */
@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
class FeedbackRequestIntentExtras implements Parcelable {
    private final boolean hideTextResponse, enableTextResponseOnStart, enableTapOutsideToClose;

    FeedbackRequestIntentExtras(boolean hideTextResponse, boolean enableTextResponseOnStart, boolean enableTapOutsideToClose) {
        this.hideTextResponse = hideTextResponse;
        this.enableTextResponseOnStart = enableTextResponseOnStart;
        this.enableTapOutsideToClose = enableTapOutsideToClose;
    }

    /**
     * Extract FeedbackRequestIntentExtras from an Intent.
     */
    static FeedbackRequestIntentExtras fromIntent(Intent intent) {
        return intent.getParcelableExtra(ExtraConstants.EXTRA_FLOW_PARAMS);
    }

    /**
     * Extract FeedbackRequestIntentExtras from a Bundle.
     */
    public static FeedbackRequestIntentExtras fromBundle(Bundle bundle) {
        return bundle.getParcelable(ExtraConstants.EXTRA_FLOW_PARAMS);
    }

    /**
     * Create a bundle containing this FeedbackRequestIntentExtras object as {@link
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
        dest.writeInt(enableTextResponseOnStart ? 1 : 0);
        dest.writeInt(enableTapOutsideToClose ? 1 : 0);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<FeedbackRequestIntentExtras> CREATOR = new Creator<FeedbackRequestIntentExtras>() {
        @Override
        public FeedbackRequestIntentExtras createFromParcel(Parcel in) {
            boolean hideTextResponse = in.readInt() != 0;
            boolean enableTextResponseOnStart = in.readInt() != 0;
            boolean enableTapOutsideToClose = in.readInt() != 0;
            return new FeedbackRequestIntentExtras(
                    hideTextResponse,
                    enableTextResponseOnStart,
                    enableTapOutsideToClose
            );
        }

        @Override
        public FeedbackRequestIntentExtras[] newArray(int size) {
            return new FeedbackRequestIntentExtras[size];
        }
    };

    boolean isEnableTextResponseOnStart() {
        return enableTextResponseOnStart;
    }

    boolean isEnableTapOutsideToClose() {
        return enableTapOutsideToClose;
    }

    boolean isHideTextResponse() {
        return hideTextResponse;
    }
}
