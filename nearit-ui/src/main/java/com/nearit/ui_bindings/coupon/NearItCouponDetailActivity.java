package com.nearit.ui_bindings.coupon;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.nearit.ui_bindings.ExtraConstants;
import com.nearit.ui_bindings.R;
import com.nearit.ui_bindings.coupon.views.CouponDetailTopSection;
import com.nearit.ui_bindings.utils.LoadImageFromURL;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import it.near.sdk.reactions.couponplugin.model.Coupon;

/**
 * Created by Federico Boschini on 06/09/17.
 */

public class NearItCouponDetailActivity extends AppCompatActivity {

    private final static String TAG = "NearItCouponDetailActivity";
    private final static String serial = "0123456789";

    private static final int VALID = 1;
    private static final int EXPIRED = -1;
    private static final int NOT_YET_VALID = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.nearit_ui_activity_coupon_detail);

        Coupon coupon = getIntent().getParcelableExtra(ExtraConstants.COUPON_EXTRA);

        CouponDetailTopSection topSection = (CouponDetailTopSection) findViewById(R.id.coupon_detail_top_section);

        switch (checkValidity(coupon)) {
            case VALID:
                topSection.setValid(coupon.getSerial());
                break;
            case NOT_YET_VALID:
                topSection.setNotValid("Not yet valid coupon");
                break;
            case EXPIRED:
                topSection.setNotValid("Expired coupon");
                break;
        }

        ProgressBar iconProgressBar = (ProgressBar) findViewById(R.id.coupon_icon_progress_bar);
        ImageView couponIcon = (ImageView) findViewById(R.id.coupon_icon);
        new LoadImageFromURL(couponIcon, iconProgressBar).execute("https://res.cloudinary.com/crunchbase-production/image/upload/v1469444473/t2gogkgbugvfjhcaokz6.png");

    }

    public static Intent createIntent(Context context, Coupon coupon) {
        return new Intent(context, NearItCouponDetailActivity.class).putExtra(ExtraConstants.COUPON_EXTRA, coupon);
    }

    public int checkValidity(Coupon coupon) {
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

        if (expirationDate != null) {
            if(System.currentTimeMillis() > expirationDate.getTime()) {
                return EXPIRED;
            } else if (redeemabilityDate != null) {
                if(System.currentTimeMillis() > redeemabilityDate.getTime()) {
                    return VALID;
                } else return NOT_YET_VALID;
            }
        }
        return -1;
    }

}
