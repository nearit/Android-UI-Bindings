package com.nearit.ui_bindings.content;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.nearit.htmltextview.HtmlTextView;
import com.nearit.ui_bindings.R;
import com.nearit.ui_bindings.content.views.ContentCTAButton;
import com.nearit.ui_bindings.utils.LoadImageFromURL;

import it.near.sdk.NearItManager;
import it.near.sdk.reactions.contentplugin.model.Content;
import it.near.sdk.trackings.TrackingInfo;

import static it.near.sdk.recipes.models.Recipe.CTA_TAPPED;

/**
 * @author Federico Boschini
 */

public class NearItContentDetailFragment extends Fragment {
    private static final String ARG_CONTENT = "content";
    private static final String ARG_TRACKING_INFO = "tracking_info";
    private static final String ARG_EXTRAS = "extras";

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

        ContentDetailExtraParams extras = null;
        if (getArguments() != null) {
            extras = getArguments().getParcelable(ARG_EXTRAS);
        }

        if (extras != null) {

        }

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.nearit_ui_fragment_content_detail, container, false);

        ScrollView scrollView = rootView.findViewById(R.id.content_scrollview);
        TextView titleTextView = rootView.findViewById(R.id.content_title);
        HtmlTextView contentView = rootView.findViewById(R.id.content_html);
        ContentCTAButton ctaButton = rootView.findViewById(R.id.cta_button);
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
        }

        if (content.getImageLink() != null) {
            contentImageContainer.setVisibility(View.VISIBLE);
            new LoadImageFromURL(contentImageView, contentImageSpinner, true).execute(content.getImageLink().getFullSize());
        }

        if (content.getCta() != null) {
            ctaButton.setVisibility(View.VISIBLE);
            ctaButton.setText(content.getCta().label);
            ctaButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    NearItManager.getInstance().sendTracking(trackingInfo, CTA_TAPPED);
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(content.getCta().url)));
                }
            });
        }

        return rootView;
    }
}
