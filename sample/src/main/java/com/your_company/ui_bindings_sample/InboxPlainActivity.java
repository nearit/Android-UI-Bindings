package com.your_company.ui_bindings_sample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.nearit.ui_bindings.NearITUIBindings;

/**
 * @author Federico Boschini
 */

public class InboxPlainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plain_inbox);

        Fragment inboxFragment = NearITUIBindings.getInstance(this)
                .createInboxListFragmentBuilder()
                .build();

        getSupportFragmentManager().beginTransaction().replace(R.id.inbox_fragment_container, inboxFragment).commit();
    }

}
