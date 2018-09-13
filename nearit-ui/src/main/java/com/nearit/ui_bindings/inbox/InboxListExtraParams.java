package com.nearit.ui_bindings.inbox;

import android.os.Parcel;
import android.os.Parcelable;

public class InboxListExtraParams implements Parcelable {

    private final int noInboxCustomLayout;
    private final boolean includeCustomJSON;
    private final boolean includeFeedbacks;
    private final boolean includeCoupons;

    InboxListExtraParams(int noInboxCustomLayout, boolean includeCustomJSON, boolean includeFeedbacks, boolean includeCoupons) {
        this.noInboxCustomLayout = noInboxCustomLayout;
        this.includeCustomJSON = includeCustomJSON;
        this.includeFeedbacks = includeFeedbacks;
        this.includeCoupons = includeCoupons;
    }

    private InboxListExtraParams(Parcel in) {
        noInboxCustomLayout = in.readInt();
        includeCustomJSON = in.readByte() != 0;
        includeFeedbacks = in.readByte() != 0;
        includeCoupons = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(noInboxCustomLayout);
        dest.writeByte((byte) (includeCustomJSON ? 1 : 0));
        dest.writeByte((byte) (includeFeedbacks ? 1 : 0));
        dest.writeByte((byte) (includeCoupons ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<InboxListExtraParams> CREATOR = new Creator<InboxListExtraParams>() {
        @Override
        public InboxListExtraParams createFromParcel(Parcel in) {
            return new InboxListExtraParams(in);
        }

        @Override
        public InboxListExtraParams[] newArray(int size) {
            return new InboxListExtraParams[size];
        }
    };

    int getNoInboxCustomLayout() {
        return noInboxCustomLayout;
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
