package com.nearit.ui_bindings.inbox;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import it.near.sdk.reactions.customjsonplugin.model.CustomJSON;
import it.near.sdk.trackings.TrackingInfo;

/**
 * @author Federico Boschini
 */
public interface HistoryCustomJsonListener {
    void onCustomJsonClicked(@NonNull CustomJSON customJSON, @Nullable TrackingInfo trackingInfo);
}
