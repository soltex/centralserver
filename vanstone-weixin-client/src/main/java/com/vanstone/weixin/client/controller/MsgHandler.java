/**
 * 
 */
package com.vanstone.weixin.client.controller;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vanstone.centralserver.common.MyAssert;
import com.vanstone.centralserver.common.ServletUtil;
import com.vanstone.centralserver.common.weixin.wrap.msg.AbstractEvent;
import com.vanstone.centralserver.common.weixin.wrap.msg.AbstractMsg;
import com.vanstone.centralserver.common.weixin.wrap.msg.Event4Click;
import com.vanstone.centralserver.common.weixin.wrap.msg.Event4FirstSubscribe;
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
import com.vanstone.weixin.client.controller.repo.WeixinListenerRepo;
import com.vanstone.weixin.client.listener.IWeixinListener;

/**
 * 微信事件处理器
 * @author shipeng
 */
public class MsgHandler {
	
	private static final Logger LOG = LoggerFactory.getLogger(MsgHandler.class);
	
	private WeixinListenerRepo weixinListenerRepo = WeixinListenerRepo.getInstance();
	
	public  MsgHandler() {}
	
	public void eventHandler(AbstractMsg msg, HttpServletRequest servletRequest, HttpServletResponse servletResponse) {
		MyAssert.notNull(msg);
		MyAssert.notNull(servletRequest);
		MyAssert.notNull(servletResponse);
		if (!weixinListenerRepo.existRepo()) {
			LOG.warn("Not exists listener regist in repo.");
			return;
		}
		Collection<IWeixinListener> weixinListeners = WeixinListenerRepo.getInstance().getRegistedListeners();
		LOG.debug("Weixin Listeners count : " + weixinListeners.size());
		String MsgType = msg.getMsgType();
		for (IWeixinListener listener : weixinListeners) {
			//文本消息
			if (MsgType.equalsIgnoreCase(AbstractMsg.COMMON_TYPE_TEXT)) {
				listener.onTextMsg((Msg4Text)msg, servletRequest, servletResponse);
				continue;
			}
			//图片消息
			if (MsgType.equalsIgnoreCase(AbstractMsg.COMMON_TYPE_IMAGE)) {
				listener.onImageMsg((Msg4Image)msg, servletRequest, servletResponse);
				continue;
			}
			//语音消息
			if (MsgType.equalsIgnoreCase(AbstractMsg.COMMON_TYPE_VOICE)) {
				if (msg instanceof Msg4SpeechRecognition) {
					listener.onSpeechRecognitionMsg((Msg4SpeechRecognition)msg, servletRequest, servletResponse);
					continue;
				}else{
					listener.onVoiceMsg((Msg4Voice)msg, servletRequest, servletResponse);
					continue;
				}
			}
			
			//视频消息
			if (MsgType.equalsIgnoreCase(AbstractMsg.COMMON_TYPE_VIDEO)) {
				listener.onVideoMsg((Msg4Video)msg, servletRequest, servletResponse);
				continue;
			}
			
			//地理位置消息
			if (MsgType.equalsIgnoreCase(AbstractMsg.COMMON_TYPE_LOCATION)) {
				listener.onLocationMsg((Msg4Location)msg, servletRequest, servletResponse);
				continue;
			}
			
			//链接消息
			if (MsgType.equalsIgnoreCase(AbstractMsg.COMMON_TYPE_LINK)) {
				listener.onLinkMsg((Msg4Link)msg, servletRequest, servletResponse);
				continue;
			}
			
			//事件类型
			if (MsgType.equalsIgnoreCase(AbstractMsg.TYPE_EVENT)) {
				AbstractEvent abstractEvent = (AbstractEvent)msg;
				String event = abstractEvent.getEvent();
				
				//关注关注事件
				if (event.equalsIgnoreCase(AbstractMsg.RECEIVE_EVENT_SUBSCRIBE)) {
					if (abstractEvent instanceof Event4FirstSubscribe) {
						listener.onFirstSubscribeEvent((Event4FirstSubscribe)msg, servletRequest, servletResponse);
						continue;
					}else{
						listener.onSubscribeEvent((Event4Subscribe)msg, servletRequest, servletResponse);
						continue;
					}
				}
				
				//取消关注事件
				if (event.equalsIgnoreCase(AbstractMsg.RECEIVE_EVENT_UNSUBSCRIBE)) {
					listener.onUnsubscribeEvent((Event4Unsubscribe)msg, servletRequest, servletResponse);
					continue;
				}
				
				//用户已关注时的事件推送
				if (event.equalsIgnoreCase(AbstractMsg.RECEIVE_EVENT_SCAN)) {
					listener.onScanEvent((Event4Scan)msg, servletRequest, servletResponse);
					continue;
				}
				
				//上报地理位置事件
				if (event.equalsIgnoreCase(AbstractMsg.RECEIVE_EVENT_LOCATION)) {
					listener.onLocationEvent((Event4Location)msg, servletRequest, servletResponse);
					continue;
				}
				
				//点击菜单拉取消息时的事件推送 
				if (event.equalsIgnoreCase(AbstractMsg.RECEIVE_EVENT_CLICK)) {
					listener.onClickEvent((Event4Click)msg, servletRequest, servletResponse);
					continue;
				}
				
				//点击菜单跳转链接时的事件推送
				if (event.equalsIgnoreCase(AbstractMsg.RECEIVE_EVENT_VIEW)) {
					listener.onViewEvent((Event4View)msg, servletRequest, servletResponse);
					continue;
				}
				
				//批量发送消息结果通知监听器
				if (event.equalsIgnoreCase(AbstractMsg.MASSSENDJOBFINISH_EVENT)) {
					listener.onMassSendJobFinishEvent((Event4MassSendJobFinish)msg, servletRequest, servletResponse);
					continue;
				}
			}
			
			//错误处理
			listener.onErrorMsg(ServletUtil.readRequestBody(servletRequest), servletRequest, servletResponse);
		}
	}
	
}
