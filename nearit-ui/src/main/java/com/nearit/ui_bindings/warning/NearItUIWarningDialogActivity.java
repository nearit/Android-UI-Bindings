package com.nearit.ui_bindings.warning;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.nearit.ui_bindings.R;
import com.nearit.ui_bindings.warning.views.NearItUIRetryButton;

/**
 * @author Federico Boschini
 */

public class NearItUIWarningDialogActivity extends AppCompatActivity {

    private static final int NEAR_RETRY_CODE = 1111;
    private static final int NEAR_CLOSE_CODE = 2222;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nearit_ui_layout_network_warning);
        NearItUIRetryButton retryButton = findViewById(R.id.retry_button);
        retryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(NEAR_RETRY_CODE);
                finish();
            }
        });

        TextView closeButton = findViewById(R.id.close_dialog_text);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(NEAR_CLOSE_CODE);
                finish();
            }
        });

    }

    public static Intent createIntent(@Nullable Context context) {
        Intent intent = null;
        if (context != null) {
            intent = new Intent(context, NearItUIWarningDialogActivity.class);
        }
        return intent;
    }

}
