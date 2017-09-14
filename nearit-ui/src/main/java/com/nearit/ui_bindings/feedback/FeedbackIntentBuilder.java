package com.nearit.ui_bindings.feedback;

import android.content.Context;
import android.content.Intent;

/**
 * Created by Federico Boschini on 29/08/17.
 */
public class FeedbackIntentBuilder {
    private Context mContext;
    private boolean mHideDate;
    private boolean mEnableTextResponse;

    public FeedbackIntentBuilder(Context context) {
        mContext = context;
    }

    public FeedbackIntentBuilder hideDate() {
        this.mHideDate = true;
        return this;
    }

    public FeedbackIntentBuilder enableTextResponse() {
        this.mEnableTextResponse = true;
        return this;
    }

    public Intent build() {
        return NearItFeedbackActivity.createIntent(mContext, getParams());
    }

    private FeedbackRequestIntentExtras getParams() {
        return new FeedbackRequestIntentExtras(
                mHideDate,
                mEnableTextResponse
        );
    }

}
