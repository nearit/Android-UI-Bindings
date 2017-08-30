package com.nearit.ui_bindings_sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.nearit.ui_bindings.NearITUIBindings;

public class MainActivity extends AppCompatActivity {

    private static final int NEAR_PERMISSION_REQUEST = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button permissions = (Button) findViewById(R.id.permissions);

        permissions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(
                        NearITUIBindings.getInstance(getApplicationContext())
                                .createPermissionRequestIntentBuilder()
//                                .setEnableTapOutsideToClose(true)
//                                .setNoBLE(true)
                                .setInvisibleLayoutMode(true)
                                .build(),
                        NEAR_PERMISSION_REQUEST);
            }
        });
    }
}
