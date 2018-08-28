package com.example.myappsecond.interfaces;

/**
 * Created by ZZG on 2018/8/23.
 */
public class RequestBody {
    String ids;
    String data;
    String messages;

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getMessages() {
        return messages;
    }

    public void setMessages(String messages) {
        this.messages = messages;
    }

    @Override
    public String toString() {
        return "RequestBody{" +
                "ids='" + ids + '\'' +
                ", data='" + data + '\'' +
                ", messages='" + messages + '\'' +
                '}';
    }
}
