package com.example.myappsecond.utils.permission;

public class PermissionEvent {

    public final static int  rejectAction = 10;
    public final static int  rejectActionLivePhoto = 11;
    public final static int  backSettingAction = 20;

    public PermissionEvent(int actiontype) {
        this.actiontype = actiontype;
    }

    private int actiontype;

    public int getActiontype() {
        return actiontype;
    }


}