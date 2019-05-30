package com.nearit.ui_bindings.permissions;

import android.support.annotation.NonNull;

import com.nearit.ui_bindings.base.BasePresenter;
import com.nearit.ui_bindings.base.BaseView;

/**
 * @author Federico Boschini
 */
interface PermissionsContract {

    interface PermissionsView extends BaseView<PermissionsPresenter> {

        void refreshCloseText();

        void hideHeader();
        void setHeader(int resId);

        void setBluetoothIcon(int resId);
        void setLocationIcon(int resId);
        void setNotificationsIcon(int resId);
        void setSadFaceIcon(int resId);
        void setWorriedFaceIcon(int resId);
        void setHappyIcon(int resId);

        void hideBluetoothButton();
        void setBluetoothButtonHappy();
        void setBluetoothButtonSad();
        void resetBluetoothButton();

        void setLocationButtonHappy();
        void setLocationButtonWorriedServices();
        void setLocationButtonWorriedWhenInUse();
        void setLocationButtonSad();
        void resetLocationButton();

        void showNotificationsButton();
        void hideNotificationsButton();
        void setNotificationsButtonHappy();
        void setNotificationsButtonSad();
        void resetNotificationsButton();

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

        void onDialogCanceled();

        void checkPermissions();

        void onLocationTapped();
        void onBluetoothTapped();
        void onNotificationsTapped();

        void finalCheck();

        void onLocationServicesOn();

        void handleLocationPermissionGranted();
        void handleLocationPermissionDenied();

        void handleActivityResult(int requestCode, int resultCode);
    }
}
