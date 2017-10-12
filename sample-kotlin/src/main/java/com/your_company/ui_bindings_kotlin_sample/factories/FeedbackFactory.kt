package com.your_company.ui_bindings_kotlin_sample.factories

import it.near.sdk.reactions.feedbackplugin.model.Feedback

/**
 * Created by Federico Boschini on 12/10/17.
 */
object FeedbackFactory {

    private val feedback = Feedback()

    init {
        feedback.question = "This is an example question: how do you rate your recent experience?"
        feedback.id = "test_feedback_id_will_not_work"
        feedback.recipeId = "test_recipe_id_will_not_work"
    }

    fun getFeedback(): Feedback = feedback

}