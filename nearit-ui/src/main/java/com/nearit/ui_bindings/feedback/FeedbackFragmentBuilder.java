package com.nearit.ui_bindings.feedback;

import android.content.Context;

import it.near.sdk.reactions.feedbackplugin.model.Feedback;

/**
 * @author Federico Boschini
 */

public class FeedbackFragmentBuilder {
    private Context mContext;
    private boolean mWithoutComment;
    private boolean mNoSuccessIcon;
    private boolean mAutoClose;
    private boolean mShowCloseButton;
    private int mIconResId;
    private Feedback mFeedback;

    public FeedbackFragmentBuilder(Context context, Feedback feedback) {
        mContext = context;
        mFeedback = feedback;
    }

    /**
     * Sets no text response
     */
    public FeedbackFragmentBuilder withoutComment() {
        mWithoutComment = true;
        return this;
    }

    /**
     * Sets custom icon to the success view
     */
    public FeedbackFragmentBuilder setSuccessIconResourceId(int successIconResId) {
        mIconResId = successIconResId;
        return this;
    }

    /**
     * Sets no icon on the success view
     */
    public FeedbackFragmentBuilder noSuccessIcon() {
        mNoSuccessIcon = true;
        return this;
    }

    /**
     * Enables autoclose of the parent activity
     */
    public FeedbackFragmentBuilder autoCloseParentActivityOnFinish() {
        mAutoClose = true;
        return this;
    }

    /**
     * Shows a close/dismiss button (text) at the bottom of the feedback request
     */
    public FeedbackFragmentBuilder showCloseButton() {
        mShowCloseButton = true;
        return this;
    }

    public NearItFeedbackFragment build() {
        return NearItFeedbackFragment.newInstance(mFeedback, getParams());
    }

    private FeedbackRequestExtras getParams() {
        FeedbackRequestExtras extras = new FeedbackRequestExtras(
                mWithoutComment,
                mIconResId,
                mNoSuccessIcon);
        if (mAutoClose) {
            extras.setAutoClose(true);
        }
        if (mShowCloseButton) {
            extras.setShowCloseButton(true);
        }
        return extras;
    }

}
