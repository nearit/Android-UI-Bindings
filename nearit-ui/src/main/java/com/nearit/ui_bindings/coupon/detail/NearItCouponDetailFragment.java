package com.nearit.ui_bindings.coupon.detail;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import android.widget.Toast;

import com.nearit.ui_bindings.coupon.QRcodeGenerator;
import com.nearit.ui_bindings.utils.images.NearItImageDownloader;
import com.nearit.ui_bindings.R;
import com.nearit.ui_bindings.coupon.views.CouponDetailTopSection;

import it.near.sdk.reactions.couponplugin.model.Coupon;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * @author Federico Boschini
 */

public class NearItCouponDetailFragment extends Fragment implements CouponDetailContract.View {

    private CouponDetailContract.Presenter presenter;

    private static final String ARG_COUPON = "coupon";
    private static final String ARG_EXTRAS = "extras";

    @Nullable
    private ProgressBar spinner;
    @Nullable
    private ImageView couponIcon, separator;
    @Nullable
    private TextView couponName, couponValue, couponDescription;
    @Nullable
    private CouponDetailTopSection topSection;

    private Coupon coupon;
    private CouponDetailExtraParams params;

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
            params = getArguments().getParcelable(ARG_EXTRAS);
        }

        new CouponDetailPresenterImpl(this, coupon, params, NearItImageDownloader.getInstance(), new QRcodeGenerator(250, 250));
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.nearit_ui_fragment_coupon_detail, container, false);

        ScrollView scrollView = rootView.findViewById(R.id.coupon_detail_scrollview);
        scrollView.setVerticalScrollBarEnabled(false);
        scrollView.setHorizontalScrollBarEnabled(false);

        topSection = rootView.findViewById(R.id.coupon_detail_top_section);
        spinner = rootView.findViewById(R.id.coupon_icon_progress_bar);
        couponIcon = rootView.findViewById(R.id.coupon_icon);
        separator = rootView.findViewById(R.id.coupon_detail_separator);
        couponName = rootView.findViewById(R.id.coupon_title);
        couponValue = rootView.findViewById(R.id.coupon_value);
        couponDescription = rootView.findViewById(R.id.coupon_description);

        if (topSection != null) {
            topSection.setCouponView(coupon);
        }

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
    public void keepScreenOn() {
        if (getActivity() != null) {
            WindowManager.LayoutParams layout = getActivity().getWindow().getAttributes();
            layout.screenBrightness = 1F;
            getActivity().getWindow().setAttributes(layout);
        }
    }

    @Override
    public void hideSeparator() {
        if (separator != null) {
            separator.setVisibility(GONE);
        }
    }

    @Override
    public void setSeparator(int separatorDrawable) {
        if (separator != null) {
            separator.setImageResource(separatorDrawable);
        }
    }

    @Override
    public void showQrCode(@NonNull Bitmap bitmap) {
        if (topSection != null) {
            topSection.setQrCode(bitmap);
        }
    }

    @Override
    public void showQrCodeError() {
        if (topSection != null) {
            topSection.hideQrCode();
        }
        if (getActivity() != null && isAdded()) {
            Toast.makeText(getActivity(), R.string.nearit_ui_qr_code_error_message, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showTitle(@NonNull String title) {
        if (couponName != null) {
            couponName.setText(title);
        }
    }

    @Override
    public void showValue(@NonNull String value) {
        if (couponValue != null) {
            couponValue.setText(value);
        }
    }

    @Override
    public void showDescription(@NonNull String description) {
        if (couponDescription != null) {
            couponDescription.setText(description);
        }
    }

    @Override
    public void showIcon(@NonNull Bitmap bitmap) {
        if (couponIcon != null) {
            couponIcon.setVisibility(VISIBLE);
            couponIcon.setScaleType(ImageView.ScaleType.FIT_XY);
            couponIcon.setAdjustViewBounds(true);
            couponIcon.setMinimumHeight(0);
            couponIcon.setImageBitmap(bitmap);
        }
    }

    @Override
    public void showIcon(int placeholderIcon) {
        if (couponIcon != null) {
            couponIcon.setVisibility(VISIBLE);
            couponIcon.setImageResource(placeholderIcon);
        }
    }

    @Override
    public void hideIcon() {
        if (couponIcon != null) {
            couponIcon.setVisibility(GONE);
        }
    }

    @Override
    public void showIcon() {
        if (couponIcon != null) {
            couponIcon.setVisibility(VISIBLE);
        }
    }

    @Override
    public void showSpinner() {
        if (spinner != null) {
            spinner.setVisibility(VISIBLE);
        }
    }

    @Override
    public void hideSpinner() {
        if (spinner != null) {
            spinner.setVisibility(GONE);
        }
    }

    @Override
    public void setDisabled() {
        if (getContext() != null && couponName != null && couponValue != null && couponDescription != null) {
            couponName.setTextColor(ContextCompat.getColor(getContext(), R.color.nearit_ui_coupon_detail_disabled_text_color));
            couponValue.setTextColor(ContextCompat.getColor(getContext(), R.color.nearit_ui_coupon_detail_disabled_text_color));
            couponDescription.setTextColor(ContextCompat.getColor(getContext(), R.color.nearit_ui_coupon_detail_disabled_text_color));
        }
    }

    @Override
    public void injectPresenter(@NonNull CouponDetailContract.Presenter presenter) {
        this.presenter = presenter;
    }
}
