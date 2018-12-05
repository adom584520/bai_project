package com.pbtd.manager.user.domain;

import java.util.Date;

public class UserBaseInfo {
    private String userid;

    private String usermobile;

    private String username;

    private String usernickname;

    private String usericon;

    private Integer vipstat;

    private String viptype;

    private Date vipcreatetime;

    private Date vipendtime;

    private Integer sharenum;

    private Integer commendnun;

    private String code;

    private Date createtime;

    private Date updatetime;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid == null ? null : userid.trim();
    }

    public String getUsermobile() {
        return usermobile;
    }

    public void setUsermobile(String usermobile) {
        this.usermobile = usermobile == null ? null : usermobile.trim();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getUsernickname() {
        return usernickname;
    }

    public void setUsernickname(String usernickname) {
        this.usernickname = usernickname == null ? null : usernickname.trim();
    }

    public String getUsericon() {
        return usericon;
    }

    public void setUsericon(String usericon) {
        this.usericon = usericon == null ? null : usericon.trim();
    }

    public Integer getVipstat() {
        return vipstat;
    }

    public void setVipstat(Integer vipstat) {
        this.vipstat = vipstat;
    }

    public String getViptype() {
        return viptype;
    }

    public void setViptype(String viptype) {
        this.viptype = viptype == null ? null : viptype.trim();
    }

    public Date getVipcreatetime() {
        return vipcreatetime;
    }

    public void setVipcreatetime(Date vipcreatetime) {
        this.vipcreatetime = vipcreatetime;
    }

    public Date getVipendtime() {
        return vipendtime;
    }

    public void setVipendtime(Date vipendtime) {
        this.vipendtime = vipendtime;
    }

    public Integer getSharenum() {
        return sharenum;
    }

    public void setSharenum(Integer sharenum) {
        this.sharenum = sharenum;
    }

    public Integer getCommendnun() {
        return commendnun;
    }

    public void setCommendnun(Integer commendnun) {
        this.commendnun = commendnun;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
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