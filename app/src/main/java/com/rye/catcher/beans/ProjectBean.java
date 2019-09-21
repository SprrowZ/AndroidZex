package com.rye.catcher.beans;

import java.io.Serializable;

/**
 * Created By RyeCatcher
 * at 2019/9/3
 */
public class ProjectBean implements Serializable {
    private String name;
    private String action;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
