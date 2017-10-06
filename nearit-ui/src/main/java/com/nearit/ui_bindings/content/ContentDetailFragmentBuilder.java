package com.nearit.ui_bindings.content;

import android.content.Context;

import it.near.sdk.reactions.contentplugin.model.Content;

/**
 * Created by Federico Boschini on 06/09/17.
 */

public class ContentDetailFragmentBuilder {
    private Context mContext;
    private Content mContent;

    public ContentDetailFragmentBuilder(Context context, Content content) {
        mContext = context;
        mContent = content;
    }

    public NearItContentDetailFragment build() {
        return NearItContentDetailFragment.newInstance(mContent, getParams());
    }

    private ContentDetailExtraParams getParams() {
        return new ContentDetailExtraParams();
    }

}
