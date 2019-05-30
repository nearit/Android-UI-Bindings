package com.nearit.ui_bindings.permissions;

import com.nearit.ui_bindings.stubs.NearItManagerStub;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import it.near.sdk.NearItManager;

import static android.app.Activity.RESULT_OK;
import static com.nearit.ui_bindings.permissions.PermissionsPresenterImpl.NEAR_BLUETOOTH_SETTINGS_CODE;
import static com.nearit.ui_bindings.utils.PermissionsUtils.LOCATION_PERMISSION_DENIED;
import static com.nearit.ui_bindings.utils.PermissionsUtils.LOCATION_PERMISSION_GRANTED;
import static com.nearit.ui_bindings.utils.PermissionsUtils.LOCATION_PERMISSION_ONLY_IN_USE;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyInt;
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
    private PermissionsContract.PermissionsView view;
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
        presenter = new PermissionsPresenterImpl(view, params, permissionsManager, state, nearItManager);
    }

    @Test
    public void onCreation_presenterGetsInjectedInVIew() {
        verify(view).injectPresenter(presenter);
    }

    @Test
    public void onStart_ifNoHeader_hideIt() {
        whenNoHeader();

        presenter.start();

        verify(view).hideHeader();
    }

    @Test
    public void onStart_ifCustomHeader_showIt() {
        whenCustomHeader();

        presenter.start();

        verify(view).setHeader(customHeaderResId);
    }

    @Test
    public void onStart_ifNoCustomIcons_showIt() {
        presenter.start();

        verify(view,never()).setHeader(anyInt());
        verify(view,never()).setBluetoothIcon(anyInt());
        verify(view,never()).setLocationIcon(anyInt());
        verify(view,never()).setNotificationsIcon(anyInt());
        verify(view,never()).setSadFaceIcon(anyInt());
        verify(view,never()).setWorriedFaceIcon(anyInt());
        verify(view,never()).setHappyIcon(anyInt());
    }

    @Test
    public void onStart_ifBluetoothCustomIcon_replaceIt() {
        when(params.getBluetoothResourceId()).thenReturn(6);

        presenter.start();

        verify(view).setBluetoothIcon(6);
    }

    @Test
    public void onStart_ifLocationCustomIcon_replaceIt() {
        when(params.getLocationResourceId()).thenReturn(6);

        presenter.start();

        verify(view).setLocationIcon(6);
    }

    @Test
    public void onStart_ifNotificationsCustomIcon_replaceIt() {
        when(params.getNotificationsResourceId()).thenReturn(6);

        presenter.start();

        verify(view).setNotificationsIcon(6);
    }

    @Test
    public void onStart_ifSadFaceCustomIcon_replaceIt() {
        when(params.getSadFaceResourceId()).thenReturn(6);

        presenter.start();

        verify(view).setSadFaceIcon(6);
    }

    @Test
    public void onStart_ifWorriedFaceCustomIcon_replaceIt() {
        when(params.getWorriedFaceResourceId()).thenReturn(6);

        presenter.start();

        verify(view).setWorriedFaceIcon(6);
    }

    @Test
    public void onStart_ifHappyFaceCustomIcon_replaceIt() {
        when(params.getHappyFaceResourceId()).thenReturn(6);

        presenter.start();

        verify(view).setHappyIcon(6);
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
    public void onStart_ifNotificationsNeverAskedAndOff_resetNotifications() {
        whenNotificationsNeverAsked();
        whenNotificationsAreOff();

        presenter.start();

        verify(view).resetNotificationsButton();
    }

    @Test
    public void onStart_ifBluetoothNeverAskedAndOff_resetBluetooth() {
        whenBluetoothNeverAsked();
        whenBluetoothIsOff();

        presenter.start();

        verify(view).resetBluetoothButton();
    }

    @Test
    public void onStart_ifBluetoothAskedAndOff_bluetoothSad() {
        whenBluetoothIsOff();
        whenBluetoothAsked();

        presenter.start();

        verify(view).setBluetoothButtonSad();
    }

    @Test
    public void onStart_ifLocationAskedAndOff_locationWorried() {
        whenLocationIsOff();
        whenLocationAsked();
        whenLocationPermissionIsGranted();

        presenter.start();

        verify(view).setLocationButtonWorriedServices();
    }

    @Test
    public void onStart_ifLocationPermissionAskedAndOff_locationSad() {
        whenLocationPermissionIsNotGranted();
        whenLocationAsked();

        presenter.start();

        verify(view).setLocationButtonSad();
    }

    @Test
    public void onStart_ifLocationPermissionAskedAndOnlyInUse_locationWorried() {
        whenLocationPermissionOnlyInUse();
        whenLocationAsked();

        presenter.start();

        verify(view).setLocationButtonWorriedWhenInUse();
    }

    @Test
    public void onStart_ifLocationPermissionNeverAsked_resetLocation() {
        whenLocationPermissionIsNotGranted();
        whenLocationNeverAsked();

        presenter.start();

        verify(view).resetLocationButton();
    }

    @Test
    public void onStart_ifLocationNeverAskedAndOff_resetLocation() {
        whenLocationIsOff();
        whenLocationNeverAsked();

        presenter.start();

        verify(view).resetLocationButton();
    }

    @Test
    public void onStart_ifNotificationsButtonNotExplicitlyEnabled_hideIt() {
        whenNotificationsAreOn();

        presenter.start();

        verify(view).hideNotificationsButton();
    }

    @Test
    public void onPermissionResult_ifGrantedAndFlightModeOff_turnOnLocation() {
        whenFlightModeIsOff();

        presenter.handleLocationPermissionGranted();

        verify(view).turnOnLocationServices(anyBoolean());
    }

    @Test
    public void onPermissionResult_ifGrantedAndFlightMode_showFlightDialog() {
        whenFlightModeIsOn();

        presenter.handleLocationPermissionGranted();

        verify(view).showAirplaneDialog();
        verify(view, never()).turnOnLocationServices(anyBoolean());
    }

    @Test
    public void onPermissionResult_ifNotGranted_checkPermissionsAndRefreshUI() {
        whenNotificationsAsked();
        whenNotificationsAreOff();

        presenter.handleLocationPermissionDenied();

        verify(view).setNotificationsButtonSad();
    }

    @Test
    public void onLocationServicesOn_checkPermissionsAndUpdateUI() {
        //  checkPermissions tested in deep next
        whenNotificationsAreOff();
        whenNotificationsAsked();

        presenter.onLocationServicesOn();

        verify(view).setNotificationsButtonSad();
    }

    @Test
    public void onActivityResult_ifLocationOnAndGranted_locationHappy() {
        whenLocationPermissionIsGranted();
        whenLocationIsOn();

        presenter.handleActivityResult(1, 2);

        verify(view).setLocationButtonHappy();
    }

    @Test
    public void onActivityResult_ifLocationAskedOnAndNotGranted_locationSad() {
        whenLocationAsked();
        whenLocationIsOn();
        whenLocationPermissionIsNotGranted();

        presenter.handleActivityResult(1, 2);

        verify(view).setLocationButtonSad();
    }

    @Test
    public void onActivityResult_ifLocationAskedOffAndGranted_locationWorried() {
        whenLocationAsked();
        whenLocationIsOff();
        whenLocationPermissionIsGranted();

        presenter.handleActivityResult(1, 2);

        verify(view).setLocationButtonWorriedServices();
    }

    @Test
    public void onActivityResult_ifLocationAskedOffAndNotGranted_locationSad() {
        whenLocationAsked();
        whenLocationIsOff();
        whenLocationPermissionIsNotGranted();

        presenter.handleActivityResult(1, 2);

        verify(view).setLocationButtonSad();
    }

    @Test
    public void onActivityResult_ifBluetoothOff_checkPermissionsAndUpdateUI() {
        whenBluetoothIsOff();

        presenter.handleActivityResult(1, 2);

        verify(view).resetBluetoothButton();
    }

    @Test
    public void onActivityResult_ifBluetoothOn_checkPermissionsAndUpdateUI() {
        whenBluetoothIsOn();

        presenter.handleActivityResult(1, 2);

        verify(view).setBluetoothButtonHappy();
    }

    @Test
    public void onActivityResult_ifNotificationsAskedAndOff_notificationsSad() {
        whenNotificationsAsked();
        whenNotificationsAreOff();

        presenter.handleActivityResult(1, 2);

        verify(view).setNotificationsButtonSad();
    }

    @Test
    public void onActivityResult_ifNotificationsOn_notificationsHappy() {
        whenNotificationsAreOn();

        presenter.handleActivityResult(NEAR_BLUETOOTH_SETTINGS_CODE, RESULT_OK);

        verify(view).setNotificationsButtonHappy();
    }

    @Test
    public void onDialogCanceled_checkPermissionsAndRefreshUI() {
        whenLocationPermissionIsNotGranted();

        presenter.onDialogCanceled();

        verify(view).resetLocationButton();
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
    public void onLocationTapped_ifPermissionGrantedAndFlightModeOn_showDialog() {
        whenLocationPermissionIsGranted();
        whenFlightModeIsOn();

        presenter.onLocationTapped();

        verify(view).showAirplaneDialog();
    }

    @Test
    public void onLocationTapped_ifAndGranted_turnOnLocation() {
        whenLocationPermissionIsGranted();

        presenter.onLocationTapped();

        verify(view).turnOnLocationServices(anyBoolean());
    }

    @Test
    public void onLocationTapped_ifNotGrantedNeverAsked_askItAndRemember() {
        whenLocationPermissionIsNotGranted();
        whenPermissionNeverAsked();

        presenter.onLocationTapped();

        verify(state).setLocationPermissionAsked();
        verify(view).requestLocationPermission();
    }

    @Test
    public void onLocationTapped_ifDeniedAndNeverAsk_showDialog() {
        whenLocationPermissionIsNotGranted();
        whenPermissionAlreadyAsked();
        whenShouldNotAskAgain();

        presenter.onLocationTapped();

        verify(view).showDontAskAgainDialog();
    }

    @Test
    public void onLocationTapped_ifDeniedCanAskAgain_askItAndRemember() {
        whenLocationPermissionIsNotGranted();
        whenPermissionAlreadyAsked();
        whenCanAskAgain();

        presenter.onLocationTapped();

        verify(state).setLocationPermissionAsked();
        verify(view).requestLocationPermission();
    }

    @Test
    public void onLocationTapped_ifOnlyInUseAndNeverAskAgain_showDialog() {
        whenLocationPermissionOnlyInUse();
        whenPermissionAlreadyAsked();
        whenShouldNotAskAgain();

        presenter.onLocationTapped();

        verify(view).showDontAskAgainDialog();
    }

    @Test
    public void onLocationTapped_ifOnlyInUseCanAskAgain_askItAndRemember() {
        whenLocationPermissionOnlyInUse();
        whenPermissionAlreadyAsked();
        whenCanAskAgain();

        presenter.onLocationTapped();

        verify(state).setLocationPermissionAsked();
        verify(view).requestLocationPermission();
    }

    @Test
    public void onFinalCheck_ifLocationOKAndAutoStartRadar_startRadarAndFinish() {
        whenLocationPermissionIsGranted();
        whenLocationIsOn();
        whenAutoStart();

        presenter.finalCheck();

        verify(nearItManager).startRadar();
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

    private void whenNotificationsButtonEnabled() {
        when(params.isShowNotificationsButton()).thenReturn(true);
    }

    private void whenAutoStart() {
        when(params.isAutoStartRadar()).thenReturn(true);
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
        when(params.getHeaderResourceId()).thenReturn(customHeaderResId);
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
        when(permissionsManager.isLocationPermissionGranted()).thenReturn(LOCATION_PERMISSION_DENIED);
    }

    private void whenLocationPermissionIsGranted() {
        when(permissionsManager.isLocationPermissionGranted()).thenReturn(LOCATION_PERMISSION_GRANTED);
    }

    private void whenLocationPermissionOnlyInUse() {
        when(permissionsManager.isLocationPermissionGranted()).thenReturn(LOCATION_PERMISSION_ONLY_IN_USE);
    }

    private void whenPermissionAlreadyAsked() {
        when(state.getLocationPermissionAsked()).thenReturn(true);
    }

    private void whenPermissionNeverAsked() {
        when(state.getLocationPermissionAsked()).thenReturn(false);
    }

    private void whenShouldNotAskAgain() {
        when(view.shouldShowRequestPermissionRationale()).thenReturn(false);
    }

    private void whenCanAskAgain() {
        when(view.shouldShowRequestPermissionRationale()).thenReturn(true);
    }

    private void whenNotificationsAsked() {
        when(state.getNotificationsAsked()).thenReturn(true);
    }

    private void whenNotificationsNeverAsked() {
        when(state.getNotificationsAsked()).thenReturn(false);
    }

    private void whenLocationAsked() {
        when(state.getLocationAsked()).thenReturn(true);
    }

    private void whenLocationNeverAsked() {
        when(state.getLocationAsked()).thenReturn(false);
    }

    private void whenBluetoothAsked() {
        when(state.getBluetoothAsked()).thenReturn(true);
    }

    private void whenBluetoothNeverAsked() {
        when(state.getBluetoothAsked()).thenReturn(false);
    }

}
