package com.nearit.ui_bindings.coupon;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nearit.ui_bindings.R;

import java.util.List;

import it.near.sdk.NearItManager;
import it.near.sdk.reactions.couponplugin.CouponListener;
import it.near.sdk.reactions.couponplugin.model.Coupon;

public class NearItCouponListFragment extends Fragment {

    private List couponList;
    private static final String ARG_EXTRAS = "extras";

    public NearItCouponListFragment() {
    }

    public static NearItCouponListFragment newInstance(Parcelable extras) {
        NearItCouponListFragment fragment = new NearItCouponListFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARG_EXTRAS, extras);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        NearItManager.getInstance().getCoupons(new CouponListener() {
            @Override
            public void onCouponsDownloaded(List<Coupon> list) {
                couponList = list;
            }

            @Override
            public void onCouponDownloadError(String s) {

            }
        });

        CouponDetailExtraParams extras = getArguments().getParcelable(ARG_EXTRAS);
        if (extras != null) {

        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.nearit_ui_fragment_coupon_detail, container, false);


        return rootView;
    }
}
