/**
 * 
 */
package com.vanstone.centralserver.common.corp.passive;

/**
 * @author shipeng
 *
 */
public enum CorpMsgType {

	TEXT("text消息", "text"),

	IMAGE("image消息", "image"),

	VOICE("voice消息", "voice"),

	VIDEO("video消息", "video"),

	SHORTVIDEO("小视频消息", "shortvideo"),

	LOCATION("location消息", "location"),

	NEWS("新闻消息", "news"),
	
	EVENT("事件", "event");

	private String desc;
	private String type;

	private CorpMsgType(String desc, String type) {
		this.desc = desc;
		this.type = type;
	}

	public String getDesc() {
		return desc;
	}

	public String getType() {
		return type;
	}
	
	public static CorpMsgType parseIgnoreCase(String typestring) {
		if (typestring == null || typestring.equals("")) {
			return null;
		}
		CorpMsgType[] msgTypes = CorpMsgType.values();
		for (CorpMsgType msgType : msgTypes) {
			if (msgType.getType().equalsIgnoreCase(typestring)) {
				return msgType;
			}
		}
		return null;
	}
}
