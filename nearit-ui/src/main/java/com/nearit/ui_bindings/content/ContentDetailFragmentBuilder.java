package com.nearit.ui_bindings.content;

import android.support.annotation.Nullable;

import it.near.sdk.reactions.contentplugin.model.Content;
import it.near.sdk.trackings.TrackingInfo;

/**
 * @author Federico Boschini
 */

public class ContentDetailFragmentBuilder {
    private final Content mContent;
    @Nullable
    private final TrackingInfo mTrackingInfo;

    public ContentDetailFragmentBuilder(Content mContent, @Nullable TrackingInfo mTrackingInfo) {
        this.mContent = mContent;
        this.mTrackingInfo = mTrackingInfo;
    }

    public NearItContentDetailFragment build() {
        return NearItContentDetailFragment.newInstance(mContent, mTrackingInfo, getParams());
    }

    private ContentDetailExtraParams getParams() {
        return new ContentDetailExtraParams();
    }

}
