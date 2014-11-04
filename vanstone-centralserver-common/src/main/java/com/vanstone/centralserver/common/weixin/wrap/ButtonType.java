/**
 * 
 */
package com.vanstone.centralserver.common.weixin.wrap;

/**
 * Menu Button类型
 * @author shipeng
 */
public enum ButtonType {
	
	Click("click"),View("view");
	
	private String code;

	private ButtonType(String code) {
		this.code = code;
	}
	
	/**
	 * 获取按钮选项
	 * @return
	 */
	public String getCode() {
		return code;
	}
	
}
