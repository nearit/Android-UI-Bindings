package com.nearit.ui_bindings.feedback;

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
public class BaseFeedbackActivity extends AppCompatActivity {

    private FeedbackRequestExtras extras;
    private boolean isEnableTapToClose = false;
    private NearItFeedbackFragment mFragment;

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

        if (savedInstanceState != null) {
            //  Restore the fragment instance
            mFragment = (NearItFeedbackFragment) getSupportFragmentManager().getFragment(savedInstanceState, "feedbackFragment");
        } else {
            mFragment = NearItFeedbackFragment.newInstance(feedback, extras);
        }

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.feedback_fragment_container, mFragment)
                .commit();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //  Save the fragment instance
        getSupportFragmentManager().putFragment(outState, "feedbackFragment", mFragment);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Rect dialogBounds = new Rect();
        getWindow().getDecorView().getHitRect(dialogBounds);

        if (!dialogBounds.contains((int) ev.getX(), (int) ev.getY())) {
            if (!isEnableTapToClose) {
                return super.dispatchTouchEvent(ev);
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                finishAndRemoveTask();
            } else {
                finish();
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    public static Intent addExtras(Intent intent, Feedback feedback, FeedbackRequestExtras params) {
        return intent.putExtra(ExtraConstants.FEEDBACK_EXTRA, feedback)
                .putExtra(ExtraConstants.EXTRA_FLOW_PARAMS, params);
    }
}
