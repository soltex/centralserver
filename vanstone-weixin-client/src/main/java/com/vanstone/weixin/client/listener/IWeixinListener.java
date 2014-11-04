/**
 * 
 */
package com.vanstone.weixin.client.listener;

import java.util.EventListener;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.vanstone.centralserver.common.weixin.wrap.msg.Event4Click;
import com.vanstone.centralserver.common.weixin.wrap.msg.Event4Location;
import com.vanstone.centralserver.common.weixin.wrap.msg.Event4Scan;
import com.vanstone.centralserver.common.weixin.wrap.msg.Event4Subscribe;
import com.vanstone.centralserver.common.weixin.wrap.msg.Event4Unsubscribe;
import com.vanstone.centralserver.common.weixin.wrap.msg.Event4View;
import com.vanstone.centralserver.common.weixin.wrap.msg.Msg4Image;
import com.vanstone.centralserver.common.weixin.wrap.msg.Msg4Link;
import com.vanstone.centralserver.common.weixin.wrap.msg.Msg4Location;
import com.vanstone.centralserver.common.weixin.wrap.msg.Msg4SpeechRecognition;
import com.vanstone.centralserver.common.weixin.wrap.msg.Msg4Text;
import com.vanstone.centralserver.common.weixin.wrap.msg.Msg4Video;
import com.vanstone.centralserver.common.weixin.wrap.msg.Msg4Voice;
import com.vanstone.centralserver.common.weixin.wrap.msg.mass.Event4MassSendJobFinish;
import com.vanstone.centralserver.common.weixin.wrap.msg.template.Event4TemplateJobSendFinish;

/**
 * @author shipeng
 *
 */
public interface IWeixinListener extends EventListener {
	
	/**
	 * 收到文本消息
	 * @param msg
	 */
	void onTextMsg(Msg4Text msg, HttpServletRequest servletRequest, HttpServletResponse servletResponse);
	
	/**
	 * 收到图片消息
	 * @param msg
	 */
	void onImageMsg(Msg4Image msg, HttpServletRequest servletRequest, HttpServletResponse servletResponse);
	
	/**
	 * 语音消息
	 * @param msg
	 * @param servletRequest
	 * @param servletResponse
	 */
	void onVoiceMsg(Msg4Voice msg, HttpServletRequest servletRequest, HttpServletResponse servletResponse);
	
	/**
	 * 视频消息
	 * @param msg
	 * @param servletRequest
	 * @param servletResponse
	 */
	void onVideoMsg(Msg4Video msg, HttpServletRequest servletRequest, HttpServletResponse servletResponse);
	
	/**
	 * 收到地理位置消息
	 * @param msg
	 * @param servletRequest
	 * @param servletResponse
	 */
	void onLocationMsg(Msg4Location msg, HttpServletRequest servletRequest, HttpServletResponse servletResponse);
	
	/**
	 * 收到链接消息
	 * @param msg
	 * @param servletRequest
	 * @param servletResponse
	 */
	void onLinkMsg(Msg4Link msg, HttpServletRequest servletRequest, HttpServletResponse servletResponse);
	
	/**
	 * 开通语音识别功能，用户每次发送语音给公众号时，微信会在推送的语音消息XML数据包中，增加一个Recongnition字段。
	 * 注：由于客户端缓存，开发者开启或者关闭语音识别功能，对新关注者立刻生效，对已关注用户需要24小时生效。开发者可以重新关注此帐号进行测试。 
	 * @param msg
	 * @param servletRequest
	 * @param servletResponse
	 */
	void onSpeechRecognitionMsg(Msg4SpeechRecognition msg, HttpServletRequest servletRequest, HttpServletResponse servletResponse);
	
	/**
	 * 关注消息处理
	 * @param event
	 * @param servletRequest
	 * @param servletResponse
	 */
	void onSubscribeEvent(Event4Subscribe event, HttpServletRequest servletRequest, HttpServletResponse servletResponse);
	
	/**
	 * 用户未关注时，进行关注后的事件推送
	 * @param event
	 * @param servletRequest
	 * @param servletResponse
	 */
	void onFirstSubscribeEvent(Event4Subscribe event, HttpServletRequest servletRequest, HttpServletResponse servletResponse);
	
	/**
	 * 取消关注消息处理
	 * @param event
	 * @param servletRequest
	 * @param servletResponse
	 */
	void onUnsubscribeEvent(Event4Unsubscribe event, HttpServletRequest servletRequest, HttpServletResponse servletResponse);
	
	/**
	 * 用户已关注时的事件推送
	 * @param event
	 * @param servletRequest
	 * @param servletResponse
	 */
	void onScanEvent(Event4Scan event, HttpServletRequest servletRequest, HttpServletResponse servletResponse);
	
	/**
	 * 上报地理位置事件
	 * @param event
	 * @param servletRequest
	 * @param servletResponse
	 */
	void onLocationEvent(Event4Location event, HttpServletRequest servletRequest, HttpServletResponse servletResponse);
	
	/**
	 * 自定义菜单事件
	 * 用户点击自定义菜单后，微信会把点击事件推送给开发者，
	 * 请注意，点击菜单弹出子菜单，不会产生上报。 
	 * @param event
	 * @param servletRequest
	 * @param servletResponse
	 */
	void onClickEvent(Event4Click event, HttpServletRequest servletRequest, HttpServletResponse servletResponse);
	
	/**
	 * 点击菜单跳转链接时的事件推送
	 * @param event
	 * @param servletRequest
	 * @param servletResponse
	 */
	void onViewEvent(Event4View event, HttpServletRequest servletRequest, HttpServletResponse servletResponse);
	
	/**
	 * 错误消息
	 * @param msg
	 */
	void onErrorMsg(String requestBody, HttpServletRequest servletRequest, HttpServletResponse servletResponse);
	
	/**
	 * 批量发送消息结果通知监听器
	 * @param requestBody
	 * @param servletRequest
	 * @param servletResponse
	 */
	void onMassSendJobFinishEvent(Event4MassSendJobFinish event, HttpServletRequest servletRequest, HttpServletResponse servletResponse);
	
	/**
	 * 模板消息发送完成通知事件
	 * @param event
	 * @param servletRequest
	 * @param servletResponse
	 */
	void onTemplateSendJobFinishEvent(Event4TemplateJobSendFinish event, HttpServletRequest servletRequest, HttpServletResponse servletResponse);
	
}
