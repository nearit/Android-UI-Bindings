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

    FeedbackRequestIntentExtras() {

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
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<FeedbackRequestIntentExtras> CREATOR = new Creator<FeedbackRequestIntentExtras>() {
        @Override
        public FeedbackRequestIntentExtras createFromParcel(Parcel in) {

            return new FeedbackRequestIntentExtras(
                    );
        }

        @Override
        public FeedbackRequestIntentExtras[] newArray(int size) {
            return new FeedbackRequestIntentExtras[size];
        }
    };


}
