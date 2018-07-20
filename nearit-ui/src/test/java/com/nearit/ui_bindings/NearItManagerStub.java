package com.nearit.ui_bindings;

import android.content.Context;

import it.near.sdk.NearItManager;

/**
 * @author Federico Boschini
 */
public class NearItManagerStub extends NearItManager {

    public NearItManagerStub(Context context) {
        super(context);
    }

    @Override
    public void startRadar() {
        super.startRadar();
    }
}