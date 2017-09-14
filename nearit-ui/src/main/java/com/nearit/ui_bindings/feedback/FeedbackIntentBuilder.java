package com.nearit.ui_bindings.feedback;

import android.content.Context;
import android.content.Intent;

import com.nearit.ui_bindings.permissions.NearItPermissionsActivity;

/**
 * Created by Federico Boschini on 29/08/17.
 */
public class FeedbackIntentBuilder {
    private Context mContext;

    public FeedbackIntentBuilder(Context context) {
        mContext = context;
    }

    public Intent build() {
        return NearItFeedbackActivity.createIntent(mContext, getParams());
    }

    private FeedbackRequestIntentExtras getParams() {
        return new FeedbackRequestIntentExtras();
    }

}
