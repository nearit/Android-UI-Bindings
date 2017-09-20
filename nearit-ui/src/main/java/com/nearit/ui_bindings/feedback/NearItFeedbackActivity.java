package com.nearit.ui_bindings.feedback;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;

import com.nearit.ui_bindings.ExtraConstants;
import com.nearit.ui_bindings.R;

import it.near.sdk.reactions.feedbackplugin.model.Feedback;

public class NearItFeedbackActivity extends AppCompatActivity {

    private FeedbackRequestExtras extras;
    private boolean isEnableTapToClose = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nearit_ui_activity_feedback);

        Intent intent = getIntent();
        if (intent.hasExtra(ExtraConstants.EXTRA_FLOW_PARAMS)) {
            extras = FeedbackRequestExtras.fromIntent(intent);
            isEnableTapToClose = extras.isEnableTapOutsideToClose();
        }

        final Feedback feedback = getIntent().getParcelableExtra(ExtraConstants.FEEDBACK_EXTRA);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.feedback_fragment_container, NearItFeedbackFragment.newInstance(feedback, extras))
                .commit();
    }

    public static Intent createIntent(Context context, Feedback feedback, FeedbackRequestExtras params) {
        return new Intent(context, NearItFeedbackActivity.class)
                .putExtra(ExtraConstants.FEEDBACK_EXTRA, feedback)
                .putExtra(ExtraConstants.EXTRA_FLOW_PARAMS, params);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Rect dialogBounds = new Rect();
        getWindow().getDecorView().getHitRect(dialogBounds);

        if (!dialogBounds.contains((int) ev.getX(), (int) ev.getY())) {
            if (!isEnableTapToClose) {
                return super.dispatchTouchEvent(ev);
            }
            finish();
        }
        return super.dispatchTouchEvent(ev);
    }
}
