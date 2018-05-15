package com.nearit.ui_bindings.content;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

import it.near.sdk.reactions.contentplugin.model.Content;
import it.near.sdk.trackings.TrackingInfo;

/**
 * @author Federico Boschini
 */

public class NearItContentDetailActivity extends BaseContentDetailActivity {

    public static Intent createIntent(Context context, @Nullable TrackingInfo trackingInfo, Content content, ContentDetailExtraParams params) {
        Intent intent = new Intent(context, NearItContentDetailActivity.class);
        return BaseContentDetailActivity.addExtras(intent, content, trackingInfo, params);
    }
}
