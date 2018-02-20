package com.nearit.ui_bindings.inbox;

import android.os.Parcel;
import android.os.Parcelable;

public class InboxListExtraParams implements Parcelable {

    private final int noInboxCustomLayout;
    private final boolean includeCustomJSON;

    public InboxListExtraParams(int noInboxCustomLayout, boolean includeCustomJSON) {
        this.noInboxCustomLayout = noInboxCustomLayout;
        this.includeCustomJSON = includeCustomJSON;
    }

    protected InboxListExtraParams(Parcel in) {
        noInboxCustomLayout = in.readInt();
        includeCustomJSON = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(noInboxCustomLayout);
        dest.writeByte((byte) (includeCustomJSON ? 1 : 0));
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

    public int getNoInboxCustomLayout() {
        return noInboxCustomLayout;
    }

    public boolean shouldIncludeCustomJSON() {
        return includeCustomJSON;
    }
}
