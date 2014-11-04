package com.vanstone.centralserver.configuration.sdk.persistence.object;

import java.util.Date;

import com.vanstone.centralserver.business.sdk.configuration.IConfInfo;

public class SysConfInfoDO implements IConfInfo {
	private Integer id;
	
	private String dataId;

	private String groupId;

	private String confValueMd5;

	private Date sysInsertTime;

	private Date sysUpdateTime;

	private String confValue;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDataId() {
		return dataId;
	}

	public void setDataId(String dataId) {
		this.dataId = dataId;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getConfValueMd5() {
		return confValueMd5;
	}

	public void setConfValueMd5(String confValueMd5) {
		this.confValueMd5 = confValueMd5;
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

	public String getConfValue() {
		return confValue;
	}

	public void setConfValue(String confValue) {
		this.confValue = confValue;
	}
}