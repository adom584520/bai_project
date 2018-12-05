package com.pbtd.playlive.domain;

import java.util.Date;

public class UserActivitiesInfo {
    private Integer id;

    private String userid;

    private Integer activenum;

    private Integer isnotwin;

    private String activename;

    private Date createtime;

    private Date updatetime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid == null ? null : userid.trim();
    }

    public Integer getActivenum() {
        return activenum;
    }

    public void setActivenum(Integer activenum) {
        this.activenum = activenum;
    }

    public Integer getIsnotwin() {
        return isnotwin;
    }

    public void setIsnotwin(Integer isnotwin) {
        this.isnotwin = isnotwin;
    }

    public String getActivename() {
        return activename;
    }

    public void setActivename(String activename) {
        this.activename = activename == null ? null : activename.trim();
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }
}