package com.nearit.ui_bindings.permissions;

import android.content.Intent;
import android.content.pm.PackageManager;

import com.nearit.ui_bindings.stubs.NearItManagerStub;
import com.nearit.ui_bindings.permissions.invisible.InvisiblePermissionsContract;
import com.nearit.ui_bindings.permissions.invisible.NearItInvisiblePresenterImpl;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import it.near.sdk.NearItManager;

import static android.app.Activity.RESULT_OK;
import static com.nearit.ui_bindings.permissions.PermissionsPresenterImpl.NEAR_BLUETOOTH_SETTINGS_CODE;
import static com.nearit.ui_bindings.permissions.PermissionsPresenterImpl.NEAR_LOCATION_SETTINGS_CODE;
import static com.nearit.ui_bindings.permissions.PermissionsPresenterImpl.NEAR_PERMISSION_REQUEST_FINE_LOCATION;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Federico Boschini
 */
public class NearItInvisiblePresenterImplTest {

    private InvisiblePermissionsContract.InvisiblePermissionsPresenter presenter;

    @Mock
    private Intent intent;

    @Mock
    private InvisiblePermissionsContract.InvisiblePermissionsView view;
    @Mock
    private PermissionsRequestExtraParams params;
    @Mock
    private PermissionsManager permissionsManager;
    @Mock
    private State state;

