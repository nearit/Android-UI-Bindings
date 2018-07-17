package com.nearit.ui_bindings.permissions;

import android.content.Intent;
import android.support.annotation.NonNull;

import com.nearit.ui_bindings.base.BasePresenter;
import com.nearit.ui_bindings.base.BaseView;

/**
 * @author Federico Boschini
 */
interface PermissionsContract {

    interface PermissionsView extends BaseView<PermissionsPresenter> {

        void recreate();

        void hideHeader();
        void setHeader(int resId);

        void hideBluetoothButton();
        void setBluetoothButtonChecked();
        void setBluetoothButtonUnchecked();
        void setLocationButtonChecked();
        void setLocationButtonUnchecked();
        void setNotificationsButtonChecked();
        void setNotificationsButtonUnchecked();

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

    interface PermissionsPresenter extends BasePresenter {

        void checkPermissions();

        void onLocationTapped();
        void onBluetoothTapped();
        void onNotificationsTapped();

        void finalCheck();

        void onLocationServicesOn();
        void handlePermissionResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults);
        void handleActivityResult(int requestCode, int resultCode, Intent data);
    }
}
