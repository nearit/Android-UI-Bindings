package com.nearit.ui_bindings.headsup;

import android.content.Context;

import it.near.sdk.geopolis.beacons.ranging.ProximityListener;

/**
 * Created by Federico Boschini on 06/09/17.
 */

public class NearItUIProximityListenerBuilder {
    private Context mContext;
    private int mCustomIcon;

    public NearItUIProximityListenerBuilder(Context context) {
        mContext = context;
    }

    public NearItUIProximityListenerBuilder setCustomIconResId(int customIconResId) {
        mCustomIcon = customIconResId;
        return this;
    }

    public ProximityListener build() {
        return new NearItUIProximityListener(mContext, getParams());
    }

    private NotificationsExtraParams getParams() {
        return new NotificationsExtraParams(
                mCustomIcon
        );
    }

}
