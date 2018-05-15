package com.your_company.ui_bindings_sample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.nearit.ui_bindings.NearITUIBindings;
import com.your_company.ui_bindings_sample.factories.FeedbackFactory;

import it.near.sdk.reactions.feedbackplugin.model.Feedback;

/**
 * @author Federico Boschini
 */

public class FeedbackActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        final FeedbackFactory feedbackFactory = new FeedbackFactory();

        Button completeFeedback = findViewById(R.id.complete);
        completeFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  In a real scenario the question is provided by the NearIT SDK
                Feedback feedback = feedbackFactory.getFeedback();
                startActivity(
                        NearITUIBindings.getInstance(FeedbackActivity.this)
                                .feedbackIntentBuilder(feedback)
                                .build()
                );
            }
        });

        Button noTextResponseFeedback = findViewById(R.id.no_text_box);
        noTextResponseFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  In a real scenario the question is provided by the NearIT SDK
                Feedback feedback = feedbackFactory.getFeedback();
                startActivity(
                        //  The text response is disabled
                        //  + tap outside to close is enabled
                        NearITUIBindings.getInstance(FeedbackActivity.this)
                                .feedbackIntentBuilder(feedback)
                                .withoutComment()
                                .enableTapOutsideToClose()
                                .build()
                );
            }
        });

        Button feedbackInPlainActivity = findViewById(R.id.feedback_in_plain_activity);
        feedbackInPlainActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FeedbackActivity.this, FeedbackPlainActivity.class));
            }
        });

    }

}
