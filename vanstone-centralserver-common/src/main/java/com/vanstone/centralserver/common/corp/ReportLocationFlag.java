/**
 * 
 */
package com.vanstone.centralserver.common.corp;

/**
 * @author shipeng
 *
 */
public enum ReportLocationFlag {

	Not_Uplaod("不上报", 0), In_Session_Upload("进入会话上报", 1), Continue_Upload("持续上报", 2);

	private String desc;
	private Integer code;

	private ReportLocationFlag(String desc, Integer code) {
		this.desc = desc;
		this.code = code;
	}

	public String getDesc() {
		return desc;
	}

	public Integer getCode() {
		return code;
	}

}
