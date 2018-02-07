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

import com.nearit.ui_bindings.NearITUIBindings;
import com.nearit.ui_bindings.R;
import com.nearit.ui_bindings.warning.NearItUIWarningDialogActivity;

import java.util.ArrayList;
import java.util.List;

import it.near.sdk.NearItManager;
import it.near.sdk.reactions.couponplugin.CouponListener;
import it.near.sdk.reactions.couponplugin.model.Coupon;

/**
 * @author Federico Boschini
 */

public class NearItCouponListFragment extends Fragment implements CouponAdapter.Item.CouponListener {

    private List<Coupon> couponList;
    private static final String ARG_EXTRAS = "extras";

    private static final int NEAR_RETRY_CODE = 1111;
    private static final int NEAR_OPEN_WARNING_CODE = 3333;

    @Nullable
    private SwipeRefreshLayout refreshLayout;
    private RelativeLayout noCouponContainer;
    private CouponAdapter couponAdapter;
    private TextView noCouponText;
    private View customNoCoupon;
    private int customNoCouponLayoutRef = 0;

    private int separatorDrawable = 0, iconDrawable = 0;
    private boolean noSeparator = false, noIcon, jaggedBorders;
    private boolean validOnly, expiredOnly, inactiveOnly, redeemedOnly, includeRedeemed, enableNetErrorDialog;

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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            CouponListExtraParams extras = getArguments().getParcelable(ARG_EXTRAS);
            if (extras != null) {
                separatorDrawable = extras.getSeparatorDrawable();
                iconDrawable = extras.getIconDrawable();
                customNoCouponLayoutRef = extras.getNoCouponLayout();
                enableNetErrorDialog = extras.isEnableNetErrorDialog();
                jaggedBorders = extras.isJaggedBorders();
                noSeparator = extras.isNoSeparator();
                noIcon = extras.isNoIcon();
                validOnly = extras.isValidOnly();
                expiredOnly = extras.isExpiredOnly();
                inactiveOnly = extras.isInactiveOnly();
                redeemedOnly = extras.isRedeemedOnly();
                includeRedeemed = extras.isIncludeRedeemed();
            }
        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.nearit_ui_fragment_coupon_list, container, false);

        noCouponText = rootView.findViewById(R.id.no_coupons_text);
        noCouponContainer = rootView.findViewById(R.id.empty_layout);

        if (customNoCouponLayoutRef != 0) {
            customNoCoupon = inflater.inflate(customNoCouponLayoutRef, noCouponContainer, false);
            noCouponContainer.addView(customNoCoupon);
        } else {
            noCouponText.setVisibility(View.VISIBLE);
        }

        refreshLayout = rootView.findViewById(R.id.refresh_layout);

        triggerRefresh();

        couponAdapter = new CouponAdapter(getContext(), this, iconDrawable, noIcon, jaggedBorders);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        RecyclerView couponsRecyclerView = rootView.findViewById(R.id.coupons_list);
        couponsRecyclerView.setLayoutManager(layoutManager);
        couponsRecyclerView.setAdapter(couponAdapter);

        return rootView;
    }

    private void triggerRefresh() {
        if (refreshLayout != null) {
            refreshLayout.post(new Runnable() {
                @Override
                public void run() {
                    refreshLayout.setRefreshing(true);
                    downloadCoupons();
                }
            });
            refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    refreshLayout.setRefreshing(true);
                    downloadCoupons();
                }
            });
        }
    }

    private void downloadCoupons() {
        NearItManager.getInstance().getCoupons(new CouponListener() {
            @Override
            public void onCouponsDownloaded(List<Coupon> list) {
                couponList = list;

                if (validOnly) {
                    couponAdapter.addValidOnly(couponList, includeRedeemed);
                } else if (inactiveOnly) {
                    couponAdapter.addInactiveOnly(couponList);
                } else if (expiredOnly) {
                    couponAdapter.addExpiredOnly(couponList, includeRedeemed);
                } else if (redeemedOnly) {
                    couponAdapter.addRedeemedOnly(couponList);
                } else {
                    couponAdapter.addData(couponList, includeRedeemed);
                }

                couponAdapter.notifyDataSetChanged();

                if (couponAdapter.getItemCount() > 0) {
                    if(customNoCoupon != null) {
                        customNoCoupon.setVisibility(View.GONE);
                        noCouponContainer.setVisibility(View.GONE);
                    }
                    noCouponText.setVisibility(View.GONE);
                }
                if (refreshLayout != null) {
                    refreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onCouponDownloadError(String s) {
                couponList = new ArrayList<>();
                couponAdapter.addData(couponList, false);
                couponAdapter.notifyDataSetChanged();
                if (couponAdapter.getItemCount() == 0) {
                    if (customNoCoupon != null) {
                        customNoCoupon.setVisibility(View.VISIBLE);
                        noCouponContainer.setVisibility(View.VISIBLE);
                    } else {
                        noCouponText.setVisibility(View.VISIBLE);
                    }
                }
                if (refreshLayout != null) {
                    refreshLayout.setRefreshing(false);
                }
                if (enableNetErrorDialog && isAdded() && getActivity() != null){
                    startActivityForResult(NearItUIWarningDialogActivity.createIntent(getActivity()), NEAR_OPEN_WARNING_CODE);
                }
            }
        });
    }

    @Override
    public void onCouponClicked(Coupon coupon) {
        CouponDetailIntentBuilder builder = NearITUIBindings.getInstance(getContext()).createCouponDetailIntentBuilder(coupon);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == NEAR_OPEN_WARNING_CODE) {
            if (resultCode == NEAR_RETRY_CODE) {
                triggerRefresh();
            }
        }
    }

}
