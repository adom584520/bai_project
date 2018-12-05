package com.pbtd.manager.system.domain;

import java.util.Date;

public class UserKeep {
    public int id;
    public Date startTime;
    public Date endTime;
    public int startUser;
    public int endUser;
    public int keepUser;
    public float keepPercent;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public int getStartUser() {
        return startUser;
    }

    public void setStartUser(int startUser) {
        this.startUser = startUser;
    }

    public int getEndUser() {
        return endUser;
    }

    public void setEndUser(int endUser) {
        this.endUser = endUser;
    }

    public int getKeepUser() {
        return keepUser;
    }

    public void setKeepUser(int keepUser) {
        this.keepUser = keepUser;
    }

    public float getKeepPercent() {
        return keepPercent;
    }

    public void setKeepPercent(float keepPercent) {
        this.keepPercent = keepPercent;
    }
}
