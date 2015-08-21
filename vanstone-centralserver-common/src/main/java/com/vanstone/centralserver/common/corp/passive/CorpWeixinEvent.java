/**
 * 
 */
package com.vanstone.centralserver.common.corp.passive;

/**
 * @author shipeng
 *
 */
public enum CorpWeixinEvent {
	
	SUBSCRIBE("订阅","subscribe"),
	UNSUBSCRIBE("取消订阅","unsubscribe"),
	LOCATION("上报地理位置事件","LOCATION"),
	CLICK("菜单点击","CLICK"),
	VIEW("视图事件","VIEW"),
	SCANCODE_PUSH("扫码推事件的事件推送","scancode_push"),
	SCANCODE_WAITMSG("扫码推事件且弹出“消息接收中”提示框的事件推送","scancode_waitmsg"),
	PIC_SYSPHOTO("弹出系统拍照发图的事件推送","pic_sysphoto"),
	PIC_PHOTO_OR_ALBUM("弹出拍照或者相册发图的事件推送","pic_photo_or_album"),
	PIC_WEIXIN("弹出微信相册发图器的事件推送","pic_weixin"),
	LOCATION_SELECT("弹出地理位置选择器的事件推送","location_select"),
	ENTER_AGENT("成员进入应用的事件推送","enter_agent"),
	BATCH_JOB_RESULT("异步任务完成事件推送","batch_job_result");
	
	private String desc;
	private String event;
	
	private CorpWeixinEvent(String desc, String event) {
		this.desc = desc;
		this.event = event;
	}

	public String getDesc() {
		return desc;
	}

	public String getEvent() {
		return event;
	}
	
	/**
	 * 解析当前事件
	 * @param eventstring
	 * @return
	 */
	public static CorpWeixinEvent parseIgnoreCase(String eventstring) {
		if (eventstring == null || eventstring.equals("")) {
			return null;
		}
		CorpWeixinEvent[] events = CorpWeixinEvent.values();
		for (CorpWeixinEvent event : events) {
			if (eventstring.equalsIgnoreCase(event.getEvent())) {
				return event;
			}
		}
		return null;
	}
}
