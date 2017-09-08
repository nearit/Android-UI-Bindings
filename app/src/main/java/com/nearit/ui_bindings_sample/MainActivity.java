package com.nearit.ui_bindings_sample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.nearit.ui_bindings.NearITUIBindings;

import it.near.sdk.NearItManager;

public class MainActivity extends AppCompatActivity {

    private static final int NEAR_PERMISSION_REQUEST = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button permissions = (Button) findViewById(R.id.permissions);
        Button permissionsNoBeacon = (Button) findViewById(R.id.permissions_no_beacon);
        Button permissionsBeaconNonBlocking = (Button) findViewById(R.id.permissions_beacon_nonblocking);

        Button permissionsInvisibleLayout = (Button) findViewById(R.id.permissions_invisible);
        Button invisibleNoBeacon = (Button) findViewById(R.id.invisible_no_beacon);
        Button invisibleNonBlocking = (Button) findViewById(R.id.invisible_non_blocking);

        Button autoStartRadar = (Button) findViewById(R.id.autostart_radar);

        Button customHeader = (Button) findViewById(R.id.custom_header);


        permissions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(
                        //  Basic config: location and bluetooth required
                        NearITUIBindings.getInstance(MainActivity.this)
                                .createPermissionRequestIntentBuilder()
                                .build(),
                        NEAR_PERMISSION_REQUEST);
            }
        });


        permissionsNoBeacon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(
                        //  Asks only for location + tap outside to close enabled
                        NearITUIBindings.getInstance(MainActivity.this)
                                .createPermissionRequestIntentBuilder()
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
                        NearITUIBindings.getInstance(MainActivity.this)
                                .createPermissionRequestIntentBuilder()
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
                        NearITUIBindings.getInstance(MainActivity.this)
                                .createPermissionRequestIntentBuilder()
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
                        NearITUIBindings.getInstance(MainActivity.this)
                                .createPermissionRequestIntentBuilder()
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
                        //  but the latter is not a blocking requirement
                        NearITUIBindings.getInstance(MainActivity.this)
                                .createPermissionRequestIntentBuilder()
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
                        NearITUIBindings.getInstance(MainActivity.this)
                                .createPermissionRequestIntentBuilder()
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
                        NearITUIBindings.getInstance(MainActivity.this)
                                .createPermissionRequestIntentBuilder()
                                .setHeaderResourceId(R.drawable.logo)
                                .build(),
                        NEAR_PERMISSION_REQUEST);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            Toast.makeText(this, "Result OK", Toast.LENGTH_SHORT).show();
            NearItManager.getInstance().startRadar();
        } else Toast.makeText(this, "Result KO", Toast.LENGTH_SHORT).show();
    }
}
