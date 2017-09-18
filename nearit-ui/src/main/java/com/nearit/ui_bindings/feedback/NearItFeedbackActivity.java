package com.nearit.ui_bindings.feedback;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.nearit.ui_bindings.ExtraConstants;
import com.nearit.ui_bindings.R;
import com.nearit.ui_bindings.views.NearItUIButton;

import it.near.sdk.NearItManager;
import it.near.sdk.reactions.feedbackplugin.FeedbackEvent;
import it.near.sdk.reactions.feedbackplugin.model.Feedback;
import it.near.sdk.recipes.NearITEventHandler;

public class NearItFeedbackActivity extends AppCompatActivity {

    @Nullable
    RatingBar ratingBar;
    @Nullable
    LinearLayout commentSection;
    @Nullable
    NearItUIButton sendButton;
    @Nullable
    TextView closeButton;
    @Nullable
    EditText commentBox;
    @Nullable
    TextView errorText;

    private boolean isEnableTapToClose = false;
    private FeedbackRequestIntentExtras extras;
    private float userRating;
    private String userComment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nearit_ui_activity_feedback);

        Intent intent = getIntent();
        if (intent.hasExtra(ExtraConstants.EXTRA_FLOW_PARAMS)) {
            extras = FeedbackRequestIntentExtras.fromIntent(intent);
            isEnableTapToClose = extras.isEnableTapOutsideToClose();
        }

        final Feedback feedback = getIntent().getParcelableExtra(ExtraConstants.FEEDBACK_EXTRA);

        ratingBar = (RatingBar) findViewById(R.id.feedback_rating);
        commentSection = (LinearLayout) findViewById(R.id.feedback_comment_section);
        sendButton = (NearItUIButton) findViewById(R.id.feedback_send_comment_button);
        closeButton = (TextView) findViewById(R.id.feedback_dismiss_text);
        commentBox = (EditText) findViewById(R.id.feedback_comment_box);
        errorText = (TextView) findViewById(R.id.feedback_error_alert);

        if (closeButton != null) {
            closeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }

        if (ratingBar != null) {
            ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                @Override
                public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                    if (rating < 1.0f) {
                        ratingBar.setRating(1.0f);
                    }

                    userRating = rating;

                    if (commentSection != null) {
                        commentSection.setVisibility(View.VISIBLE);
                    }
                    if (sendButton != null) {
                        sendButton.setVisibility(View.VISIBLE);
                    }
                }
            });
        }

        if (sendButton != null) {
            sendButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (userRating < 1.0f) {
                        userRating = 1.0f;
                    }
                    userComment = commentBox != null ? commentBox.getText().toString() : "";
                    NearItManager.getInstance().sendEvent(new FeedbackEvent(feedback, (int) userRating, userComment), new NearITEventHandler() {
                        @Override
                        public void onSuccess() {
                            finish();
                        }

                        @Override
                        public void onFail(int i, String s) {
                            if (errorText != null) {
                                errorText.setVisibility(View.VISIBLE);
                            }
                            sendButton.setText("RIPROVA");
                            sendButton.setChecked();
                        }
                    });
                }
            });
        }

    }

    public static Intent createIntent(Context context, Feedback feedback, FeedbackRequestIntentExtras params) {
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
