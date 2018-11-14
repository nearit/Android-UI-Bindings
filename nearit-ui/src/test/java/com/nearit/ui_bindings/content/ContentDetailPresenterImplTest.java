package com.nearit.ui_bindings.content;

import android.graphics.Bitmap;

import com.nearit.ui_bindings.stubs.ContentStub;
import com.nearit.ui_bindings.utils.images.ImageDownloadListener;
import com.nearit.ui_bindings.utils.images.NearItImageDownloader;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import it.near.sdk.NearItManager;
import it.near.sdk.reactions.contentplugin.model.ContentLink;
import it.near.sdk.reactions.contentplugin.model.ImageSet;
import it.near.sdk.trackings.TrackingInfo;

import static it.near.sdk.recipes.models.Recipe.CTA_TAPPED;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Federico Boschini
 */
@RunWith(MockitoJUnitRunner.class)
public class ContentDetailPresenterImplTest {

    @Mock
    private ContentDetailContract.View view;

    private ContentStub content;

    @Mock
    private TrackingInfo trackingInfo;
    @Mock
    private ContentDetailExtraParams params;
    @Mock
    private NearItImageDownloader imageDownloader;
    @Mock
    private NearItManager nearItManager;

    @Mock
    private Bitmap bitmap;

    @Captor
    private ArgumentCaptor<ImageDownloadListener> captor;
    @Captor
    private ArgumentCaptor<String> stringCaptor;

    private ContentDetailContract.Presenter presenter;

    @Before
    public void setUp() {
        content = Mockito.mock(ContentStub.class);
        presenter = new ContentDetailPresenterImpl(view, content, trackingInfo, params, imageDownloader, nearItManager);
    }

    @Test
    public void onCreation_presenterGetsInjectedInView() {
        verify(view).injectPresenter(presenter);
    }

    @Test
    public void onStart_ifNoTitle_doNotShow() {
        content.title = null;

        presenter.start();

        verify(view, never()).showTitle(anyString());
    }

    @Test
    public void onStart_ifTitle_showIt() {
        String title = "sei";
        content.title = title;

        presenter.start();

        verify(view).showTitle(title);
    }

    @Test
    public void onStart_ifNoContent_doNotShow() {
        content.contentString = null;

        presenter.start();

        verify(view, never()).showContent(anyString());
    }

    @Test
    public void onStart_ifContent_showIt() {
        String contentText = "sette";
        content.contentString = contentText;

        presenter.start();

        verify(view).showContent(contentText);
    }

    @Test
    public void onStart_ifNoCTA_doNotShow() {
        when(content.getCta()).thenReturn(null);

        presenter.start();

        verify(view, never()).showCtaButton(anyString());
    }

    @Test
    public void onStart_ifCTA_showIt() {
        String ctaLabel = "otto";
        ContentLink cta = new ContentLink(ctaLabel, "link");
        when(content.getCta()).thenReturn(cta);

        presenter.start();

        verify(view).showCtaButton(ctaLabel);
    }

    @Test
    public void onStart_ifNoImage_doNotShow() {
        when(content.getImageLink()).thenReturn(null);

        presenter.start();

        verify(view, never()).showImageContainer();
        verify(view, never()).showImageSpinner();
        verify(view, never()).showImageRetry();
        verify(view, never()).hideImage();
        verify(view, never()).hideImageSpinner();
        verify(view, never()).showImage(any(Bitmap.class));
    }

    @Test
    public void onStart_ifImage_downloadIt() {
        String imageUrl = "nove";
        ImageSet imageSet = new ImageSet();
        imageSet.setFullSize(imageUrl);
        when(content.getImageLink()).thenReturn(imageSet);

        presenter.start();

        verify(imageDownloader).downloadImage(stringCaptor.capture(), any(ImageDownloadListener.class));
        String invokedUrl = stringCaptor.getValue();
        assertThat(invokedUrl, is(imageUrl));
    }

    @Test
    public void onStart_ifImageAndSuccess_showAndHideSpinnerAndImage() {
        String imageUrl = "nove";
        ImageSet imageSet = new ImageSet();
        imageSet.setFullSize(imageUrl);
        when(content.getImageLink()).thenReturn(imageSet);

        presenter.start();

        InOrder verifier = inOrder(view);
        verifier.verify(view).showImageContainer();
        verifier.verify(view).hideImage();
        verifier.verify(view).showImageSpinner();
        verify(imageDownloader).downloadImage(anyString(), captor.capture());
        ImageDownloadListener listener = captor.getValue();
        listener.onSuccess(bitmap);

        verifier.verify(view).hideImageSpinner();
        verifier.verify(view).showImage(bitmap);
    }

    @Test
    public void onStart_ifImageButError_showAndHideSpinnerAndShowRetry() {
        String imageUrl = "nove";
        ImageSet imageSet = new ImageSet();
        imageSet.setFullSize(imageUrl);
        when(content.getImageLink()).thenReturn(imageSet);

        presenter.start();

        InOrder verifier = inOrder(view);
        verifier.verify(view).showImageContainer();
        verifier.verify(view).hideImage();
        verifier.verify(view).showImageSpinner();
        verify(imageDownloader).downloadImage(anyString(), captor.capture());
        ImageDownloadListener listener = captor.getValue();
        listener.onError();

        verifier.verify(view).hideImageSpinner();
        verifier.verify(view).showImageRetry();
        verify(view, never()).showImage(bitmap);
    }

    @Test
    public void onReloadImage_downloadImageAgain() {
        ImageSet imageSet = new ImageSet();
        imageSet.setFullSize("dieci");
        when(content.getImageLink()).thenReturn(imageSet);

        presenter.reloadImage();

        verify(view).showImageContainer();
    }

    @Test
    public void onHandleCtaTap_sendTracking() {
        presenter.handleCtaTap();

        verify(nearItManager).sendTracking(trackingInfo, CTA_TAPPED);
    }

    @Test
    public void onHandleCtaTap_ifOpenInWebView_openIt() {
        String ctaLabel = "otto";
        String ctaLink = "diciotto";
        ContentLink cta = new ContentLink(ctaLabel, ctaLink);
        when(content.getCta()).thenReturn(cta);
        when(params.isOpenLinksInWebView()).thenReturn(true);

        presenter.handleCtaTap();

        verify(view, never()).openLink(anyString());
        verify(view).openLinkInWebView(ctaLink);
    }

    @Test
    public void onHandleCtaTap_ifClassicLinkOpening_openIt() {
        String ctaLabel = "otto";
        String ctaLink = "diciotto";
        ContentLink cta = new ContentLink(ctaLabel, ctaLink);
        when(content.getCta()).thenReturn(cta);
        when(params.isOpenLinksInWebView()).thenReturn(false);

        presenter.handleCtaTap();

        verify(view).openLink(ctaLink);
        verify(view, never()).openLinkInWebView(anyString());
    }

    @Test
    public void onHandleLinkTap_ifOpenInWebView_openIt() {
        String link = "ghiacciolo";
        when(params.isOpenLinksInWebView()).thenReturn(true);

        presenter.handleLinkTap(link);

        verify(view, never()).openLink(anyString());
        verify(view).openLinkInWebView(link);
    }

    @Test
    public void onHandleLinkTap_ifClassicLinkOpening_openIt() {
        String link = "gelato";
        when(params.isOpenLinksInWebView()).thenReturn(false);

        presenter.handleLinkTap(link);

        verify(view).openLink(link);
        verify(view, never()).openLinkInWebView(anyString());
    }

}