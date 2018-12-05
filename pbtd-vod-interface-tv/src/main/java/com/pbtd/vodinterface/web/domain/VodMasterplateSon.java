package com.pbtd.vodinterface.web.domain;

public class VodMasterplateSon {
    private Integer id;

    private Integer masterplateid;

    private Integer count;

    private Integer masterplatenum;

    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMasterplateid() {
        return masterplateid;
    }

    public void setMasterplateid(Integer masterplateid) {
        this.masterplateid = masterplateid;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getMasterplatenum() {
        return masterplatenum;
    }

    public void setMasterplatenum(Integer masterplatenum) {
        this.masterplatenum = masterplatenum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }
}