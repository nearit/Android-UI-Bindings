package com.nearit.ui_bindings;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;

import com.nearit.ui_bindings.permissions.PermissionsRequestIntentExtras;

/**
 * Created by Federico Boschini on 28/08/17.
 */

public class PermissionsActivity extends AppCompatActivity {

    private PermissionsRequestIntentExtras params;
    private boolean isEnableTapToClose = false;
    private boolean isInvisibleLayoutMode = false;
    private boolean isAutoStartRadar = false;
    private boolean isNoBLE = false;
    private boolean isNonBlockingBLE = false;

    private Button bleButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH, LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH);

        Intent intent = getIntent();

        if (intent.hasExtra(ExtraConstants.EXTRA_FLOW_PARAMS)) {
            params = PermissionsRequestIntentExtras.fromIntent(intent);
            isEnableTapToClose = params.isEnableTapToClose();
            isInvisibleLayoutMode = params.isInvisibleLayoutMode();
            isAutoStartRadar = params.isAutoStartRadar();
            isNoBLE = params.isNoBLE();
            isNonBlockingBLE = params.isNonBlockingBLE();
        }

        if(!isInvisibleLayoutMode){
            setContentView(R.layout.activity_nearui_permissions);
        }


        bleButton = (Button) findViewById(R.id.ble_button);
    }

    public static Intent createIntent(Context context, PermissionsRequestIntentExtras params) {
        return new Intent(context, PermissionsActivity.class).putExtra(ExtraConstants.EXTRA_FLOW_PARAMS, params);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        if(isNoBLE) {
            bleButton.setVisibility(View.GONE);
        } else {
            bleButton.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (MotionEvent.ACTION_OUTSIDE == event.getAction()) {
            if (isEnableTapToClose) {
                finish();
            }
            return true;
        }
        return super.onTouchEvent(event);
    }
}
