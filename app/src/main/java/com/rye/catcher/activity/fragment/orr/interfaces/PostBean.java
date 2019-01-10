package com.rye.catcher.activity.fragment.orr.interfaces;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created at 2019/1/10.
 *
 * @author Zzg
 * @function:
 */
public class PostBean implements Parcelable {
    private String city;
    private String job;
    private boolean sex;

    public PostBean(){

    }

    protected PostBean(Parcel in) {
        city = in.readString();
        job = in.readString();
        sex = in.readByte() != 0;
    }

    public static final Creator<PostBean> CREATOR = new Creator<PostBean>() {
        @Override
        public PostBean createFromParcel(Parcel in) {
            return new PostBean(in);
        }

        @Override
        public PostBean[] newArray(int size) {
            return new PostBean[size];
        }
    };

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public boolean isSex() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

    @Override
    public String toString() {
        return "PostBean{" +
                "city='" + city + '\'' +
                ", job='" + job + '\'' +
                ", sex=" + sex +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(city);
        dest.writeString(job);
        dest.writeByte((byte) (sex ? 1 : 0));
    }
}
