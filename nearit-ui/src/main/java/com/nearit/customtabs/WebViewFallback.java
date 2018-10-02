/*
 * Copyright 2017 Sascha Peilicke
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.nearit.customtabs;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * Default {@link CustomTabsHelper.CustomTabFallback} implementation
 * that uses {@link WebViewActivity} to display the requested {@link Uri}.
 */
public final class WebViewFallback implements CustomTabsHelper.CustomTabFallback {
    /**
     * @param context The {@link Context} that wants to open the Uri
     * @param uri     The {@link Uri} to be opened by the fallback
     */
    @Override
    public void openUri(final Context context, final Uri uri) {
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtra(WebViewActivity.EXTRA_URL, uri.toString());
        context.startActivity(intent);
    }
}