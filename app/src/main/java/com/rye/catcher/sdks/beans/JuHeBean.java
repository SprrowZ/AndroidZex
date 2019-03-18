package com.rye.catcher.sdks.beans;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created at 2019/3/18.
 *
 * @author Zzg
 * @function:
 */
public class JuHeBean implements Parcelable {
    private String error_code;//错误码
    private String reason;//错误原因
    private String result;//返回结果
    private String resultcode;//结果码

    protected JuHeBean(Parcel in) {
        error_code = in.readString();
        reason = in.readString();
        result = in.readString();
        resultcode = in.readString();
    }

    public static final Creator<JuHeBean> CREATOR = new Creator<JuHeBean>() {
        @Override
        public JuHeBean createFromParcel(Parcel in) {
            return new JuHeBean(in);
        }

        @Override
        public JuHeBean[] newArray(int size) {
            return new JuHeBean[size];
        }
    };

    public String getError_code() {
        return error_code;
    }

    public void setError_code(String error_code) {
        this.error_code = error_code;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getResultcode() {
        return resultcode;
    }

    public void setResultcode(String resultcode) {
        this.resultcode = resultcode;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(error_code);
        dest.writeString(reason);
        dest.writeString(result);
        dest.writeString(resultcode);
    }
}
