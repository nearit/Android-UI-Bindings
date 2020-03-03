package com.nearit.ui_bindings.content;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.nearit.customtabs.CustomTabsHelper;
import com.nearit.customtabs.WebViewFallback;
import com.nearit.htmltextview.HtmlTextView;
import com.nearit.htmltextview.NearItMovementMethod;
import com.nearit.ui_bindings.R;
import com.nearit.ui_bindings.content.views.ContentCTAButton;

import it.near.sdk.logging.NearLog;
import it.near.sdk.reactions.contentplugin.model.Content;
import it.near.sdk.trackings.TrackingInfo;

/**
 * @author Federico Boschini
 */
public class NearItContentDetailFragment extends Fragment implements ContentDetailContract.View, NearItMovementMethod.OnMovementLinkClickListener {

    private static final String TAG = "NearItContentFragm";

    private static final String ARG_CONTENT = "content";
    private static final String ARG_TRACKING_INFO = "tracking_info";
    private static final String ARG_EXTRAS = "extras";

    @Nullable
    private Content content;
    @Nullable
    private TrackingInfo trackingInfo;

    @Nullable
    private TextView titleTextView;
    @Nullable
    private HtmlTextView contentTextView;
    @Nullable
    private ImageView contentImageView;
    @Nullable
    private ProgressBar imageSpinner;
    @Nullable
    private ContentCTAButton ctaButton;
    @Nullable
    private LinearLayout imageContainer;

    private ContentDetailContract.Presenter presenter;

    public NearItContentDetailFragment() {}

    public static NearItContentDetailFragment newInstance(Content content, @Nullable TrackingInfo trackingInfo, Parcelable extras) {
        NearItContentDetailFragment fragment = new NearItContentDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARG_EXTRAS, extras);
        bundle.putParcelable(ARG_TRACKING_INFO, trackingInfo);
        bundle.putParcelable(ARG_CONTENT, content);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        if (args != null) {
            content = args.getParcelable(ARG_CONTENT);
            trackingInfo = args.getParcelable(ARG_TRACKING_INFO);
        }

        ContentDetailExtraParams params = null;
        if (getArguments() != null) {
            params = getArguments().getParcelable(ARG_EXTRAS);
        }

        ContentDetailPresenterImpl.obtain(this, content, trackingInfo, params);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.nearit_ui_fragment_content_detail, container, false);

        imageSpinner = rootView.findViewById(R.id.content_image_progress_bar);
        contentImageView = rootView.findViewById(R.id.content_image);
        titleTextView = rootView.findViewById(R.id.content_title);
        contentTextView = rootView.findViewById(R.id.content_html);
        ctaButton = rootView.findViewById(R.id.cta_button);
        imageContainer = rootView.findViewById(R.id.content_image_container);

        ScrollView scrollView = rootView.findViewById(R.id.content_scrollview);
        scrollView.setHorizontalScrollBarEnabled(false);
        scrollView.setVerticalScrollBarEnabled(false);

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.start();
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.stop();
    }

    @Override
    public void showTitle(@NonNull String title) {
        if (titleTextView != null) {
            titleTextView.setVisibility(View.VISIBLE);
            titleTextView.setText(title);
        }
    }

    @Override
    public void showContent(@NonNull String content) {
        if (contentTextView != null) {
            contentTextView.setVisibility(View.VISIBLE);
            contentTextView.setHtml(content);
            contentTextView.setMovementMethod(new NearItMovementMethod(this, getContext()));
        }
    }

    @Override
    public void showImageContainer() {
        if (imageContainer != null) {
            imageContainer.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showImageSpinner() {
        if (imageSpinner != null) {
            imageSpinner.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void hideImageSpinner() {
        if (imageSpinner != null) {
            imageSpinner.setVisibility(View.GONE);
        }
    }

    @Override
    public void hideImage() {
        if (contentImageView != null) {
            contentImageView.setVisibility(View.GONE);
        }
    }

    @Override
    public void showImage(@NonNull Bitmap bitmap) {
        if (contentImageView != null) {
            contentImageView.setVisibility(View.VISIBLE);
            contentImageView.setScaleType(ImageView.ScaleType.FIT_XY);
            contentImageView.setAdjustViewBounds(true);
            contentImageView.setMinimumHeight(0);
            contentImageView.setImageBitmap(bitmap);
        }
    }

    @Override
    public void showImageRetry() {
        if (contentImageView != null) {
            contentImageView.setVisibility(View.VISIBLE);
            contentImageView.setScaleType(ImageView.ScaleType.CENTER);
            contentImageView.setAdjustViewBounds(false);
            contentImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    presenter.reloadImage();
                }
            });
        }
    }

    @Override
    public void showCtaButton(@NonNull String label) {
        if (ctaButton != null) {
            ctaButton.setVisibility(View.VISIBLE);
            ctaButton.setText(label);
            ctaButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    presenter.handleCtaTap();
                }
            });
        }
    }

    @Override
    public void openLink(@NonNull String url) {
        if (getContext() != null) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            if (intent.resolveActivity(getContext().getPackageManager()) != null) {
                startActivity(intent);
            } else {
                NearLog.e(TAG, String.format("Unable to open link: %s", url));
            }
        }
    }

    @Override
    public void openLinkInWebView(@NonNull String url) {
        if (getContext() != null) {
            CustomTabsIntent.Builder intentBuilder = new CustomTabsIntent.Builder();
            intentBuilder.setToolbarColor(ContextCompat.getColor(getContext(), R.color.nearit_ui_webview_toolbar_color));

            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));

            if (intent.resolveActivity(getContext().getPackageManager()) != null) {
                CustomTabsHelper.openCustomTab(
                        getContext(), intentBuilder.build(), Uri.parse(url), new WebViewFallback());
            } else {
                NearLog.e(TAG, String.format("Unable to open link: %s", url));
            }
        }
    }

    @Override
    public void injectPresenter(@NonNull ContentDetailContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onLinkClicked(String linkText, NearItMovementMethod.LinkType linkType) {
        presenter.handleLinkTap(linkText, linkType);
    }
}
