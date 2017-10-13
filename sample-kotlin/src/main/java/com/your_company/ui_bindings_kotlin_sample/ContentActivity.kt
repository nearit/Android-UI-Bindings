package com.your_company.ui_bindings_kotlin_sample

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import com.nearit.ui_bindings.NearITUIBindings
import com.your_company.ui_bindings_kotlin_sample.factories.ContentFactory
import kotlinx.android.synthetic.main.activity_content.*

/**
 * Created by Federico Boschini on 12/10/17.
 */
class ContentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_content)

        content.setOnClickListener {
            //  In a real scenario the content is provided by the NearIT SDK
            val content = ContentFactory.getCompleteContent()
            startActivity(
                    //  Basic example with a complete content
                    NearITUIBindings.getInstance(applicationContext)
                            .createContentDetailIntentBuilder(content)
                            .build())
        }

        no_image.setOnClickListener {
            //  In a real scenario the content is provided by the NearIT SDK
            val content = ContentFactory.getNoImageContent()
            startActivity(
                    //  Example with a content without image
                    //  + enable tapping outside the dialog to close it
                    NearITUIBindings.getInstance(applicationContext)
                            .createContentDetailIntentBuilder(content)
                            .enableTapOutsideToClose()
                            .build())
        }

        no_cta.setOnClickListener {
            //  In a real scenario the content is provided by the NearIT SDK
            val content = ContentFactory.getNoCTAContent()
            startActivity(
                    //  Example with a content without image
                    NearITUIBindings.getInstance(applicationContext)
                            .createContentDetailIntentBuilder(content)
                            .build())
        }

        val plainActivity = findViewById<View>(R.id.plain_content_activity) as Button
        plainActivity.setOnClickListener {
            //  start a plain activity that shows the fragment fullscreen
            //  please check it
            startActivity(Intent(this@ContentActivity, ContentPlainActivity::class.java))
        }
    }

}