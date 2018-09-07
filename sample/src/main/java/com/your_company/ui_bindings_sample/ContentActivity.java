package com.your_company.ui_bindings_sample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.nearit.ui_bindings.NearITUIBindings;
import com.your_company.ui_bindings_sample.factories.ContentFactory;

import it.near.sdk.reactions.contentplugin.model.Content;
import it.near.sdk.trackings.TrackingInfo;

/**
 * @author Federico Boschini
 */

public class ContentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);

        final ContentFactory contentFactory = new ContentFactory();
        final TrackingInfo trackingInfo = TrackingInfo.fromRecipeId("my_fake_recipe_id");

        Button complete = findViewById(R.id.content);
        complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  In a real scenario the content is provided by the NearIT SDK
                Content content = contentFactory.getCompleteContent();
                startActivity(
                        //  Basic example with a complete content
                        NearITUIBindings.getInstance(getApplicationContext())
                                .contentIntentBuilder(content, trackingInfo)
                                .openLinksInWebView()
                                .build());
            }
        });

        Button noImage = findViewById(R.id.no_image);
        noImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  In a real scenario the content is provided by the NearIT SDK
                Content content = contentFactory.getNoImageContent();
                startActivity(
                        //  Example with a content without image
                        //  + enable tapping outside the dialog to close it
                        NearITUIBindings.getInstance(getApplicationContext())
                                .contentIntentBuilder(content, trackingInfo)
                                .enableTapOutsideToClose()
                                .openLinksInWebView()
                                .build());
            }
        });

        Button noCta = findViewById(R.id.no_cta);
        noCta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  In a real scenario the content is provided by the NearIT SDK
                Content content = contentFactory.getNoCTAContent();
                startActivity(
                        //  Example with a content without image
                        NearITUIBindings.getInstance(getApplicationContext())
                                .contentIntentBuilder(content, trackingInfo)
                                .build());
            }
        });

        Button plainActivity = findViewById(R.id.plain_content_activity);
        plainActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  start a plain activity that shows the fragment fullscreen
                //  please check it
                startActivity(new Intent(ContentActivity.this, ContentPlainActivity.class));
            }
        });
    }
}
