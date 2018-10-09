package com.nearit.ui_bindings.coupon;

import com.google.common.collect.Lists;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import it.near.sdk.NearItManager;
import it.near.sdk.reactions.couponplugin.CouponListener;
import it.near.sdk.reactions.couponplugin.model.Coupon;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsCollectionContaining.hasItem;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Federico Boschini
 */
@RunWith(MockitoJUnitRunner.class)
public class CouponListPresenterImplTest {

    private static final String getCouponsError = "error";

    @Mock
    private NearItManager nearItManager;
    @Mock
    private CouponListContract.View view;
    @Mock
    private CouponListExtraParams params;

    @Mock
    private Coupon coupon;
    @Mock
    private Coupon validCoupon;
    @Mock
    private Coupon validCoupon2;
    @Mock
    private Coupon expiredCoupon;
    @Mock
    private Coupon expiredCoupon2;
    @Mock
    private Coupon inactiveCoupon;
    @Mock
    private Coupon inactiveCoupon2;
    @Mock
    private Coupon redeemedCoupon;

    @Captor
    private ArgumentCaptor<List<Coupon>> captor;

    private CouponListContract.Presenter presenter;

    @Before
    public void setUp() {
        presenter = new CouponListPresenterImpl(nearItManager, view, params);
    }

    @Test
    public void onCreation_presenterIsInjected() {
        verify(view).injectPresenter(presenter);
    }

    @Test
    public void onCouponClicked_openDetail() {
        presenter.couponClicked(coupon);
        verify(view).openDetail(coupon);
    }

    @Test
    public void onStart_ifFetchFails_showEmptyLayoutAndError() {
        mockGetCouponsError();

        presenter.start();

        verify(view).showCouponList(Collections.<Coupon>emptyList());
        verify(view).showEmptyLayout();
        verify(view).showRefreshError(getCouponsError);
    }

    @Test
    public void onStart_ifFetchSucceeds_butEmpty_showEmptyLayout(){
        mockGetCoupons(Collections.<Coupon>emptyList());

        presenter.start();

        verify(view).showEmptyLayout();
    }

    @Test
    public void onStart_ifFetchSucceeds_showDefaultList() {
        when(params.isDefaultList()).thenReturn(true);
        presenter = new CouponListPresenterImpl(nearItManager, view, params);
        mockGetCoupons(buildCouponList());

        presenter.start();

        verify(view).showCouponList(captor.capture());
        List<Coupon> captured = captor.getValue();
        assertThat(captured, hasItem(validCoupon));
        assertThat(captured, hasItem(inactiveCoupon));
        assertThat(captured, not(hasItem(expiredCoupon)));
        assertThat(captured, not(hasItem(redeemedCoupon)));
        verify(view).hideEmptyLayout();
    }

    @Test
    public void onStart_ifFetchSucceeds_ifValidOnly_showValidOnly() {
        when(params.isDefaultList()).thenReturn(false);
        when(params.isValid()).thenReturn(true);
        presenter = new CouponListPresenterImpl(nearItManager, view, params);
        mockGetCoupons(buildCouponList());

        presenter.start();

        verify(view).showCouponList(captor.capture());
        List<Coupon> captured = captor.getValue();
        assertThat(captured, hasItem(validCoupon));
        assertThat(captured, not(hasItem(expiredCoupon)));
        assertThat(captured, not(hasItem(redeemedCoupon)));
        assertThat(captured, not(hasItem(inactiveCoupon)));
        verify(view).hideEmptyLayout();
    }

    @Test
    public void onStart_ifFetchSucceeds_ifExpiredOnly_showExpiredOnly() {
        when(params.isDefaultList()).thenReturn(false);
        when(params.isExpired()).thenReturn(true);
        presenter = new CouponListPresenterImpl(nearItManager, view, params);
        mockGetCoupons(buildCouponList());

        presenter.start();

        verify(view).showCouponList(captor.capture());
        List<Coupon> captured = captor.getValue();
        assertThat(captured, hasItem(expiredCoupon));
        assertThat(captured, not(hasItem(validCoupon)));
        assertThat(captured, not(hasItem(redeemedCoupon)));
        assertThat(captured, not(hasItem(inactiveCoupon)));
        verify(view).hideEmptyLayout();
    }

    @Test
    public void onStart_ifFetchSucceeds_ifInactiveOnly_showInactiveOnly() {
        when(params.isDefaultList()).thenReturn(false);
        when(params.isInactive()).thenReturn(true);
        presenter = new CouponListPresenterImpl(nearItManager, view, params);
        mockGetCoupons(buildCouponList());

        presenter.start();

        verify(view).showCouponList(captor.capture());
        List<Coupon> captured = captor.getValue();
        assertThat(captured, hasItem(inactiveCoupon));
        assertThat(captured, not(hasItem(validCoupon)));
        assertThat(captured, not(hasItem(redeemedCoupon)));
        assertThat(captured, not(hasItem(expiredCoupon)));
        verify(view).hideEmptyLayout();
    }