    private NearItManager nearItManager;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        nearItManager = Mockito.mock(NearItManagerStub.class);
        presenter = new NearItInvisiblePresenterImpl(view, params, permissionsManager, state, nearItManager);
    }

    @Test
    public void onCreation_presenterGetsInjectedInVIew() {
        verify(view).injectPresenter(presenter);
    }

    @Test
    public void onStart_ifAllPermissionsGranted_finishWithOK() {
        whenNotificationsAreOn();
        whenBleIsAvailable();
        whenBluetoothIsOn();
        whenLocationIsOn();
        whenLocationPermissionIsGranted();

        presenter.start();

        verify(view).finishWithOkResult();
    }

    @Test
    public void onStart_ifFlightMode_showDialog() {
        whenFlightModeIsOn();

        presenter.start();

        verify(view).showAirplaneDialog();
    }

    @Test
    public void onStart_ifADialogHasBeenShown_recreateView() {
        whenFlightModeIsOn();

        presenter.start();

        presenter.start();

        verify(view).recreate();
    }

    @Test
    public void onStart_ifLocationGranted_turnOnLocation() {
        whenLocationPermissionIsGranted();

        presenter.start();

        verify(view).turnOnLocationServices(anyBoolean());
    }

    @Test
    public void onStart_ifLocationNotGrantedNeverAsked_askItAndRemember() {
        whenLocationPermissionIsNotGranted();
        whenPermissionNeverAsked();

        presenter.start();

        verify(view).requestLocationPermission();
        verify(state).setLocationPermissionAsked();
    }

    @Test
    public void onStart_ifLocationNotGrantedAskedAndDenied_showDontAskAgainDialog() {
        whenLocationPermissionIsNotGranted();
        whenPermissionAlreadyAsked();
        when(view.shouldShowRequestPermissionRationale()).thenReturn(false);

        presenter.start();

        verify(view, never()).requestLocationPermission();
        verify(state, never()).setLocationPermissionAsked();
        verify(view).showDontAskAgainDialog();
    }

    @Test
    public void onStart_ifLocationNotGrantedAskedNotDenied_askItAndRemember() {
        whenLocationPermissionIsNotGranted();
        whenPermissionAlreadyAsked();
        when(view.shouldShowRequestPermissionRationale()).thenReturn(true);

        presenter.start();

        verify(view).requestLocationPermission();
        verify(state).setLocationPermissionAsked();
        verify(view, never()).showDontAskAgainDialog();
    }

    @Test
    public void onStart_ifLocationGrantedLocationOnAndBluetoothAvailableButOff_turnItOn() {
        whenLocationPermissionIsGranted();
        whenLocationIsOn();
        whenBluetoothIsOff();
        whenBleIsAvailable();

        presenter.start();

        verify(view).turnOnBluetooth();
    }

    @Test
    public void onStart_ifLocationGrantedONBluetoothAvailableAndONNotificationsOff_showDialog() {
        whenLocationPermissionIsGranted();
        whenLocationIsOn();
        whenBleIsAvailable();
        whenBluetoothIsOn();
        whenNotificationsAreOff();

        presenter.start();

        verify(view).showNotificationsDialog();
    }

    @Test
    public void onStart_locationGrantedAndON_bluetoothAvailableAndON_NotificationOff_noNotification_doNOTshowDialog() {
        whenLocationPermissionIsGranted();
        whenLocationIsOn();
        whenBleIsAvailable();
        whenBluetoothIsOn();
        whenNotificationsAreOff();
        whenNoNotifications();

        presenter.start();

        verify(view, never()).showNotificationsDialog();
    }

    @Test
    public void onLocationResultKO_finishWithSomeResult() {
        whenBleIsAvailable();
        whenBluetoothIsOff();

        presenter.handleActivityResult(NEAR_LOCATION_SETTINGS_CODE, 6, intent);

        verify(view).finishWithKoResult();
    }

    @Test
    public void onBluetoothResultOK_ifAllPermissionsOK_finishWithOK() {
        whenLocationPermissionIsGranted();
        whenLocationIsOn();
        whenBleIsAvailable();
        whenBluetoothIsOn();
        whenNotificationsAreOn();

        presenter.handleActivityResult(NEAR_BLUETOOTH_SETTINGS_CODE, RESULT_OK, intent);

        verify(view).finishWithOkResult();
    }

    @Test
    public void onBluetoothResultOK_ifNotificationsOFF_showDialog() {
        whenLocationPermissionIsGranted();
        whenLocationIsOn();
        whenBleIsAvailable();
        whenBluetoothIsOn();
        whenNotificationsAreOff();

        presenter.handleActivityResult(NEAR_BLUETOOTH_SETTINGS_CODE, RESULT_OK, intent);

        verify(view).showNotificationsDialog();
    }

    @Test
    public void onBluetoothResultOK_ifNotificationsOFF_ifNoNotification_doNOTshowDialog() {
        whenLocationPermissionIsGranted();
        whenLocationIsOn();
        whenBleIsAvailable();
        whenBluetoothIsOn();
        whenNotificationsAreOff();
        whenNoNotifications();

        presenter.handleActivityResult(NEAR_BLUETOOTH_SETTINGS_CODE, RESULT_OK, intent);

        verify(view, never()).showNotificationsDialog();
    }

    @Test
    public void onBluetoothResultOK_ifLocationGrantedButOFF_turnItOn() {
        whenLocationPermissionIsGranted();
        whenLocationIsOff();

        presenter.handleActivityResult(NEAR_BLUETOOTH_SETTINGS_CODE, RESULT_OK, intent);

        verify(view).turnOnLocationServices(anyBoolean());
    }

    @Test
    public void onBluetoothResultOK_ifLocationOnButNotGranted_requestPermission() {
        whenLocationPermissionIsNotGranted();
        whenLocationIsOn();

        presenter.handleActivityResult(NEAR_BLUETOOTH_SETTINGS_CODE, RESULT_OK, intent);

        verify(view).requestLocationPermission();
    }

    @Test
    public void onBluetoothResultKO_finishWithSomeResult() {
        whenLocationPermissionIsNotGranted();

        presenter.handleActivityResult(NEAR_BLUETOOTH_SETTINGS_CODE, 6, intent);

        verify(view).finishWithKoResult();
    }

    @Test
    public void onLocationServiceOn_ifNoBeaconAndNotificationOff_showNotificationsDialog() {
        whenNoBeacon();
        whenNotificationsAreOff();

        presenter.onLocationServicesOn();

        verify(view).showNotificationsDialog();
    }

    @Test
    public void onLocationServiceOn_ifNoBeaconAndNotificationOff_ifNoNotification_showNotificationsDialog() {
        whenNoBeacon();
        whenNotificationsAreOff();
        whenNoNotifications();

        presenter.onLocationServicesOn();

        verify(view, never()).showNotificationsDialog();
    }

    @Test
    public void onLocationServiceOn_ifBluetoothAvailableAndOff_turnItOn() {
        whenBluetoothIsOff();
        whenBleIsAvailable();

        presenter.onLocationServicesOn();

        verify(view).turnOnBluetooth();
    }

    @Test
    public void onLocationServicesOn_ifAllPermissionsOKAndFlightOff_finishWithOK() {
        whenBluetoothIsOn();
        whenLocationIsOn();
        whenLocationPermissionIsGranted();
        whenFlightModeIsOff();
        whenNotificationsAreOn();

        presenter.onLocationServicesOn();

        verify(view).finishWithOkResult();
    }

    @Test
    public void onLocationServicesOn_ifAllPermissionsOKAndAutoStart_startRadarAndfinishWithOK() {
        whenBluetoothIsOn();
        whenLocationIsOn();
        whenLocationPermissionIsGranted();
        whenFlightModeIsOff();
        whenNotificationsAreOn();
        whenAutoStartRadar();

        presenter.onLocationServicesOn();

        verify(nearItManager).startRadar();
        verify(view).finishWithOkResult();
    }

    @Test
    public void onLocationServicesOn_ifLocationStillGrantedButOff_finishWithKO() {
        whenLocationPermissionIsGranted();
        whenLocationIsOff();
        whenFlightModeIsOff();
        whenNotificationsAreOn();
        whenBleIsAvailable();
        whenBluetoothIsOn();

        presenter.onLocationServicesOn();

        verify(view).finishWithKoResult();
    }

    @Test
    public void onLocationServicesOn_ifLocationStillOnButNotGranted_finishWithKO() {
        whenLocationPermissionIsNotGranted();
        whenLocationIsOn();
        whenFlightModeIsOff();
        whenNotificationsAreOn();
        whenBleIsAvailable();
        whenBluetoothIsOn();

        presenter.onLocationServicesOn();

        verify(view).finishWithKoResult();
    }

    @Test
    public void onPermissionResult_ifNotGranted_finishWithKO() {
        String[] permissions = {};
        int[] results = {PackageManager.PERMISSION_DENIED};

        presenter.handlePermissionResult(NEAR_PERMISSION_REQUEST_FINE_LOCATION, permissions, results);

        verify(view, never()).turnOnLocationServices(anyBoolean());
        verify(view, never()).showAirplaneDialog();

        verify(view).finishWithKoResult();
    }

    @Test
    public void onPermissionResult_ifGrantedAndFlightOn_showDialog() {
        whenFlightModeIsOn();

        String[] permissions = {};
        int[] results = {PackageManager.PERMISSION_GRANTED};

        presenter.handlePermissionResult(NEAR_PERMISSION_REQUEST_FINE_LOCATION, permissions, results);

        verify(view, never()).turnOnLocationServices(anyBoolean());
        verify(view).showAirplaneDialog();
    }

    @Test
    public void onDialogClosed_ifBluetoothAvailableAndOff_finalCheckAndFinishWithKO() {
        whenLocationIsOn();
        whenLocationPermissionIsGranted();
        whenBleIsAvailable();
        whenBluetoothIsOff();

        presenter.onDialogClosed();

        verify(view).finishWithKoResult();
    }


    private void whenAutoStartRadar() {
        when(params.isAutoStartRadar()).thenReturn(true);
    }

    private void whenNoBeacon() {
        when(params.isNoBeacon()).thenReturn(true);
    }

    private void whenNoNotifications() {
        when(params.isNoNotifications()).thenReturn(true);
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
        when(state.getLocationPermissionAsked()).thenReturn(true);
    }

    private void whenPermissionNeverAsked() {
        when(state.getLocationPermissionAsked()).thenReturn(false);
    }

}
