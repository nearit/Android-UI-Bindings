package com.nearit.ui_bindings.coupon;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
    private int iconPlaceholderResId = 0;
    private boolean noIcon;

    CouponAdapter(Context context, Item.CouponListener couponListener, int iconPlaceholderResId, boolean noIcon) {
        this.context = context;
        this.couponListener = couponListener;
        this.iconPlaceholderResId = iconPlaceholderResId;
        this.noIcon = noIcon;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Item(LayoutInflater.from(context).inflate(R.layout.nearit_ui_layout_coupon_preview, parent, false), couponListener, context, iconPlaceholderResId, noIcon);
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
        int iconPlaceholderResId;
        boolean noIcon;
        @Nullable
        ImageView couponIcon;
        @Nullable
        ProgressBar iconProgressBar;
        @Nullable
        TextView couponTitle, couponValue, couponValidity;

        Item(View itemView, CouponListener couponListener, Context context, int iconPlaceholderResId, boolean noIcon) {
            super(itemView);
            this.itemView = itemView;
            this.couponListener = couponListener;
            this.context = context;
            this.iconPlaceholderResId = iconPlaceholderResId;
            this.noIcon = noIcon;
            couponIcon = itemView.findViewById(R.id.coupon_icon);
            iconProgressBar = itemView.findViewById(R.id.coupon_icon_progress_bar);
            couponTitle = itemView.findViewById(R.id.coupon_title);
            couponValue = itemView.findViewById(R.id.coupon_value);
            couponValidity = itemView.findViewById(R.id.coupon_validity);
        }

        interface CouponListener {
            void onCouponClicked(Coupon coupon);
        }

        void bindData(final Coupon coupon) {
            if(noIcon) {
                if (couponIcon != null) {
                    couponIcon.setVisibility(View.GONE);
                }
                if (iconProgressBar != null) {
                    iconProgressBar.setVisibility(View.GONE);
                }
            } else {
                if(iconPlaceholderResId != 0) {
                    if (couponIcon != null) {
                        couponIcon.setImageResource(iconPlaceholderResId);
                    }
                }
                if (coupon.getIconSet() != null) {
                    new LoadImageFromURL(couponIcon, iconProgressBar).execute(coupon.getIconSet().getFullSize());
                }
            }
            if (couponTitle != null) {
                couponTitle.setText(coupon.name);
            }
            if (couponValue != null) {
                couponValue.setText(coupon.value);
            }
            setValidity(coupon.getRedeemableFromDate(), coupon.getExpiresAtDate(), coupon.getRedeemedAtDate());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    couponListener.onCouponClicked(coupon);
                }
            });
        }

        void setValidity(@Nullable Date redeemableFrom, @Nullable Date expiresAt, @Nullable Date redeemedAt) {
            if (redeemedAt != null) {
                if (couponValidity != null) {
                    couponValidity.setText(context.getResources().getString(R.string.nearit_ui_coupon_redeemed_text));
                }
                couponValidity.setTextColor(ContextCompat.getColor(context, R.color.nearit_ui_coupon_list_redeemed_text_color));
            } else {
                if (expiresAt != null) {
                    if (System.currentTimeMillis() < expiresAt.getTime()) {
                        if (redeemableFrom != null) {
                            if (System.currentTimeMillis() > redeemableFrom.getTime()) {
                                if (couponValidity != null) {
                                    couponValidity.setText(context.getResources().getString(R.string.nearit_ui_coupon_valid_text));
                                }
                                couponValidity.setTextColor(ContextCompat.getColor(context, R.color.nearit_ui_coupon_list_valid_text_color));
                            } else {
                                if (couponValidity != null) {
                                    couponValidity.setText(context.getResources().getString(R.string.nearit_ui_coupon_inactive_text));
                                }
                                couponValidity.setTextColor(ContextCompat.getColor(context, R.color.nearit_ui_coupon_list_inactive_text_color));
                            }
                        }
                    } else {
                        if (couponValidity != null) {
                            couponValidity.setText(context.getResources().getString(R.string.nearit_ui_coupon_expired_text));
                        }
                        couponValidity.setTextColor(ContextCompat.getColor(context, R.color.nearit_ui_coupon_list_expired_text_color));
                    }
                }
            }
        }
    }
}
