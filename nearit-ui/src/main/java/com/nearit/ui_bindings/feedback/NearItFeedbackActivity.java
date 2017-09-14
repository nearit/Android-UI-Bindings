package com.nearit.ui_bindings.feedback;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;

import com.nearit.ui_bindings.ExtraConstants;
import com.nearit.ui_bindings.R;
import com.nearit.ui_bindings.permissions.NearItPermissionsActivity;
import com.nearit.ui_bindings.permissions.PermissionsRequestIntentExtras;

public class NearItFeedbackActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_near_it_feedback);
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
