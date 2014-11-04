/**
 * 
 */
package com.vanstone.centralserver.common.weixin.wrap.menu;

import java.util.ArrayList;
import java.util.Collection;

import com.vanstone.centralserver.common.Constants;
import com.vanstone.centralserver.common.weixin.WeixinException;
import com.vanstone.centralserver.common.weixin.wrap.ButtonType;

/**
 * 微信菜单选项
 * @author shipeng
 */
public class MenuItem {
	
	private boolean parentMenu = false;
	/**按钮类型,Click or View*/
	private ButtonType buttonType;
	/**按钮显示名称*/
	private String name;
	/**当按钮类型为Click类型，点击事件*/
	private String key;
	/**按钮类型为View类型的连接地址*/
	private String url;
	/**子菜单容器*/
	private Collection<MenuItem> subMenuItems = new ArrayList<MenuItem>();
	
	/**
	 * 创建父菜单
	 * @param name
	 */
	public MenuItem(String name) {
		if (name == null || "".equals(name)) {
			throw new IllegalArgumentException("name empty.");
		}
		this.name = name;
		this.parentMenu = true;
	}
	
	/**
	 * 创建子菜单项
	 * @param buttonType
	 * @param value
	 */
	public MenuItem(ButtonType buttonType, String name, String value) {
		if (buttonType == null || value == null || "".equals(value) || name == null || "".equals(name)) {
			throw new IllegalArgumentException();
		}
		this.name = name;
		this.parentMenu = false;
		if (buttonType == ButtonType.Click) {
			this.buttonType = ButtonType.Click;
			this.key = value;
		}else {
			this.buttonType = ButtonType.View;
			this.url = value;
		}
	}
	
	public ButtonType getButtonType() {
		return buttonType;
	}

	public String getName() {
		return name;
	}

	public String getKey() {
		return key;
	}

	public String getUrl() {
		return url;
	}
	
	public boolean isParentMenu() {
		return parentMenu;
	}

	/**
	 * 添加子菜单
	 * @param menuItem
	 */
	public void addSubMenuItem(MenuItem menuItem) throws WeixinException {
		if (menuItem == null) {
			throw new IllegalArgumentException();
		}
		if (!this.parentMenu) {
			throw new IllegalArgumentException();
		}
		if (menuItem.isParentMenu()) {
			throw new IllegalArgumentException();
		}
		if (this.subMenuItems.size() >=Constants.MAX_SUB_MENUITEM_NUM) {
			throw new WeixinException(WeixinException.ErrorCode.MAX_SUB_MENU_ITEM_COUNT_GT_5);
		}
		this.subMenuItems.add(menuItem);
	}
	
	/**
	 * 清理子菜单Item
	 */
	public void clearSubMenuItems() {
		if (!this.parentMenu) {
			throw new IllegalArgumentException();
		}
		this.subMenuItems.clear();
	}
	
	/**
	 * 子菜单数量
	 * @return
	 */
	public int subMenuItemsize() {
		return this.subMenuItems.size();
	}
	
	/**
	 * 是否存在Item
	 * @return
	 */
	public boolean existMenuItems() {
		return this.subMenuItems.size() != 0;
	}

	/**
	 * 获取子菜单列表
	 * @return
	 */
	public Collection<MenuItem> getSubMenuItems() {
		return subMenuItems;
	}
	
}
