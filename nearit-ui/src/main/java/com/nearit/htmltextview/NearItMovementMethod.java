package com.nearit.htmltextview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.text.Spannable;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.URLSpan;
import android.util.Patterns;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.TextView;

/**
 * @author Federico Boschini
 */
public class NearItMovementMethod extends LinkMovementMethod {

    @Nullable
    private OnMovementLinkClickListener listener;
    @Nullable
    private GestureDetector gestureDetector;
    private HtmlTextView textView;
    private Spannable spannable;

    public enum LinkType {
        PHONE, WEB_URL, EMAIL_ADDRESS, NONE
    }

    public NearItMovementMethod(@Nullable OnMovementLinkClickListener listener, Context context) {
        this.listener = listener;
        this.gestureDetector = new GestureDetector(context, new SimpleOnGestureListener());
    }

    @Override
    public boolean onTouchEvent(TextView textView, Spannable spannable, MotionEvent event) {
        this.textView = (HtmlTextView) textView;
        this.spannable = spannable;
        if (gestureDetector != null) {
            gestureDetector.onTouchEvent(event);
        }
        return false;
    }

    public interface OnMovementLinkClickListener {
        void onLinkClicked(String linkText, LinkType linkType);
    }

    private class SimpleOnGestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {

            String linkText = getLinkText(textView, spannable, e);
            LinkType linkType = LinkType.NONE;

            if (Patterns.PHONE.matcher(linkText).matches()) {
                linkType = LinkType.PHONE;
            } else if (Patterns.WEB_URL.matcher(linkText).matches()) {
                linkType = LinkType.WEB_URL;
            } else if (Patterns.EMAIL_ADDRESS.matcher(linkText).matches()) {
                linkType = LinkType.EMAIL_ADDRESS;
            }

            if (listener != null) {
                listener.onLinkClicked(linkText, linkType);
            }

            return false;
        }

        private String getLinkText(HtmlTextView textView, Spannable spannable, MotionEvent event) {
            int x = Math.round(event.getX());
            int y = Math.round(event.getY());

            x -= textView.getTotalPaddingLeft();
            y -= textView.getTotalPaddingTop();

            x += textView.getScrollX();
            y += textView.getScrollY();

            Layout layout = textView.getLayout();
            int line = layout.getLineForVertical(y);
            int offset = layout.getOffsetForHorizontal(line, x);

            ClickableSpan[] link = spannable.getSpans(offset, offset, ClickableSpan.class);

            if (link.length == 0) {
                return "";
            } else {
                return ((URLSpan) link[0]).getURL();
            }
        }
    }

}
