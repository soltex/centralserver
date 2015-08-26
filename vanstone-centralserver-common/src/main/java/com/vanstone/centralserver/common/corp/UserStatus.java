/**
 * 
 */
package com.vanstone.centralserver.common.corp;

/**
 * @author shipeng
 *
 */
public enum UserStatus {
	Subscribe("已关注", 1), Forbit("已冻结", 2), Unsubscribe("未关注", 4);
	
	private String desc;
	private Integer code;

	private UserStatus(String desc, Integer code) {
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
