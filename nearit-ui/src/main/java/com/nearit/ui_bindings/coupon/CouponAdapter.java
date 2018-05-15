package com.nearit.ui_bindings.coupon;

import android.content.Context;
import android.support.annotation.NonNull;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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

class CouponAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    @Nullable
    private List<Coupon> couponList;
    private final Context context;
    private final Item.CouponListener couponListener;
    private int iconPlaceholderResId = 0;
    private final boolean noIcon;
    private final boolean jaggedBorders;

    CouponAdapter(Context context, Item.CouponListener couponListener, int iconPlaceholderResId, boolean noIcon, boolean jaggedBorders) {
        this.context = context;
        this.couponListener = couponListener;
        this.iconPlaceholderResId = iconPlaceholderResId;
        this.noIcon = noIcon;
        this.jaggedBorders = jaggedBorders;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (jaggedBorders) {
            return new Item(LayoutInflater.from(context).inflate(R.layout.nearit_ui_layout_jagged_coupon_preview, parent, false), couponListener, context, iconPlaceholderResId, noIcon);
        } else {
            return new Item(LayoutInflater.from(context).inflate(R.layout.nearit_ui_layout_coupon_preview, parent, false), couponListener, context, iconPlaceholderResId, noIcon);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Item iHolder = (Item) holder;
        if (couponList != null) {
            iHolder.bindData(couponList.get(position));
        }
    }

    void addData(List<Coupon> couponList) {
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

    @Override
    public int getItemCount() {
        return couponList != null ? couponList.size() : 0;
    }

    static class Item extends RecyclerView.ViewHolder {
        final CouponListener couponListener;
        final Context context;
        final View itemView;
        final int iconPlaceholderResId;
        final boolean noIcon;
        @Nullable
        final
        ImageView couponIcon;
        @Nullable
        final
        ProgressBar iconProgressBar;
        @Nullable
        final
        TextView couponTitle;
        @Nullable
        final TextView couponValue;
        @Nullable
        final TextView couponValidity;
        private final SimpleDateFormat formatDate;

        Item(View itemView, CouponListener couponListener, Context context, int iconPlaceholderResId, boolean noIcon) {
            super(itemView);
            this.formatDate = new SimpleDateFormat(context.getResources().getString(R.string.nearit_ui_coupon_date_pretty_format), Locale.US);
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
            if (noIcon) {
                if (couponIcon != null) {
                    couponIcon.setVisibility(View.GONE);
                }
                if (iconProgressBar != null) {
                    iconProgressBar.setVisibility(View.GONE);
                }
            } else {
                if (iconPlaceholderResId != 0) {
                    if (couponIcon != null) {
                        couponIcon.setImageResource(iconPlaceholderResId);
                    }
                }
                if (coupon.getIconSet() != null) {
                    new LoadImageFromURL(couponIcon, iconProgressBar).execute(coupon.getIconSet().getFullSize());
                }
            }
            if (couponTitle != null) {
                couponTitle.setText(coupon.getTitle());
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
                setRedeemed();
            } else {
                if (expiresAt != null) {
                    if (System.currentTimeMillis() < expiresAt.getTime()) {
                        if (redeemableFrom != null) {
                            if (System.currentTimeMillis() > redeemableFrom.getTime()) {
                                setValid();
                            } else {
                                setInactive(redeemableFrom);
                            }
                        } else {
                            setValid();
                        }
                    } else {
                        setExpired();
                    }
                } else {
                    if (redeemableFrom != null) {
                        if (System.currentTimeMillis() > redeemableFrom.getTime()) {
                            setValid();
                        } else {
                            setInactive(redeemableFrom);
                        }
                    } else {
                        setValid();
                    }
                }
            }
        }

        void setValid() {
            if (couponValidity != null) {
                couponValidity.setText(context.getResources().getString(R.string.nearit_ui_coupon_valid_text));
                couponValidity.setTextColor(ContextCompat.getColor(context, R.color.nearit_ui_coupon_list_valid_text_color));
            }
        }

        void setInactive(Date redeemableFromDate) {
            if (couponValidity != null) {
                String formattedRedeem;
                formattedRedeem = formatDate.format(redeemableFromDate);
                couponValidity.setText(context.getResources().getString(R.string.nearit_ui_coupon_list_inactive_text).concat(" "+formattedRedeem));
                couponValidity.setTextColor(ContextCompat.getColor(context, R.color.nearit_ui_coupon_list_inactive_text_color));
            }
        }

        void setExpired() {
            if (couponValidity != null) {
                couponValidity.setText(context.getResources().getString(R.string.nearit_ui_coupon_expired_text));
                couponValidity.setTextColor(ContextCompat.getColor(context, R.color.nearit_ui_coupon_list_expired_text_color));
            }
        }

        void setRedeemed() {
            if (couponValidity != null) {
                couponValidity.setText(context.getResources().getString(R.string.nearit_ui_coupon_redeemed_text));
                couponValidity.setTextColor(ContextCompat.getColor(context, R.color.nearit_ui_coupon_list_redeemed_text_color));
            }
        }

    }
}
