package com.nearit.ui_bindings.feedback;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.widget.RatingBar;
import android.widget.Toast;

import com.nearit.ui_bindings.ExtraConstants;
import com.nearit.ui_bindings.R;

public class NearItFeedbackActivity extends AppCompatActivity {

    @Nullable
    RatingBar ratingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nearit_ui_activity_feedback);

        ratingBar = (RatingBar) findViewById(R.id.feedback_rating);

        if (ratingBar != null) {
            ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                @Override
                public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                    Toast.makeText(NearItFeedbackActivity.this, Float.toString(rating), Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    public static Intent createIntent(Context context, FeedbackRequestIntentExtras params) {
        return new Intent(context, NearItFeedbackActivity.class).putExtra(ExtraConstants.EXTRA_FLOW_PARAMS, params);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Rect dialogBounds = new Rect();
        getWindow().getDecorView().getHitRect(dialogBounds);

        if (!dialogBounds.contains((int) ev.getX(), (int) ev.getY())) {
//            if (!isEnableTapToClose) {
//                return true;
//            }
//            finalCheck();
        }
        return super.dispatchTouchEvent(ev);
    }
}
