package com.nearit.ui_bindings.content;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.nearit.ui_bindings.ExtraConstants;
import com.nearit.ui_bindings.R;

import it.near.sdk.reactions.contentplugin.model.Content;

/**
 * Created by Federico Boschini on 06/09/17.
 */

public class NearItContentDetailActivity extends AppCompatActivity {

    private final static String TAG = "NearItContentDetailActiv";

    private ContentDetailExtraParams extras;
    private boolean isEnableTapToClose = false;

    @Nullable
    LinearLayout closeButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.nearit_ui_activity_content_detail);

        closeButton = (LinearLayout) findViewById(R.id.content_close_icon);
        if (closeButton != null) {
            closeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
        }

        Intent intent = getIntent();
        if (intent.hasExtra(ExtraConstants.EXTRA_FLOW_PARAMS)) {
            extras = ContentDetailExtraParams.fromIntent(intent);
            isEnableTapToClose = extras.isEnableTapOutsideToClose();
        }

        Content content = getIntent().getParcelableExtra(ExtraConstants.CONTENT_EXTRA);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_fragment_container, NearItContentDetailFragment.newInstance(content, extras))
                .commit();

    }

    public static Intent createIntent(Context context, Content content, ContentDetailExtraParams params) {
        return new Intent(context, NearItContentDetailActivity.class)
                .putExtra(ExtraConstants.CONTENT_EXTRA, content)
                .putExtra(ExtraConstants.EXTRA_FLOW_PARAMS, params);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Rect dialogBounds = new Rect();
        getWindow().getDecorView().getHitRect(dialogBounds);

        if (!dialogBounds.contains((int) ev.getX(), (int) ev.getY())) {
            if (!isEnableTapToClose) {
                return true;
            }
            finish();
        }
        return super.dispatchTouchEvent(ev);
    }

}
