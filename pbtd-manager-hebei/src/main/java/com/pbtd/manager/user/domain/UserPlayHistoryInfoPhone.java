package com.pbtd.manager.user.domain;

import java.util.Date;

public class UserPlayHistoryInfoPhone {
    private Integer id;

    private String userid;

    private Date createtime;

    private Integer playpos;

    private Integer playtime;

    private Integer dram;

    private String dramaname;

    private String seriesname;

    private String pictureurl1;

    private String pictureurl2;

    private Integer duration;

    private String viewpoint;

    private Integer seriescode;

    private Integer status;

    private String channellist;

    private String channelname;

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

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Integer getPlaypos() {
        return playpos;
    }

    public void setPlaypos(Integer playpos) {
        this.playpos = playpos;
    }

    public Integer getPlaytime() {
        return playtime;
    }

    public void setPlaytime(Integer playtime) {
        this.playtime = playtime;
    }

    public Integer getDram() {
        return dram;
    }

    public void setDram(Integer dram) {
        this.dram = dram;
    }

    public String getDramaname() {
        return dramaname;
    }

    public void setDramaname(String dramaname) {
        this.dramaname = dramaname == null ? null : dramaname.trim();
    }

    public String getSeriesname() {
        return seriesname;
    }

    public void setSeriesname(String seriesname) {
        this.seriesname = seriesname == null ? null : seriesname.trim();
    }

    public String getPictureurl1() {
        return pictureurl1;
    }

    public void setPictureurl1(String pictureurl1) {
        this.pictureurl1 = pictureurl1 == null ? null : pictureurl1.trim();
    }

    public String getPictureurl2() {
        return pictureurl2;
    }

    public void setPictureurl2(String pictureurl2) {
        this.pictureurl2 = pictureurl2 == null ? null : pictureurl2.trim();
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getViewpoint() {
        return viewpoint;
    }

    public void setViewpoint(String viewpoint) {
        this.viewpoint = viewpoint == null ? null : viewpoint.trim();
    }

    public Integer getSeriescode() {
        return seriescode;
    }

    public void setSeriescode(Integer seriescode) {
        this.seriescode = seriescode;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getChannellist() {
        return channellist;
    }

    public void setChannellist(String channellist) {
        this.channellist = channellist == null ? null : channellist.trim();
    }

    public String getChannelname() {
        return channelname;
    }

    public void setChannelname(String channelname) {
        this.channelname = channelname == null ? null : channelname.trim();
    }
}