package com.nearit.ui_bindings_sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.nearit.ui_bindings.NearITUIBindings;

public class FeedbackActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        Button completeFeedback = (Button) findViewById(R.id.complete);
        completeFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(
                        NearITUIBindings.getInstance(FeedbackActivity.this)
                                .createFeedbackIntentBuilder()
                                .build()
                );
            }
        });
    }

}
