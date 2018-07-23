package com.nearit.ui_bindings.coupon.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nearit.ui_bindings.R;
import com.nearit.ui_bindings.coupon.CouponConstants;
import com.nearit.ui_bindings.coupon.QRcodeGenerator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import it.near.sdk.reactions.couponplugin.model.Coupon;

import static com.nearit.ui_bindings.coupon.CouponConstants.EXPIRED;
import static com.nearit.ui_bindings.coupon.CouponConstants.NOT_YET_VALID;
import static com.nearit.ui_bindings.coupon.CouponConstants.REDEEMED;
import static com.nearit.ui_bindings.coupon.CouponConstants.VALID;

/**
 * @author Federico Boschini
 */

public class CouponDetailTopSection extends RelativeLayout {

    private final SimpleDateFormat formatDate = new SimpleDateFormat(getResources().getString(R.string.nearit_ui_coupon_date_pretty_format), Locale.US);

    @Nullable
    private TextView notValidTextView;
    @Nullable
    private LinearLayout qrCodeContainer;
    @Nullable
    private ImageView qrCodeImageView;
    @Nullable
    private TextView serialTextView;
    @Nullable
    private ProgressBar qrCodeSpinner;
    @Nullable
    private TextView validityTextView;
    @Nullable
    private TextView validityPeriodTextView;

    private QRcodeGenerator qRcodeGenerator;

    public CouponDetailTopSection(Context context) {
        super(context);
    }

    public CouponDetailTopSection(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CouponDetailTopSection(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.nearit_ui_layout_coupon_detail_top_section, this);
        notValidTextView = findViewById(R.id.validity_text);

        validityTextView = findViewById(R.id.coupon_validity);
        validityPeriodTextView = findViewById(R.id.coupon_validity_period);

        qrCodeContainer = findViewById(R.id.qr_code_container);
        qrCodeSpinner = findViewById(R.id.qr_code_progress_bar);
        qrCodeImageView = findViewById(R.id.qr_code);
        serialTextView = findViewById(R.id.coupon_serial);

        qRcodeGenerator = new QRcodeGenerator(250, 250, new QRcodeGenerator.GeneratorListener() {
            @Override
            public void onComplete(Bitmap qrcode) {
                if (qrCodeImageView != null) {
                    qrCodeImageView.setVisibility(VISIBLE);
                    qrCodeImageView.setImageBitmap(qrcode);
                }
                if (qrCodeSpinner != null) {
                    qrCodeSpinner.setVisibility(GONE);
                }
            }
        });
    }

