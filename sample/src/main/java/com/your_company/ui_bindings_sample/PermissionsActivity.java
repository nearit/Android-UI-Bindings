package com.your_company.ui_bindings_sample;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.nearit.ui_bindings.NearITUIBindings;
import com.nearit.ui_bindings.permissions.views.PermissionBar;

import it.near.sdk.NearItManager;

/**
 * @author Federico Boschini
 */

public class PermissionsActivity extends AppCompatActivity {

    private static final int NEAR_PERMISSION_REQUEST = 1000;
    PermissionBar bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permissions);

        bar = findViewById(R.id.permission_bar);
        bar.bindToActivity(this, NEAR_PERMISSION_REQUEST);

        Button permissions = findViewById(R.id.permissions);
        Button permissionsNoBeacon = findViewById(R.id.permissions_no_beacon);
        Button permissionsBeaconNonBlocking = findViewById(R.id.permissions_beacon_nonblocking);

        Button permissionsInvisibleLayout = findViewById(R.id.permissions_invisible);
        Button invisibleNoBeacon = findViewById(R.id.invisible_no_beacon);
        Button invisibleNonBlocking = findViewById(R.id.invisible_non_blocking);

        Button autoStartRadar = findViewById(R.id.autostart_radar);

        Button customHeader = findViewById(R.id.custom_header);

        permissions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(
                        //  Basic config: location and bluetooth required
                        //  + enable tap outside to close
                        NearITUIBindings.getInstance(PermissionsActivity.this)
                                .permissionsIntentBuilder()
                                .enableTapOutsideToClose()
                                .build(),
                        NEAR_PERMISSION_REQUEST);
            }
        });

        permissionsNoBeacon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(
                        //  Asks only for location
                        //  + tap outside to close enabled
                        NearITUIBindings.getInstance(PermissionsActivity.this)
                                .permissionsIntentBuilder()
                                .noBeacon()
                                .enableTapOutsideToClose()
                                .build(),
                        NEAR_PERMISSION_REQUEST);
            }
        });

        permissionsBeaconNonBlocking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(
                        //  Asks for location and bluetooth
                        //  but the latter is not a blocking requirement
                        NearITUIBindings.getInstance(PermissionsActivity.this)
                                .permissionsIntentBuilder()
                                .nonBlockingBeacon()
                                .build(),
                        NEAR_PERMISSION_REQUEST);
            }
        });

        permissionsInvisibleLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(
                        //  Invisible layout: asks for location and bluetooth via system dialogs
                        NearITUIBindings.getInstance(PermissionsActivity.this)
                                .permissionsIntentBuilder()
                                .invisibleLayoutMode()
                                .build(),
                        NEAR_PERMISSION_REQUEST);
            }
        });

        invisibleNoBeacon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(
                        //  Invisible layout: asks only for location
                        NearITUIBindings.getInstance(PermissionsActivity.this)
                                .permissionsIntentBuilder()
                                .invisibleLayoutMode()
                                .noBeacon()
                                .build(),
                        NEAR_PERMISSION_REQUEST);
            }
        });

        invisibleNonBlocking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(
                        //  Invisible layout: asks for location and bluetooth
                        //  but the latter is not a blocking requiremen
                        NearITUIBindings.getInstance(PermissionsActivity.this)
                                .permissionsIntentBuilder()
                                .invisibleLayoutMode()
                                .nonBlockingBeacon()
                                .build(),
                        NEAR_PERMISSION_REQUEST);
            }
        });

        autoStartRadar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(
                        //  Asks for permissions (basic config) and if user grants all
                        //  NearIT-UI will automatically start the NearIT radar
                        NearITUIBindings.getInstance(PermissionsActivity.this)
                                .permissionsIntentBuilder()
                                .automaticRadarStart()
                                .build(),
                        NEAR_PERMISSION_REQUEST);
            }
        });

        customHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(
                        //  Basic config + custom header
                        NearITUIBindings.getInstance(PermissionsActivity.this)
                                .permissionsIntentBuilder()
                                .setHeaderResourceId(R.drawable.logo)
                                .build(),
                        NEAR_PERMISSION_REQUEST);
            }
        });
    }

    @SuppressLint("MissingPermission")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        bar.onActivityResult(requestCode, resultCode);
        if (requestCode == NEAR_PERMISSION_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                Toast.makeText(this, "Result OK", Toast.LENGTH_SHORT).show();
                NearItManager.getInstance().startRadar();
            } else Toast.makeText(this, "Result KO", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        bar.unbindFromActivity();
        super.onDestroy();
    }
}
