package com.nearit.ui_bindings.feedback.views;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatRatingBar;
import android.util.AttributeSet;

/**
 * Created by Federico Boschini on 14/09/17.
 */

public class NearItRatingBar extends AppCompatRatingBar {

    private int fullIcon;
    private int emptyIcon;

    public NearItRatingBar(Context context) {
        super(context);
    }

    public NearItRatingBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NearItRatingBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setProgressDrawable(Drawable d) {
        super.setProgressDrawable(d);
    }

    @Override
    public void setStepSize(float stepSize) {
        super.setStepSize(stepSize);
    }

    @Override
    public void setActivated(boolean activated) {
        super.setActivated(activated);
    }

    @Override
    public void setOnRatingBarChangeListener(OnRatingBarChangeListener listener) {
        super.setOnRatingBarChangeListener(listener);
    }

    @Override
    public void setNumStars(int numStars) {
        super.setNumStars(numStars);
    }

    @Override
    public int getNumStars() {
        return super.getNumStars();
    }
}
