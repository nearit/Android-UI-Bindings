package com.nearit.ui_bindings.feedback;

import android.content.Context;

import it.near.sdk.reactions.feedbackplugin.model.Feedback;

/**
 * Created by Federico Boschini on 06/09/17.
 */

public class FeedbackFragmentBuilder {
    private Context mContext;
    private boolean mWithoutComment;
    private boolean mNoSuccessIcon;
    private boolean mAutoClose;
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
    public FeedbackFragmentBuilder setNoSuccessIcon() {
        mNoSuccessIcon = true;
        return this;
    }

    public FeedbackFragmentBuilder setAutoClose() {
        mAutoClose = true;
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
        return extras;
    }

}
