package com.rye.catcher.GreenDaos.Base;

import org.greenrobot.greendao.annotation.Entity;

import java.io.Serializable;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created at 2018/9/14.
 *
 * @author Zzg
 */
@Entity
public class TB_KeyValue implements Serializable {
    private static final long serialVersionUID = 11L;
    private String KEY;
    private String VALUE;
    @Generated(hash = 840374381)
    public TB_KeyValue(String KEY, String VALUE) {
        this.KEY = KEY;
        this.VALUE = VALUE;
    }
    @Generated(hash = 886999769)
    public TB_KeyValue() {
    }
    public String getKEY() {
        return this.KEY;
    }
    public void setKEY(String KEY) {
        this.KEY = KEY;
    }
    public String getVALUE() {
        return this.VALUE;
    }
    public void setVALUE(String VALUE) {
        this.VALUE = VALUE;
    }
}
