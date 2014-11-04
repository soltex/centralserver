/**
 * 
 */
package com.vanstone.centralserver.common.weixin.wrap.msg.template;

import com.vanstone.centralserver.common.Constants;

/**
 * @author shipeng
 *
 */
public class ColorUtil {
	
	/**
	 * 建立以及验证颜色值
	 * @param color
	 * @return
	 */
	public static String buildWeixinTemplateColorValue(String color, boolean defaultColor) {
		if ((color == null || color.equals("")) && defaultColor) {
			return Constants.WEIXIN_TEMPLATE_MSG_DEFAULT_COLOR;
		}
		if (color != null && !color.equals("") && !color.startsWith("#")) {
			color = "#" + color;
		}
		return color;
	}
	
}
