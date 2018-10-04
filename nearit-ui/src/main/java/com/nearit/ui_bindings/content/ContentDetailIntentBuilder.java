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
    private boolean mOpenLinksInWebView = false;
    private final Content mContent;
    @Nullable
    private final TrackingInfo mTrackingInfo;

    private final boolean mSingleInstance;

    public ContentDetailIntentBuilder(Context context, Content content, @Nullable TrackingInfo trackingInfo, boolean singleInstance) {
        mContext = context;
        mContent = content;
        mTrackingInfo = trackingInfo;
        mSingleInstance = singleInstance;
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

    public ContentDetailIntentBuilder openLinksInWebView() {
        mOpenLinksInWebView = true;
        return this;
    }

    public Intent build() {
        if (mSingleInstance) return NearItContentDetailActivitySingleInstance.createIntent(mContext, mTrackingInfo, mContent, getParams());
        else return NearItContentDetailActivity.createIntent(mContext, mTrackingInfo, mContent, getParams());
    }

    private ContentDetailExtraParams getParams() {
        return new ContentDetailExtraParams(
                mEnableTapOutsideToClose,
                mOpenLinksInWebView);
    }

}
