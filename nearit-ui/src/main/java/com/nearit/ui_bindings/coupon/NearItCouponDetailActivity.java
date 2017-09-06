package com.nearit.ui_bindings.coupon;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.nearit.strippedzxing.BarcodeFormat;
import com.nearit.strippedzxing.WriterException;
import com.nearit.strippedzxing.common.BitMatrix;
import com.nearit.strippedzxing.qrcode.QRCodeWriter;
import com.nearit.ui_bindings.ExtraConstants;
import com.nearit.ui_bindings.R;

/**
 * Created by Federico Boschini on 06/09/17.
 */

public class NearItCouponDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        Intent intent = getIntent();
//        if(intent.hasExtra(ExtraConstants.EXTRA_FLOW_PARAMS)) {
//
//        }
        setContentView(R.layout.nearit_ui_activity_coupon_detail);

        ImageView qrCode = (ImageView) findViewById(R.id.qr_code);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            qrCode.setImageBitmap(generateQR("0123456789", 200, 200));
        }
    }

    public static Intent createIntent(Context context, CouponDetailIntentExtras params) {
        return new Intent(context, NearItCouponDetailActivity.class).putExtra(ExtraConstants.EXTRA_FLOW_PARAMS, params);
    }

    public Bitmap generateQR(String serial, int width, int height) {
        QRCodeWriter writer = new QRCodeWriter();
        BitMatrix matrix = null;
        try {
            matrix = writer.encode(serial, BarcodeFormat.QR_CODE, width, height);
        } catch (WriterException ex) {
            ex.printStackTrace();
        }
        Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (matrix != null) {
                    bmp.setPixel(x, y, matrix.get(x, y) ? Color.BLACK : Color.WHITE);
                }
            }
        }
        return bmp;

    }
}
