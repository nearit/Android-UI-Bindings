package com.nearit.ui_bindings.coupon;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.nearit.strippedzxing.BarcodeFormat;
import com.nearit.strippedzxing.WriterException;
import com.nearit.strippedzxing.common.BitMatrix;
import com.nearit.strippedzxing.qrcode.QRCodeWriter;

/**
 * Created by Federico Boschini on 07/09/17.
 */

public class QRcodeGenerator extends AsyncTask<String, Void, Bitmap> {
    @Nullable
    private ImageView imageView;
    @Nullable
    private ProgressBar progressBar;
    private int width;
    private int height;

    public QRcodeGenerator(@Nullable ImageView imageView, int width, int height, @Nullable ProgressBar progressBar) {
        this.imageView = imageView;
        this.width = width;
        this.height = height;
        this.progressBar = progressBar;
    }

    QRcodeGenerator(@Nullable ImageView imageView, int width, int height) {
        this.imageView = imageView;
        this.width = width;
        this.height = height;
    }

    QRcodeGenerator(@Nullable ImageView imageView) {
        this.imageView = imageView;
        this.width = 250;
        this.height = 250;
    }

    @Override
    protected void onPreExecute() {
        if (imageView != null) {
            imageView.setVisibility(View.GONE);
        }
        if (progressBar != null) {
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected Bitmap doInBackground(String... strings) {
        Bitmap bitmap;

        bitmap = generateQR(strings[0], width, height);

        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap qrCode) {
        if (imageView != null) {
            imageView.setVisibility(View.VISIBLE);
            if (qrCode != null) {
                imageView.setImageBitmap(qrCode);
            }
        }
        if (progressBar != null) {
            progressBar.setVisibility(View.GONE);
        }
    }

    private Bitmap generateQR(String serial, int width, int height) {
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
