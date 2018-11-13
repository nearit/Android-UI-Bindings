package com.nearit.ui_bindings.coupon.detail;

import android.graphics.Bitmap;

import com.nearit.ui_bindings.CouponStub;
import com.nearit.ui_bindings.utils.images.ImageDownloadListener;
import com.nearit.ui_bindings.utils.images.NearItImageDownloader;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import it.near.sdk.reactions.contentplugin.model.ImageSet;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Federico Boschini
 */
@RunWith(MockitoJUnitRunner.Silent.class)
public class CouponDetailPresenterImplTest {

    @Mock
    private NearItImageDownloader imageDownloader;
    @Mock
    private CouponDetailContract.View view;
    @Mock
    private CouponDetailExtraParams params;
    @Mock
    private ImageSet imageSet;
    @Mock
    private Bitmap bitmap;

    private CouponStub coupon;

    private CouponDetailContract.Presenter presenter;

    @Before
    public void setUp() {
        coupon = Mockito.mock(CouponStub.class);
        presenter = new CouponDetailPresenterImpl(view, coupon, params, imageDownloader);
    }

    @Test
    public void onCreation_presenterIsInjected() {
        verify(view).injectPresenter(presenter);
    }

    @Test
    public void onStart_ifWakeLockIsDisabled_doNotKeepScreenOn() {
        when(params.isNoWakeLock()).thenReturn(true);

        presenter.start();

        verify(view, never()).keepScreenOn();
    }

    @Test
    public void onStart_ifCouponIsValid_keepScreenOnAtMaxBrightness() {
        when(params.isNoWakeLock()).thenReturn(false);
        whenValidCoupon();

        presenter.start();

        verify(view).keepScreenOn();
    }

    @Test
    public void onStart_ifCouponIsNotValidYet_setViewDisabled() {
        whenNotValidYetCoupon();

        presenter.start();

        verify(view).setDisabled();
    }

    @Test
    public void onStart_ifCouponAlreadyRedeemed_setViewDisabled() {
        whenRedeemedCoupon();

        presenter.start();

        verify(view).setDisabled();
    }

    @Test
    public void onStart_ifCouponIsValid_doNOTSetViewDisabled() {
        whenValidCoupon();

        presenter.start();

        verify(view, never()).setDisabled();
    }

    @Test
    public void onStart_ifSeparatorIsDisabled_hideItFromView() {
        when(params.isNoSeparator()).thenReturn(true);

        presenter.start();

        verify(view).hideSeparator();
    }

    @Test
    public void onStart_ifSeparatorIsNOTDisabled_doNOTHideIt() {
        when(params.isNoSeparator()).thenReturn(false);

        presenter.start();

        verify(view, never()).hideSeparator();
    }

    @Test
    public void onStart_ifSeparatorIsDisabled_ignoreDrawableSeparator() {
        when(params.isNoSeparator()).thenReturn(true);
        when(params.getSeparatorDrawable()).thenReturn(6);

        presenter.start();

        verify(view).hideSeparator();
        verify(view, never()).setSeparator(anyInt());
    }

    @Test
    public void onStart_ifValidCustomSeparator_setItToTheView() {
        int drawable = 6;
        when(params.isNoSeparator()).thenReturn(false);
        when(params.getSeparatorDrawable()).thenReturn(drawable);

        presenter.start();

        verify(view).setSeparator(drawable);
    }

    @Test
    public void onStart_ifInvalidCustomSeparator_doNOTsetIt() {
        when(params.isNoSeparator()).thenReturn(false);
        when(params.getSeparatorDrawable()).thenReturn(0);

        presenter.start();

        verify(view, never()).setSeparator(anyInt());
    }

    @Test
    public void onStart_ifNoCouponTitle_doNOTSetTitle() {
        when(coupon.getTitle()).thenReturn(null);

        presenter.start();

        verify(view, never()).showTitle(anyString());
    }

    @Test
    public void onStart_ifCouponTitle_setItToView() {
        String title = "title";
        when(coupon.getTitle()).thenReturn(title);

        presenter.start();

        verify(view).showTitle(title);
    }

    @Test
    public void onStart_ifNoCouponValue_doNOTsetIt() {
        coupon.value = null;

        presenter.start();

        verify(view, never()).showValue(anyString());
    }

    @Test
    public void onStart_ifCouponValue_setItToView() {
        String value = "value";
        coupon.value = value;

        presenter.start();

        verify(view).showValue(value);
    }

    @Test
    public void onStart_ifNoCouponDescription_doNOTsetIt() {
        coupon.description = null;

        presenter.start();

        verify(view, never()).showDescription(anyString());
    }

    @Test
    public void onStart_ifCouponDescription_setItToView() {
        String descr = "value";
        coupon.description = descr;

        presenter.start();

        verify(view).showDescription(descr);
    }

    @Test
    public void onStart_ifInvalidCouponIcon_doNOTsetIt() {
        when(params.getIconDrawable()).thenReturn(0);

        presenter.start();

        verify(view, never()).showIcon(anyInt());
    }

