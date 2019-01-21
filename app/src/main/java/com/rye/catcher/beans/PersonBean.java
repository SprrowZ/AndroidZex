package com.rye.catcher.beans;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created at 2018/12/6.
 *
 * @author Zzg
 */
public class PersonBean implements Parcelable {
    private String name;
    private boolean sex;

    protected PersonBean(Parcel in) {
        name = in.readString();
        sex = in.readByte() != 0;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSex() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

    public PersonBean(String name, boolean sex) {
        this.name = name;
        this.sex = sex;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
      dest.writeString(name);
      //通过byte传递boolean值
      dest.writeByte((byte)(sex?1:0));
    }

    public static final Creator<PersonBean> CREATOR = new Creator<PersonBean>() {
        @Override
        public PersonBean createFromParcel(Parcel in) {
            return new PersonBean(in);
        }

        @Override
        public PersonBean[] newArray(int size) {
            return new PersonBean[size];
        }
    };
    @Override
    public String toString() {
        return "PersonBean{" +
                "name='" + name + '\'' +
                ", sex=" + sex +
                '}';
    }
}
