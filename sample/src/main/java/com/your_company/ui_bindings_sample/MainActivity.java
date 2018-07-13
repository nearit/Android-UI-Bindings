package com.your_company.ui_bindings_sample;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.nearit.ui_bindings.NearITUIBindings;
import com.nearit.ui_bindings.permissions.views.PermissionSnackBar;

import it.near.sdk.NearItManager;

/**
 * @author Federico Boschini
 */

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private PermissionSnackBar snackBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayout mainContainer = findViewById(R.id.main_activity_container);

        snackBar = PermissionSnackBar.make(mainContainer, "Fornisci tutte le autorizzazioni necessarie", -2)
                .setAction("OK!")
                .autoStartRadar()
                .noBeacon()
                .bindToActivity(this, 6)
                .addCallback(new BaseTransientBottomBar.BaseCallback<PermissionSnackBar>() {
                    @Override
                    public void onDismissed(PermissionSnackBar transientBottomBar, int event) {
                        Toast.makeText(MainActivity.this, "Dismissed", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onShown(PermissionSnackBar transientBottomBar) {
                        Toast.makeText(MainActivity.this, "Shown", Toast.LENGTH_SHORT).show();
                    }
                })
                .show();
    }

    public void openCoordLayout(View view) {
        startActivity(new Intent(this, CoordLayoutActivity.class));
    }

    public void openPermissions(View view) {
        startActivity(new Intent(this, PermissionsActivity.class));
    }

    public void openCoupon(View view) {
        startActivity(new Intent(this, CouponsActivity.class));
    }

    public void openFeedback(View view) {
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        snackBar.unbindFromActivity();
    }

    @SuppressLint("MissingPermission")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        snackBar.onActivityResult(requestCode, resultCode);
        if (requestCode == 6) {
            if (resultCode == Activity.RESULT_OK) {
                Toast.makeText(this, "Result OK", Toast.LENGTH_SHORT).show();
                NearItManager.getInstance().startRadar();
            } else Toast.makeText(this, "Result KO", Toast.LENGTH_SHORT).show();
        }
    }
}