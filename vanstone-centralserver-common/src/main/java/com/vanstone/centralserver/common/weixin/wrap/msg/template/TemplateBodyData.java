/**
 * 
 */
package com.vanstone.centralserver.common.weixin.wrap.msg.template;

import java.util.LinkedHashMap;
import java.util.Map;

import com.vanstone.centralserver.common.JsonUtil;
import com.vanstone.centralserver.common.MyAssert;

/**
 * @author shipeng
 *
 */
public class TemplateBodyData {
	
	private String toUser;
	private String templateId;
	private String url;
	private String topColor;
	private ValueAndColor firstValueColor;
	private Map<String, ValueAndColor> params = new LinkedHashMap<String, ValueAndColor>();
	private ValueAndColor remarkValueColor;
	
	/**
	 * 默认构造函数
	 * @param toUserOpenId 用户OpenID
	 * @param templateId 模板ID,定义在微信控制台中
	 * @param url	点击的URL
	 * @param topColor 头部信息颜色
	 * @param firstValueColor  头部文字显示信息以及颜色
	 * @param params 相关参数显示信息以及颜色
	 * @param remarkValueColor 备注信息以及颜色
	 * @param defaultColor 是否使用默认颜色
	 */
	public TemplateBodyData(String toUserOpenId, String templateId, String url, String topColor,
			ValueAndColor firstValueColor, Map<String, ValueAndColor> params, ValueAndColor remarkValueColor, boolean defaultColor) {
		MyAssert.hasText(toUserOpenId);
		MyAssert.hasText(templateId);
		MyAssert.notNull(firstValueColor);
		this.toUser = toUserOpenId;
		this.templateId = templateId;
		this.url = url;
		this.topColor = ColorUtil.buildWeixinTemplateColorValue(topColor, defaultColor);
		this.firstValueColor = firstValueColor;
		if (params != null && params.size() >0) {
			this.params = params;
		}
		this.remarkValueColor = remarkValueColor;
	}
	
	public TemplateBodyData(String toUserOpenId, String templateId, String url) {
		this(toUserOpenId, templateId, url, null, null, null, null, false);
	}
	
	public String getToUser() {
		return toUser;
	}
	
	public void setToUser(String toUser) {
		this.toUser = toUser;
	}

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTopColor() {
		return topColor;
	}

	public void setTopColor(String topColor) {
		this.topColor = topColor;
	}

	public ValueAndColor getFirstValueColor() {
		return firstValueColor;
	}

	public void setFirstValueColor(ValueAndColor firstValueColor) {
		this.firstValueColor = firstValueColor;
	}

	public ValueAndColor getRemarkValueColor() {
		return remarkValueColor;
	}

	public void setRemarkValueColor(ValueAndColor remarkValueColor) {
		this.remarkValueColor = remarkValueColor;
	}

	public Map<String, ValueAndColor> getParams() {
		return params;
	}
	
	/**
	 * 增加模板参数
	 * @param key
	 * @param value
	 * @param color
	 */
	public void addParam(String key, String value, String color) {
		MyAssert.hasText(key);
		MyAssert.hasText(value);
		ValueAndColor valueAndColor = new ValueAndColor(value, color);
		this.params.put(key, valueAndColor);
	}
	
	/**
	 * 添加参数
	 * @param key
	 * @param value
	 */
	public void addParam(String key, String value) {
		this.addParam(key, value, null);
	}
	
	public void clearParams() {
		this.params.clear();
	}
	
	public String toJson() {
		Map<String, Object> allMap = new LinkedHashMap<String, Object>();
		allMap.put("touser", this.toUser);
		allMap.put("template_id", this.templateId);
		allMap.put("url", this.url);
		allMap.put("topcolor", this.topColor);
		
		Map<String, Object> dataMap = new LinkedHashMap<String, Object>();
		
		Map<String, Object> firstDataMap = new LinkedHashMap<String, Object>();
		firstDataMap.put("value", this.firstValueColor.getValue());
		firstDataMap.put("color", this.firstValueColor.getColor());
		dataMap.put("first", firstDataMap);
		
		for (Map.Entry<String, ValueAndColor> entry : this.getParams().entrySet()) {
			Map<String, Object> paramDataMap = new LinkedHashMap<String, Object>();
			paramDataMap.put("value", entry.getValue().getValue());
			paramDataMap.put("color", entry.getValue().getColor());
			dataMap.put(entry.getKey(), paramDataMap);
		}
		
		if (this.remarkValueColor != null) {
			Map<String, Object> remarkMap = new LinkedHashMap<String, Object>();
			remarkMap.put("value", this.remarkValueColor.getValue());
			remarkMap.put("color", this.remarkValueColor.getColor());
			dataMap.put("remark", remarkMap);
		}
		
		allMap.put("data", dataMap);
		return JsonUtil.object2PrettyString(allMap, false);
	}
	
}
