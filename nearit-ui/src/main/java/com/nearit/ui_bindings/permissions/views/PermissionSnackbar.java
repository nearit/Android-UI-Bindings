package com.nearit.ui_bindings.permissions.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.nearit.ui_bindings.R;

/**
 * Created by Federico Boschini on 25/09/17.
 */

public class PermissionSnackbar extends RelativeLayout {

    public PermissionSnackbar(Context context) {
        super(context);
        init();
    }

    public PermissionSnackbar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PermissionSnackbar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.nearit_ui_layout_permission_snackbar, this);
    }
}
