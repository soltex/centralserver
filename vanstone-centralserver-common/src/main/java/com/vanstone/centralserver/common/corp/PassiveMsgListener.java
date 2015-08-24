/**
 * 
 */
package com.vanstone.centralserver.common.corp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.vanstone.centralserver.common.corp.passive.event.PassiveBatchJobResultEvent;
import com.vanstone.centralserver.common.corp.passive.event.PassiveClickEvent;
import com.vanstone.centralserver.common.corp.passive.event.PassiveEnterAgentEvent;
import com.vanstone.centralserver.common.corp.passive.event.PassiveLocationEvent;
import com.vanstone.centralserver.common.corp.passive.event.PassiveLocationSelectEvent;
import com.vanstone.centralserver.common.corp.passive.event.PassivePicPhotoORAlbumEvent;
import com.vanstone.centralserver.common.corp.passive.event.PassivePicSysphotoEvent;
import com.vanstone.centralserver.common.corp.passive.event.PassivePicWeixinEvent;
import com.vanstone.centralserver.common.corp.passive.event.PassiveScancodePushEvent;
import com.vanstone.centralserver.common.corp.passive.event.PassiveScancodeWaitmsgEvent;
import com.vanstone.centralserver.common.corp.passive.event.PassiveSubscribeEvent;
import com.vanstone.centralserver.common.corp.passive.event.PassiveUnsubscribeEvent;
import com.vanstone.centralserver.common.corp.passive.event.PassiveViewEvent;
import com.vanstone.centralserver.common.corp.passive.msg.PassiveImageMsg;
import com.vanstone.centralserver.common.corp.passive.msg.PassiveLocationMsg;
import com.vanstone.centralserver.common.corp.passive.msg.PassiveShortvideoMsg;
import com.vanstone.centralserver.common.corp.passive.msg.PassiveTextMsg;
import com.vanstone.centralserver.common.corp.passive.msg.PassiveVideoMsg;
import com.vanstone.centralserver.common.corp.passive.msg.PassiveVoiceMsg;

/**
 * @author shipeng 被动消息以及事件监听器
 */
public interface PassiveMsgListener {

	/**
	 * 上传图片消息监听
	 * 
	 * @param msg
	 * @param servletRequest
	 * @param servletResponse
	 */
	void OnPassiveImageMsg(PassiveImageMsg msg, HttpServletRequest servletRequest, HttpServletResponse servletResponse);

	/**
	 * 上报地理位置监听
	 * 
	 * @param msg
	 * @param servletRequest
	 * @param servletResponse
	 */
	void OnPassiveLocationMsg(PassiveLocationMsg msg, HttpServletRequest servletRequest, HttpServletResponse servletResponse);

	/**
	 * 上传小视频监听
	 * 
	 * @param msg
	 * @param servletRequest
	 * @param servletResponse
	 */
	void OnPassiveShortvideoMsg(PassiveShortvideoMsg msg, HttpServletRequest servletRequest, HttpServletResponse servletResponse);

	/**
	 * 上传文本监听
	 * 
	 * @param msg
	 * @param servletRequest
	 * @param servletResponse
	 */
	void OnPassiveTextMsg(PassiveTextMsg msg, HttpServletRequest servletRequest, HttpServletResponse servletResponse);

	/**
	 * 上传视频监听
	 * 
	 * @param msg
	 * @param servletRequest
	 * @param servletResponse
	 */
	void OnPassiveVideoMsg(PassiveVideoMsg msg, HttpServletRequest servletRequest, HttpServletResponse servletResponse);

	/**
	 * 上传语音监听
	 * 
	 * @param msg
	 * @param servletRequest
	 * @param servletResponse
	 */
	void OnPassiveVoiceMsg(PassiveVoiceMsg msg, HttpServletRequest servletRequest, HttpServletResponse servletResponse);

