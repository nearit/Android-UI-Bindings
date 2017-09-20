package com.nearit.ui_bindings_sample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.nearit.ui_bindings.NearITUIBindings;

import it.near.sdk.reactions.feedbackplugin.model.Feedback;

/**
 * Created by Federico Boschini on 13/09/17.
 */

public class FeedbackPlainActivity extends AppCompatActivity {
    Fragment feedbackFragment;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plain_feedback);

        FeedbackFactory couponFactory = new FeedbackFactory();

        //  In a real scenario the feedback request is provided by the NearIT SDK
        Feedback feedback = couponFactory.getFeedback();

        if (savedInstanceState != null) {
            //  Restore the fragment instance
            feedbackFragment = getSupportFragmentManager().getFragment(savedInstanceState, "feedbackFragment");
        } else {
            feedbackFragment = NearITUIBindings.getInstance(this)
                    .createFeedbackFragmentBuilder(feedback)
                    .build();
        }

        getSupportFragmentManager().beginTransaction().replace(R.id.feedback_container, feedbackFragment).commit();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //  Save the fragment instance
        getSupportFragmentManager().putFragment(outState, "feedbackFragment", feedbackFragment);
    }
}
