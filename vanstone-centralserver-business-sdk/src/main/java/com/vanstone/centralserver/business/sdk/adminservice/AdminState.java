/**
 * 
 */
package com.vanstone.centralserver.business.sdk.adminservice;

import com.vanstone.business.lang.BaseEnum;

/**
 * @author shipeng
 *
 */
public enum AdminState implements BaseEnum<Integer> {
	
	Active("启用", 1), Forbit("禁用",0);
	
	private Integer code;
	private String desc;
	
	private AdminState(String desc, Integer code) {
		this.desc = desc;
		this.code = code;
	}
	
	@Override
	public Integer getCode() {
		return this.code;
	}

	@Override
	public String getDesc() {
		return this.desc;
	}
	
}
