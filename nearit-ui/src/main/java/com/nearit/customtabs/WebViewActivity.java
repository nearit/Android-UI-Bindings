// Copyright 2015 Google Inc. All Rights Reserved.
//
// EDIT by Federico Boschini <federico@nearit.com>
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.nearit.customtabs;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.nearit.ui_bindings.R;

/**
 * This Activity is used as a fallback when there is no browser installed that supports
 * Chrome Custom Tabs
 */
public class WebViewActivity extends AppCompatActivity {

    public static final String EXTRA_URL = "extra.url";
    public static final String EXTRA_HOME_AS_UP_INDICATOR = "extra.home.as.up";

    private WebView webView;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nearit_ui_activity_webview);

        webView = findViewById(R.id.webview);
        webView.setWebViewClient(new WebViewClient());

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        if (getIntent() != null) {

            if (getIntent().hasExtra(EXTRA_URL)) {
                String url = getIntent().getStringExtra(EXTRA_URL);
                setTitle(url);
                webView.loadUrl(url);
            }
            if (getIntent().hasExtra(EXTRA_HOME_AS_UP_INDICATOR)) {
                int homeAsUpIndicatorResId = getIntent().getIntExtra(EXTRA_HOME_AS_UP_INDICATOR, 0);
                if (getSupportActionBar() != null) {
                    if (homeAsUpIndicatorResId != 0) {
                        getSupportActionBar().setHomeAsUpIndicator(homeAsUpIndicatorResId);
                    }
                }
            }


        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
