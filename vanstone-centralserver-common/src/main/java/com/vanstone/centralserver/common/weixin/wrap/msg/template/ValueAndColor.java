/**
 * 
 */
package com.vanstone.centralserver.common.weixin.wrap.msg.template;

import com.vanstone.centralserver.common.MyAssert;

/**
 * @author shipeng
 */
public class ValueAndColor {
	private String value;
	private String color;
	
	public ValueAndColor(String value, String color) {
		MyAssert.hasText(value);
		this.value = value;
		this.color = color;
	}
	
	/**
	 * @param value
	 */
	public ValueAndColor(String value) {
		this(value, null);
	}
	
	public String getValue() {
		return value;
	}
	
	public String getColor() {
		return color;
	}

}
