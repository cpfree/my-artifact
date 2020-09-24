package com.github.cpfniliu.dal;

import java.util.Date;

public class AccLinkPermRes {
    private String guid;

    private String permKey;

    private String resKey;

    private String action;

    private Date addTime;

    private Date updateTime;

    private String state;

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid == null ? null : guid.trim();
    }

    public String getPermKey() {
        return permKey;
    }

    public void setPermKey(String permKey) {
        this.permKey = permKey == null ? null : permKey.trim();
    }

    public String getResKey() {
        return resKey;
    }

    public void setResKey(String resKey) {
        this.resKey = resKey == null ? null : resKey.trim();
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action == null ? null : action.trim();
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }

    @Override
    public String toString() {
        return "AccLinkPermRes{" +
                "guid='" + guid + '\'' +
                ", permKey='" + permKey + '\'' +
                ", resKey='" + resKey + '\'' +
                ", action='" + action + '\'' +
                ", addTime=" + addTime +
                ", updateTime=" + updateTime +
                ", state='" + state + '\'' +
                '}';
    }
}
