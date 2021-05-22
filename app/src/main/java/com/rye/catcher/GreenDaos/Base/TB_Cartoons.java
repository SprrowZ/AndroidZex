//package com.rye.catcher.GreenDaos.Base;
//
//import org.greenrobot.greendao.DaoException;
//import org.greenrobot.greendao.annotation.Entity;
//import org.greenrobot.greendao.annotation.Generated;
//import org.greenrobot.greendao.annotation.Id;
//import org.greenrobot.greendao.annotation.Keep;
//import org.greenrobot.greendao.annotation.NotNull;
//import org.greenrobot.greendao.annotation.ToMany;
//import org.greenrobot.greendao.annotation.Unique;
//
//import java.io.Serializable;
//import java.util.Date;
//import java.util.List;
//
//
//
///**
// * Created by ZZG on 2018/3/22.
// */
//@Entity
//public class TB_Cartoons implements Serializable{
//    private static final long serialVersionUID = 1L;
//    //表ID
//    @Id(autoincrement = true)
//    private Long ID;
//    @NotNull @Unique
//    //动漫名
//    public String  NAME="";
//    //演员
//    public String  ACTORS;
//    //开更时间
//    private Date ISSUE_TIME;
//    //是否完结
//    private boolean IS_END;
//    //导演
//    private String DIRECTOR;
//    //男主角
//    private String HERO;
//    //女主角
//    private String HEROINE;
//    //国家
//    private int PART;
//    //动漫详情
//    private String PLOT;//情节。。。详情
//    //插入时间
//    private Date INSERT_TIME;
//    @ToMany(referencedJoinProperty = "CARTOON_ID")
//    private List<TB_Character> CHARACTERS;//一对多关系，关联动漫人物表
//    /** Used to resolve relations */
//    @Generated(hash = 2040040024)
//    private transient DaoSession daoSession;
//    /** Used for active entity operations. */
//    @Generated(hash = 2009244494)
//    private transient TB_CartoonsDao myDao;
//    @Keep
//    public TB_Cartoons(Long ID, @NotNull String NAME, String ACTORS,
//                       Date ISSUE_TIME, boolean IS_END, String DIRECTOR, String HERO,
//                       String HEROINE, int PART, String PLOT, Date INSERT_TIME) {
//        this.ID = ID;
//        this.NAME = NAME;
//        this.ACTORS = ACTORS;
//        this.ISSUE_TIME = ISSUE_TIME;
//        this.IS_END = IS_END;
//        this.DIRECTOR = DIRECTOR;
//        this.HERO = HERO;
//        this.HEROINE = HEROINE;
//        this.PART = PART;
//        this.PLOT = PLOT;
//        this.INSERT_TIME = INSERT_TIME;
//    }
//    @Generated(hash = 881707586)
//    public TB_Cartoons() {
//    }
//    public Long getID() {
//        return this.ID;
//    }
//    public void setID(Long ID) {
//        this.ID = ID;
//    }
//    public String getNAME() {
//        return this.NAME;
//    }
//    public void setNAME(String NAME) {
//        this.NAME = NAME;
//    }
//    public String getACTORS() {
//        return this.ACTORS;
//    }
//    public void setACTORS(String ACTORS) {
//        this.ACTORS = ACTORS;
//    }
//    public Date getISSUE_TIME() {
//        return this.ISSUE_TIME;
//    }
//    public void setISSUE_TIME(Date ISSUE_TIME) {
//        this.ISSUE_TIME = ISSUE_TIME;
//    }
//    public boolean getIS_END() {
//        return this.IS_END;
//    }
//    public void setIS_END(boolean IS_END) {
//        this.IS_END = IS_END;
//    }
//    public String getDIRECTOR() {
//        return this.DIRECTOR;
//    }
//    public void setDIRECTOR(String DIRECTOR) {
//        this.DIRECTOR = DIRECTOR;
//    }
//    public String getHERO() {
//        return this.HERO;
//    }
//    public void setHERO(String HERO) {
//        this.HERO = HERO;
//    }
//    public String getHEROINE() {
//        return this.HEROINE;
//    }
//    public void setHEROINE(String HEROINE) {
//        this.HEROINE = HEROINE;
//    }
//    public int getPART() {
//        return this.PART;
//    }
//    public void setPART(int PART) {
//        this.PART = PART;
//    }
//    public String getPLOT() {
//        return this.PLOT;
//    }
//    public void setPLOT(String PLOT) {
//        this.PLOT = PLOT;
//    }
//    public Date getINSERT_TIME() {
//        return this.INSERT_TIME;
//    }
//    public void setINSERT_TIME(Date INSERT_TIME) {
//        this.INSERT_TIME = INSERT_TIME;
//    }
//    /**
//     * To-many relationship, resolved on first access (and after reset).
//     * Changes to to-many relations are not persisted, make changes to the target entity.
//     */
//    @Generated(hash = 405763815)
//    public List<TB_Character> getCHARACTERS() {
//        if (CHARACTERS == null) {
//            final DaoSession daoSession = this.daoSession;
//            if (daoSession == null) {
//                throw new DaoException("Entity is detached from DAO context");
//            }
//            TB_CharacterDao targetDao = daoSession.getTB_CharacterDao();
//            List<TB_Character> CHARACTERSNew = targetDao
//                    ._queryTB_Cartoons_CHARACTERS(ID);
//            synchronized (this) {
//                if (CHARACTERS == null) {
//                    CHARACTERS = CHARACTERSNew;
//                }
//            }
//        }
//        return CHARACTERS;
//    }
//    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
//    @Generated(hash = 1644062828)
//    public synchronized void resetCHARACTERS() {
//        CHARACTERS = null;
//    }
//    /**
//     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
//     * Entity must attached to an entity context.
//     */
//    @Generated(hash = 128553479)
//    public void delete() {
//        if (myDao == null) {
//            throw new DaoException("Entity is detached from DAO context");
//        }
//        myDao.delete(this);
//    }
//    /**
//     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
//     * Entity must attached to an entity context.
//     */
//    @Generated(hash = 1942392019)
//    public void refresh() {
//        if (myDao == null) {
//            throw new DaoException("Entity is detached from DAO context");
//        }
//        myDao.refresh(this);
//    }
//    /**
//     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
//     * Entity must attached to an entity context.
//     */
//    @Generated(hash = 713229351)
//    public void update() {
//        if (myDao == null) {
//            throw new DaoException("Entity is detached from DAO context");
//        }
//        myDao.update(this);
//    }
//    /** called by internal mechanisms, do not call yourself. */
//    @Generated(hash = 849930508)
//    public void __setDaoSession(DaoSession daoSession) {
//        this.daoSession = daoSession;
//        myDao = daoSession != null ? daoSession.getTB_CartoonsDao() : null;
//    }
//
//}