	/**
	 * 异步任务完成事件推送
	 * 
	 * @param Event
	 * @param servletRequest
	 * @param servletResponse
	 */
	void OnPassiveBatchJobResultEvent(PassiveBatchJobResultEvent Event, HttpServletRequest servletRequest, HttpServletResponse servletResponse);

	/**
	 * 菜单点击事件
	 * 
	 * @param Event
	 * @param servletRequest
	 * @param servletResponse
	 */
	void OnPassiveClickEvent(PassiveClickEvent Event, HttpServletRequest servletRequest, HttpServletResponse servletResponse);

	/**
	 * 成员进入应用的事件推送
	 * 
	 * @param Event
	 * @param servletRequest
	 * @param servletResponse
	 */
	void OnPassiveEnterAgentEvent(PassiveEnterAgentEvent Event, HttpServletRequest servletRequest, HttpServletResponse servletResponse);

	/**
	 * 上报地理位置事件
	 * 
	 * @param Event
	 * @param servletRequest
	 * @param servletResponse
	 */
	void OnPassiveLocationEvent(PassiveLocationEvent Event, HttpServletRequest servletRequest, HttpServletResponse servletResponse);

	/**
	 * 弹出地理位置选择器的事件推送
	 * 
	 * @param Event
	 * @param servletRequest
	 * @param servletResponse
	 */
	void OnPassiveLocationSelectEvent(PassiveLocationSelectEvent Event, HttpServletRequest servletRequest, HttpServletResponse servletResponse);

	/**
	 * 弹出拍照或者相册发图的事件推送
	 * 
	 * @param Event
	 * @param servletRequest
	 * @param servletResponse
	 */
	void OnPassivePicPhotoORAlbumEvent(PassivePicPhotoORAlbumEvent Event, HttpServletRequest servletRequest, HttpServletResponse servletResponse);

	/**
	 * 弹出系统拍照发图的事件推送
	 * 
	 * @param Event
	 * @param servletRequest
	 * @param servletResponse
	 */
	void OnPassivePicSysphotoEvent(PassivePicSysphotoEvent Event, HttpServletRequest servletRequest, HttpServletResponse servletResponse);

	/**
	 * 弹出微信相册发图器的事件推送
	 * 
	 * @param Event
	 * @param servletRequest
	 * @param servletResponse
	 */
	void OnPassivePicWeixinEvent(PassivePicWeixinEvent Event, HttpServletRequest servletRequest, HttpServletResponse servletResponse);

	/**
	 * 扫码推事件的事件推送
	 * 
	 * @param Event
	 * @param servletRequest
	 * @param servletResponse
	 */
	void OnPassiveScancodePushEvent(PassiveScancodePushEvent Event, HttpServletRequest servletRequest, HttpServletResponse servletResponse);

	/**
	 * 扫码推事件且弹出“消息接收中”提示框的事件推送
	 * 
	 * @param msg
	 * @param servletRequest
	 * @param servletResponse
	 */
	void OnPassiveScancodeWaitMsgEvent(PassiveScancodeWaitmsgEvent msg, HttpServletRequest servletRequest, HttpServletResponse servletResponse);

	/**
	 * 订阅
	 * 
	 * @param Event
	 * @param servletRequest
	 * @param servletResponse
	 */
	void OnPassiveSubscribeEvent(PassiveSubscribeEvent Event, HttpServletRequest servletRequest, HttpServletResponse servletResponse);

	/**
	 * 取消订阅
	 * 
	 * @param Event
	 * @param servletRequest
	 * @param servletResponse
	 */
	void OnPassiveUnsubscribeEvent(PassiveUnsubscribeEvent Event, HttpServletRequest servletRequest, HttpServletResponse servletResponse);

	/**
	 * 视图事件
	 * 
	 * @param Event
	 * @param servletRequest
	 * @param servletResponse
	 */
	void OnPassiveViewEvent(PassiveViewEvent Event, HttpServletRequest servletRequest, HttpServletResponse servletResponse);

}
