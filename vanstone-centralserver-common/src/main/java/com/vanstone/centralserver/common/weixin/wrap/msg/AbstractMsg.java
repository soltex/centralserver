/**
 * 
 */
package com.vanstone.centralserver.common.weixin.wrap.msg;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * @author shipeng
 */
public abstract class AbstractMsg {

	/**接受验证参数名称*/
	public static final String VALIDATE_STR_NAME = "echostr";
	
	/**文本消息*/
	public static final String COMMON_TYPE_TEXT = "text";
	
	/**图片消息*/
	public static final String COMMON_TYPE_IMAGE = "image";
	
	/**语音消息*/
	public static final String COMMON_TYPE_VOICE = "voice";
	
	/**视频消息*/
	public static final String COMMON_TYPE_VIDEO = "video";
	
	/**地理位置消息*/
	public static final String COMMON_TYPE_LOCATION = "location";
	
	/**链接消息*/
	public static final String COMMON_TYPE_LINK = "link";
	
	/**音乐消息*/
	public static final String COMMON_TYPE_MUSIC = "music";
	
	/**图文消息*/
	public static final String COMMON_TYPE_NEWS = "news";
	
	/**事件类型*/
	public static final String TYPE_EVENT = "event";
	
	/**关注关注事件,扫描带参数二维码事件*/
	public static final String RECEIVE_EVENT_SUBSCRIBE = "subscribe";
	
	/**取消关注事件*/
	public static final String RECEIVE_EVENT_UNSUBSCRIBE = "unsubscribe";
	
	/**用户已关注时的事件推送*/
	public static final String RECEIVE_EVENT_SCAN = "SCAN";
	
	/**上报地理位置事件*/
	public static final String RECEIVE_EVENT_LOCATION = "LOCATION";
	
	/**自定义菜单事件*/
	public static final String RECEIVE_EVENT_CLICK = "CLICK";
	
	/**点击菜单跳转链接时的事件推送*/
	public static final String RECEIVE_EVENT_VIEW = "VIEW";
	
	/**消息推送结果通知事件*/
	public static final String MASSSENDJOBFINISH_EVENT = "MASSSENDJOBFINISH";
	
	/**模板消息发送结束*/
	public static final String TEMPLATESENDJOBFINISH_EVENT = "TEMPLATESENDJOBFINISH";
	
	/**
	 * 开发者微信号
	 */
	private String toUserName;
	/**
	 * 发送方帐号（一个OpenID）
	 */
	private String fromUserName;
	/**
	 * 消息创建时间 （整型）
	 */
	private Date createTime;
	/**
	 * text
	 */
	private String msgType;
	/**
	 * 扩展参数
	 * */
	private Map<String, Object> extendParams = new LinkedHashMap<String, Object>();
	
	public AbstractMsg(String msgType) {
		this.msgType = msgType;
	}
	
	public String getToUserName() {
		return toUserName;
	}

	public void setToUserName(String toUserName) {
		this.toUserName = toUserName;
	}

	public String getFromUserName() {
		return fromUserName;
	}

	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getMsgType() {
		return msgType;
	}

	protected void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	public Map<String, Object> getExtendParams() {
		return extendParams;
	}
	
	public void addParam(String paramName, Object paramValue) {
		extendParams.put(paramName, paramValue);
	}
	
	public void addParams(Map<String, Object> params) {
		extendParams.putAll(params);
	}
	
	public void clearParams() {
		extendParams.clear();
	}
	
	protected Document toReplyDoc() {
		Document document = DocumentHelper.createDocument();
		Element xmlElement = document.addElement("xml");
		Element ToUserNameElement = xmlElement.addElement("ToUserName");
		ToUserNameElement.setText(this.getToUserName());

		Element FromUserNameElement = xmlElement.addElement("FromUserName");
		FromUserNameElement.setText(this.getFromUserName());

		Element CreateTimeElement = xmlElement.addElement("CreateTime");
		if (this.getCreateTime() == null) {
			this.setCreateTime(new Date());
		}
		CreateTimeElement.setText(String.valueOf(this.getCreateTime()));

		Element MsgTypeElement = xmlElement.addElement("MsgType");
		MsgTypeElement.setText(this.getMsgType());

		return document;
	}
}
