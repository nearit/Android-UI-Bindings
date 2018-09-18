package com.nearit.ui_bindings.inbox;

import android.os.Parcel;
import android.os.Parcelable;

public class NotificationHistoryExtraParams implements Parcelable {

    private final int emptyListCustomLayout;
    private final boolean includeCustomJSON;
    private final boolean includeFeedbacks;
    private final boolean includeCoupons;

    NotificationHistoryExtraParams(int emptyListCustomLayout, boolean includeCustomJSON, boolean includeFeedbacks, boolean includeCoupons) {
        this.emptyListCustomLayout = emptyListCustomLayout;
        this.includeCustomJSON = includeCustomJSON;
        this.includeFeedbacks = includeFeedbacks;
        this.includeCoupons = includeCoupons;
    }

    private NotificationHistoryExtraParams(Parcel in) {
        emptyListCustomLayout = in.readInt();
        includeCustomJSON = in.readByte() != 0;
        includeFeedbacks = in.readByte() != 0;
        includeCoupons = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(emptyListCustomLayout);
        dest.writeByte((byte) (includeCustomJSON ? 1 : 0));
        dest.writeByte((byte) (includeFeedbacks ? 1 : 0));
        dest.writeByte((byte) (includeCoupons ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<NotificationHistoryExtraParams> CREATOR = new Creator<NotificationHistoryExtraParams>() {
        @Override
        public NotificationHistoryExtraParams createFromParcel(Parcel in) {
            return new NotificationHistoryExtraParams(in);
        }

        @Override
        public NotificationHistoryExtraParams[] newArray(int size) {
            return new NotificationHistoryExtraParams[size];
        }
    };

    int getEmptyListCustomLayout() {
        return emptyListCustomLayout;
    }

    boolean shouldIncludeCustomJSON() {
        return includeCustomJSON;
    }

    public boolean shouldIncludeFeedbacks() {
        return includeFeedbacks;
    }

    public boolean shouldIncludeCoupons() {
        return includeCoupons;
    }
}
