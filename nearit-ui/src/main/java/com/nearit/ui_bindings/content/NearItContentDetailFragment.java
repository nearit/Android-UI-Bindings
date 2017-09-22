package com.nearit.ui_bindings.content;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nearit.ui_bindings.R;

import it.near.sdk.reactions.contentplugin.model.Content;

public class NearItContentDetailFragment extends Fragment {
    private static final String ARG_CONTENT = "content";
    private static final String ARG_EXTRAS = "extras";

    private Content content;

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

        return rootView;
    }
}
