package com.nearit.ui_bindings_kotlin_sample

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.nearit.ui_bindings.NearITUIBindings
import it.near.sdk.reactions.contentplugin.model.Content
import it.near.sdk.reactions.couponplugin.model.Coupon
import it.near.sdk.reactions.customjsonplugin.model.CustomJSON
import it.near.sdk.reactions.feedbackplugin.model.Feedback
import it.near.sdk.reactions.simplenotificationplugin.model.SimpleNotification
import it.near.sdk.trackings.TrackingInfo
import it.near.sdk.utils.CoreContentsListener
import it.near.sdk.utils.NearUtils
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.debug
import org.jetbrains.anko.startActivity


/**
 * Created by Federico Boschini on 12/10/17.
 */
class MainActivity : AppCompatActivity(), CoreContentsListener, AnkoLogger {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        permissions_demo.setOnClickListener {
            startActivity<PermissionsActivity>()
        }

        coupon_demo.setOnClickListener {
            startActivity<CouponsActivity>()
        }

        feedback_demo.setOnClickListener {
            startActivity<FeedbackActivity>()
        }

        content_demo.setOnClickListener {
            startActivity<ContentActivity>()
        }

    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        onNewIntent(intent)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        intent?.let { i ->
            i.extras?.let {
                if (NearUtils.carriesNearItContent(i)) {
                    NearUtils.parseCoreContents(i, this)
                }
            }
        }
    }

    //  The following methods will manage real NearIT notification

    override fun gotContentNotification(content: Content, trackingInfo: TrackingInfo) {
        debug("content notification received")
        startActivity(NearITUIBindings.getInstance(this).createContentDetailIntentBuilder(content).build())
    }

    override fun gotCouponNotification(coupon: Coupon, trackingInfo: TrackingInfo) {
        debug("coupon notification received")
        startActivity(NearITUIBindings.getInstance(this).createCouponDetailIntentBuilder(coupon).build())
    }

    override fun gotSimpleNotification(simpleNotification: SimpleNotification, trackingInfo: TrackingInfo) {
        debug("simple notification received")
    }

    override fun gotCustomJSONNotification(customJSON: CustomJSON, trackingInfo: TrackingInfo) {
        debug("customJson notification received")
    }

    override fun gotFeedbackNotification(feedback: Feedback, trackingInfo: TrackingInfo) {
        debug("feedback notification received")
        startActivity(NearITUIBindings.getInstance(this).createFeedbackIntentBuilder(feedback).build())
    }
}