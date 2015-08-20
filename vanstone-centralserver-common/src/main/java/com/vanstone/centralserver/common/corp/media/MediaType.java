/**
 * 
 */
package com.vanstone.centralserver.common.corp.media;

/**
 * @author shipeng
 *
 */
public enum MediaType {
	
	Image("图片", "image"), Voice("语音", "voice"), Video("视频", "video"), File("普通文件", "file");
	
	private String desc;
	private String code;
	
	private MediaType(String desc, String code) {
		this.desc = desc;
		this.code = code;
	}

	public String getDesc() {
		return desc;
	}

	public String getCode() {
		return code;
	}
	
	/**
	 * 解析MediaType
	 * @param code
	 * @return
	 */
	public static MediaType parse(String code) {
		MediaType[] mediaTypes = MediaType.values();
		for (MediaType mediaType : mediaTypes) {
			if (mediaType.getCode().equals(code)) {
				return mediaType;
			}
		}
		throw new IllegalArgumentException();
	}
}
