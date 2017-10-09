package com.nearit.ui_bindings.feedback;

import android.content.Context;
import android.content.Intent;

import it.near.sdk.reactions.feedbackplugin.model.Feedback;

/**
 * Created by Federico Boschini on 29/08/17.
 */
public class FeedbackIntentBuilder {
    private Context mContext;
    private Feedback mFeedback;
    private boolean mHideTextResponse;
    private boolean mNoSuccessIcon;
    private int mIconResId;
    private boolean mEnableTapOutside;

    public FeedbackIntentBuilder(Context context, Feedback feedback) {
        mContext = context;
        mFeedback = feedback;
    }

    /**
     * Sets no text response
     */
    public FeedbackIntentBuilder withoutComment() {
        this.mHideTextResponse = true;
        return this;
    }

    /**
     * Sets custom icon to the success view
     */
    public FeedbackIntentBuilder setSuccessIconResId(int iconResId) {
        this.mIconResId = iconResId;
        return this;
    }

    /**
     * Sets no icon on the success view
     */
    public FeedbackIntentBuilder setNoSuccessIcon() {
        this.mNoSuccessIcon = true;
        return this;
    }

    /**
     * Sets tap outside the dialog to close enabled
     */
    public FeedbackIntentBuilder enableTapOutsideToClose() {
        this.mEnableTapOutside = true;
        return this;
    }

    public Intent build() {
        return NearItFeedbackActivity.createIntent(mContext, mFeedback, getParams());
    }

    private FeedbackRequestExtras getParams() {
        return new FeedbackRequestExtras(
                mHideTextResponse,
                mIconResId,
                mNoSuccessIcon,
                mEnableTapOutside
        );
    }

}
