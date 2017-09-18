package com.nearit.ui_bindings_sample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

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

    }

}