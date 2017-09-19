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
public class FeedbackRequestIntentExtras implements Parcelable {
    private final boolean hideDate, hideTextResponse, enableTextResponseOnStart, enableTapOutsideToClose;

    FeedbackRequestIntentExtras(boolean hideDate, boolean hideTextResponse, boolean enableTextResponseOnStart, boolean enableTapOutsideToClose) {
        this.hideDate = hideDate;
        this.hideTextResponse = hideTextResponse;
        this.enableTextResponseOnStart = enableTextResponseOnStart;
        this.enableTapOutsideToClose = enableTapOutsideToClose;
    }

    /**
     * Extract FeedbackRequestIntentExtras from an Intent.
     */
    public static FeedbackRequestIntentExtras fromIntent(Intent intent) {
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
        dest.writeInt(hideDate ? 1 : 0);
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
            boolean hideDate = in.readInt() != 0;
            boolean hideTextResponse = in.readInt() != 0;
            boolean enableTextResponseOnStart = in.readInt() != 0;
            boolean enableTapOutsideToClose = in.readInt() != 0;
            return new FeedbackRequestIntentExtras(
                    hideDate,
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

    public boolean isHideDate() {
        return hideDate;
    }

    public boolean isEnableTextResponseOnStart() {
        return enableTextResponseOnStart;
    }

    public boolean isEnableTapOutsideToClose() {
        return enableTapOutsideToClose;
    }

    public boolean isHideTextResponse() {
        return hideTextResponse;
    }
}
