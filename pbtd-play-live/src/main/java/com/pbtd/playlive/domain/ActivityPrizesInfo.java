package com.pbtd.playlive.domain;

import java.util.Date;

public class ActivityPrizesInfo {
    private Integer id;

    private Integer prizecode;

    private Integer prizenum;

    private Integer prizeresnum;

    private String language;

    private Date createtime;

    private Date updatetime;

    private String activename;

    private Long starttime;

    private Long endtime;

    private byte[] prizename;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPrizecode() {
        return prizecode;
    }

    public void setPrizecode(Integer prizecode) {
        this.prizecode = prizecode;
    }

    public Integer getPrizenum() {
        return prizenum;
    }

    public void setPrizenum(Integer prizenum) {
        this.prizenum = prizenum;
    }

    public Integer getPrizeresnum() {
        return prizeresnum;
    }

    public void setPrizeresnum(Integer prizeresnum) {
        this.prizeresnum = prizeresnum;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language == null ? null : language.trim();
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

    public String getActivename() {
        return activename;
    }

    public void setActivename(String activename) {
        this.activename = activename == null ? null : activename.trim();
    }

    public Long getStarttime() {
        return starttime;
    }

    public void setStarttime(Long starttime) {
        this.starttime = starttime;
    }

    public Long getEndtime() {
        return endtime;
    }

    public void setEndtime(Long endtime) {
        this.endtime = endtime;
    }

    public byte[] getPrizename() {
        return prizename;
    }

    public void setPrizename(byte[] prizename) {
        this.prizename = prizename;
    }
}