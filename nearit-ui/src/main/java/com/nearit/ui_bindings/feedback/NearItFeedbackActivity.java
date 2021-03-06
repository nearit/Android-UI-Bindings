package com.nearit.ui_bindings.feedback;

import android.content.Context;
import android.content.Intent;

import it.near.sdk.reactions.feedbackplugin.model.Feedback;

/**
 * @author Federico Boschini
 */

public class NearItFeedbackActivity extends BaseFeedbackActivity {

    public static Intent createIntent(Context context, Feedback feedback, FeedbackRequestExtras params) {
        Intent intent = new Intent(context, NearItFeedbackActivity.class);
        return BaseFeedbackActivity.addExtras(intent, feedback, params);
    }

}
