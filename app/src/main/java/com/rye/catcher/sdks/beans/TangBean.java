package com.rye.catcher.sdks.beans;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created at 2019/3/18.
 *
 * @author Zzg
 * @function:唐诗实体类
 */
public class TangBean  {
    private String code;
    private String message;
    private ResultBean result;




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





    public static class ResultBean {
        private String authors;
        private String content;
        private String title;

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


        public String getAuthors() {
            return authors;
        }

        public void setAuthors(String authors) {
            this.authors = authors;
        }


    }
}
