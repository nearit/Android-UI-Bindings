package com.nearit.ui_bindings_sample;

import it.near.sdk.reactions.feedbackplugin.model.Feedback;

/**
 * Created by Federico Boschini on 18/09/17.
 */

class FeedbackFactory {

    private Feedback feedback = new Feedback();

    FeedbackFactory() {
        feedback.question = "This is an example question: how do you rate your recent experience?";
        feedback.setId("test_feedback_id_will_not_work");
        feedback.setRecipeId("test_recipe_id_will_not_work");
    }

    Feedback getFeedback() {
        return feedback;
    }

}
