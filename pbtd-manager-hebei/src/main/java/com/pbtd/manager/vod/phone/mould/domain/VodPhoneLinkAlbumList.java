package com.pbtd.manager.vod.phone.mould.domain;

import java.io.Serializable;

public class VodPhoneLinkAlbumList implements Serializable {
	private static final long serialVersionUID = 1L;
    private Integer id;

    private Integer moduleid;

    private Long seriescode;

    private Integer masterplatenum;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getModuleid() {
        return moduleid;
    }

    public void setModuleid(Integer moduleid) {
        this.moduleid = moduleid;
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
}