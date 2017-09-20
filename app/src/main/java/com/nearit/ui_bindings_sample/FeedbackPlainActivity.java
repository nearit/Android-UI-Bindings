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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plain_feedback);

        FeedbackFactory couponFactory = new FeedbackFactory();

        //  In a real scenario the feedback request is provided by the NearIT SDK
        Feedback feedback = couponFactory.getFeedback();

        Fragment feedbackFragment = NearITUIBindings.getInstance(this)
                .createFeedbackFragmentBuilder(feedback)
                .build();

        getSupportFragmentManager().beginTransaction().replace(R.id.feedback_container, feedbackFragment).commit();

    }
}
