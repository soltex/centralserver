/**
 * 
 */
package com.vanstone.centralserver.common.weixin.wrap;

/**
 * @author shipeng
 * 	国家地区语言版本，zh_CN 简体，zh_TW 繁体，en 英语 
 */
public enum Language {
	
	zh_CN("简体","zh_CN"),zh_TW("繁体","zh_TW"),en("英语","en");
	
	private String desc;
	private String code;
	
	private Language(String desc,String code) {
		this.desc = desc;
		this.code = code;
	}

	public String getDesc() {
		return desc;
	}

	public String getCode() {
		return code;
	}
	
}
