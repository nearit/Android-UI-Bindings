package com.nearit.ui_bindings.inbox;

import android.os.Parcel;
import android.os.Parcelable;

public class InboxListExtraParams implements Parcelable {

    private final int noInboxCustomLayout;

    public InboxListExtraParams(int noInboxCustomLayout) {
        this.noInboxCustomLayout = noInboxCustomLayout;
    }

    public int getNoInboxCustomLayout() {
        return noInboxCustomLayout;
    }

    protected InboxListExtraParams(Parcel in) {
        noInboxCustomLayout = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(noInboxCustomLayout);
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
}
