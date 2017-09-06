package com.nearit.ui_bindings_sample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.nearit.ui_bindings.NearITUIBindings;

public class CouponsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupons);

        Button valid = (Button) findViewById(R.id.valid_coupon);
        valid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(
                        NearITUIBindings.getInstance(getApplicationContext())
                                .createCouponDetailIntentBuilder()
                                .build());
            }
        });
    }
}
