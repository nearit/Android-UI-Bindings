package com.nearit.ui_bindings.coupon;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nearit.ui_bindings.NearITUIBindings;
import com.nearit.ui_bindings.R;
import com.nearit.ui_bindings.warning.NearItUIWarningDialogActivity;

import java.util.Collections;
import java.util.List;

import it.near.sdk.NearItManager;
import it.near.sdk.reactions.couponplugin.model.Coupon;


/**
 * @author Federico Boschini
 */

public class NearItCouponListFragment extends Fragment implements CouponListContract.View, CouponAdapter.CouponAdapterListener {

    private static final String EXTRAS = "extras";

    private static final int NEAR_RETRY_CODE = 1111;
    private static final int NEAR_OPEN_WARNING_CODE = 3333;

    private CouponListContract.Presenter presenter;

    private SwipeRefreshLayout refreshLayout;
    private RelativeLayout noCouponContainer;
    private CouponAdapter couponAdapter;
    private TextView noCouponText;
    private View customNoCoupon;
    private int customNoCouponLayoutRef = 0;

    private int separatorDrawable = 0, iconDrawable = 0;
    private boolean noSeparator = false, noIcon, jaggedBorders;
    private boolean enableNetErrorDialog;

    private CouponListExtraParams extras;

    public NearItCouponListFragment() {
    }

    public static NearItCouponListFragment newInstance(@Nullable Parcelable extras) {
        NearItCouponListFragment fragment = new NearItCouponListFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(EXTRAS, extras);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            extras = getArguments().getParcelable(EXTRAS);
            if (extras != null) {
                separatorDrawable = extras.getSeparatorDrawable();
                iconDrawable = extras.getIconDrawable();
                customNoCouponLayoutRef = extras.getNoCouponLayout();
                enableNetErrorDialog = extras.isEnableNetErrorDialog();
                jaggedBorders = extras.isJaggedBorders();
                noSeparator = extras.isNoSeparator();
                noIcon = extras.isNoIcon();
            }
        }

        new CouponListPresenterImpl(NearItManager.getInstance(), this, extras);

    }

    @Override
    public void injectPresenter(@NonNull CouponListContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.nearit_ui_fragment_coupon_list, container, false);

        noCouponText = rootView.findViewById(R.id.no_coupons_text);
        noCouponContainer = rootView.findViewById(R.id.empty_layout);
        RecyclerView couponsRecyclerView = rootView.findViewById(R.id.coupons_list);
        couponsRecyclerView.setHasFixedSize(true);
        couponsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        couponAdapter = new CouponAdapter(getContext(), this, iconDrawable, noIcon, jaggedBorders);
        couponsRecyclerView.setAdapter(couponAdapter);

        refreshLayout = rootView.findViewById(R.id.refresh_layout);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.requestRefresh();
            }
        });

        if (customNoCouponLayoutRef != 0) {
            customNoCoupon = inflater.inflate(customNoCouponLayoutRef, noCouponContainer, false);
            noCouponContainer.addView(customNoCoupon);
        } else {
            noCouponText.setVisibility(View.VISIBLE);
        }

        return rootView;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(EXTRAS, extras);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.start();
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.stop();
    }

    @Override
    public void showCouponList(List<Coupon> couponList) {
        refreshLayout.setRefreshing(false);
        couponAdapter.updateCoupons(couponList);
    }

    @Override
    public void showEmptyLayout() {
        showCouponList(Collections.<Coupon>emptyList());
        if (customNoCoupon != null) {
            customNoCoupon.setVisibility(View.VISIBLE);
            noCouponContainer.setVisibility(View.VISIBLE);
        }
        noCouponText.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideEmptyLayout() {
        if (customNoCoupon != null) {
            customNoCoupon.setVisibility(View.GONE);
            noCouponContainer.setVisibility(View.GONE);
        }
        noCouponText.setVisibility(View.GONE);
    }

    @Override
    public void showRefreshError(String error) {
        refreshLayout.setRefreshing(false);
        couponAdapter.updateCoupons(Collections.<Coupon>emptyList());
        if (enableNetErrorDialog && isAdded() && getActivity() != null){
            startActivityForResult(NearItUIWarningDialogActivity.createIntent(getActivity()), NEAR_OPEN_WARNING_CODE);
        } else {
            Toast.makeText(getActivity(), "Error downloading coupons", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == NEAR_OPEN_WARNING_CODE) {
            if (resultCode == NEAR_RETRY_CODE) {
                presenter.requestRefresh();
            }
        }
    }

    @Override
    public void onCouponClicked(Coupon coupon) {
        presenter.couponClicked(coupon);
    }

    @Override
    public void openDetail(Coupon coupon) {
        CouponDetailIntentBuilder builder = NearITUIBindings.getInstance(getContext()).couponIntentBuilder(coupon, true);
        if (noSeparator) {
            builder.setNoSeparator();
        }
        if (iconDrawable != 0) {
            builder.setIconPlaceholderResourceId(iconDrawable);
        }
        if (separatorDrawable != 0) {
            builder.setSeparatorResourceId(separatorDrawable);
        }
        if (isAdded() && getActivity() != null) {
            startActivity(builder.build());
        }
    }

    public void refreshList() {
        presenter.requestRefresh();
    }
}
