package com.nearit.ui_bindings.inbox;

import android.support.annotation.NonNull;

import com.google.common.collect.Lists;
import com.nearit.ui_bindings.inbox.NotificationHistoryContract.NotificationHistoryPresenter;
import com.nearit.ui_bindings.inbox.NotificationHistoryContract.NotificationHistoryView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.List;

import it.near.sdk.NearItManager;
import it.near.sdk.reactions.contentplugin.model.Content;
import it.near.sdk.reactions.couponplugin.model.Coupon;
import it.near.sdk.reactions.customjsonplugin.model.CustomJSON;
import it.near.sdk.reactions.feedbackplugin.model.Feedback;
import it.near.sdk.reactions.simplenotificationplugin.model.SimpleNotification;
import it.near.sdk.recipes.inbox.NotificationHistoryManager;
import it.near.sdk.recipes.inbox.model.HistoryItem;
import it.near.sdk.recipes.models.Recipe;
import it.near.sdk.trackings.TrackingInfo;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsCollectionContaining.hasItem;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class NotificationHistoryPresenterImplTest {

    private NotificationHistoryPresenter notificationHistoryPresenter;
    @Mock
    private NearItManager nearit;
    @Mock
    private NotificationHistoryView view;
    @Mock
    private NotificationHistoryExtraParams params;
    private List<HistoryItem> items;
    private HistoryItem customJsonItem;
    private HistoryItem feedbackItem;
    private HistoryItem couponItem;
    @Captor
    private ArgumentCaptor<List<HistoryItem>> itemsCaptor;

    @Before
    public void setUp() {
        items = buildFullList();
        when(params.shouldIncludeCustomJSON()).thenReturn(true);
        when(params.shouldIncludeFeedbacks()).thenReturn(true);
        when(params.shouldIncludeCoupons()).thenReturn(true);
        notificationHistoryPresenter = new NotificationHistoryPresenterImpl(nearit, view, params);
    }

    @Test
    public void start_emptyInbox() {
        mockInboxFromNear(new ArrayList<HistoryItem>());
        notificationHistoryPresenter.start();
        verify(view, atLeastOnce()).showEmptyLayout();
        verify(view, never()).hideEmptyLayout();
        verify(view, never()).showNotificationHistory(ArgumentMatchers.<HistoryItem>anyList());
    }

    @Test
    public void start_onInboxError() {
        mockInboxError("error");
        notificationHistoryPresenter.start();
        verify(view, atLeastOnce()).showEmptyLayout();
        verify(view).showRefreshError(anyInt());
    }

    @Test
    public void startWithNoFilter_shouldNotFilterElements() {
        mockInboxFromNear(items);
        notificationHistoryPresenter.start();
        verify(view).showNotificationHistory(items);
    }

    @Test
    public void refreshNoFilter_shouldRefresh() {
        mockInboxFromNear(items);
        notificationHistoryPresenter.requestRefresh();
        verify(view).showNotificationHistory(items);
    }

    @Test
    public void refresh_canFail() {
        mockInboxError("error");
        notificationHistoryPresenter.requestRefresh();
        verify(view).showRefreshError(anyInt());
    }

    @Test
    public void startWithFeedBackFilter_shouldNotShowFeedback() {
        when(params.shouldIncludeFeedbacks()).thenReturn(false);
        mockInboxFromNear(items);
        notificationHistoryPresenter.start();
        verify(view).showNotificationHistory(itemsCaptor.capture());
        List<HistoryItem> captured = itemsCaptor.getValue();
        assertThat(captured, not(hasItem(feedbackItem)));
    }

    @Test
    public void startWithCustomJsonFilter_shouldNotShowCustomJson() {
        when(params.shouldIncludeCustomJSON()).thenReturn(false);
        mockInboxFromNear(items);
        notificationHistoryPresenter.start();
        verify(view).showNotificationHistory(itemsCaptor.capture());
        List<HistoryItem> captured = itemsCaptor.getValue();
        assertThat(captured, not(hasItem(customJsonItem)));
    }

    @Test
    public void startWithCouponFilter_shouldNotShowCoupon() {
        when(params.shouldIncludeCoupons()).thenReturn(false);
        mockInboxFromNear(items);
        notificationHistoryPresenter.start();
        verify(view).showNotificationHistory(itemsCaptor.capture());
        assertThat(itemsCaptor.getValue(), not(hasItem(couponItem)));
    }

    @Test
    public void onItemClick_openDetailAndTrack() {
        HistoryItem item = new HistoryItem();
        TrackingInfo ti = new TrackingInfo();
        item.trackingInfo = ti;
        notificationHistoryPresenter.itemClicked(item);
        verify(nearit).sendTracking(ti, Recipe.OPENED);
        verify(view).openDetail(item);
    }

    @Test
    public void onNotificationRead_sendTrackingIfSimpleNotification() {
        HistoryItem item = new HistoryItem();
        SimpleNotification simpleNotification = Mockito.mock(SimpleNotification.class);
        TrackingInfo ti = new TrackingInfo();
        item.reaction = simpleNotification;
        item.trackingInfo = ti;
        notificationHistoryPresenter.onNotificationRead(item);
        verify(nearit).sendTracking(ti, Recipe.OPENED);
    }

    @Test
    public void onNotificationRead_dontSendTrackingOnNotSimpleNotification() {
        HistoryItem item = new HistoryItem();
        Feedback simpleNotification = Mockito.mock(Feedback.class);
        TrackingInfo ti = new TrackingInfo();
        item.reaction = simpleNotification;
        item.trackingInfo = ti;
        notificationHistoryPresenter.onNotificationRead(item);
        verify(nearit, never()).sendTracking(any(TrackingInfo.class), anyString());
        item.reaction = Mockito.mock(Content.class);
        notificationHistoryPresenter.onNotificationRead(item);
        verify(nearit, never()).sendTracking(any(TrackingInfo.class), anyString());
        item.reaction = Mockito.mock(CustomJSON.class);
        notificationHistoryPresenter.onNotificationRead(item);
        verify(nearit, never()).sendTracking(any(TrackingInfo.class), anyString());
    }

    private void mockInboxFromNear(final List<HistoryItem> items) {
        doAnswer(new Answer() {
                     @Override
                     public Object answer(InvocationOnMock invocation) throws Throwable {
                         ((NotificationHistoryManager.OnNotificationHistoryListener) invocation.getArguments()[0]).onNotifications(items);
                         return null;
                     }
                 }
        ).when(nearit).getHistory(any(NotificationHistoryManager.OnNotificationHistoryListener.class));
    }

    private void mockInboxError(final String errorMessage) {
        doAnswer(new Answer() {
                     @Override
                     public Object answer(InvocationOnMock invocation) throws Throwable {
                         ((NotificationHistoryManager.OnNotificationHistoryListener) invocation.getArguments()[0]).onError(errorMessage);
                         return null;
                     }
                 }
        ).when(nearit).getHistory(any(NotificationHistoryManager.OnNotificationHistoryListener.class));
    }

    /**
     * builds a list with one item for each inbox near content type (no coupons)
     */
    @NonNull
    private List<HistoryItem> buildFullList() {
        HistoryItem simpleNotifItem = new HistoryItem();
        simpleNotifItem.reaction = Mockito.mock(SimpleNotification.class);
        HistoryItem contentItem = new HistoryItem();
        contentItem.reaction = Mockito.mock(Content.class);
        customJsonItem = new HistoryItem();
        customJsonItem.reaction = Mockito.mock(CustomJSON.class);
        feedbackItem = new HistoryItem();
        feedbackItem.reaction = Mockito.mock(Feedback.class);
        couponItem = new HistoryItem();
        couponItem.reaction = Mockito.mock(Coupon.class);
        return Lists.newArrayList(simpleNotifItem, contentItem, customJsonItem, feedbackItem, couponItem);
    }
}
