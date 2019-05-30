package com.nearit.ui_bindings.permissions.invisible;

import com.nearit.ui_bindings.base.BasePresenter;
import com.nearit.ui_bindings.base.BaseView;

/**
 * @author Federico Boschini
 */
public interface InvisiblePermissionsContract {

    interface InvisiblePermissionsView extends BaseView<InvisiblePermissionsPresenter> {
        void recreate();

        void showAirplaneDialog();
        void showDontAskAgainDialog();
        void showNotificationsDialog();

        void finishWithOkResult();
        void finishWithKoResult();

        void requestLocationPermission();
        void requestLocationServices(boolean needBle);
        void requestBluetooth();

        boolean shouldShowRequestPermissionRationale();
    }

    interface InvisiblePermissionsPresenter extends BasePresenter {
        void onDialogClosed();
        void onLocationServicesOn();

        void handleLocationPermissionGranted();
        void handleLocationPermissionDenied();

        void handleLocationServicesDenied();
        void handleBluetoothGranted();
        void handleBluetoothDenied();
    }

}
