package com.nearit.ui_bindings.inbox;

import android.support.annotation.NonNull;

import com.google.common.collect.Lists;
import com.nearit.ui_bindings.inbox.InboxContract.Presenter;
import com.nearit.ui_bindings.inbox.InboxContract.View;

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
import it.near.sdk.reactions.customjsonplugin.model.CustomJSON;
import it.near.sdk.reactions.feedbackplugin.model.Feedback;
import it.near.sdk.reactions.simplenotificationplugin.model.SimpleNotification;
import it.near.sdk.recipes.inbox.InboxManager;
import it.near.sdk.recipes.inbox.model.InboxItem;
import it.near.sdk.recipes.models.Recipe;
import it.near.sdk.trackings.TrackingInfo;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsCollectionContaining.hasItem;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class InboxPresenterImplTest {

    private Presenter inboxPresenter;
    @Mock
    private NearItManager nearit;
    @Mock
    private View view;
    @Mock
    private InboxListExtraParams params;
    private List<InboxItem> items;
    private InboxItem customJsonItem;
    private InboxItem feedbackItem;
    @Captor
    private ArgumentCaptor<List<InboxItem>> itemsCaptor;

    @Before
    public void setUp() throws Exception {
        when(params.shouldIncludeCustomJSON()).thenReturn(true);
        when(params.shouldIncludeFeedbacks()).thenReturn(true);
        inboxPresenter = new InboxPresenterImpl(nearit, view, params);
    }

    @Test
    public void start_emptyInbox() {
        mockInboxFromNear(new ArrayList<InboxItem>());
        inboxPresenter.start();
        verify(view, atLeastOnce()).showEmptyLayout();
        verify(view, never()).hideEmptyLayout();
        verify(view, never()).showInboxItems(ArgumentMatchers.<InboxItem>anyList());
    }

    @Test
    public void start_onInboxError() {
        mockInboxError("error");
        inboxPresenter.start();
        verify(view, atLeastOnce()).showEmptyLayout();
        verify(view).showRefreshError("error");
    }

    @Test
    public void startWithNoFilter_shouldNotFilterElements() {
        items = buildFullList();
        mockInboxFromNear(items);
        inboxPresenter.start();
        verify(view).showInboxItems(items);
    }

    @Test
    public void refreshNoFilter_shouldRefresh() {
        items = buildFullList();
        mockInboxFromNear(items);
        inboxPresenter.requestRefresh();
        verify(view).showInboxItems(items);
    }

    @Test
    public void refresh_canFail() {
        mockInboxError("error");
        inboxPresenter.requestRefresh();
        verify(view).showRefreshError("error");
    }

    @Test
    public void startWithFeedBackFilter_shouldNotShowFeedback() {
        items = buildFullList();
        when(params.shouldIncludeFeedbacks()).thenReturn(false);
        mockInboxFromNear(items);
        inboxPresenter.start();
        verify(view).showInboxItems(itemsCaptor.capture());
        List<InboxItem> captured = itemsCaptor.getValue();
        assertThat(captured, not(hasItem(feedbackItem)));
    }

    @Test
    public void startWithCustomJsonFilter_shouldNotShowCustomJson() {
        items = buildFullList();
        when(params.shouldIncludeCustomJSON()).thenReturn(false);
        mockInboxFromNear(items);
        inboxPresenter.start();
        verify(view).showInboxItems(itemsCaptor.capture());
        List<InboxItem> captured = itemsCaptor.getValue();
        assertThat(captured, not(hasItem(customJsonItem)));
    }

    @Test
    public void onItemClick_openDetailAndTrack() {
        InboxItem item = new InboxItem();
        TrackingInfo ti = new TrackingInfo();
        item.trackingInfo = ti;
        inboxPresenter.itemClicked(item);
        verify(nearit).sendTracking(ti, Recipe.OPENED);
        verify(view).openDetail(item);
    }

    @Test
    public void onNotificationRead_sendTrackingIfSimpleNotification() {
        InboxItem item = new InboxItem();
        SimpleNotification simpleNotification = Mockito.mock(SimpleNotification.class);
        TrackingInfo ti = new TrackingInfo();
        item.reaction = simpleNotification;
        item.trackingInfo = ti;
        inboxPresenter.onNotificationRead(item);
        verify(nearit).sendTracking(ti, Recipe.OPENED);
    }

    @Test
    public void onNotificationRead_dontSendTrackingOnNotSimpleNotification() {
        InboxItem item = new InboxItem();
        Feedback simpleNotification = Mockito.mock(Feedback.class);
        TrackingInfo ti = new TrackingInfo();
        item.reaction = simpleNotification;
        item.trackingInfo = ti;
        inboxPresenter.onNotificationRead(item);
        verify(nearit, never()).sendTracking(any(TrackingInfo.class), anyString());
        item.reaction = Mockito.mock(Content.class);
        inboxPresenter.onNotificationRead(item);
        verify(nearit, never()).sendTracking(any(TrackingInfo.class), anyString());
        item.reaction = Mockito.mock(CustomJSON.class);
        inboxPresenter.onNotificationRead(item);
        verify(nearit, never()).sendTracking(any(TrackingInfo.class), anyString());
    }

    private void mockInboxFromNear(final List<InboxItem> items) {
        doAnswer(new Answer() {
                     @Override
                     public Object answer(InvocationOnMock invocation) throws Throwable {
                         ((InboxManager.OnInboxMessages) invocation.getArguments()[0]).onMessages(items);
                         return null;
                     }
                 }
        ).when(nearit).getInbox(any(InboxManager.OnInboxMessages.class));
    }

    private void mockInboxError(final String errorMessage) {
        doAnswer(new Answer() {
                     @Override
                     public Object answer(InvocationOnMock invocation) throws Throwable {
                         ((InboxManager.OnInboxMessages) invocation.getArguments()[0]).onError(errorMessage);
                         return null;
                     }
                 }
        ).when(nearit).getInbox(any(InboxManager.OnInboxMessages.class));
    }

    /**
     * builds a list with one item for each inbox near content type (no coupons)
     */
    @NonNull
    private List<InboxItem> buildFullList() {
        InboxItem simpleNotifItem = new InboxItem();
        simpleNotifItem.reaction = Mockito.mock(SimpleNotification.class);
        InboxItem contentItem = new InboxItem();
        contentItem.reaction = Mockito.mock(Content.class);
        customJsonItem = new InboxItem();
        customJsonItem.reaction = Mockito.mock(CustomJSON.class);
        feedbackItem = new InboxItem();
        feedbackItem.reaction = Mockito.mock(Feedback.class);
        return Lists.newArrayList(simpleNotifItem, contentItem, customJsonItem, feedbackItem);
    }
}
