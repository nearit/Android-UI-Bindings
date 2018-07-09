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
 * @author Federico Boschini
 */

public final class LoadImageFromURL extends AsyncTask<String, Void, Bitmap> {
    @Nullable
    private final ImageView imageView;
    @Nullable
    private final ProgressBar progressBar;
    private final boolean enableReload;

    private String url;
    private static final boolean ENABLE_RELOAD_DEFAULT = false;
    private static final ProgressBar PROGRESS_BAR_DEFAULT = null;

    public LoadImageFromURL(@Nullable ImageView imageView) {
        this(imageView, PROGRESS_BAR_DEFAULT);
    }

    public LoadImageFromURL(@Nullable ImageView imageView, @Nullable ProgressBar progressBar) {
        this(imageView, progressBar, ENABLE_RELOAD_DEFAULT);
    }

    public LoadImageFromURL(@Nullable ImageView imageView, @Nullable ProgressBar progressBar, boolean enableReload) {
        this.imageView = imageView;
        this.progressBar = progressBar;
        this.enableReload = enableReload;
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
        this.url = url;
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
                //  show icon
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                imageView.setAdjustViewBounds(true);
                imageView.setMinimumHeight(0);
                /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    imageView.setBackground(null);
                } else {
                    imageView.setBackgroundDrawable(null);
                }*/
                imageView.setImageBitmap(icon);
            } else {
                //  show reload
                if (enableReload) {
                    imageView.setScaleType(ImageView.ScaleType.CENTER);
                    imageView.setAdjustViewBounds(false);
                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            new LoadImageFromURL(imageView, progressBar).execute(url);
                        }
                    });
                }
            }
        }
        if (progressBar != null) {
            progressBar.setVisibility(View.GONE);
        }
    }
}
