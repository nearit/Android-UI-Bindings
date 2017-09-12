package com.nearit.ui_bindings.coupon;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nearit.ui_bindings.ExtraConstants;
import com.nearit.ui_bindings.R;
import com.nearit.ui_bindings.coupon.views.CouponDetailTopSection;
import com.nearit.ui_bindings.utils.LoadImageFromURL;

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
 * Created by Federico Boschini on 06/09/17.
 */

public class NearItCouponDetailActivity extends AppCompatActivity {

    private final static String TAG = "NearItCouponDetailActivity";

    @Nullable
    private ImageView couponIcon;
    @Nullable
    private ProgressBar iconProgressBar;
    @Nullable
    private TextView couponName, couponValue, couponDescription;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.nearit_ui_activity_coupon_detail);

        Coupon coupon = getIntent().getParcelableExtra(ExtraConstants.COUPON_EXTRA);

        CouponDetailTopSection topSection = (CouponDetailTopSection) findViewById(R.id.coupon_detail_top_section);

        topSection.setCouponView(coupon);

        iconProgressBar = (ProgressBar) findViewById(R.id.coupon_icon_progress_bar);
        couponIcon = (ImageView) findViewById(R.id.coupon_icon);

        couponName = (TextView) findViewById(R.id.coupon_name);
        couponName.setText(coupon.name);
        couponValue = (TextView) findViewById(R.id.coupon_value);
        couponValue.setText(coupon.value);
        couponDescription = (TextView) findViewById(R.id.coupon_description);
        couponDescription.setText(coupon.description);

        if((coupon.getRedeemableFromDate() != null) && (coupon.getRedeemableFromDate().getTime() > System.currentTimeMillis())) {
            couponName.setTextColor(ContextCompat.getColor(this, R.color.nearit_ui_black_35));
            couponValue.setTextColor(ContextCompat.getColor(this, R.color.nearit_ui_black_35));
            couponDescription.setTextColor(ContextCompat.getColor(this, R.color.nearit_ui_black_35));
        }

//        new LoadImageFromURL(couponIcon, iconProgressBar).execute("https://res.cloudinary.com/crunchbase-production/image/upload/v1469444473/t2gogkgbugvfjhcaokz6.png");

    }

    public static Intent createIntent(Context context, Coupon coupon) {
        return new Intent(context, NearItCouponDetailActivity.class).putExtra(ExtraConstants.COUPON_EXTRA, coupon);
    }

}
