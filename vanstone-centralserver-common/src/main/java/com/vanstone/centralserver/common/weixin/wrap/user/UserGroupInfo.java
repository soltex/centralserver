/**
 * 
 */
package com.vanstone.centralserver.common.weixin.wrap.user;

/**
 * 用户分组信息
 * @author shipeng
 * 
 */
public class UserGroupInfo {

	/**分组id，由微信分配 */
	private Integer id;
	/**分组名字，UTF8编码 */
	private String name;
	/**分组内用户数量,默认-1未初始化*/
	private int count = -1;
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

}
