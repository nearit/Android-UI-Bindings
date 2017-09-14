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
    private final boolean hideDate, enableTextResponse;

    FeedbackRequestIntentExtras(boolean hideDate, boolean enableTextResponse) {
        this.hideDate = hideDate;
        this.enableTextResponse = enableTextResponse;
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
        dest.writeInt(enableTextResponse ? 1 : 0);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<FeedbackRequestIntentExtras> CREATOR = new Creator<FeedbackRequestIntentExtras>() {
        @Override
        public FeedbackRequestIntentExtras createFromParcel(Parcel in) {
            boolean hideDate = in.readInt() != 0;
            boolean enableTextResponse = in.readInt() != 0;
            return new FeedbackRequestIntentExtras(
                    hideDate,
                    enableTextResponse
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

    public boolean isEnableTextResponse() {
        return enableTextResponse;
    }
}
