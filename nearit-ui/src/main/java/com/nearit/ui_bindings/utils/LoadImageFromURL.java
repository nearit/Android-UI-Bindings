package com.nearit.ui_bindings.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Created by Federico Boschini on 07/09/17.
 */

public final class LoadImageFromURL extends AsyncTask<String, Void, Bitmap> {
    @Nullable
    private ImageView imageView;
    @Nullable
    private ProgressBar progressBar;

    public LoadImageFromURL(@Nullable ImageView imageView, @Nullable ProgressBar progressBar) {
        this.imageView = imageView;
        this.progressBar = progressBar;
    }

    public LoadImageFromURL(@Nullable ImageView imageView) {
        this.imageView = imageView;
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
    protected Bitmap doInBackground(String... urls) {
        String url = urls[0];
        Bitmap icon = null;
        InputStream in;
        try {
            in = new URL(url).openStream();
            icon = BitmapFactory.decodeStream(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return icon;
    }

    @Override
    protected void onPostExecute(Bitmap icon) {
        if (imageView != null) {
            imageView.setVisibility(View.VISIBLE);
            if (icon != null) {
                imageView.setImageBitmap(icon);
            }
        }
        if (progressBar != null) {
            progressBar.setVisibility(View.GONE);
        }
    }
}
