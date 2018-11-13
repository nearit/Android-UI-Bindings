package com.nearit.ui_bindings.coupon;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.nearit.ui_bindings.nearXing.BarcodeFormat;
import com.nearit.ui_bindings.nearXing.WriterException;
import com.nearit.ui_bindings.nearXing.common.BitMatrix;
import com.nearit.ui_bindings.nearXing.qrcode.QRCodeWriter;

/**
 * @author Federico Boschini
 */

public class QRcodeGenerator extends AsyncTask<String, Void, Bitmap> {

    private final int width;
    private final int height;

    @Nullable
    private GeneratorListener listener;

    public QRcodeGenerator(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void setListener(@NonNull GeneratorListener listener) {
        this.listener = listener;
    }

    @Override
    protected Bitmap doInBackground(String... strings) {
        Bitmap bitmap;

        bitmap = generateQR(strings[0], width, height);

        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap qrCode) {
        if (listener != null) {
            listener.onComplete(qrCode);
        }
    }

    private Bitmap generateQR(String serial, int width, int height) {
        QRCodeWriter writer = new QRCodeWriter();
        BitMatrix matrix = null;
        try {
            matrix = writer.encode(serial, BarcodeFormat.QR_CODE, width, height);
        } catch (WriterException ex) {
            if (listener != null) {
                listener.onError();
            }
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

    public interface GeneratorListener {
        void onComplete(Bitmap qrCode);
        void onError();
    }
}
