package com.nearit.ui_bindings.content;

import android.content.Context;
import android.content.Intent;

import it.near.sdk.reactions.contentplugin.model.Content;

/**
 * @author Federico Boschini
 */

public class ContentDetailIntentBuilder {
    private Context mContext;
    private boolean mEnableTapOutsideToClose = false;
    private Content mContent;

    public ContentDetailIntentBuilder(Context context, Content content) {
        mContext = context;
        mContent = content;
    }

    /**
     * Enables or disables tap outside the dialog to close
     * <p>
     * <p> default is false
     */
    public ContentDetailIntentBuilder enableTapOutsideToClose() {
        mEnableTapOutsideToClose = true;
        return this;
    }

    public Intent build() {
        return NearItContentDetailActivity.createIntent(mContext, mContent, getParams());
    }

    private ContentDetailExtraParams getParams() {
        return new ContentDetailExtraParams(
                mEnableTapOutsideToClose);
    }

}
