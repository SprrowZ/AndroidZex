package com.rye.catcher.sdks.beans;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created at 2019/3/18.
 *
 * @author Zzg
 * @function:唐诗实体类
 */
public class TangBean implements Parcelable{
    private String code;

    protected TangBean(Parcel in) {
        code = in.readString();
        message = in.readString();
    }

    public static final Creator<TangBean> CREATOR = new Creator<TangBean>() {
        @Override
        public TangBean createFromParcel(Parcel in) {
            return new TangBean(in);
        }

        @Override
        public TangBean[] newArray(int size) {
            return new TangBean[size];
        }
    };

    @Override
    public String toString() {
        return "TangBean{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", result=" + result +
                '}';
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    private String message;
    private ResultBean result;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(code);
        dest.writeString(message);
    }

    public static class ResultBean {
        private String authors;
        private String content;
        private String title;


        public String getAuthors() {
            return authors;
        }

        public void setAuthors(String authors) {
            this.authors = authors;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
        @Override
        public String toString() {
            return "ResultBean{" +
                    "authors='" + authors + '\'' +
                    ", content='" + content + '\'' +
                    ", title='" + title + '\'' +
                    '}';
        }

    }
}
