/**
 * 
 */
package com.vanstone.centralserver.common.weixin.wrap;

/**
 * @author shipeng
 */
public enum MediaType {
	
	/**图片*/
	Image("image"),
	/**语音*/
	Voice("voice"),
	/**视频*/
	Video("video"),
	/**缩略图*/
	Thumb("thumb");
	
	private String code;
	
	private MediaType(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}
	
}
