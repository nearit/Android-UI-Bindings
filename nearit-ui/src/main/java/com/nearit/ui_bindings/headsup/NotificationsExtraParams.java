package com.nearit.ui_bindings.headsup;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import com.nearit.ui_bindings.ExtraConstants;

/**
 * Created by Federico Boschini on 06/09/17.
 */

class NotificationsExtraParams implements Parcelable {

    private final int iconDrawable;

    NotificationsExtraParams(int iconDrawable) {
        this.iconDrawable = iconDrawable;
    }

    public static final Creator<NotificationsExtraParams> CREATOR = new Creator<NotificationsExtraParams>() {
        @Override
        public NotificationsExtraParams createFromParcel(Parcel in) {
            int iconDrawable = in.readInt();

            return new NotificationsExtraParams(
                    iconDrawable
            );
        }

        @Override
        public NotificationsExtraParams[] newArray(int size) {
            return new NotificationsExtraParams[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(iconDrawable);
    }

    int getIconDrawable() {
        return iconDrawable;
    }
}
