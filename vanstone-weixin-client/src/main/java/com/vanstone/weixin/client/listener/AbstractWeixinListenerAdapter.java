/**
 * 
 */
package com.vanstone.weixin.client.listener;

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
 * WeixinListener适配器
 * @author shipeng
 */
public abstract class AbstractWeixinListenerAdapter implements IWeixinListener {

	@Override
    public void onTextMsg(Msg4Text msg, HttpServletRequest servletRequest, HttpServletResponse servletResponse) {}

	@Override
    public void onImageMsg(Msg4Image msg, HttpServletRequest servletRequest, HttpServletResponse servletResponse) {}

	@Override
    public void onVoiceMsg(Msg4Voice msg, HttpServletRequest servletRequest, HttpServletResponse servletResponse) {}

	@Override
    public void onVideoMsg(Msg4Video msg, HttpServletRequest servletRequest, HttpServletResponse servletResponse) {}

	@Override
    public void onLocationMsg(Msg4Location msg, HttpServletRequest servletRequest, HttpServletResponse servletResponse) {}

	@Override
    public void onLinkMsg(Msg4Link msg, HttpServletRequest servletRequest, HttpServletResponse servletResponse) {}

	@Override
    public void onSpeechRecognitionMsg(Msg4SpeechRecognition msg, HttpServletRequest servletRequest, HttpServletResponse servletResponse) {}

	@Override
    public void onSubscribeEvent(Event4Subscribe event, HttpServletRequest servletRequest, HttpServletResponse servletResponse) {}

	@Override
    public void onFirstSubscribeEvent(Event4Subscribe event, HttpServletRequest servletRequest, HttpServletResponse servletResponse) {}

	@Override
    public void onUnsubscribeEvent(Event4Unsubscribe event, HttpServletRequest servletRequest,HttpServletResponse servletResponse) {}

	@Override
    public void onScanEvent(Event4Scan event, HttpServletRequest servletRequest, HttpServletResponse servletResponse) {}

	@Override
    public void onLocationEvent(Event4Location event, HttpServletRequest servletRequest,HttpServletResponse servletResponse) {}

	@Override
    public void onClickEvent(Event4Click event, HttpServletRequest servletRequest, HttpServletResponse servletResponse) {}

	@Override
    public void onViewEvent(Event4View event, HttpServletRequest servletRequest, HttpServletResponse servletResponse) {}

	@Override
    public void onErrorMsg(String requestBody, HttpServletRequest servletRequest, HttpServletResponse servletResponse) {}

	@Override
	public void onMassSendJobFinishEvent(Event4MassSendJobFinish event, HttpServletRequest servletRequest,HttpServletResponse servletResponse) {}

	@Override
	public void onTemplateSendJobFinishEvent(Event4TemplateJobSendFinish event, HttpServletRequest servletRequest,HttpServletResponse servletResponse) {}
	
}
