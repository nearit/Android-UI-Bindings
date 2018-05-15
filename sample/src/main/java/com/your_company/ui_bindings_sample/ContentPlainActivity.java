package com.your_company.ui_bindings_sample;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.nearit.ui_bindings.NearITUIBindings;
import com.your_company.ui_bindings_sample.factories.ContentFactory;

import it.near.sdk.reactions.contentplugin.model.Content;
import it.near.sdk.trackings.TrackingInfo;

/**
 * @author Federico Boschini
 */

public class ContentPlainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plain_content);

        ContentFactory contentFactory = new ContentFactory();

        //  In a real scenario the content is provided by the NearIT SDK
        Content content = contentFactory.getCompleteContent();
        TrackingInfo trackingInfo = TrackingInfo.fromRecipeId("my_fake_recipe");

        Fragment contentFragment = NearITUIBindings.getInstance(this)
                .contentFragmentBuilder(content, trackingInfo)
                .build();

        getSupportFragmentManager().beginTransaction().replace(R.id.content_container, contentFragment).commit();
    }
}
