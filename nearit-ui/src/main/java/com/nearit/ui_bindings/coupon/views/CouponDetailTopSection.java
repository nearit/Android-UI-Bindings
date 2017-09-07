package com.nearit.ui_bindings.coupon.views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nearit.ui_bindings.R;
import com.nearit.ui_bindings.coupon.QRcodeGenerator;

/**
 * Created by Federico Boschini on 07/09/17.
 */

public class CouponDetailTopSection extends RelativeLayout {
    @Nullable
    private TextView validityTextView;
    @Nullable
    private ImageView qrCodeImageView;
    @Nullable
    private TextView serialTextView;
    @Nullable
    private ProgressBar spinner;

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
        validityTextView = (TextView) findViewById(R.id.validity_text);
        qrCodeImageView = (ImageView) findViewById(R.id.qr_code);
        serialTextView = (TextView) findViewById(R.id.coupon_serial);
        spinner = (ProgressBar) findViewById(R.id.qr_code_progress_bar);
    }

    public void setValid(String serialText) {
        if (validityTextView != null) {
            validityTextView.setVisibility(GONE);
        }
        if (qrCodeImageView != null) {
            new QRcodeGenerator(qrCodeImageView, 250, 250, spinner).execute(serialText);
        }
        if (serialTextView != null) {
            serialTextView.setText(serialText);
        }
    }

    public void setNotValid(String validityText) {
        if (qrCodeImageView != null) {
            qrCodeImageView.setVisibility(GONE);
        }
        if (serialTextView != null) {
            serialTextView.setVisibility(GONE);
        }
        if (spinner != null) {
            spinner.setVisibility(GONE);
        }
        if (validityTextView != null) {
            validityTextView.setVisibility(VISIBLE);
            validityTextView.setText(validityText);
        }
    }
}
