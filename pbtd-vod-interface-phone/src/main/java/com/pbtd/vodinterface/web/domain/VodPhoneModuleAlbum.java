package com.pbtd.vodinterface.web.domain;

public class VodPhoneModuleAlbum {
    private Integer id;

    private String moduleid;

    private Long seriescode;

    private Integer masterplatenum;

    private Integer sequence;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getModuleid() {
        return moduleid;
    }

    public void setModuleid(String moduleid) {
        this.moduleid = moduleid == null ? null : moduleid.trim();
    }

    public Long getSeriescode() {
        return seriescode;
    }

    public void setSeriescode(Long seriescode) {
        this.seriescode = seriescode;
    }

    public Integer getMasterplatenum() {
        return masterplatenum;
    }

    public void setMasterplatenum(Integer masterplatenum) {
        this.masterplatenum = masterplatenum;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }
}