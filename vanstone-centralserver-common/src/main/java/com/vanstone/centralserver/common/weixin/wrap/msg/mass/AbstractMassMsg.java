/**
 * 
 */
package com.vanstone.centralserver.common.weixin.wrap.msg.mass;

/**
 * 
 * 
 *  群发的消息类型，
 *  图文消息为mpnews，
 *  文本消息为text，
 *  语音为voice，
 *  音乐为music，
 *  图片为image，
 *  视频为video 
 * @author shipeng
 */
public abstract class AbstractMassMsg {
	
	/**图文类型*/
	public static final String MPNEWS_TYPE = "mpnews";
	
	/**文本类型*/
	public static final String TEXT_TYPE = "text";
	
	/**音频类型*/
	public static final String VOICE_TYPE = "voice";
	
	/**音乐类型*/
	public static final String MUSIC_TYPE = "music";
	
	/**图片类型*/
	public static final String IMAGE_TYPE = "image";
	
	/**视频类型*/
	public static final String MPVIDEO_TYPE = "mpvideo";
	
	/**消息类型*/
	private String msgtype;
	
	public AbstractMassMsg(String msgtype) {
		this.msgtype = msgtype;
	}

	public String getMsgtype() {
		return msgtype;
	}
	
	/**
	 * 转换为Json信息
	 * @return
	 */
	public abstract String toJson() ;
}
