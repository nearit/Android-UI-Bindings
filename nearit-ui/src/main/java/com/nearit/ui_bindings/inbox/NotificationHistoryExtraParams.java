package com.nearit.ui_bindings.inbox;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;

public class NotificationHistoryExtraParams implements Parcelable {

    private final int emptyListCustomLayout;
    private final boolean includeCustomJSON;
    private final boolean includeFeedbacks;
    private final boolean includeCoupons;
    @Nullable
    private final String activityTitle;

    NotificationHistoryExtraParams(int emptyListCustomLayout, boolean includeCustomJSON, boolean includeFeedbacks, boolean includeCoupons, @Nullable String activityTitle) {
        this.emptyListCustomLayout = emptyListCustomLayout;
        this.includeCustomJSON = includeCustomJSON;
        this.includeFeedbacks = includeFeedbacks;
        this.includeCoupons = includeCoupons;
        this.activityTitle = activityTitle;
    }

    private NotificationHistoryExtraParams(Parcel in) {
        emptyListCustomLayout = in.readInt();
        includeCustomJSON = in.readByte() != 0;
        includeFeedbacks = in.readByte() != 0;
        includeCoupons = in.readByte() != 0;
        activityTitle = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(emptyListCustomLayout);
        dest.writeByte((byte) (includeCustomJSON ? 1 : 0));
        dest.writeByte((byte) (includeFeedbacks ? 1 : 0));
        dest.writeByte((byte) (includeCoupons ? 1 : 0));
        dest.writeString(activityTitle);
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

    boolean shouldIncludeFeedbacks() {
        return includeFeedbacks;
    }

    boolean shouldIncludeCoupons() {
        return includeCoupons;
    }

    @Nullable
    String getActivityTitle() {
        return activityTitle;
    }
}
