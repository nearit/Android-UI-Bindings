package com.your_company.ui_bindings_kotlin_sample

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.nearit.ui_bindings.NearITUIBindings
import com.your_company.ui_bindings_kotlin_sample.factories.FeedbackFactory
import kotlinx.android.synthetic.main.activity_feedback.*

/**
* @author Federico Boschini
*/

class FeedbackActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feedback)

        complete.setOnClickListener {
            //  In a real scenario the question is provided by the NearIT SDK
            val feedback = FeedbackFactory.getFeedback()
            startActivity(
                    NearITUIBindings.getInstance(this@FeedbackActivity)
                            .feedbackIntentBuilder(feedback)
                            .build()
            )
        }

        no_text_box.setOnClickListener {
            //  In a real scenario the question is provided by the NearIT SDK
            val feedback = FeedbackFactory.getFeedback()
            startActivity(
                    //  The text response is disabled
                    //  + tap outside to close is enabled
                    NearITUIBindings.getInstance(this@FeedbackActivity)
                            .feedbackIntentBuilder(feedback)
                            .withoutComment()
                            .enableTapOutsideToClose()
                            .build()
            )
        }

        feedback_in_plain_activity.setOnClickListener {
            startActivity(Intent(this@FeedbackActivity, FeedbackPlainActivity::class.java))
        }

    }

}