/**
 * 
 */
package com.vanstone.centralserver.common.weixin.wrap.menu;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.vanstone.centralserver.common.Constants;
import com.vanstone.centralserver.common.JsonUtil;
import com.vanstone.centralserver.common.weixin.WeixinException;
import com.vanstone.centralserver.common.weixin.wrap.ButtonType;

/**
 * @author shipeng
 */
public class Menu {
	
	private static Log LOG = LogFactory.getLog(Menu.class);
	
	private Collection<MenuItem> items = new ArrayList<MenuItem>();
	
	/**
	 * 添加父菜单
	 * @param item
	 * @throws WeixinException
	 */
	public void addMenuItem(MenuItem item) throws WeixinException {
		if (items.size() >= Constants.MAX_MENUITEM_NUM) {
			throw new WeixinException(WeixinException.ErrorCode.MAX_MENU_ITEM_COUNT_GT_3);
		}
		this.items.add(item);
	}
	
	/**
	 * 清理菜单选项
	 */
	public void clearMenuItems() {
		this.items.clear();
	}
	
	/**
	 * 是否存在Item
	 * @return
	 */
	public boolean existItems() {
		return this.items.size() != 0;
	}
	
	/**
	 * 菜单大小
	 * @return
	 */
	public int size() {
		return this.items.size();
	}
	
	/**
	 * 转换为Weixin JSON
	 * @return
	 */
	public String toJson() {
		if (!this.existItems()) {
			throw new IllegalArgumentException();
		}
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		List<Object> buttons = new ArrayList<Object>();
		
		for (MenuItem item : items) {
			if (item.getButtonType() != null && item.getButtonType().equals(ButtonType.View)) {
				//页面展示类型
				Map<String, Object> viewMap = new LinkedHashMap<String, Object>();
				viewMap.put("type", item.getButtonType().getCode());
				viewMap.put("name", item.getName());
				viewMap.put("url", item.getUrl());
				buttons.add(viewMap);
				continue;
			}
			if (item.getButtonType() != null && item.getButtonType().equals(ButtonType.Click)) {
				//按钮点击类型
				Map<String, Object> clickMap = new LinkedHashMap<String, Object>();
				clickMap.put("type", item.getButtonType().getCode());
				clickMap.put("name", item.getName());
				clickMap.put("key", item.getKey());
				buttons.add(clickMap);
				continue;
			}
			if (item.isParentMenu()) {
				//存在子菜单
				Map<String, Object> subMap = new LinkedHashMap<String, Object>();
				List<Map<String, Object>> subMenuButtons = new ArrayList<Map<String, Object>>();
				for (MenuItem subitem : item.getSubMenuItems()) {
					if (subitem.getButtonType() != null && subitem.getButtonType().equals(ButtonType.View)) {
						//页面展示类型
						Map<String, Object> viewMap = new LinkedHashMap<String, Object>();
						viewMap.put("type", subitem.getButtonType().getCode());
						viewMap.put("name", subitem.getName());
						viewMap.put("url", subitem.getUrl());
						subMenuButtons.add(viewMap);
						continue;
					}
					if (subitem.getButtonType() != null && subitem.getButtonType().equals(ButtonType.Click)) {
						//按钮点击类型
						Map<String, Object> clickMap = new LinkedHashMap<String, Object>();
						clickMap.put("type", subitem.getButtonType().getCode());
						clickMap.put("name", subitem.getName());
						clickMap.put("key", subitem.getKey());
						subMenuButtons.add(clickMap);
						continue;
					}
				}
				subMap.put("name", item.getName());
				subMap.put("sub_button", subMenuButtons);
				buttons.add(subMap);
				continue;
			}
		}
		
		map.put("button", buttons);
		String json = JsonUtil.object2PrettyString(map,false);
		if (LOG.isInfoEnabled()) {
			LOG.debug(json);
		}
		return json;
	}
}
