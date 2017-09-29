package com.nearit.ui_bindings.coupon;

import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nearit.ui_bindings.R;
import com.nearit.ui_bindings.utils.LoadImageFromURL;

import java.util.Date;
import java.util.List;

import it.near.sdk.reactions.couponplugin.model.Coupon;

/**
 * Created by Federico Boschini on 29/09/17.
 */

class CouponAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    @Nullable
    private List<Coupon> couponList;
    private Context context;
    private Item.CouponListener couponListener;

    CouponAdapter(Context context, Item.CouponListener couponListener) {
        this.context = context;
        this.couponListener = couponListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Item(LayoutInflater.from(context).inflate(R.layout.nearit_ui_layout_coupon_preview, parent, false), couponListener, context);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Item iHolder = (Item) holder;
        if (couponList != null) {
            iHolder.bindData(couponList.get(position));
        }
    }

    void addData(List<Coupon> couponList) {
        this.couponList = couponList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return couponList != null ? couponList.size() : 0;
    }

    static class Item extends RecyclerView.ViewHolder {
        CouponListener couponListener;
        Context context;
        View itemView;
        @Nullable
        ImageView couponIcon;
        @Nullable
        ProgressBar iconProgressBar;
        @Nullable
        TextView couponTitle, couponValue, couponValidity;

        Item(View itemView, CouponListener couponListener, Context context) {
            super(itemView);
            this.itemView = itemView;
            this.couponListener = couponListener;
            this.context = context;
            couponIcon = (ImageView) itemView.findViewById(R.id.coupon_icon);
            iconProgressBar = (ProgressBar) itemView.findViewById(R.id.coupon_icon_progress_bar);
            couponTitle = (TextView) itemView.findViewById(R.id.coupon_title);
            couponValue = (TextView) itemView.findViewById(R.id.coupon_value);
            couponValidity = (TextView) itemView.findViewById(R.id.coupon_validity);
        }

        interface CouponListener {
            void onCouponClicked(Coupon coupon);
        }

        void bindData(final Coupon coupon) {
            if (coupon.getIconSet() != null) {
                new LoadImageFromURL(couponIcon, iconProgressBar).execute(coupon.getIconSet().getFullSize());
            }
            if (couponTitle != null) {
                couponTitle.setText(coupon.name);
            }
            if (couponValue != null) {
                couponValue.setText(coupon.value);
            }
            setValidity(coupon.getRedeemableFromDate(), coupon.getExpiresAtDate());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    couponListener.onCouponClicked(coupon);
                }
            });
        }

        void setValidity(@Nullable Date redeemableFrom, @Nullable Date expiresAt) {
            if (expiresAt != null) {
                if (System.currentTimeMillis() < expiresAt.getTime()) {
                    if (redeemableFrom != null) {
                        if (System.currentTimeMillis() > redeemableFrom.getTime()) {
                            if (couponValidity != null) {
                                couponValidity.setText("Valid coupon");
                            }
                            couponValidity.setTextColor(ContextCompat.getColor(context, R.color.nearit_ui_coupon_list_valid_text_color));
                        } else {
                            if (couponValidity != null) {
                                couponValidity.setText("Inactive coupon");
                            }
                            couponValidity.setTextColor(ContextCompat.getColor(context, R.color.nearit_ui_coupon_list_inactive_text_color));
                        }
                    }
                } else {
                    if (couponValidity != null) {
                        couponValidity.setText("Expired coupon");
                    }
                    couponValidity.setTextColor(ContextCompat.getColor(context, R.color.nearit_ui_coupon_list_expired_text_color));
                }
            }
        }
    }
}
