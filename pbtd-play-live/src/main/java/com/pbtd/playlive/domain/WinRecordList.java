package com.pbtd.playlive.domain;

import java.util.Date;

public class WinRecordList {
    private Integer id;

    private String userid;

    private String usermobile;

    private Date createtime;

    private Date updatetime;

    private Integer prizecode;

    private String activename;

    private Integer activetype;

    private String useraddress;

    private byte[] prizename;

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

    public String getUsermobile() {
        return usermobile;
    }

    public void setUsermobile(String usermobile) {
        this.usermobile = usermobile == null ? null : usermobile.trim();
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

    public Integer getPrizecode() {
        return prizecode;
    }

    public void setPrizecode(Integer prizecode) {
        this.prizecode = prizecode;
    }

    public String getActivename() {
        return activename;
    }

    public void setActivename(String activename) {
        this.activename = activename == null ? null : activename.trim();
    }

    public Integer getActivetype() {
        return activetype;
    }

    public void setActivetype(Integer activetype) {
        this.activetype = activetype;
    }

    public String getUseraddress() {
        return useraddress;
    }

    public void setUseraddress(String useraddress) {
        this.useraddress = useraddress == null ? null : useraddress.trim();
    }

    public byte[] getPrizename() {
        return prizename;
    }

    public void setPrizename(byte[] prizename) {
        this.prizename = prizename;
    }
}