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
    private boolean mEnableTextResponseOnStart;
    private boolean mEnableTapOutside;

    public FeedbackIntentBuilder(Context context, Feedback feedback) {
        mContext = context;
        mFeedback = feedback;
    }

    public FeedbackIntentBuilder hideTextResponse() {
        this.mHideTextResponse = true;
        return this;
    }

    public FeedbackIntentBuilder enableTextResponseOnStart() {
        this.mEnableTextResponseOnStart = true;
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
                mHideTextResponse,
                mEnableTextResponseOnStart,
                mEnableTapOutside
        );
    }

}
