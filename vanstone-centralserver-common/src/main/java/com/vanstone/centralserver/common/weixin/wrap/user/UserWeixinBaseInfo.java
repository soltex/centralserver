/**
 * 
 */
package com.vanstone.centralserver.common.weixin.wrap.user;

import java.util.Date;

import org.apache.commons.codec.binary.Base64;

import com.vanstone.centralserver.common.Constants;
import com.vanstone.centralserver.common.weixin.wrap.Language;
import com.vanstone.centralserver.common.weixin.wrap.Sex;
import com.vanstone.emoji.EmojiHelper;

/**
 * @author shipeng
 */
public class UserWeixinBaseInfo {
	/** 用户是否订阅该公众号标识，值为0时，代表此用户没有关注该公众号，拉取不到其余信息。 */
	private boolean subscribe;
	/** 用户的标识，对当前公众号唯一 */
	private String openid;
	/** 用户的昵称 */
	private String nickname;
	/** 用户的性别，值为1时是男性，值为2时是女性，值为0时是未知 */
	private Sex sex;
	/** 用户所在城市 */
	private String city;
	/** 用户所在国家 */
	private String country;
	/** 用户所在省份 */
	private String province;
	/** 用户的语言，简体中文为zh_CN */
	private Language language;
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
	/** 用户关注时间，为时间戳。如果用户曾多次关注，则取最后关注时间 */
	private Date subscribeTime;
	/** 只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段。详见：获取用户个人信息（UnionID机制） */
	private String unionid;

	public boolean isSubscribe() {
		return subscribe;
	}

	public void setSubscribe(boolean subscribe) {
		this.subscribe = subscribe;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getNickname() {
		return nickname;
	}
	
	/**
	 * 返回Nickname的Base64值，用于防治出现Emoji值
	 * @return
	 */
	public String getNickNameBase64() {
		if (this.getNickname() != null && !this.getNickname().equals("")) {
			return Base64.encodeBase64String(this.getNickname().getBytes(Constants.SYS_CHARSET_UTF8));
		}
		return null;
	}
	
	/**
	 * 返回NickName Emoji值 转换后的值
	 * @return
	 */
	public String getNickNameEmojiConvert() {
		if (this.getNickname() != null && !this.getNickname().equals("")) { 
			return EmojiHelper.emoji2cn(this.getNickname());
		}
		return null;
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

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public Language getLanguage() {
		return language;
	}

	public void setLanguage(Language language) {
		this.language = language;
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

	public String getUnionid() {
		return unionid;
	}

	public void setUnionid(String unionid) {
		this.unionid = unionid;
	}

	public Date getSubscribeTime() {
		return subscribeTime;
	}

	public void setSubscribeTime(Date subscribeTime) {
		this.subscribeTime = subscribeTime;
	}

}
