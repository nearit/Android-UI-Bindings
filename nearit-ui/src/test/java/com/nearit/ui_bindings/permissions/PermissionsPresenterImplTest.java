package com.nearit.ui_bindings.permissions;

import android.content.Intent;
import android.content.pm.PackageManager;

import com.nearit.ui_bindings.utils.SpManager;
import com.nearit.ui_bindings.utils.VersionManager;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static android.app.Activity.RESULT_OK;
import static com.nearit.ui_bindings.permissions.PermissionsPresenterImpl.NEAR_BLUETOOTH_SETTINGS_CODE;
import static com.nearit.ui_bindings.permissions.PermissionsPresenterImpl.NEAR_LOCATION_SETTINGS_CODE;
import static com.nearit.ui_bindings.permissions.PermissionsPresenterImpl.NEAR_PERMISSION_REQUEST_FINE_LOCATION;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Federico Boschini
 */
public class PermissionsPresenterImplTest {

    private PermissionsContract.PermissionsPresenter presenter;

    private int customHeaderResId = 666;

    @Mock
    private Intent intent;

    @Mock
    private PermissionsContract.PermissionsView view;
    @Mock
    private PermissionsRequestExtraParams params;
    @Mock
    private PermissionsManager permissionsManager;
    @Mock
    private SpManager spManager;
    @Mock
    private VersionManager versionManager;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        presenter = new PermissionsPresenterImpl(view, params, permissionsManager, spManager, versionManager);
    }

    @Test
    public void onCreation_presenterGetsInjectedInVIew() {
        verify(view).injectPresenter(presenter);
    }

    @Test
    public void onStart_ifUIModeAndNoHeader_hideIt() {
        whenNoHeader();

        presenter.start();

        verify(view).hideHeader();
    }

    @Test
    public void onStart_ifUIModeAndCustomHeader_showIt() {
        whenCustomHeader();

        presenter.start();

        verify(view).setHeader(customHeaderResId);
    }

    @Test
    public void onStart_ifNoBeaconAndBleAvailable_hideButton() {
        whenBleIsAvailable();
        whenNoBeacon();

        presenter.start();

        verify(view).hideBluetoothButton();
    }

    @Test
    public void onStart_ifBleNotAvailable_hideButton() {
        whenBleIsNotAvailable();

        presenter.start();

        verify(view).hideBluetoothButton();
    }

    @Test
    public void onPermissionResult_ifGrantedAndFlightModeOff_turnOnLocation() {
        whenFlightModeIsOff();
        String[] permissions = {};
        int[] results = {PackageManager.PERMISSION_GRANTED};

        presenter.handlePermissionResult(NEAR_PERMISSION_REQUEST_FINE_LOCATION, permissions, results);

        verify(view).turnOnLocationServices(anyBoolean());
    }

    @Test
    public void onPermissionResult_ifGrantedAndFlightMode_showFlightDialog() {
        whenFlightModeIsOn();
        String[] permissions = {};
        int[] results = {PackageManager.PERMISSION_GRANTED};

        presenter.handlePermissionResult(NEAR_PERMISSION_REQUEST_FINE_LOCATION, permissions, results);

        verify(view).showAirplaneDialog();
        verify(view, never()).turnOnLocationServices(anyBoolean());
    }

    @Test
    public void onLocationServicesOn_checkPermissionsAndUpdateUI() {
        //  checkPermissions tested in deep next
        whenNotificationsAreOff();

        presenter.onLocationServicesOn();

        verify(view).setNotificationsButtonUnchecked();
    }

    @Test
    public void onActivityResult_ifLocationOnAndGranted_checkPermissionsAndUpdateUI() {
        whenLocationPermissionIsGranted();
        whenLocationIsOn();

        presenter.handleActivityResult(1, 2, intent);

        verify(view).setLocationButtonChecked();
    }

    @Test
    public void onActivityResult_ifLocationOnAndNotGranted_checkPermissionsAndUpdateUI() {
        whenLocationIsOn();
        whenLocationPermissionIsNotGranted();

        presenter.handleActivityResult(1, 2, intent);

        verify(view).setLocationButtonUnchecked();
    }

    @Test
    public void onActivityResult_ifLocationOffAndGranted_checkPermissionsAndUpdateUI() {
        whenLocationIsOff();
        whenLocationPermissionIsGranted();

        presenter.handleActivityResult(1, 2, intent);

        verify(view).setLocationButtonUnchecked();
    }

    @Test
    public void onActivityResult_ifLocationOffAndNotGranted_checkPermissionsAndUpdateUI() {
        whenLocationIsOff();
        whenLocationPermissionIsNotGranted();

        presenter.handleActivityResult(1, 2, intent);

        verify(view).setLocationButtonUnchecked();
    }

    @Test
    public void onActivityResult_ifBluetoothOff_checkPermissionsAndUpdateUI() {
        whenBluetoothIsOff();

        presenter.handleActivityResult(1, 2, intent);

        verify(view).setBluetoothButtonUnchecked();
    }

    @Test
    public void onActivityResult_ifBluetoothOn_checkPermissionsAndUpdateUI() {
        whenBluetoothIsOn();

        presenter.handleActivityResult(1, 2, intent);

        verify(view).setBluetoothButtonChecked();
    }


    @Test
    public void onActivityResult_ifNotificationsOff_checkPermissionsAndUpdateUI() {
        whenNotificationsAreOff();

        presenter.handleActivityResult(1, 2, intent);

        verify(view).setNotificationsButtonUnchecked();
    }

    @Test
    public void onActivityResult_ifNotificationsOn_checkPermissionsAndUpdateUI() {
        whenNotificationsAreOn();

        presenter.handleActivityResult(NEAR_BLUETOOTH_SETTINGS_CODE, RESULT_OK, intent);

        verify(view).setNotificationsButtonChecked();
    }

    @Test
    public void onNotificationsTapped_ifNotificationsOff_showDialog() {
        whenNotificationsAreOff();

        presenter.onNotificationsTapped();

        verify(view).showNotificationsDialog();
    }

    @Test
    public void onNotificationsTapped_ifNotificationsOn_doNotShowDialog() {
        whenNotificationsAreOn();

        presenter.onNotificationsTapped();

        verify(view, never()).showNotificationsDialog();
    }

    @Test
    public void onBluetoothTapped_turnOnBluetooth() {
        presenter.onBluetoothTapped();

        verify(view).turnOnBluetooth();
    }

    @Test
    public void onLocationTapped_ifPreMarshmallow_skipPermissionAndTurnOnLocation() {
        whenSDKPreMarshmallow();

        presenter.onLocationTapped();

        verify(view).turnOnLocationServices(anyBoolean());
    }

    @Test
    public void onLocationTapped_ifPostMarshmallowAndGranted_turnOnLocation() {
        whenSDKAtLeastMarshmallow();
        whenLocationPermissionIsGranted();

        presenter.onLocationTapped();

        verify(view).turnOnLocationServices(anyBoolean());
    }

    @Test
    public void onLocationTapped_ifPostMarshmallowNotGrantedNeverAsked_askItAndRemember() {
        whenSDKAtLeastMarshmallow();
        whenLocationPermissionIsNotGranted();
        whenPermissionNeverAsked();

        presenter.onLocationTapped();

        verify(spManager).setLocationPermissionAsked();
        verify(view).requestLocationPermission();
    }

    @Test
    public void onLocationTapped_ifPostMarshmallowDeniedAndNeverAsk_showDialog() {
        whenSDKAtLeastMarshmallow();
        whenLocationPermissionIsNotGranted();
        whenPermissionAlreadyAsked();
        whenShouldNotAskAgain();

        presenter.onLocationTapped();

        verify(view).showDontAskAgainDialog();
    }

    @Test
    public void onLocationTapped_ifPostMarshmallowDeniedCanAskAgain_askItAndRemember() {
        whenSDKAtLeastMarshmallow();
        whenLocationPermissionIsNotGranted();
        whenPermissionAlreadyAsked();
        whenCanAskAgain();

        presenter.onLocationTapped();

        verify(spManager).setLocationPermissionAsked();
        verify(view).requestLocationPermission();
    }

    @Test
    public void onFinalCheck_ifAllPermissionsOK_finishWithOK() {
        whenLocationPermissionIsGranted();
        whenLocationIsOn();
        whenBluetoothIsOn();
        whenNotificationsAreOn();

        presenter.finalCheck();

        verify(view).finishWithOkResult();
    }

    @Test
    public void onFinalCheck_ifBleNotAvailableAndAllOK_finishWithOK() {
        whenLocationPermissionIsGranted();
        whenLocationIsOn();
        whenBleIsNotAvailable();
        whenNotificationsAreOn();

        presenter.finalCheck();

        verify(view).finishWithOkResult();
    }

    @Test
    public void onFinalCheck_ifNoBeaconAndAllOK_finishWithOK() {
        whenLocationPermissionIsGranted();
        whenLocationIsOn();
        whenNoBeacon();
        whenNotificationsAreOn();

        presenter.finalCheck();

        verify(view).finishWithOkResult();
    }

    @Test
    public void onFinalCheck_ifNonBlockingBeaconAndAllOK_finishWithOK() {
        whenLocationPermissionIsGranted();
        whenLocationIsOn();
        whenNonBlockingBeacon();
        whenNotificationsAreOn();

        presenter.finalCheck();

        verify(view).finishWithOkResult();
    }

    @Test
    public void onFinalCheck_ifLocationNotGranted_finishWithKO() {
        whenLocationPermissionIsNotGranted();
        whenLocationIsOn();
        whenBluetoothIsOn();
        whenNotificationsAreOn();

        presenter.finalCheck();

        verify(view).finishWithKoResult();
    }

    @Test
    public void onFinalCheck_ifLocationOff_finishWithKO() {
        whenLocationPermissionIsGranted();
        whenLocationIsOff();
        whenBluetoothIsOn();
        whenNotificationsAreOn();

        presenter.finalCheck();

        verify(view).finishWithKoResult();
    }

    @Test
    public void onFinalCheck_ifBluetoothIsOffAndRequired_finishWithKO() {
        whenLocationPermissionIsGranted();
        whenLocationIsOn();
        whenBluetoothIsOff();
        whenNotificationsAreOn();

        presenter.finalCheck();

        verify(view).finishWithKoResult();
    }

    @Test
    public void onFinalCheck_ifNotificationsAreOff_finishWithKO() {
        whenLocationPermissionIsGranted();
        whenLocationIsOn();
        whenBluetoothIsOn();
        whenNotificationsAreOff();

        presenter.finalCheck();

        verify(view).finishWithKoResult();
    }


    private void whenNoBeacon() {
        when(params.isNoBeacon()).thenReturn(true);
    }

    private void whenNonBlockingBeacon() {
        when(params.isNonBlockingBeacon()).thenReturn(true);
    }

    private void whenNoHeader() {
        when(params.isNoHeader()).thenReturn(true);
    }

    private void whenCustomHeader() {
        when(params.getHeaderDrawable()).thenReturn(customHeaderResId);
    }

    private void whenBleIsNotAvailable() {
        when(permissionsManager.isBleAvailable()).thenReturn(false);
    }

    private void whenBleIsAvailable() {
        when(permissionsManager.isBleAvailable()).thenReturn(true);
    }

    private void whenBluetoothIsOff() {
        when(permissionsManager.isBluetoothOn()).thenReturn(false);
    }

    private void whenBluetoothIsOn() {
        when(permissionsManager.isBluetoothOn()).thenReturn(true);
    }

    private void whenLocationIsOff() {
        when(permissionsManager.areLocationServicesOn()).thenReturn(false);
    }

    private void whenFlightModeIsOn() {
        when(permissionsManager.isFlightModeOn()).thenReturn(true);
    }

    private void whenFlightModeIsOff() {
        when(permissionsManager.isFlightModeOn()).thenReturn(false);
    }

    private void whenNotificationsAreOn() {
        when(permissionsManager.areNotificationsEnabled()).thenReturn(true);
    }

    private void whenNotificationsAreOff() {
        when(permissionsManager.areNotificationsEnabled()).thenReturn(false);
    }

    private void whenLocationIsOn() {
        when(permissionsManager.areLocationServicesOn()).thenReturn(true);
    }

    private void whenLocationPermissionIsNotGranted() {
        when(permissionsManager.isLocationPermissionGranted()).thenReturn(false);
    }

    private void whenLocationPermissionIsGranted() {
        when(permissionsManager.isLocationPermissionGranted()).thenReturn(true);
    }

    private void whenPermissionAlreadyAsked() {
        when(spManager.locationPermissionAlreadyAsked()).thenReturn(true);
    }

    private void whenPermissionNeverAsked() {
        when(spManager.locationPermissionAlreadyAsked()).thenReturn(false);
    }

    private void whenShouldNotAskAgain() {
        when(view.shouldShowRequestPermissionRationale()).thenReturn(false);
    }

    private void whenCanAskAgain() {
        when(view.shouldShowRequestPermissionRationale()).thenReturn(true);
    }

    private void whenSDKAtLeastMarshmallow() {
        when(versionManager.atLeastMarshmallow()).thenReturn(true);
    }

    private void whenSDKPreMarshmallow() {
        when(versionManager.atLeastMarshmallow()).thenReturn(false);
    }

}
