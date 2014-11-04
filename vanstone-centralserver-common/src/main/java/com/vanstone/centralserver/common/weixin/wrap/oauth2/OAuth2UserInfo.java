/**
 * 
 */
package com.vanstone.centralserver.common.weixin.wrap.oauth2;

import java.util.ArrayList;
import java.util.Collection;

import com.vanstone.centralserver.common.MyAssert;
import com.vanstone.centralserver.common.weixin.wrap.Sex;

/**
 * 通过OAuth2UserInfo获取的信息
 * @author shipeng
 */
public class OAuth2UserInfo {
	/**  用户的唯一标识 */
	private String openId;
	/** 用户昵称*/
	private String nickname;
	/**  用户的性别，值为1时是男性，值为2时是女性，值为0时是未知 */
	private Sex sex;
	/** 用户个人资料填写的省份*/
	private String province;
	/**  普通用户个人资料填写的城市 */
	private String city;
	/**  国家，如中国为CN */
	private String country;
	/** 用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像），用户没有头像时该项为空 */
	private String headimgurl0;
	/** 用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像），用户没有头像时该项为空 */
	private String headimgurl46;
	/** 用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像），用户没有头像时该项为空 */
	private String headimgurl64;
	/** 用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像），用户没有头像时该项为空 */
	private String headimgurl96;
	/** 用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像），用户没有头像时该项为空 */
	private String headimgurl130;
	/**  用户特权信息，json 数组，如微信沃卡用户为（chinaunicom） */
	private Collection<String> privilege = new ArrayList<String>();
	
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public Sex getSex() {
		return sex;
	}
	public void setSex(Sex sex) {
		this.sex = sex;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getHeadimgurl0() {
		return headimgurl0;
	}
	public void setHeadimgurl0(String headimgurl0) {
		this.headimgurl0 = headimgurl0;
	}
	public String getHeadimgurl46() {
		return headimgurl46;
	}
	public void setHeadimgurl46(String headimgurl46) {
		this.headimgurl46 = headimgurl46;
	}
	public String getHeadimgurl64() {
		return headimgurl64;
	}
	public void setHeadimgurl64(String headimgurl64) {
		this.headimgurl64 = headimgurl64;
	}
	public String getHeadimgurl96() {
		return headimgurl96;
	}
	public void setHeadimgurl96(String headimgurl96) {
		this.headimgurl96 = headimgurl96;
	}
	public String getHeadimgurl130() {
		return headimgurl130;
	}
	public void setHeadimgurl130(String headimgurl130) {
		this.headimgurl130 = headimgurl130;
	}
	public Collection<String> getPrivilege() {
		return privilege;
	}
	public void addPrivilege(String strprivilege) {
		MyAssert.hasText(strprivilege);
		this.privilege.add(strprivilege);
	}
	
	public void addPrivileges(Collection<String> privileges) {
		MyAssert.notEmpty(privileges);
		this.privilege.addAll(privileges);
	}
	
	public void clearPrivileges() {
		this.privilege.clear();
	}
	
}
