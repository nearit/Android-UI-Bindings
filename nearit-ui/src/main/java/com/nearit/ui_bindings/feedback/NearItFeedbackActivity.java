package com.nearit.ui_bindings.feedback;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;

import com.nearit.ui_bindings.ExtraConstants;
import com.nearit.ui_bindings.R;

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
