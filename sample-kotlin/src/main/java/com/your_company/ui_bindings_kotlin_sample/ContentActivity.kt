package com.your_company.ui_bindings_kotlin_sample

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.nearit.ui_bindings.NearITUIBindings
import com.your_company.ui_bindings_kotlin_sample.factories.ContentFactory
import it.near.sdk.trackings.TrackingInfo
import kotlinx.android.synthetic.main.activity_content.*

/**
* @author Federico Boschini
*/

class ContentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_content)
        val trackingInfo = TrackingInfo.fromRecipeId("my_fake_recipe_id")

        content.setOnClickListener {
            //  In a real scenario the content is provided by the NearIT SDK
            val content = ContentFactory.getCompleteContent()
            startActivity(
                    //  Basic example with a complete content
                    NearITUIBindings.getInstance(this@ContentActivity)
                            .contentIntentBuilder(content, trackingInfo)
                            .openLinksInWebView()
                            .build())
        }

        no_image.setOnClickListener {
            //  In a real scenario the content is provided by the NearIT SDK
            val content = ContentFactory.getNoImageContent()
            startActivity(
                    //  Example with a content without image
                    //  + enable tapping outside the dialog to close it
                    NearITUIBindings.getInstance(this@ContentActivity)
                            .contentIntentBuilder(content, trackingInfo)
                            .enableTapOutsideToClose()
                            .openLinksInWebView()
                            .build())
        }

        no_cta.setOnClickListener {
            //  In a real scenario the content is provided by the NearIT SDK
            val content = ContentFactory.getNoCTAContent()
            startActivity(
                    //  Example with a content without image
                    NearITUIBindings.getInstance(this@ContentActivity)
                            .contentIntentBuilder(content, trackingInfo)
                            .build())
        }

        plain_content_activity.setOnClickListener {
            //  start a plain activity that shows the fragment fullscreen
            //  please check it
            startActivity(Intent(this@ContentActivity, ContentPlainActivity::class.java))
        }
    }

}