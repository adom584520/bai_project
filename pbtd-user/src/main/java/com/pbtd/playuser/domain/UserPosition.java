package com.pbtd.playuser.domain;

import java.util.Date;

public class UserPosition {
    private Integer id;

    private Long mobilenum;

    private Integer channelid;

    private Integer lunbotu;

    private Integer moban;

    private Integer biaoqian;

    private Integer tuijian;

    private Integer wenzi;

    private Integer zhuanji;

    private Integer yunyinwei;

    private String seriescode;

    private String beizhu;

    private Date createtime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getMobilenum() {
        return mobilenum;
    }

    public void setMobilenum(Long mobilenum) {
        this.mobilenum = mobilenum;
    }

    public Integer getChannelid() {
        return channelid;
    }

    public void setChannelid(Integer channelid) {
        this.channelid = channelid;
    }

    public Integer getLunbotu() {
        return lunbotu;
    }

    public void setLunbotu(Integer lunbotu) {
        this.lunbotu = lunbotu;
    }

    public Integer getMoban() {
        return moban;
    }

    public void setMoban(Integer moban) {
        this.moban = moban;
    }

    public Integer getBiaoqian() {
        return biaoqian;
    }

    public void setBiaoqian(Integer biaoqian) {
        this.biaoqian = biaoqian;
    }

    public Integer getTuijian() {
        return tuijian;
    }

    public void setTuijian(Integer tuijian) {
        this.tuijian = tuijian;
    }

    public Integer getWenzi() {
        return wenzi;
    }

    public void setWenzi(Integer wenzi) {
        this.wenzi = wenzi;
    }

    public Integer getZhuanji() {
        return zhuanji;
    }

    public void setZhuanji(Integer zhuanji) {
        this.zhuanji = zhuanji;
    }

    public Integer getYunyinwei() {
        return yunyinwei;
    }

    public void setYunyinwei(Integer yunyinwei) {
        this.yunyinwei = yunyinwei;
    }

    public String getSeriescode() {
        return seriescode;
    }

    public void setSeriescode(String seriescode) {
        this.seriescode = seriescode == null ? null : seriescode.trim();
    }

    public String getBeizhu() {
        return beizhu;
    }

    public void setBeizhu(String beizhu) {
        this.beizhu = beizhu == null ? null : beizhu.trim();
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }
}