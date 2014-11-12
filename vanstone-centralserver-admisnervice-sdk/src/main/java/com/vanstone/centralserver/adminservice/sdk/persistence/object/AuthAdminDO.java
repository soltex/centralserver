package com.vanstone.centralserver.adminservice.sdk.persistence.object;

import java.util.Date;

public class AuthAdminDO {
    private String id;

    private String adminName;

    private String adminPwd;

    private Date sysInsertTime;

    private Date sysUpdateTime;

    private Date lastLoginTime;

    private Integer adminState;

    private String fullName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getAdminPwd() {
        return adminPwd;
    }

    public void setAdminPwd(String adminPwd) {
        this.adminPwd = adminPwd;
    }

    public Date getSysInsertTime() {
        return sysInsertTime;
    }

    public void setSysInsertTime(Date sysInsertTime) {
        this.sysInsertTime = sysInsertTime;
    }

    public Date getSysUpdateTime() {
        return sysUpdateTime;
    }

    public void setSysUpdateTime(Date sysUpdateTime) {
        this.sysUpdateTime = sysUpdateTime;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public Integer getAdminState() {
        return adminState;
    }

    public void setAdminState(Integer adminState) {
        this.adminState = adminState;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}