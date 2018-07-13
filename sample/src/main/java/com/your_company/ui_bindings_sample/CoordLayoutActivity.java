package com.your_company.ui_bindings_sample;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.nearit.ui_bindings.permissions.views.PermissionSnackBar;

import it.near.sdk.NearItManager;

public class CoordLayoutActivity extends AppCompatActivity {

    private PermissionSnackBar snackBar;
    private static final int NEAR_PERMISSION_REQUEST = 1000;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coord_layout);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        snackBar = PermissionSnackBar.make(toolbar, "Fornisci tutte le autorizzazioni necessarie", -2)
                .setAction("OK!")
                .autoStartRadar()
                .noBeacon()
                .bindToActivity(this, NEAR_PERMISSION_REQUEST)
                .addCallback(new BaseTransientBottomBar.BaseCallback<PermissionSnackBar>() {
                    @Override
                    public void onDismissed(PermissionSnackBar transientBottomBar, int event) {
                        Toast.makeText(CoordLayoutActivity.this, "Coord:Dismissed", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onShown(PermissionSnackBar transientBottomBar) {
                        Toast.makeText(CoordLayoutActivity.this, "Coord:Shown", Toast.LENGTH_SHORT).show();
                    }
                }).show();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
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
        if (requestCode == NEAR_PERMISSION_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                Toast.makeText(this, "Result OK", Toast.LENGTH_SHORT).show();
                NearItManager.getInstance().startRadar();
            } else Toast.makeText(this, "Result KO", Toast.LENGTH_SHORT).show();
        }
    }
}
