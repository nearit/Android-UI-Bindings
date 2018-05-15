package com.nearit.ui_bindings.content;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

import it.near.sdk.reactions.contentplugin.model.Content;
import it.near.sdk.trackings.TrackingInfo;

/**
 * @author Federico Boschini
 */

public class ContentDetailIntentBuilder {
    private final Context mContext;
    private boolean mEnableTapOutsideToClose = false;
    private final Content mContent;
    @Nullable
    private final TrackingInfo mTrackingInfo;

    public ContentDetailIntentBuilder(Context context, Content content,@Nullable TrackingInfo trackingInfo) {
        mContext = context;
        mContent = content;
        mTrackingInfo = trackingInfo;
    }

    public ContentDetailIntentBuilder(Context context, Content content) {
        this(context, content, null);
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
        return NearItContentDetailActivity.createIntent(mContext, mTrackingInfo, mContent, getParams());
    }

    private ContentDetailExtraParams getParams() {
        return new ContentDetailExtraParams(
                mEnableTapOutsideToClose);
    }

}