    @Test
    public void onStart_ifFetchSucceeds_ifRedeemedOnly_showRedeemedOnly() {
        when(params.isDefaultList()).thenReturn(false);
        when(params.isRedeemed()).thenReturn(true);
        presenter = new CouponListPresenterImpl(nearItManager, view, params);
        mockGetCoupons(buildCouponList());

        presenter.start();

        verify(view).showCouponList(captor.capture());
        List<Coupon> captured = captor.getValue();
        assertThat(captured, hasItem(redeemedCoupon));
        assertThat(captured, not(hasItem(validCoupon)));
        assertThat(captured, not(hasItem(inactiveCoupon)));
        assertThat(captured, not(hasItem(expiredCoupon)));
        verify(view).hideEmptyLayout();
    }

    @Test
    public void onStart_ifFetchSucceeds_ifValidAndRedeemed_showThose() {
        when(params.isDefaultList()).thenReturn(false);
        when(params.isValid()).thenReturn(true);
        when(params.isRedeemed()).thenReturn(true);
        presenter = new CouponListPresenterImpl(nearItManager, view, params);
        mockGetCoupons(buildCouponList());

        presenter.start();

        verify(view).showCouponList(captor.capture());
        List<Coupon> captured = captor.getValue();
        assertThat(captured, hasItem(validCoupon));
        assertThat(captured, hasItem(redeemedCoupon));
        assertThat(captured, not(hasItem(inactiveCoupon)));
        assertThat(captured, not(hasItem(expiredCoupon)));
        verify(view).hideEmptyLayout();
    }

    @Test
    public void onFetchSuccess_couponsAreShownOrderedByClaimDate() {
        when(params.isDefaultList()).thenReturn(true);
        presenter = new CouponListPresenterImpl(nearItManager, view, params);
        mockGetCoupons(buildCouponList());

        presenter.requestRefresh();

        verify(view).showCouponList(captor.capture());
        List<Coupon> expected = Lists.newArrayList(validCoupon2, validCoupon, inactiveCoupon2, inactiveCoupon);
        List<Coupon> captured = captor.getValue();
        assertThat(captured, is(expected));
        verify(view).hideEmptyLayout();
    }

    @Test
    public void onFetchSuccess_forCustomList_couponsAreShownOrderedByClaimDate() {
        when(params.isRedeemed()).thenReturn(true);
        when(params.isExpired()).thenReturn(true);
        when(params.isInactive()).thenReturn(true);
        presenter = new CouponListPresenterImpl(nearItManager, view, params);
        mockGetCoupons(buildCouponList());

        presenter.requestRefresh();

        verify(view).showCouponList(captor.capture());
        List<Coupon> expected = Lists.newArrayList(inactiveCoupon2, inactiveCoupon, expiredCoupon, expiredCoupon2, redeemedCoupon);
        List<Coupon> captured = captor.getValue();
        assertThat(captured, is(expected));
        verify(view).hideEmptyLayout();
    }

    @Test
    public void onRefresh_ifCouponFetchError_showEmptyLayoutAndError() {
        mockGetCouponsError();

        presenter.requestRefresh();

        verify(view).showCouponList(Collections.<Coupon>emptyList());
        verify(view).showEmptyLayout();
        verify(view).showRefreshError(getCouponsError);
    }


    private List<Coupon> buildCouponList() {
        buildCoupons();
        return Lists.newArrayList(validCoupon, expiredCoupon, inactiveCoupon, expiredCoupon2, inactiveCoupon2, validCoupon2, redeemedCoupon);
    }

    private void buildCoupons() {
        when(validCoupon.getRedeemedAtDate()).thenReturn(null);
        when(validCoupon.getExpiresAtDate()).thenReturn(null);
        when(validCoupon.getRedeemableFromDate()).thenReturn(null);
        when(validCoupon.getClaimedAtDate()).thenReturn(new Date(106, 3, 6));

        when(validCoupon2.getRedeemedAtDate()).thenReturn(null);
        when(validCoupon2.getExpiresAtDate()).thenReturn(null);
        when(validCoupon2.getRedeemableFromDate()).thenReturn(null);
        when(validCoupon2.getClaimedAtDate()).thenReturn(new Date(106, 3, 7));

        when(expiredCoupon.getRedeemedAtDate()).thenReturn(null);
        when(expiredCoupon.getExpiresAtDate()).thenReturn(new Date(106, 5, 6));

        when(expiredCoupon2.getRedeemedAtDate()).thenReturn(null);
        when(expiredCoupon2.getExpiresAtDate()).thenReturn(new Date(106, 5, 6));
        when(expiredCoupon2.getClaimedAtDate()).thenReturn(new Date(106, 5, 5));

        when(inactiveCoupon.getRedeemableFromDate()).thenReturn(new Date(130, 5, 6));
        when(inactiveCoupon.getClaimedAtDate()).thenReturn(new Date(106, 6, 5));

        when(inactiveCoupon2.getRedeemableFromDate()).thenReturn(new Date(130, 5, 6));
        when(inactiveCoupon2.getClaimedAtDate()).thenReturn(new Date(106, 6, 6));

        when(redeemedCoupon.getRedeemedAtDate()).thenReturn(new Date(106, 5, 6));
    }

    private void mockGetCoupons(final List<Coupon> coupons) {
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) {
                ((CouponListener) invocation.getArguments()[0]).onCouponsDownloaded(coupons);
                return null;
            }
        }).when(nearItManager).getCoupons(any(CouponListener.class));
    }

    private void mockGetCouponsError() {
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) {
                ((CouponListener) invocation.getArguments()[0]).onCouponDownloadError(getCouponsError);
                return null;
            }
        }).when(nearItManager).getCoupons(any(CouponListener.class));
    }

}