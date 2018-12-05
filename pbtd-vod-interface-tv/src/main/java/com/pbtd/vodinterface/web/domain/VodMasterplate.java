package com.pbtd.vodinterface.web.domain;

public class VodMasterplate {
    private Integer id;

    private String masterplatename;

    private Integer masterplatenum;

    private String masterplatephoto;

    private String describes;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMasterplatename() {
        return masterplatename;
    }

    public void setMasterplatename(String masterplatename) {
        this.masterplatename = masterplatename == null ? null : masterplatename.trim();
    }

    public Integer getMasterplatenum() {
        return masterplatenum;
    }

    public void setMasterplatenum(Integer masterplatenum) {
        this.masterplatenum = masterplatenum;
    }

    public String getMasterplatephoto() {
        return masterplatephoto;
    }

    public void setMasterplatephoto(String masterplatephoto) {
        this.masterplatephoto = masterplatephoto == null ? null : masterplatephoto.trim();
    }

    public String getDescribes() {
        return describes;
    }

    public void setDescribes(String describes) {
        this.describes = describes == null ? null : describes.trim();
    }
}