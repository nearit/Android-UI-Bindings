package com.your_company.ui_bindings_kotlin_sample

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.nearit.ui_bindings.NearITUIBindings
import com.your_company.ui_bindings_kotlin_sample.factories.FeedbackFactory

/**
* @author Federico Boschini
*/

class FeedbackPlainActivity : AppCompatActivity() {

    private var feedbackFragment: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plain_feedback)

        //  In a real scenario the feedback request is provided by the NearIT SDK
        val feedback = FeedbackFactory.getFeedback()

        feedbackFragment = savedInstanceState?.let {
            //  Restore the fragment instance
            supportFragmentManager.getFragment(savedInstanceState, "feedbackFragment")
        } ?:    //  Create a new fragment instance
                NearITUIBindings.getInstance(this@FeedbackPlainActivity)
                .createFeedbackFragmentBuilder(feedback)
                .build()


        supportFragmentManager
                .beginTransaction()
                .replace(R.id.feedback_container, feedbackFragment)
                .commit()

    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        //  Save the fragment instance
        supportFragmentManager.putFragment(outState, "feedbackFragment", feedbackFragment)
    }

}