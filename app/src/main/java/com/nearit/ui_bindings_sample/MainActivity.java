package com.nearit.ui_bindings_sample;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.nearit.ui_bindings.NearITUIBindings;

import it.near.sdk.reactions.contentplugin.model.Content;
import it.near.sdk.reactions.couponplugin.model.Coupon;
import it.near.sdk.reactions.customjsonplugin.model.CustomJSON;
import it.near.sdk.reactions.feedbackplugin.model.Feedback;
import it.near.sdk.reactions.simplenotificationplugin.model.SimpleNotification;
import it.near.sdk.trackings.TrackingInfo;
import it.near.sdk.utils.CoreContentsListener;
import it.near.sdk.utils.NearUtils;

public class MainActivity extends AppCompatActivity implements CoreContentsListener{

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button permissions = (Button) findViewById(R.id.permissions_demo);
        permissions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, PermissionsActivity.class));
            }
        });

        Button coupon = (Button) findViewById(R.id.coupon_demo);
        coupon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, CouponsActivity.class));
            }
        });

        Button feedback = (Button) findViewById(R.id.feedback_demo);
        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, FeedbackActivity.class));
            }
        });

        Button content = (Button) findViewById(R.id.content_demo);
        content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ContentActivity.class));
            }
        });

    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        onNewIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent != null &&
                intent.getExtras() != null &&
                NearUtils.carriesNearItContent(intent)) {

            NearUtils.parseCoreContents(intent, this);
        }
    }

    //  The following methods will manage real NearIT notification

    @Override
    public void gotContentNotification(Content content, TrackingInfo trackingInfo) {
        //  Not yet implemented
        Log.d(TAG, "content notification received");
    }

    @Override
    public void gotCouponNotification(Coupon coupon, TrackingInfo trackingInfo) {
        Log.d(TAG, "coupon notification received");
        startActivity(NearITUIBindings.getInstance(this).createCouponDetailIntentBuilder(coupon).build());
    }

    @Override
    public void gotCustomJSONNotification(CustomJSON customJSON, TrackingInfo trackingInfo) {
        //  Not yet implemented
        Log.d(TAG, "customJson notification received");
    }

    @Override
    public void gotSimpleNotification(SimpleNotification simpleNotification, TrackingInfo trackingInfo) {
        //  Not yet implemented
        Log.d(TAG, "simple notification received");
    }

    @Override
    public void gotFeedbackNotification(Feedback feedback, TrackingInfo trackingInfo) {
        Log.d(TAG, "feedback notification received");
        startActivity(NearITUIBindings.getInstance(this).createFeedbackIntentBuilder(feedback).build());
    }
}