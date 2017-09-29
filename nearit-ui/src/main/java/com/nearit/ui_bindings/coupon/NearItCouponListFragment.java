package com.nearit.ui_bindings.coupon;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nearit.ui_bindings.NearITUIBindings;
import com.nearit.ui_bindings.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import it.near.sdk.NearItManager;
import it.near.sdk.reactions.couponplugin.CouponListener;
import it.near.sdk.reactions.couponplugin.model.Coupon;

public class NearItCouponListFragment extends Fragment implements CouponAdapter.Item.CouponListener {

    private List<Coupon> couponList;
    private static final String ARG_EXTRAS = "extras";

    @Nullable
    private SwipeRefreshLayout refreshLayout;
    private CouponAdapter couponAdapter;
    private TextView noCouponPlaceholder;

    private int separatorDrawable = 0, iconDrawable = 0;
    private boolean noSeparator = false, noIcon;
    private boolean validOnly = false, expiredOnly = false, inactiveOnly = false, redeemedOnly = false, includeRedeemed = false;

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

        CouponListExtraParams extras = getArguments().getParcelable(ARG_EXTRAS);
        if (extras != null) {
            separatorDrawable = extras.getSeparatorDrawable();
            iconDrawable = extras.getIconDrawable();
            noSeparator = extras.isNoSeparator();
            noIcon = extras.isNoIcon();
            validOnly = extras.isValidOnly();
            expiredOnly = extras.isExpiredOnly();
            inactiveOnly = extras.isInactiveOnly();
            redeemedOnly = extras.isRedeemedOnly();
            includeRedeemed = extras.isIncludeRedeemed();
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.nearit_ui_fragment_coupon_list, container, false);

        noCouponPlaceholder = rootView.findViewById(R.id.no_coupons_text);

        couponAdapter = new CouponAdapter(getContext(), this, iconDrawable, noIcon);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        RecyclerView couponsRecyclerView = rootView.findViewById(R.id.coupons_list);
        couponsRecyclerView.setLayoutManager(layoutManager);
        couponsRecyclerView.setAdapter(couponAdapter);

        couponList = new ArrayList();

        for (int i = 0; i <= 5; i++) {
            CouponFactory cf = new CouponFactory();
            couponList.add(cf.getValidCoupon());
        }
        for (int i = 0; i <= 5; i++) {
            CouponFactory cf = new CouponFactory();
            couponList.add(cf.getInactiveCoupon());
        }
        for (int i = 0; i <= 5; i++) {
            CouponFactory cf = new CouponFactory();
            couponList.add(cf.getExpiredCoupon());
        }
        Collections.shuffle(couponList);

        refreshLayout = rootView.findViewById(R.id.refresh_layout);
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

        return rootView;
    }

    private void downloadCoupons() {
        NearItManager.getInstance().getCoupons(new CouponListener() {
            @Override
            public void onCouponsDownloaded(List<Coupon> list) {
                if (list.size() > 0) {
                    couponList = list;
                }
                if (!includeRedeemed) {
                    couponList = filterCoupons(couponList);
                } else {
                    if (validOnly) {
                        couponList = getValidOnly(couponList);
                    } else if (inactiveOnly) {
                        couponList = getInactiveOnly(couponList);
                    } else if (expiredOnly) {
                        couponList = getExpiredOnly(couponList);
                    } else if (redeemedOnly) {
                        couponList = getRedeemedOnly(couponList);
                    }
                }

                couponAdapter.addData(couponList);
                couponAdapter.notifyDataSetChanged();
                if (couponAdapter.getItemCount() > 0) {
                    noCouponPlaceholder.setVisibility(View.GONE);
                }
                if (refreshLayout != null) {
                    refreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onCouponDownloadError(String s) {
                if (refreshLayout != null) {
                    refreshLayout.setRefreshing(false);
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
        startActivity(builder.build());
    }

    private List<Coupon> filterCoupons(List<Coupon> couponList) {
        Iterator<Coupon> iterator = couponList.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getRedeemedAtDate() != null) {
                couponList.remove(iterator.next());
            }
        }
        return couponList;
    }

    private List<Coupon> getValidOnly(List<Coupon> couponList) {
        Iterator<Coupon> iterator = couponList.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getExpiresAtDate() != null) {
                couponList.remove(iterator.next());
            } else {
                if (iterator.next().getExpiresAtDate() != null && iterator.next().getExpiresAtDate().getTime() < System.currentTimeMillis()) {
                    couponList.remove(iterator.next());
                }
                if (iterator.next().getRedeemableFromDate() != null && iterator.next().getRedeemableFromDate().getTime() > System.currentTimeMillis()) {
                    couponList.remove(iterator.next());
                }
            }
        }
        return couponList;
    }

    private List<Coupon> getExpiredOnly(List<Coupon> couponList) {
        Iterator<Coupon> iterator = couponList.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getExpiresAtDate() != null) {
                couponList.remove(iterator.next());
            } else {
                if (iterator.next().getExpiresAtDate() != null && !(iterator.next().getExpiresAtDate().getTime() < System.currentTimeMillis())) {
                    couponList.remove(iterator.next());
                }
            }
        }
        return couponList;
    }

    private List<Coupon> getInactiveOnly(List<Coupon> couponList) {
        Iterator<Coupon> iterator = couponList.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getExpiresAtDate() != null) {
                couponList.remove(iterator.next());
            } else {
                if (iterator.next().getRedeemableFromDate() != null && !(iterator.next().getRedeemableFromDate().getTime() > System.currentTimeMillis())) {
                    couponList.remove(iterator.next());
                }
            }
        }
        return couponList;
    }

    private List<Coupon> getRedeemedOnly(List<Coupon> couponList) {
        Iterator<Coupon> iterator = couponList.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getExpiresAtDate() == null) {
                couponList.remove(iterator.next());
            }
        }
        return couponList;
    }
}
