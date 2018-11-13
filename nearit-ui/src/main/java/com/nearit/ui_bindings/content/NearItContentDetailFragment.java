package com.nearit.ui_bindings.content;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
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

import it.near.sdk.NearItManager;
import it.near.sdk.reactions.contentplugin.model.Content;
import it.near.sdk.trackings.TrackingInfo;

import static it.near.sdk.recipes.models.Recipe.CTA_TAPPED;

/**
 * @author Federico Boschini
 */

public class NearItContentDetailFragment extends Fragment implements NearItMovementMethod.OnMovementLinkClickListener {

    private static final String TAG = "NearItContentFragm";

    private static final String ARG_CONTENT = "content";
    private static final String ARG_TRACKING_INFO = "tracking_info";
    private static final String ARG_EXTRAS = "extras";

    private boolean openLinksInTabs = false;

    private Content content;
    @Nullable
    private TrackingInfo trackingInfo;

    public NearItContentDetailFragment() {
    }

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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        if (args != null) {
            content = args.getParcelable(ARG_CONTENT);
            trackingInfo = args.getParcelable(ARG_TRACKING_INFO);
        }

        ContentDetailExtraParams extras;
        if (getArguments() != null) {
            extras = getArguments().getParcelable(ARG_EXTRAS);
            if (extras != null) {
                openLinksInTabs = extras.isOpenLinksInWebView();
            }
        }


    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.nearit_ui_fragment_content_detail, container, false);

        ScrollView scrollView = rootView.findViewById(R.id.content_scrollview);
        TextView titleTextView = rootView.findViewById(R.id.content_title);
        HtmlTextView contentView = rootView.findViewById(R.id.content_html);
        final ContentCTAButton ctaButton = rootView.findViewById(R.id.cta_button);
        ImageView contentImageView = rootView.findViewById(R.id.content_image);
        ProgressBar contentImageSpinner = rootView.findViewById(R.id.content_image_progress_bar);
        LinearLayout contentImageContainer = rootView.findViewById(R.id.content_image_container);

        scrollView.setHorizontalScrollBarEnabled(false);
        scrollView.setVerticalScrollBarEnabled(false);

        if (content.title != null) {
            titleTextView.setVisibility(View.VISIBLE);
            titleTextView.setText(content.title);
        }

        if (content.contentString != null) {
            contentView.setVisibility(View.VISIBLE);
            contentView.setHtml(content.contentString);
            contentView.setMovementMethod(new NearItMovementMethod(this, getContext()));
        }

        if (content.getImageLink() != null) {
            contentImageContainer.setVisibility(View.VISIBLE);
            //new LoadImageFromURL(contentImageView, contentImageSpinner, true).execute(content.getImageLink().getFullSize());
        }

        if (content.getCta() != null) {
            ctaButton.setVisibility(View.VISIBLE);
            ctaButton.setText(content.getCta().label);
            ctaButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    NearItManager.getInstance().sendTracking(trackingInfo, CTA_TAPPED);
                    if (getContext() != null) {
                        if (openLinksInTabs) {
                            openInCustomTab(content.getCta().url);
                        } else {
                            openUrl(content.getCta().url);
                        }
                    }
                }
            });
        }

        return rootView;
    }

    private void openInCustomTab(String url) {
        assert content.getCta() != null;

        if (getContext() != null) {
            CustomTabsIntent.Builder intentBuilder = new CustomTabsIntent.Builder();
            intentBuilder.setToolbarColor(ContextCompat.getColor(getContext(), R.color.nearit_ui_webview_toolbar_color));

            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));

            if (intent.resolveActivity(getContext().getPackageManager()) != null) {
                CustomTabsHelper.openCustomTab(
                        getContext(), intentBuilder.build(), Uri.parse(url), new WebViewFallback());
            } else {
                Log.e(TAG, String.format("Unable to open link: %s", url));
            }
        }
    }

    private void openUrl(String url) {
        assert content.getCta() != null;

        if (getContext() != null) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            if (intent.resolveActivity(getContext().getPackageManager()) != null) {
                startActivity(intent);
            } else {
                Log.e(TAG, String.format("Unable to open link: %s", url));
            }
        }
    }

    @Override
    public void onLinkClicked(String linkText, NearItMovementMethod.LinkType linkType) {
        if (openLinksInTabs) {
            openInCustomTab(linkText);
        } else {
            openUrl(linkText);
        }
    }
}
