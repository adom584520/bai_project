package com.pbtd.manager.user.domain;

import java.util.Date;

public class UserLoginList {
    private Integer id;

    private String usermobile;

    private Integer sourceflag;

    private Date creattime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsermobile() {
        return usermobile;
    }

    public void setUsermobile(String usermobile) {
        this.usermobile = usermobile == null ? null : usermobile.trim();
    }

    public Integer getSourceflag() {
        return sourceflag;
    }

    public void setSourceflag(Integer sourceflag) {
        this.sourceflag = sourceflag;
    }

    public Date getCreattime() {
        return creattime;
    }

    public void setCreattime(Date creattime) {
        this.creattime = creattime;
    }
}