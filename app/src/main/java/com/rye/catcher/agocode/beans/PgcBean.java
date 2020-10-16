package com.rye.catcher.agocode.beans;

/**
 * Create by rye
 * at 2020-09-27
 *
 * @description:
 */
public class PgcBean {
    public int pgcId;
    public String pgcName;
    public String pgcCover;
    public String pgcNation;
    public long pgcNumber;

    @Override
    public String toString() {
        return "PgcBean{" +
                "pgcId=" + pgcId +
                ", pgcName='" + pgcName + '\'' +
                ", pgcCover='" + pgcCover + '\'' +
                ", pgcNation='" + pgcNation + '\'' +
                ", pgcNumber=" + pgcNumber +
                '}';
    }
}
