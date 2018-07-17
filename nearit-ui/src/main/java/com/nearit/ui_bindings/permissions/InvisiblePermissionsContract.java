package com.nearit.ui_bindings.permissions;

import android.content.Intent;
import android.support.annotation.NonNull;

import com.nearit.ui_bindings.base.BasePresenter;
import com.nearit.ui_bindings.base.BaseView;

/**
 * @author Federico Boschini
 */
public interface InvisiblePermissionsContract {

    interface InvisiblePermissionsView extends BaseView<InvisiblePermissionsPresenter> {
        void showAirplaneDialog();
        void showDontAskAgainDialog();
        void showNotificationsDialog();

        void finishWithOkResult();
        void finishWithKoResult();

        void requestLocationPermission();
        void turnOnLocationServices(boolean needBle);
        void turnOnBluetooth();

        boolean shouldShowRequestPermissionRationale();
    }

    interface InvisiblePermissionsPresenter extends BasePresenter {
        void onDialogClosed();
        void onLocationServicesOn();

        void handlePermissionResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults);
        void handleActivityResult(int requestCode, int resultCode, Intent data);
    }

}
