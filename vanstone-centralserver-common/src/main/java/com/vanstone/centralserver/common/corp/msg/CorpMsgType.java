/**
 * 
 */
package com.vanstone.centralserver.common.corp.msg;

/**
 * @author shipeng
 *
 */
public enum CorpMsgType {

	Text("text消息", "text"),

	Image("image消息", "image"),

	Voice("voice消息", "voice"),

	Video("video消息", "video"),

	File("file消息", "file"),

	News("news消息", "news"),

	Mpnews("mpnews消息", "mpnews");

	private String desc;
	private String code;

	private CorpMsgType(String desc, String code) {
		this.desc = desc;
		this.code = code;
	}

	public String getDesc() {
		return desc;
	}

	public String getCode() {
		return code;
	}
	
}