    @Test
    public void onStart_ifPlaceholderIcon_setIt() {
        int drawable = 6;
        when(params.getIconDrawable()).thenReturn(drawable);

        presenter.start();

        verify(view).showIcon(6);
    }

    @Test
    public void onStart_spinnerIsShownAndIconIsHidden() {
        when(coupon.getIconSet()).thenReturn(imageSet);

        presenter.start();

        verify(view).hideIcon();
        verify(view).showSpinner();
    }

    @Test
    public void onStart_onImageError_showDefaultIfValidAndHideSpinner() throws InterruptedException, ExecutionException, TimeoutException {
        String imageUrl = "sei";
        when(coupon.getIconSet()).thenReturn(imageSet);
        when(imageSet.getFullSize()).thenReturn(imageUrl);
        whenImageDownloadError();

        presenter.start();

        verify(view).hideIcon();
        verify(view).showSpinner();
        verify(view).hideSpinner();
        verify(view).showIcon();
        verify(view, never()).showIcon(any(Bitmap.class));
    }

    @Test
    public void onStart_onImageSuccess_showItAndHideSpinner() throws InterruptedException, ExecutionException, TimeoutException {
        String imageUrl = "sei";
        int drawable = 6;
        when(coupon.getIconSet()).thenReturn(imageSet);
        when(imageSet.getFullSize()).thenReturn(imageUrl);
        when(params.getIconDrawable()).thenReturn(drawable);
        whenImageDownloadSuccess();

        presenter.start();

        verify(view).hideIcon();
        verify(view).showSpinner();
        verify(view).hideSpinner();
        verify(view).showIcon(bitmap);
    }

    @Test
    public void onStart_onImageTimeout_showDefaultAndHideSpinner() throws InterruptedException, ExecutionException, TimeoutException {
        String imageUrl = "sei";
        int drawable = 6;
        when(coupon.getIconSet()).thenReturn(imageSet);
        when(imageSet.getFullSize()).thenReturn(imageUrl);
        when(params.getIconDrawable()).thenReturn(drawable);
        doThrow(new TimeoutException()).when(imageDownloader).downloadImage(anyString(), any(ImageDownloadListener.class), anyInt());

        presenter.start();

        verify(view).hideIcon();
        verify(view).showSpinner();
        verify(view).hideSpinner();
        verify(view).showIcon();
    }

    @Test
    public void onStart_onImageInterrupted_showDefaultAndHideSpinner() throws InterruptedException, ExecutionException, TimeoutException {
        String imageUrl = "sei";
        int drawable = 6;
        when(coupon.getIconSet()).thenReturn(imageSet);
        when(imageSet.getFullSize()).thenReturn(imageUrl);
        when(params.getIconDrawable()).thenReturn(drawable);
        doThrow(new InterruptedException()).when(imageDownloader).downloadImage(anyString(), any(ImageDownloadListener.class), anyInt());

        presenter.start();

        verify(view).hideIcon();
        verify(view).showSpinner();
        verify(view).hideSpinner();
        verify(view).showIcon();
    }

    @Test
    public void onStart_onImageExecutionException_showDefaultAndHideSpinner() throws InterruptedException, ExecutionException, TimeoutException {
        String imageUrl = "sei";
        int drawable = 6;
        when(coupon.getIconSet()).thenReturn(imageSet);
        when(imageSet.getFullSize()).thenReturn(imageUrl);
        when(params.getIconDrawable()).thenReturn(drawable);
        doThrow(new ExecutionException(new Throwable())).when(imageDownloader).downloadImage(anyString(), any(ImageDownloadListener.class), anyInt());

        presenter.start();

        verify(view).hideIcon();
        verify(view).showSpinner();
        verify(view).hideSpinner();
        verify(view).showIcon();
    }




    private void whenImageDownloadSuccess() throws InterruptedException, ExecutionException, TimeoutException {
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) {
                ((ImageDownloadListener) invocation.getArgument(1)).onSuccess(bitmap);
                return null;
            }
        }).when(imageDownloader).downloadImage(anyString(), any(ImageDownloadListener.class), anyInt());
    }

    private void whenImageDownloadError() throws InterruptedException, ExecutionException, TimeoutException {
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) {
                ((ImageDownloadListener) invocation.getArgument(1)).onError();
                return null;
            }
        }).when(imageDownloader).downloadImage(anyString(), any(ImageDownloadListener.class), anyInt());
    }

    private void whenValidCoupon() {
        when(coupon.getRedeemedAtDate()).thenReturn(null);
        when(coupon.getRedeemableFromDate()).thenReturn(null);
        when(coupon.getExpiresAtDate()).thenReturn(null);
        when(coupon.getClaimedAtDate()).thenReturn(new Date(106, 3, 6));
    }

    private void whenNotValidYetCoupon() {
        when(coupon.getRedeemableFromDate()).thenReturn(new Date(130, 5, 6));
        when(coupon.getClaimedAtDate()).thenReturn(new Date(106, 6, 6));
    }

    private void whenRedeemedCoupon() {
        when(coupon.getRedeemedAtDate()).thenReturn(new Date(106, 6, 6));
        when(coupon.getRedeemedAt()).thenReturn("sei");
    }

}