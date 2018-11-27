package com.nearit.ui_bindings.feedback;

import android.content.Context;
import android.content.Intent;

import com.nearit.ui_bindings.NearItLaunchMode;

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

    private final NearItLaunchMode mLaunchMode;
    private int mFlags;

    public FeedbackIntentBuilder(Context context, Feedback feedback, NearItLaunchMode launchMode) {
        mContext = context;
        mFeedback = feedback;
        mAutoCloseParentActivity = true;
        mShowCloseButton = true;
        mLaunchMode = launchMode;
    }

    public FeedbackIntentBuilder(Context context, Feedback feedback, NearItLaunchMode launchMode, int flags) {
        this(context, feedback, launchMode);
        mFlags = flags;
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
        Intent intent;
        switch (mLaunchMode) {
            case SINGLE_INSTANCE:
                intent = NearItFeedbackActivitySingleInstance.createIntent(mContext, mFeedback, getParams());
                break;
            case SINGLE_TOP:
                intent = NearItFeedbackActivitySingleTop.createIntent(mContext, mFeedback, getParams());
                break;
            case SINGLE_TASK:
                intent = NearItFeedbackActivitySingleTask.createIntent(mContext, mFeedback, getParams());
                break;
            case STANDARD:
            default:
                intent = NearItFeedbackActivity.createIntent(mContext, mFeedback, getParams());
                break;
        }

        intent.addFlags(mFlags);
        return intent;
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
