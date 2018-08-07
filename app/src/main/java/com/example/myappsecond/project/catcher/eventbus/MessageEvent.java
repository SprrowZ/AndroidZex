package com.example.myappsecond.project.catcher.eventbus;

import java.io.Serializable;

/**
 * Created by ZZG on 2018/8/7.
 */
public class MessageEvent implements Serializable {
    private String message;

    private String from;
    private String to;
    public MessageEvent(String message,String from,String to){
         this.message=message;
         this.from=from;
         this.to=to;
    }
    public MessageEvent(String message){
        this.message=message;
    }
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

}
