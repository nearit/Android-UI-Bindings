package com.nearit.ui_bindings.coupon.list;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nearit.ui_bindings.utils.images.Image;
import com.nearit.ui_bindings.utils.images.ImageDownloadListener;
import com.nearit.ui_bindings.utils.images.NearItImageDownloader;
import com.nearit.ui_bindings.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import it.near.sdk.reactions.couponplugin.model.Coupon;

/**
 * @author Federico Boschini
 */
class CouponViewHolder extends RecyclerView.ViewHolder {

    private final CouponAdapter.CouponAdapterListener couponListener;
    private final Context context;
    private final View itemView;
    private final int iconPlaceholderResId;
    private final boolean noIcon;
    @Nullable
    private final ImageView couponIcon;
    @Nullable
    private final ProgressBar iconProgressBar;
    @Nullable
    private final TextView couponTitle;
    @Nullable
    private final TextView couponValue;
    @Nullable
    private final TextView couponValidity;
    private final SimpleDateFormat formatDate;

    CouponViewHolder(View itemView, CouponAdapter.CouponAdapterListener couponListener, Context context, int iconPlaceholderResId, boolean noIcon) {
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

    void setItem(final Coupon coupon) {
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
                if (couponIcon != null) {
                    couponIcon.setVisibility(View.GONE);
                }
                if (iconProgressBar != null) {
                    iconProgressBar.setVisibility(View.VISIBLE);
                }

                NearItImageDownloader.getInstance().downloadImage(coupon.getIconSet().getFullSize(), new ImageDownloadListener() {
                    @Override
                    public void onSuccess(@Nullable Image image) {
                        if (couponIcon != null) {
                            couponIcon.setVisibility(View.VISIBLE);
                            if (image != null && (image.getUrl().equals(coupon.getIconSet().getFullSize()))) {
                                couponIcon.setScaleType(ImageView.ScaleType.FIT_XY);
                                couponIcon.setAdjustViewBounds(true);
                                couponIcon.setMinimumHeight(0);
                                couponIcon.setImageBitmap(image.getBitmap());
                            }
                        }
                        if (iconProgressBar != null) {
                            iconProgressBar.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onError() {
                        hideSpinnerAndSetDefault();
                    }
                });
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

    private void hideSpinnerAndSetDefault() {
        if (couponIcon != null) {
            couponIcon.setVisibility(View.VISIBLE);
        }
        if (iconProgressBar != null) {
            iconProgressBar.setVisibility(View.GONE);
        }
    }

    private void setValidity(@Nullable Date redeemableFrom, @Nullable Date expiresAt, @Nullable Date redeemedAt) {
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

    private void setValid() {
        if (couponValidity != null) {
            couponValidity.setText(context.getResources().getString(R.string.nearit_ui_coupon_list_valid_text));
            couponValidity.setTextColor(ContextCompat.getColor(context, R.color.nearit_ui_coupon_list_valid_text_color));
        }
    }

    private void setInactive(Date redeemableFromDate) {
        if (couponValidity != null) {
            String formattedRedeem;
            formattedRedeem = formatDate.format(redeemableFromDate);
            couponValidity.setText(context.getResources().getString(R.string.nearit_ui_coupon_list_inactive_text).concat(" " + formattedRedeem));
            couponValidity.setTextColor(ContextCompat.getColor(context, R.color.nearit_ui_coupon_list_inactive_text_color));
        }
    }

    private void setExpired() {
        if (couponValidity != null) {
            couponValidity.setText(context.getResources().getString(R.string.nearit_ui_coupon_list_expired_text));
            couponValidity.setTextColor(ContextCompat.getColor(context, R.color.nearit_ui_coupon_list_expired_text_color));
        }
    }

    private void setRedeemed() {
        if (couponValidity != null) {
            couponValidity.setText(context.getResources().getString(R.string.nearit_ui_coupon_list_redeemed_text));
            couponValidity.setTextColor(ContextCompat.getColor(context, R.color.nearit_ui_coupon_list_redeemed_text_color));
        }
    }
}
