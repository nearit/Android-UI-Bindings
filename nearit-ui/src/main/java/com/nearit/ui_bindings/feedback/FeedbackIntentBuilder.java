package com.nearit.ui_bindings.feedback;

import android.content.Context;
import android.content.Intent;

import it.near.sdk.reactions.feedbackplugin.model.Feedback;

/**
 * @author Federico Boschini
 */

public class FeedbackIntentBuilder {
    private final Context mContext;
    private final Feedback mFeedback;
    private boolean mHideTextResponse;
    private boolean mNoSuccessIcon;
    private final boolean mAutoCloseParentActivity;
    private final boolean mShowCloseButton;
    private int mIconResId;
    private boolean mEnableTapOutside;

    private final boolean mSingleInstance;

    public FeedbackIntentBuilder(Context context, Feedback feedback, boolean singleInstance) {
        mContext = context;
        mFeedback = feedback;
        mAutoCloseParentActivity = true;
        mShowCloseButton = true;
        mSingleInstance = singleInstance;
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
        if (mSingleInstance) return NearItFeedbackActivitySingleInstance.createIntent(mContext, mFeedback, getParams());
        else return NearItFeedbackActivity.createIntent(mContext, mFeedback, getParams());
    }

    private FeedbackRequestExtras getParams() {
        return new FeedbackRequestExtras(
                mHideTextResponse,
                mIconResId,
                mNoSuccessIcon,
                mEnableTapOutside,
                mAutoCloseParentActivity,
                mShowCloseButton
        );
    }

}
