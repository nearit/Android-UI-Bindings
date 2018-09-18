package com.nearit.ui_bindings.inbox.viewholders;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.nearit.ui_bindings.R;
import com.nearit.ui_bindings.inbox.NotificationsAdapter;
import com.nearit.ui_bindings.inbox.customviews.NearITNotificationCardLayout;

import it.near.sdk.reactions.couponplugin.model.Claim;
import it.near.sdk.reactions.couponplugin.model.Coupon;

public class CouponNotificationViewHolder extends BaseViewHolder<Coupon> {

    public static final int VIEWTYPE = 5;

    private final Button button;
    private final NearITNotificationCardLayout layout;

    public CouponNotificationViewHolder(LayoutInflater inflater, ViewGroup parent, final NotificationsAdapter.InboxAdapterListener listener, NotificationsAdapter.NotificationReadListener readListener) {
        super(inflater.inflate(R.layout.nearit_ui_notification_coupon_item, parent, false), readListener);
        layout = itemView.findViewById(R.id.layout);
        button = itemView.findViewById(R.id.detail_button);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item.read = true;
                layout.setMessageUnread(false);
                button.setTypeface(null, Typeface.NORMAL);
                listener.onInboxItemTap(item);
            }
        });
    }

    @Override
    protected void bindToView(Coupon coupon) {
        if (coupon.claims == null) return;
        Claim claim = coupon.claims.get(0);
        if (claim == null) return;
        layout.setTimestamp(item.timestamp);
        layout.setNotification(coupon.notificationMessage);
        layout.setMessageUnread(!item.read);
        button.setTypeface(null,
                item.read ? Typeface.NORMAL: Typeface.BOLD);
        Context context = itemView.getContext();
        button.setTextColor(context.getResources().getColor(item.read ?
                R.color.nearit_ui_notification_card_text_read_color :
                R.color.nearit_ui_notification_card_text_unread_color)
        );
    }
}
