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
    private boolean mHideDate;
    private boolean mEnableTextResponse;
    private boolean mEnableTapOutside;

    public FeedbackIntentBuilder(Context context, Feedback feedback) {
        mContext = context;
        mFeedback = feedback;
    }

    public FeedbackIntentBuilder hideDate() {
        this.mHideDate = true;
        return this;
    }

    public FeedbackIntentBuilder enableTextResponse() {
        this.mEnableTextResponse = true;
        return this;
    }

    public FeedbackIntentBuilder enableTapOutsideToClose() {
        this.mEnableTapOutside = true;
        return this;
    }

    public Intent build() {
        return NearItFeedbackActivity.createIntent(mContext, mFeedback, getParams());
    }

    private FeedbackRequestIntentExtras getParams() {
        return new FeedbackRequestIntentExtras(
                mHideDate,
                mEnableTextResponse,
                mEnableTapOutside
        );
    }

}
