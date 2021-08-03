package com.everdeng.android.app.wanandroid.util;

import android.os.Parcel;
import android.os.Parcelable;

public class TestBean implements Parcelable {
    private String title;
    private int id;
    private boolean like;


    protected TestBean(Parcel in) {
        title = in.readString();
        id = in.readInt();
        like = in.readByte() != 0;
    }

    public static final Creator<TestBean> CREATOR = new Creator<TestBean>() {
        @Override
        public TestBean createFromParcel(Parcel in) {
            return new TestBean(in);
        }

        @Override
        public TestBean[] newArray(int size) {
            return new TestBean[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeInt(id);
        dest.writeByte((byte) (like ? 1 : 0));
    }
}