    public void setCouponView(Coupon coupon) {
        if (validityPeriodTextView != null) {
            String formattedExp = "", formattedRedeem = "";
            String validityPeriod = "";

            validityPeriodTextView.setVisibility(VISIBLE);

            if (validityTextView != null) {
                validityTextView.setText(getResources().getString(R.string.nearit_ui_coupon_validity_label_valid));
            }

            if (coupon.getRedeemableFromDate() != null && coupon.getExpiresAtDate() != null) {
                validityPeriod = getResources().getString(R.string.nearit_ui_coupon_validity_period_from_to);
                formattedRedeem = formatDate.format(coupon.getRedeemableFromDate());
                formattedExp = formatDate.format(coupon.getExpiresAtDate());
            } else if (coupon.getExpiresAtDate() != null) {
                validityPeriod = getResources().getString(R.string.nearit_ui_coupon_validity_period_until);
                formattedExp = formatDate.format(coupon.getExpiresAtDate());
            } else if (coupon.getRedeemableFromDate() != null) {
                validityPeriod = getResources().getString(R.string.nearit_ui_coupon_validity_period_from);
                formattedRedeem = formatDate.format(coupon.getRedeemableFromDate());
            } else {
                if (validityTextView != null) {
                    validityTextView.setText(R.string.nearit_ui_coupon_validity_label_valid_no_period);
                }
                validityPeriodTextView.setVisibility(GONE);
            }
            validityPeriodTextView.setText(String.format(validityPeriod, formattedRedeem, formattedExp));
        }

        switch (checkValidity(coupon)) {
            case VALID:
                if (validityTextView != null) {
                    validityTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.nearit_ui_coupon_detail_valid_color));
                }
                if (notValidTextView != null) {
                    notValidTextView.setVisibility(GONE);
                }
                if (qrCodeImageView != null) {
                    if (coupon.getSerial() != null) {
                        qrCodeImageView.setVisibility(View.GONE);
                        if (qrCodeSpinner != null) {
                            qrCodeSpinner.setVisibility(View.VISIBLE);
                        }
                        qRcodeGenerator.execute(coupon.getSerial());
                    }
                }
                if (serialTextView != null) {
                    serialTextView.setText(coupon.getSerial());
                }
                break;
            case EXPIRED:
                if (validityTextView != null) {
                    validityTextView.setText(getResources().getString(R.string.nearit_ui_coupon_validity_label_valid));
                    validityTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.nearit_ui_coupon_detail_expired_color));
                }
                if (notValidTextView != null) {
                    notValidTextView.setVisibility(VISIBLE);
                    notValidTextView.setText(getResources().getString(R.string.nearit_ui_coupon_expired_text));
                    notValidTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.nearit_ui_coupon_detail_expired_color));
                }

                if (qrCodeContainer != null) {
                    qrCodeContainer.setVisibility(GONE);
                }
                if (qrCodeImageView != null) {
                    qrCodeImageView.setVisibility(GONE);
                }
                if (serialTextView != null) {
                    serialTextView.setVisibility(GONE);
                }
                if (qrCodeSpinner != null) {
                    qrCodeSpinner.setVisibility(GONE);
                }
                break;
            case NOT_YET_VALID:
                if (validityTextView != null) {
                    validityTextView.setText(getResources().getString(R.string.nearit_ui_coupon_validity_label_valid));
                    validityTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.nearit_ui_coupon_detail_inactive_color));
                }
                if (notValidTextView != null) {
                    notValidTextView.setVisibility(VISIBLE);
                    notValidTextView.setText(getResources().getString(R.string.nearit_ui_coupon_inactive_text));
                    notValidTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.nearit_ui_coupon_detail_inactive_color));
                }

                if (qrCodeContainer != null) {
                    qrCodeContainer.setVisibility(GONE);
                }
                if (qrCodeImageView != null) {
                    qrCodeImageView.setVisibility(GONE);
                }
                if (serialTextView != null) {
                    serialTextView.setVisibility(GONE);
                }
                if (qrCodeSpinner != null) {
                    qrCodeSpinner.setVisibility(GONE);
                }
                break;
            case REDEEMED:
                if (validityTextView != null) {
                    validityTextView.setText(getResources().getString(R.string.nearit_ui_coupon_validity_label_redeemed));
                    validityTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.nearit_ui_coupon_detail_expired_color));
                }
                validityPeriodTextView.setText(formatDate.format(coupon.getRedeemedAtDate()));
                if (notValidTextView != null) {
                    notValidTextView.setVisibility(VISIBLE);
                    notValidTextView.setText(getResources().getString(R.string.nearit_ui_coupon_redeemed_text));
                    notValidTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.nearit_ui_coupon_detail_expired_color));
                }

                if (qrCodeContainer != null) {
                    qrCodeContainer.setVisibility(GONE);
                }
                if (qrCodeImageView != null) {
                    qrCodeImageView.setVisibility(GONE);
                }
                if (serialTextView != null) {
                    serialTextView.setVisibility(GONE);
                }
                if (qrCodeSpinner != null) {
                    qrCodeSpinner.setVisibility(GONE);
                }
                break;
        }
    }

    private int checkValidity(Coupon coupon) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(CouponConstants.date, Locale.US);
        Date expirationDate = null, redeemabilityDate = null;

        try {
            expirationDate = dateFormat.parse(CouponConstants.lastDay);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        try {
            redeemabilityDate = dateFormat.parse(CouponConstants.firstDay);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (coupon.getExpiresAtDate() != null) {
            expirationDate = coupon.getExpiresAtDate();
        }

        if (coupon.getRedeemableFromDate() != null) {
            redeemabilityDate = coupon.getRedeemableFromDate();
        }


        if (coupon.getRedeemedAtDate() != null) {
            return REDEEMED;
        }
        if (expirationDate != null) {
            if (System.currentTimeMillis() > expirationDate.getTime()) {
                return EXPIRED;
            } else if (redeemabilityDate != null) {
                if (System.currentTimeMillis() > redeemabilityDate.getTime()) {
                    return VALID;
                } else return NOT_YET_VALID;
            }
        }
        return EXPIRED;
    }
}
