package com.nearit.ui_bindings_sample;

import java.util.ArrayList;

import it.near.sdk.reactions.couponplugin.model.Claim;
import it.near.sdk.reactions.couponplugin.model.Coupon;
import it.near.sdk.reactions.feedbackplugin.model.Feedback;

/**
 * Created by Federico Boschini on 18/09/17.
 */

class FeedbackFactory {

    private Feedback feedback = new Feedback();

    FeedbackFactory() {
        feedback.question = "Cosa ne pensi?";
        feedback.setId("test_feedback_id_will_not_work");
        feedback.setRecipeId("test_recipe_id_will_not_work");
    }

    Feedback getFeedback() {
        return feedback;
    }

}
