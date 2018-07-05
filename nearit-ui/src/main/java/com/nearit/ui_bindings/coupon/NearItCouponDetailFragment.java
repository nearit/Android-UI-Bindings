package com.nearit.ui_bindings.coupon;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.nearit.ui_bindings.R;
import com.nearit.ui_bindings.coupon.views.CouponDetailTopSection;
import com.nearit.ui_bindings.utils.LoadImageFromURL;

import it.near.sdk.reactions.couponplugin.model.Coupon;

/**
 * @author Federico Boschini
 */

public class NearItCouponDetailFragment extends Fragment {
    private static final String ARG_COUPON = "coupon";
    private static final String ARG_EXTRAS = "extras";

    private Coupon coupon;
    private int separatorDrawable = 0, iconDrawable = 0;
    private boolean noSeparator = false;
    private boolean noWakeLock = false;

    public NearItCouponDetailFragment() {
    }

    public static NearItCouponDetailFragment newInstance(Coupon coupon, Parcelable extras) {
        NearItCouponDetailFragment fragment = new NearItCouponDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARG_EXTRAS, extras);
        bundle.putParcelable(ARG_COUPON, coupon);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            coupon = getArguments().getParcelable(ARG_COUPON);
            CouponDetailExtraParams extras = getArguments().getParcelable(ARG_EXTRAS);
            if (extras != null) {
                separatorDrawable = extras.getSeparatorDrawable();
                iconDrawable = extras.getIconDrawable();
                noSeparator = extras.isNoSeparator();
                noWakeLock = extras.isNoWakeLock();
            }
        }

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.nearit_ui_fragment_coupon_detail, container, false);

        CouponDetailTopSection topSection = rootView.findViewById(R.id.coupon_detail_top_section);

        ScrollView scrollView = rootView.findViewById(R.id.coupon_detail_scrollview);
        scrollView.setVerticalScrollBarEnabled(false);
        scrollView.setHorizontalScrollBarEnabled(false);

        topSection.setCouponView(coupon);

        if (!noWakeLock &&
                coupon.getRedeemedAtDate() == null &&
                (coupon.getRedeemableFromDate()!= null && coupon.getRedeemableFromDate().getTime() < System.currentTimeMillis()) &&
                (coupon.getExpiresAtDate() != null && coupon.getExpiresAtDate().getTime() > System.currentTimeMillis())) {

            if (getActivity() != null) {
                WindowManager.LayoutParams layout = getActivity().getWindow().getAttributes();
                layout.screenBrightness = 1F;
                getActivity().getWindow().setAttributes(layout);
            }
        }

        ProgressBar iconProgressBar = rootView.findViewById(R.id.coupon_icon_progress_bar);
        ImageView couponIcon = rootView.findViewById(R.id.coupon_icon);
        ImageView separator = rootView.findViewById(R.id.coupon_detail_separator);

        if (noSeparator) {
            separator.setVisibility(View.GONE);
        }

        if (!noSeparator && separatorDrawable != 0 && separator != null) {
            separator.setImageResource(separatorDrawable);
        }

        TextView couponName = rootView.findViewById(R.id.coupon_title);
        if (coupon.getTitle() != null) {
            couponName.setText(coupon.getTitle());
        }
        TextView couponValue = rootView.findViewById(R.id.coupon_value);
        if (coupon.value != null) {
            couponValue.setText(coupon.value);
        }
        TextView couponDescription = rootView.findViewById(R.id.coupon_description);
        if (coupon.description != null) {
            couponDescription.setText(coupon.description);
        }

        if ((coupon.getRedeemableFromDate() != null) && (coupon.getRedeemableFromDate().getTime() > System.currentTimeMillis()) || coupon.getRedeemedAtDate() != null) {
            if (getContext() != null) {
                couponName.setTextColor(ContextCompat.getColor(getContext(), R.color.nearit_ui_coupon_detail_disabled_text_color));
                couponValue.setTextColor(ContextCompat.getColor(getContext(), R.color.nearit_ui_coupon_detail_disabled_text_color));
                couponDescription.setTextColor(ContextCompat.getColor(getContext(), R.color.nearit_ui_coupon_detail_disabled_text_color));
            }
        }

        if (iconDrawable != 0 && couponIcon != null) {
            couponIcon.setImageResource(iconDrawable);
        }

        if (coupon.getIconSet() != null) {
            new LoadImageFromURL(couponIcon, iconProgressBar).execute(coupon.getIconSet().getFullSize());
        }


        return rootView;
    }
}
