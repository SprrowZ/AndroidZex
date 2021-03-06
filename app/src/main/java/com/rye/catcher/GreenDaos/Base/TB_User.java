package com.rye.catcher.GreenDaos.Base;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by ZZG on 2018/3/20.
 */
@Entity
public class TB_User {
    @Id(autoincrement=true)
    private Long id;
    private String name;
    private String sex;
    private String salary;
    @Generated(hash = 1858814971)
    public TB_User(Long id, String name, String sex, String salary) {
        this.id = id;
        this.name = name;
        this.sex = sex;
        this.salary = salary;
    }
    @Generated(hash = 1614867957)
    public TB_User() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getSex() {
        return this.sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }
    public String getSalary() {
        return this.salary;
    }
    public void setSalary(String salary) {
        this.salary = salary;
    }




}
