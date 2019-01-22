package com.rye.catcher.beans;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created at 2019/1/21.
 *
 * @author Zzg
 * @function:
 */
public class MultiBean implements Parcelable{
    private String fileName;
    private String fileType;
    private String user;

    protected MultiBean(Parcel in) {
        fileName = in.readString();
        fileType = in.readString();
        user = in.readString();
    }

    public static final Creator<MultiBean> CREATOR = new Creator<MultiBean>() {
        @Override
        public MultiBean createFromParcel(Parcel in) {
            return new MultiBean(in);
        }

        @Override
        public MultiBean[] newArray(int size) {
            return new MultiBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(fileName);
        dest.writeString(fileType);
        dest.writeString(user);
    }
}
