package com.nearit.ui_bindings.content;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nearit.ui_bindings.R;
import com.nearit.ui_bindings.content.views.ContentCTAButton;
import com.nearit.ui_bindings.utils.LoadImageFromURL;

import it.near.sdk.reactions.contentplugin.model.Content;


public class NearItContentDetailFragment extends Fragment {
    private static final String ARG_CONTENT = "content";
    private static final String ARG_EXTRAS = "extras";

    private Content content;
    private TextView titleTextView;
    private WebView contentView;
    private ContentCTAButton ctaButton;
    private ImageView contentImageView;
    private ProgressBar contentImageSpinner;
    private LinearLayout contentImageContainer;

    public NearItContentDetailFragment() {
    }

    public static NearItContentDetailFragment newInstance(Content content, Parcelable extras) {
        NearItContentDetailFragment fragment = new NearItContentDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARG_EXTRAS, extras);
        bundle.putParcelable(ARG_CONTENT, content);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        content = getArguments().getParcelable(ARG_CONTENT);
        ContentDetailExtraParams extras = getArguments().getParcelable(ARG_EXTRAS);
        if (extras != null) {

        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.nearit_ui_fragment_content_detail, container, false);

        titleTextView = (TextView) rootView.findViewById(R.id.content_title);
        contentView = (WebView) rootView.findViewById(R.id.content_text);
        ctaButton = (ContentCTAButton) rootView.findViewById(R.id.cta_button);
        contentImageView = (ImageView) rootView.findViewById(R.id.content_image);
        contentImageSpinner = (ProgressBar) rootView.findViewById(R.id.content_image_progress_bar);
        contentImageContainer = (LinearLayout) rootView.findViewById(R.id.content_image_container);

        if (content.title != null) {
            titleTextView.setVisibility(View.VISIBLE);
            titleTextView.setText(content.title);
        }

        if (content.contentString != null) {
            contentView.setVisibility(View.VISIBLE);
            contentView.getSettings();
            contentView.setBackgroundColor(Color.TRANSPARENT);
            contentView.loadDataWithBaseURL("", content.contentString, "text/html", "UTF-8", "");
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
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(content.getCta().url)));
                }
            });
        }

        return rootView;
    }
}
