package com.vanstone.weixin.corp.client.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vanstone.centralserver.common.ServletUtil;
import com.vanstone.centralserver.common.corp.ICorpApp;
import com.vanstone.centralserver.common.corp.passive.AbstractPassiveMsg;
import com.vanstone.centralserver.common.corp.passive.CorpWeixinEvent;
import com.vanstone.centralserver.common.corp.passive.PassiveCorpMsgType;
import com.vanstone.centralserver.common.corp.passive.event.AbstractPassiveEvent;
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
import com.vanstone.centralserver.common.util.aes.AesException;
import com.vanstone.centralserver.common.util.aes.WXBizMsgCrypt;
import com.vanstone.weixin.corp.client.conf.CorpClientConf;

/**
 * @author shipeng
 *
 */
public class ListenerSelector {
	
	private static Logger LOG = LoggerFactory.getLogger(ListenerSelector.class);
	
	/**
	 * 监听器选择
	 * @param servletRequest
	 * @param servletResponse
	 * @throws AesException 
	 * @throws DocumentException 
	 */
	public void execute(ICorpApp corpApp, HttpServletRequest servletRequest, HttpServletResponse servletResponse) throws AesException, DocumentException {
		if (corpApp.getListener() == null) {
			LOG.error("CorpApp Listener Not Found.!");
			return;
		}
		
		String request_body_xml = ServletUtil.readRequestBody(servletRequest);
		
		String msg_signature = servletRequest.getParameter("msg_signature");
		String timestamp = servletRequest.getParameter("timestamp");
		String nonce = servletRequest.getParameter("nonce");
		
		WXBizMsgCrypt msgCrypt = new WXBizMsgCrypt(corpApp.getToken(), corpApp.getEncodingAESKey(), CorpClientConf.getInstance().getCorp().getAppID());
		String msgxml = msgCrypt.DecryptMsg(msg_signature, timestamp, nonce, request_body_xml);
		
		Document document = DocumentHelper.parseText(msgxml);
		Element rootElement = document.getRootElement();
		
		String string_msgtype = rootElement.elementText("MsgType");
		
		AbstractPassiveMsg passiveMsg = null;
		//消息定义
		if (string_msgtype.equals(PassiveCorpMsgType.TEXT.getType())) {
			passiveMsg = new PassiveTextMsg();
		}else if (string_msgtype.equals(PassiveCorpMsgType.IMAGE.getType())) {
			passiveMsg = new PassiveImageMsg();
		}else if (string_msgtype.equals(PassiveCorpMsgType.VOICE.getType())) {
			passiveMsg = new PassiveVoiceMsg();
		}else if (string_msgtype.equals(PassiveCorpMsgType.VIDEO.getType())) {
			passiveMsg = new PassiveVideoMsg();
		}else if (string_msgtype.equals(PassiveCorpMsgType.SHORTVIDEO.getType())) {
			passiveMsg = new PassiveShortvideoMsg();
		}else if (string_msgtype.equals(PassiveCorpMsgType.LOCATION.getType())) {
			passiveMsg = new PassiveLocationMsg();
		}else if (string_msgtype.equals(PassiveCorpMsgType.EVENT.getType())) {
			//事件类型
			String string_event = rootElement.elementText("Event");
			if (string_event.equals(CorpWeixinEvent.SUBSCRIBE.getEvent())) {
				passiveMsg = new PassiveSubscribeEvent();
			}else if (string_event.equals(CorpWeixinEvent.UNSUBSCRIBE.getEvent())) {
				passiveMsg = new PassiveUnsubscribeEvent();
			}else if (string_event.equals(CorpWeixinEvent.LOCATION.getEvent())) {
				passiveMsg = new PassiveLocationEvent();
			}else if (string_event.equals(CorpWeixinEvent.CLICK.getEvent())) {
				passiveMsg = new PassiveClickEvent();
			}else if (string_event.equals(CorpWeixinEvent.VIEW.getEvent())) {
				passiveMsg = new PassiveViewEvent();
			}else if (string_event.equals(CorpWeixinEvent.SCANCODE_PUSH.getEvent())) {
				passiveMsg = new PassiveScancodePushEvent();
			}else if (string_event.equals(CorpWeixinEvent.SCANCODE_WAITMSG.getEvent())) {
				passiveMsg = new PassiveScancodeWaitmsgEvent();
			}else if (string_event.equals(CorpWeixinEvent.PIC_SYSPHOTO.getEvent())) {
				passiveMsg = new PassivePicSysphotoEvent();
			}else if (string_event.equals(CorpWeixinEvent.PIC_PHOTO_OR_ALBUM.getEvent())) {
				passiveMsg = new PassivePicPhotoORAlbumEvent();
			}else if (string_event.equals(CorpWeixinEvent.PIC_WEIXIN.getEvent())) {
				passiveMsg = new PassivePicWeixinEvent();
			}else if (string_event.equals(CorpWeixinEvent.LOCATION_SELECT.getEvent())) {
				passiveMsg = new PassiveLocationSelectEvent();
			}else if (string_event.equals(CorpWeixinEvent.ENTER_AGENT.getEvent())) {
				passiveMsg = new PassiveEnterAgentEvent();
			}else if (string_event.equals(CorpWeixinEvent.BATCH_JOB_RESULT.getEvent())) {
				passiveMsg = new PassiveBatchJobResultEvent();
			}
		}
		
		if (passiveMsg == null) {
			throw new IllegalArgumentException();
		}
		//数据初始化
		passiveMsg.initial(rootElement, servletRequest);
		//监听器处理
		
		PassiveCorpMsgType passiveCorpMsgType = passiveMsg.getMsgType();
		if (passiveCorpMsgType.equals(PassiveCorpMsgType.TEXT)) {
			corpApp.getListener().OnPassiveTextMsg((PassiveTextMsg)passiveMsg, servletRequest, servletResponse);
			return;
		} 
		if (passiveCorpMsgType.equals(PassiveCorpMsgType.IMAGE)) {
			corpApp.getListener().OnPassiveImageMsg((PassiveImageMsg)passiveMsg, servletRequest, servletResponse);
			return;
		} 
		if (passiveCorpMsgType.equals(PassiveCorpMsgType.VOICE)) {
			corpApp.getListener().OnPassiveVoiceMsg((PassiveVoiceMsg)passiveMsg, servletRequest, servletResponse);
			return;
		} 
		if (passiveCorpMsgType.equals(PassiveCorpMsgType.VIDEO)) {
			corpApp.getListener().OnPassiveVideoMsg((PassiveVideoMsg)passiveMsg, servletRequest, servletResponse);
			return;
		} 
		if (passiveCorpMsgType.equals(PassiveCorpMsgType.SHORTVIDEO)) {
			corpApp.getListener().OnPassiveShortvideoMsg((PassiveShortvideoMsg)passiveMsg, servletRequest, servletResponse);
			return;
		}
		if (passiveCorpMsgType.equals(PassiveCorpMsgType.LOCATION)) {
			corpApp.getListener().OnPassiveLocationMsg((PassiveLocationMsg)passiveMsg, servletRequest, servletResponse);
			return;
		}
		
		if (passiveCorpMsgType.equals(PassiveCorpMsgType.EVENT)) {
			//事件处理
			CorpWeixinEvent weixinEvent = ((AbstractPassiveEvent)passiveMsg).getEvent();
			
			if (weixinEvent.equals(CorpWeixinEvent.SUBSCRIBE)) {
				corpApp.getListener().OnPassiveSubscribeEvent((PassiveSubscribeEvent)passiveMsg, servletRequest, servletResponse);
				return;
			}
			
			if (weixinEvent.equals(CorpWeixinEvent.UNSUBSCRIBE)) {
				corpApp.getListener().OnPassiveUnsubscribeEvent((PassiveUnsubscribeEvent)passiveMsg, servletRequest, servletResponse);
				return;
			}
			
			if (weixinEvent.equals(CorpWeixinEvent.CLICK)) {
				corpApp.getListener().OnPassiveClickEvent((PassiveClickEvent)passiveMsg, servletRequest, servletResponse);
				return;
			}
			
			if (weixinEvent.equals(CorpWeixinEvent.VIEW)) {
				corpApp.getListener().OnPassiveViewEvent((PassiveViewEvent)passiveMsg, servletRequest, servletResponse);
				return;
			}
			
			if (weixinEvent.equals(CorpWeixinEvent.SCANCODE_PUSH)) {
				corpApp.getListener().OnPassiveScancodePushEvent((PassiveScancodePushEvent)passiveMsg, servletRequest, servletResponse);
				return;
			}
			
			if (weixinEvent.equals(CorpWeixinEvent.SCANCODE_WAITMSG)) {
				corpApp.getListener().OnPassiveScancodeWaitMsgEvent((PassiveScancodeWaitmsgEvent)passiveMsg, servletRequest, servletResponse);
				return;
			}
			
			if (weixinEvent.equals(CorpWeixinEvent.PIC_SYSPHOTO)) {
				corpApp.getListener().OnPassivePicSysphotoEvent((PassivePicSysphotoEvent)passiveMsg, servletRequest, servletResponse);
				return;
			}
			
			if (weixinEvent.equals(CorpWeixinEvent.PIC_PHOTO_OR_ALBUM)) {
				corpApp.getListener().OnPassivePicPhotoORAlbumEvent((PassivePicPhotoORAlbumEvent)passiveMsg, servletRequest, servletResponse);
				return;
			}
			
			if (weixinEvent.equals(CorpWeixinEvent.PIC_WEIXIN)) {
				corpApp.getListener().OnPassivePicWeixinEvent((PassivePicWeixinEvent)passiveMsg, servletRequest, servletResponse);
				return;
			}
			
			if (weixinEvent.equals(CorpWeixinEvent.LOCATION_SELECT)) {
				corpApp.getListener().OnPassiveLocationSelectEvent((PassiveLocationSelectEvent)passiveMsg, servletRequest, servletResponse);
				return;
			}
			
			if (weixinEvent.equals(CorpWeixinEvent.ENTER_AGENT)) {
				corpApp.getListener().OnPassiveEnterAgentEvent((PassiveEnterAgentEvent)passiveMsg, servletRequest, servletResponse);
				return;
			}
			
			if (weixinEvent.equals(CorpWeixinEvent.BATCH_JOB_RESULT)) {
				corpApp.getListener().OnPassiveBatchJobResultEvent((PassiveBatchJobResultEvent)passiveMsg, servletRequest, servletResponse);
				return;
			}
		}
	}
}
