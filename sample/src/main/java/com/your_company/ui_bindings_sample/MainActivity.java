package com.your_company.ui_bindings_sample;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.nearit.ui_bindings.NearITUIBindings;

/**
 * @author Federico Boschini
 */

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void openPermissions(View view) {
        startActivity(new Intent(this, PermissionsActivity.class));
    }

    public void openCoupon(View view) {
        startActivity(new Intent(this, CouponsActivity.class));
    }

    public void openFeedbacks(View view) {
        startActivity(new Intent(this, FeedbackActivity.class));
    }

    public void openContent(View view) {
        startActivity(new Intent(this, ContentActivity.class));
    }

    public void openInbox(View view) {
        startActivity(new Intent(this, InboxActivity.class));
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        onNewIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        boolean handled = NearITUIBindings.onNewIntent(this, intent);

        if (!handled) {
            //  intent was not carrying an in-app content
        }
    }
}