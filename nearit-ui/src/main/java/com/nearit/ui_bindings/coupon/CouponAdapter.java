package com.nearit.ui_bindings.coupon;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.nearit.ui_bindings.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import it.near.sdk.reactions.couponplugin.model.Coupon;

import static com.nearit.ui_bindings.coupon.CouponUtils.excludeRedeemed;
import static com.nearit.ui_bindings.coupon.CouponUtils.getExpired;
import static com.nearit.ui_bindings.coupon.CouponUtils.getInactive;
import static com.nearit.ui_bindings.coupon.CouponUtils.getRedeemed;
import static com.nearit.ui_bindings.coupon.CouponUtils.getValid;
import static com.nearit.ui_bindings.coupon.CouponUtils.sortByClaimedAtDate;

/**
 * @author Federico Boschini
 */

class CouponAdapter extends RecyclerView.Adapter<CouponViewHolder> {

    private final Context context;
    private final CouponAdapterListener couponListener;

    private int iconPlaceholderResId;
    private final boolean noIcon;
    private final boolean jaggedBorders;

    private List<Coupon> couponList = Collections.emptyList();

    CouponAdapter(Context context, CouponAdapterListener couponListener, int iconPlaceholderResId, boolean noIcon, boolean jaggedBorders) {
        this.context = context;
        this.couponListener = couponListener;
        this.iconPlaceholderResId = iconPlaceholderResId;
        this.noIcon = noIcon;
        this.jaggedBorders = jaggedBorders;
    }

    void updateCoupons(List<Coupon> couponList) {
        sortByClaimedAtDate(couponList);

        //  filter out redeemed
        List<Coupon> redeemedList = getRedeemed(couponList);
        excludeRedeemed(couponList);

        List<Coupon> validList = getValid(couponList);
        List<Coupon> inactiveList = getInactive(couponList);
        List<Coupon> expiredList = getExpired(couponList);

        //  compose final list
        List<Coupon> finalList = new ArrayList<>();
        finalList.addAll(validList);
        finalList.addAll(inactiveList);
        finalList.addAll(expiredList);
        finalList.addAll(redeemedList);

        this.couponList = finalList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CouponViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (jaggedBorders) {
            return new CouponViewHolder(LayoutInflater.from(context).inflate(R.layout.nearit_ui_layout_jagged_coupon_preview, parent, false), couponListener, context, iconPlaceholderResId, noIcon);
        } else {
            return new CouponViewHolder(LayoutInflater.from(context).inflate(R.layout.nearit_ui_layout_coupon_preview, parent, false), couponListener, context, iconPlaceholderResId, noIcon);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull CouponViewHolder holder, int position) {
        holder.setItem(couponList.get(position));
    }

    @Override
    public int getItemCount() {
        return couponList.size();
    }

    public interface CouponAdapterListener {
        void onCouponClicked(Coupon coupon);
    }

}
