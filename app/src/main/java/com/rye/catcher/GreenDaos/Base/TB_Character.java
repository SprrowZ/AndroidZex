package com.rye.catcher.GreenDaos.Base;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

import java.io.Serializable;

/**
 * Created by Zzg on 2018/7/22.
 */
@Entity
public class TB_Character implements Serializable {
    private static final long serialVersionUID = 2L;
    private Long ID;
    private String NAME;
    private boolean SEX;
    @Id
    private String CARTOON_NAME;
    private int AGE;
    private String ANIME_DUBBING;//声优
    private String NATIONALITY;//国籍
    private Long CARTOON_ID;
    @Generated(hash = 215871986)
    public TB_Character(Long ID, String NAME, boolean SEX, String CARTOON_NAME,
            int AGE, String ANIME_DUBBING, String NATIONALITY, Long CARTOON_ID) {
        this.ID = ID;
        this.NAME = NAME;
        this.SEX = SEX;
        this.CARTOON_NAME = CARTOON_NAME;
        this.AGE = AGE;
        this.ANIME_DUBBING = ANIME_DUBBING;
        this.NATIONALITY = NATIONALITY;
        this.CARTOON_ID = CARTOON_ID;
    }
    @Generated(hash = 1832405729)
    public TB_Character() {
    }
    public Long getID() {
        return this.ID;
    }
    public void setID(Long ID) {
        this.ID = ID;
    }
    public String getNAME() {
        return this.NAME;
    }
    public void setNAME(String NAME) {
        this.NAME = NAME;
    }
    public boolean getSEX() {
        return this.SEX;
    }
    public void setSEX(boolean SEX) {
        this.SEX = SEX;
    }
    public String getCARTOON_NAME() {
        return this.CARTOON_NAME;
    }
    public void setCARTOON_NAME(String CARTOON_NAME) {
        this.CARTOON_NAME = CARTOON_NAME;
    }
    public int getAGE() {
        return this.AGE;
    }
    public void setAGE(int AGE) {
        this.AGE = AGE;
    }
    public String getANIME_DUBBING() {
        return this.ANIME_DUBBING;
    }
    public void setANIME_DUBBING(String ANIME_DUBBING) {
        this.ANIME_DUBBING = ANIME_DUBBING;
    }
    public String getNATIONALITY() {
        return this.NATIONALITY;
    }
    public void setNATIONALITY(String NATIONALITY) {
        this.NATIONALITY = NATIONALITY;
    }
    public Long getCARTOON_ID() {
        return this.CARTOON_ID;
    }
    public void setCARTOON_ID(Long CARTOON_ID) {
        this.CARTOON_ID = CARTOON_ID;
    }


}
