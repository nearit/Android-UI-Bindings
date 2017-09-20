package com.nearit.ui_bindings_sample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.nearit.ui_bindings.NearITUIBindings;

import it.near.sdk.reactions.feedbackplugin.model.Feedback;

public class FeedbackActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        final FeedbackFactory feedbackFactory = new FeedbackFactory();

        Button completeFeedback = (Button) findViewById(R.id.complete);
        completeFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  In a real scenario the question is provided by the NearIT SDK
                Feedback feedback = feedbackFactory.getFeedback();
                startActivity(
                        NearITUIBindings.getInstance(FeedbackActivity.this)
                                .createFeedbackIntentBuilder(feedback)
                                .build()
                );
            }
        });

        Button noTextResponseFeedback = (Button) findViewById(R.id.no_text_box);
        noTextResponseFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  In a real scenario the question is provided by the NearIT SDK
                Feedback feedback = feedbackFactory.getFeedback();
                startActivity(
                        NearITUIBindings.getInstance(FeedbackActivity.this)
                                .createFeedbackIntentBuilder(feedback)
                                .hideTextResponse()
                                .enableTapOutsideToClose()
                                .build()
                );
            }
        });

        Button feedbackInPlainActivity = (Button) findViewById(R.id.feedback_in_plain_activity);
        feedbackInPlainActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FeedbackActivity.this, FeedbackPlainActivity.class));
            }
        });

    }

}
