package com.your_company.ui_bindings_sample.factories;

import it.near.sdk.reactions.feedbackplugin.model.Feedback;

/**
 * @author Federico Boschini
 */

public class FeedbackFactory {

    private Feedback feedback = new Feedback();

    public FeedbackFactory() {
        feedback.question = "This is an example question: how do you rate your recent experience?";
        feedback.setId("test_feedback_id_will_not_work");
        feedback.setRecipeId("test_recipe_id_will_not_work");
    }

    public Feedback getFeedback() {
        return feedback;
    }

}
