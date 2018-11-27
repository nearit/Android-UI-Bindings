package com.nearit.ui_bindings.content;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.nearit.ui_bindings.NearItLaunchMode;

import it.near.sdk.reactions.contentplugin.model.Content;
import it.near.sdk.trackings.TrackingInfo;

/**
 * @author Federico Boschini
 */

public class ContentDetailIntentBuilder {
    private final Context mContext;
    private boolean mEnableTapOutsideToClose = false;
    public static boolean openLinksInWebView = false;
    private final Content mContent;
    @Nullable
    private final TrackingInfo mTrackingInfo;

    private final NearItLaunchMode mLaunchMode;
    private int mFlags;

    public ContentDetailIntentBuilder(Context context, Content content, @Nullable TrackingInfo trackingInfo, NearItLaunchMode launchMode) {
        mContext = context;
        mContent = content;
        mTrackingInfo = trackingInfo;
        mLaunchMode = launchMode;
    }

    public ContentDetailIntentBuilder(Context context, Content content, @Nullable TrackingInfo trackingInfo, NearItLaunchMode launchMode, int flags) {
        this(context, content, trackingInfo, launchMode);
        mFlags = flags;
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
        openLinksInWebView = true;
        return this;
    }

    public Intent build() {
        Intent intent;
        switch (mLaunchMode) {
            case SINGLE_INSTANCE:
                intent = NearItContentDetailActivitySingleInstance.createIntent(mContext, mTrackingInfo, mContent, getParams());
                break;
            case SINGLE_TOP:
                intent = NearItContentDetailActivitySingleTop.createIntent(mContext, mTrackingInfo, mContent, getParams());
                break;
            case SINGLE_TASK:
                intent = NearItContentDetailActivitySingleTask.createIntent(mContext, mTrackingInfo, mContent, getParams());
                break;
            default:
            case STANDARD:
                intent = NearItContentDetailActivity.createIntent(mContext, mTrackingInfo, mContent, getParams());
        }

        intent.addFlags(mFlags);
        return intent;
    }

    private ContentDetailExtraParams getParams() {
        return new ContentDetailExtraParams(
                openLinksInWebView,
                mEnableTapOutsideToClose);
    }

}
