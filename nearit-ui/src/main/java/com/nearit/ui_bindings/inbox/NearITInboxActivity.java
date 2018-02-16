package com.nearit.ui_bindings.inbox;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.nearit.ui_bindings.ExtraConstants;
import com.nearit.ui_bindings.R;

import it.near.sdk.NearItManager;

public class NearITInboxActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.nearit_ui_activity_inbox);

        InboxListExtraParams extras = null;
        Intent intent = getIntent();
        if (intent != null &&
                intent.hasExtra(ExtraConstants.EXTRA_FLOW_PARAMS)) {
            extras = intent.getParcelableExtra(ExtraConstants.EXTRA_FLOW_PARAMS);
        }

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.inbox_fragment_container,
                        NearITInboxFragment.newInstance(extras, NearItManager.getInstance()))
                .commit();
    }

    @NonNull
    public static Intent createIntent(Context context, InboxListExtraParams extras) {
        return new Intent(context, NearITInboxActivity.class)
                .putExtra(ExtraConstants.EXTRA_FLOW_PARAMS, extras);
    }
}
