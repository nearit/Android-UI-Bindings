package com.nearit.ui_bindings.content;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.nearit.ui_bindings.utils.images.Image;
import com.nearit.ui_bindings.utils.images.ImageDownloadListener;
import com.nearit.ui_bindings.utils.images.NearItImageDownloader;

import it.near.sdk.NearItManager;
import it.near.sdk.reactions.contentplugin.model.Content;
import it.near.sdk.trackings.TrackingInfo;

import static it.near.sdk.recipes.models.Recipe.CTA_TAPPED;

/**
 * @author Federico Boschini
 */
public class ContentDetailPresenterImpl implements ContentDetailContract.Presenter {

    private ContentDetailContract.View view;
    private Content content;
    @Nullable
    private TrackingInfo trackingInfo;
    private ContentDetailExtraParams params;
    private NearItImageDownloader imageDownloader;
    private NearItManager nearItManager;

    ContentDetailPresenterImpl(ContentDetailContract.View view, Content content, @Nullable TrackingInfo trackingInfo, @Nullable ContentDetailExtraParams params, NearItImageDownloader imageDownloader, NearItManager nearItManager) {
        this.view = view;
        this.content = content;
        this.trackingInfo = trackingInfo;
        this.params = params;
        this.imageDownloader = imageDownloader;
        this.nearItManager = nearItManager;
        init();
    }

    private void init() {
        view.injectPresenter(this);
    }

    @Override
    public void start() {
        if (content.title != null) {
            view.showTitle(content.title);
        }
        if (content.contentString != null) {
            view.showContent(content.contentString);
        }
        if (content.getCta() != null) {
            view.showCtaButton(content.getCta().label);
        }
        downloadImage();
    }

    @Override
    public void stop() { }

    @Override
    public void handleLinkTap(@NonNull String link) {
        if (params.isOpenLinksInWebView()) {
            view.openLinkInWebView(link);
        } else {
            view.openLink(link);
        }
    }

    @Override
    public void handleCtaTap() {
        nearItManager.sendTracking(trackingInfo, CTA_TAPPED);
        if (content.getCta() != null) {
            if (params.isOpenLinksInWebView()) {
                view.openLinkInWebView(content.getCta().url);
            } else {
                view.openLink(content.getCta().url);
            }
        }
    }

    @Override
    public void reloadImage() {
        downloadImage();
    }

    private void downloadImage() {
        if (content.getImageLink() != null && content.getImageLink().getFullSize() != null) {
            view.showImageContainer();
            view.hideImage();
            view.showImageSpinner();
            imageDownloader.downloadImage(content.getImageLink().getFullSize(), new ImageDownloadListener() {
                @Override
                public void onSuccess(@NonNull Image image) {
                    view.hideImageSpinner();
                    view.showImage(image.getBitmap());
                }

                @Override
                public void onError() {
                    view.hideImageSpinner();
                    view.showImageRetry();
                }
            });
        }
    }

    public static ContentDetailPresenterImpl obtain(ContentDetailContract.View view, Content content, @Nullable TrackingInfo trackingInfo, @Nullable ContentDetailExtraParams params) {
        return new ContentDetailPresenterImpl(view, content, trackingInfo, params, NearItImageDownloader.getInstance(), NearItManager.getInstance());
    }
}
