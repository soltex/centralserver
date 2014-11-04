/**
 * 
 */
package com.vanstone.centralserver.common.weixin.wrap.msg;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.vanstone.centralserver.common.weixin.wrap.msg.mass.Event4MassSendJobFinish;
import com.vanstone.centralserver.common.weixin.wrap.msg.template.Event4TemplateJobSendFinish;


/**
 * @author shipeng
 */
public class MessageHelper {
	
	/**
	 * 解析Msg为AbstractMsg对象
	 * @param msg
	 * @return
	 */
	public static AbstractMsg parseMsg(String msg) {
		if (msg == null || "".equals(msg)) {
			return null;
		}
		Document document = null;
		try {
			document = DocumentHelper.parseText(msg);
		} catch (DocumentException e) {
			e.printStackTrace();
			throw new IllegalArgumentException(e);
		}
		Element xmlElement = document.getRootElement();
		Element ToUserNameElement = xmlElement.element("ToUserName");
		String ToUserName = ToUserNameElement.getText();
		
		Element fromUserNameElement = xmlElement.element("FromUserName");
		String FromUserName = fromUserNameElement.getText();
		
		Element createTimeElement = xmlElement.element("CreateTime");
		Long CreateTime = Long.parseLong(createTimeElement.getText());
		
		Element MsgTypeElement = xmlElement.element("MsgType");
		String MsgType = MsgTypeElement.getText();
		
		if (MsgType == null || "".equals(MsgType)) {
			throw new IllegalArgumentException();
		}
		
		AbstractMsg msgobject = null;
		
		//普通消息
		if (MsgType.equalsIgnoreCase(AbstractMsg.COMMON_TYPE_TEXT)) {
			msgobject = new Msg4Text();
			Element contentElement = xmlElement.element("Content");
			String content = contentElement.getText();
			((Msg4Text)msgobject).setContent(content);
			
			Element MsgIdElement = xmlElement.element("MsgId");
			String MsgId = MsgIdElement.getText();
			((Msg4Text)msgobject).setMsgId(MsgId);
			_initBaseInfo(msgobject, ToUserName, FromUserName, CreateTime);
			return msgobject;
		}
		
		//图片消息
		if (MsgType.equalsIgnoreCase(AbstractMsg.COMMON_TYPE_IMAGE)) {
			msgobject = new Msg4Image();
			Element picUrlElement = xmlElement.element("PicUrl");
			String PicUrl = picUrlElement.getText();
			((Msg4Image)msgobject).setPicUrl(PicUrl);
			
			Element mediaIdElement = xmlElement.element("MediaId");
			String MediaId = mediaIdElement.getText();
			((Msg4Image)msgobject).setMediaId(MediaId);
			
			Element MsgIdElement = xmlElement.element("MsgId");
			String MsgId = MsgIdElement.getText();
			((Msg4Image)msgobject).setMsgId(MsgId);
			_initBaseInfo(msgobject, ToUserName, FromUserName, CreateTime);
			return msgobject;
		}
		
		//语音消息
		if (MsgType.equalsIgnoreCase(AbstractMsg.COMMON_TYPE_VOICE)) {
			Element recognitionElement = xmlElement.element("Recognition");
			if (recognitionElement == null) {
				msgobject = new Msg4Voice();
			}else{
				msgobject = new Msg4SpeechRecognition();
				((Msg4SpeechRecognition)msgobject).setRecognition(recognitionElement.getText());
			}
			
			Element FormatElement = xmlElement.element("Format");
			String format = FormatElement.getText();
			((Msg4Voice)msgobject).setFormat(format);
			
			Element mediaIdElement = xmlElement.element("MediaId");
			String MediaId = mediaIdElement.getText();
			((Msg4Voice)msgobject).setMediaId(MediaId);
			
			Element MsgIdElement = xmlElement.element("MsgId");
			String MsgId = MsgIdElement.getText();
			((Msg4Voice)msgobject).setMsgId(MsgId);
			_initBaseInfo(msgobject, ToUserName, FromUserName, CreateTime);
			return msgobject;
		}
		
		//视频消息
		if (MsgType.equalsIgnoreCase(AbstractMsg.COMMON_TYPE_VIDEO)) {
			msgobject = new Msg4Video();
			Element thumbMediaIdElement = xmlElement.element("ThumbMediaId");
			String ThumbMediaId = thumbMediaIdElement.getText();
			((Msg4Video)msgobject).setThumbMediaId(ThumbMediaId);
			
			Element mediaIdElement = xmlElement.element("MediaId");
			String MediaId = mediaIdElement.getText();
			((Msg4Video)msgobject).setMediaId(MediaId);
			
			Element MsgIdElement = xmlElement.element("MsgId");
			String MsgId = MsgIdElement.getText();
			((Msg4Video)msgobject).setMsgId(MsgId);
			_initBaseInfo(msgobject, ToUserName, FromUserName, CreateTime);
			return msgobject;
		}
		
		//地理位置消息
		if (MsgType.equalsIgnoreCase(AbstractMsg.COMMON_TYPE_LOCATION)) {
			msgobject = new Msg4Location();
			Element location_XElement = xmlElement.element("Location_X");
			String Location_X = location_XElement.getText();
			((Msg4Location)msgobject).setLocationX(Double.parseDouble(Location_X));
			
			Element location_YElement = xmlElement.element("Location_Y");
			String Location_Y = location_YElement.getText();
			((Msg4Location)msgobject).setLocationY(Double.parseDouble(Location_Y));
			
			Element ScaleElement = xmlElement.element("Scale");
			String Scale = ScaleElement.getText();
			((Msg4Location)msgobject).setScale(Double.parseDouble(Scale));
			
			Element labelElement = xmlElement.element("Label");
			String Label = labelElement.getText();
			((Msg4Location)msgobject).setLabel(Label);
			
			Element MsgIdElement = xmlElement.element("MsgId");
			String MsgId = MsgIdElement.getText();
			((Msg4Location)msgobject).setMsgId(MsgId);
			_initBaseInfo(msgobject, ToUserName, FromUserName, CreateTime);
			return msgobject;
		}
		
		//链接消息
		if (MsgType.equalsIgnoreCase(AbstractMsg.COMMON_TYPE_LINK)) {
			msgobject = new Msg4Link();
			Element titleElement = xmlElement.element("Title");
			String Title = titleElement.getText();
			((Msg4Link)msgobject).setTitle(Title);
			
			Element descriptionElement= xmlElement.element("Description");
			String description = descriptionElement.getText();
			((Msg4Link)msgobject).setDescription(description);
			
			Element urlElement= xmlElement.element("Url");
			String Url = urlElement.getText();
			((Msg4Link)msgobject).setUrl(Url);
			
			Element MsgIdElement = xmlElement.element("MsgId");
			String MsgId = MsgIdElement.getText();
			((Msg4Video)msgobject).setMsgId(MsgId);
			
			_initBaseInfo(msgobject, ToUserName, FromUserName, CreateTime);
			return msgobject;
		}
		
		
		//事件类型
		if (MsgType.equalsIgnoreCase(AbstractMsg.TYPE_EVENT)) {
			Element eventElement = xmlElement.element("Event");
			if (eventElement == null || eventElement.getText() == null || "".equals(eventElement.getText())) {
				throw new IllegalArgumentException();
			}
			String event = eventElement.getText();
			
			//关注关注事件
			if (event.equalsIgnoreCase(AbstractMsg.RECEIVE_EVENT_SUBSCRIBE)) {
				Element eventKeyElement = xmlElement.element("EventKey");
				String EventKey = eventKeyElement.getText();
				if (EventKey == null || "".equals(EventKey)) {
					//普通订阅
					msgobject = new Event4Subscribe();
					
					_initBaseInfo(msgobject, ToUserName, FromUserName, CreateTime);
					return msgobject;
				}else{
					//用户未关注时，进行关注后的事件推送
					msgobject = new Event4FirstSubscribe();
					((Event4FirstSubscribe)msgobject).setEventKey(EventKey);
					
					Element ticketElement = xmlElement.element("Ticket");
					String Ticket = ticketElement.getText();
					((Event4FirstSubscribe)msgobject).setTicket(Ticket);
					
					_initBaseInfo(msgobject, ToUserName, FromUserName, CreateTime);
					return msgobject;
				}
			}
			
			//取消关注事件
			if (event.equalsIgnoreCase(AbstractMsg.RECEIVE_EVENT_UNSUBSCRIBE)) {
				msgobject = new Event4Unsubscribe();
				
				_initBaseInfo(msgobject, ToUserName, FromUserName, CreateTime);
				return msgobject;
			}
			
			//用户已关注时的事件推送
			if (event.equalsIgnoreCase(AbstractMsg.RECEIVE_EVENT_SCAN)) {
				msgobject = new Event4Scan();
				
				Element eventKeyElement = xmlElement.element("EventKey");
				String EventKey = eventKeyElement.getText();
				((Event4Scan)msgobject).setEventKey(EventKey);
				
				Element ticketElement = xmlElement.element("Ticket");
				String Ticket = ticketElement.getText();
				((Event4Scan)msgobject).setTicket(Ticket);
				
				_initBaseInfo(msgobject, ToUserName, FromUserName, CreateTime);
				return msgobject;
			}
			
			//上报地理位置事件
			if (event.equalsIgnoreCase(AbstractMsg.RECEIVE_EVENT_LOCATION)) {
				msgobject = new Event4Location();
				
				Element latitudeElement = xmlElement.element("Latitude");
				String Latitude = latitudeElement.getText();
				((Event4Location)msgobject).setLatitude(Double.parseDouble(Latitude));
				
				Element longitudeElement = xmlElement.element("Longitude");
				String Longitude = longitudeElement.getText();
				((Event4Location)msgobject).setLongitude(Double.parseDouble(Longitude));
				
				Element precisionElement = xmlElement.element("Precision");
				String Precision = precisionElement.getText();
				((Event4Location)msgobject).setPrecision(Double.parseDouble(Precision));
				
				_initBaseInfo(msgobject, ToUserName, FromUserName, CreateTime);
				return msgobject;
			}
			
			//点击菜单拉取消息时的事件推送 
			if (event.equalsIgnoreCase(AbstractMsg.RECEIVE_EVENT_CLICK)) {
				msgobject = new Event4Click();
				
				Element EventKeyElement = xmlElement.element("EventKey");
				String EventKey = EventKeyElement.getText();
				((Event4Click)msgobject).setEventKey(EventKey);
				
				_initBaseInfo(msgobject, ToUserName, FromUserName, CreateTime);
				return msgobject;
			}
			
			//点击菜单跳转链接时的事件推送
			if (event.equalsIgnoreCase(AbstractMsg.RECEIVE_EVENT_VIEW)) {
				msgobject = new Event4View();
				
				Element EventKeyElement = xmlElement.element("EventKey");
				String EventKey = EventKeyElement.getText();
				((Event4View)msgobject).setEventKey(EventKey);
				
				_initBaseInfo(msgobject, ToUserName, FromUserName, CreateTime);
				return msgobject;
			}
			
			//推送消息结果事件通知
			if (event.equalsIgnoreCase(AbstractMsg.MASSSENDJOBFINISH_EVENT)) {
				msgobject = new Event4MassSendJobFinish();
				/*
				 * <MsgID>1988</MsgID>
					<Status><![CDATA[sendsuccess]]></Status>
					<TotalCount>100</TotalCount>
					<FilterCount>80</FilterCount>
					<SentCount>75</SentCount>
					<ErrorCount>5</ErrorCount>
				 */
				Element MsgIDElement = xmlElement.element("MsgID");
				String strMsgId = MsgIDElement != null ? MsgIDElement.getTextTrim() : null;
				if (!StringUtils.isEmpty(strMsgId) && !StringUtils.isBlank(strMsgId)) {
					((Event4MassSendJobFinish)msgobject).setMsgId(Integer.parseInt(strMsgId));
				}
				Element StatusElement = xmlElement.element("Status");
				String strStatus =StatusElement != null ? StatusElement.getTextTrim() : null;
				if (!StringUtils.isEmpty(strStatus) && !StringUtils.isBlank(strStatus)) {
					((Event4MassSendJobFinish)msgobject).setStatus(strStatus);
				}
				Element TotalCountElement = xmlElement.element("TotalCount");
				String strTotalCount = TotalCountElement != null ? TotalCountElement.getTextTrim() : null;
				if (!StringUtils.isEmpty(strTotalCount) && !StringUtils.isBlank(strTotalCount)) {
					((Event4MassSendJobFinish)msgobject).setTotalCount(Integer.parseInt(strTotalCount));
				}
				Element FilterCountElement = xmlElement.element("FilterCount");
				String strFilterCount = FilterCountElement != null ? FilterCountElement.getTextTrim() : null;
				if (!StringUtils.isEmpty(strFilterCount) && !StringUtils.isBlank(strFilterCount)) {
					((Event4MassSendJobFinish)msgobject).setFilterCount(Integer.parseInt(strFilterCount));
				}
				Element SentCountElement = xmlElement.element("SentCount");
				String strSendCount = SentCountElement != null ? SentCountElement.getTextTrim() : null;
				if (!StringUtils.isEmpty(strSendCount) && !StringUtils.isBlank(strSendCount)) {
					((Event4MassSendJobFinish)msgobject).setSentCount(Integer.parseInt(strSendCount));
				}
				Element ErrorCountElement = xmlElement.element("ErrorCount");
				String strErrorCount = ErrorCountElement != null ? ErrorCountElement.getTextTrim() : null;
				if (!StringUtils.isEmpty(strErrorCount) && !StringUtils.isBlank(strErrorCount)) {
					((Event4MassSendJobFinish)msgobject).setErrorCount(Integer.parseInt(strErrorCount));
				}
				_initBaseInfo(msgobject, ToUserName, FromUserName, CreateTime);
				return msgobject;
			}
			
			//在模版消息发送任务完成后，微信服务器会将是否送达成功作为通知，发送到开发者中心中填写的服务器配置地址中。 
			if (event.equalsIgnoreCase(AbstractMsg.TEMPLATESENDJOBFINISH_EVENT)) {
				msgobject = new Event4TemplateJobSendFinish();
				Element MsgIDElement = xmlElement.element("MsgID");
				String strMsgId = MsgIDElement != null ? MsgIDElement.getTextTrim() : null;
				if (!StringUtils.isEmpty(strMsgId) && !StringUtils.isBlank(strMsgId)) {
					((Event4TemplateJobSendFinish)msgobject).setMsgId(Integer.parseInt(strMsgId));
				}
				Element StatusElement = xmlElement.element("Status");
				String strStatus =StatusElement != null ? StatusElement.getTextTrim() : null;
				if (!StringUtils.isEmpty(strStatus) && !StringUtils.isBlank(strStatus)) {
					((Event4TemplateJobSendFinish)msgobject).setStatus(strStatus);
				}
				_initBaseInfo(msgobject, ToUserName, FromUserName, CreateTime);
				return msgobject;
			}
		}
		
		
		
		
		
		throw new IllegalArgumentException("msgtype not found.");
	}
	
	/**
	 * 初始化AbstractMsg信息
	 * @param msg
	 * @param ToUserName
	 * @param FromUserName
	 * @param CreateTime
	 */
	private static void _initBaseInfo(AbstractMsg msg, String ToUserName, String FromUserName, Long CreateTime) {
		msg.setToUserName(ToUserName);
		msg.setFromUserName(FromUserName);
		msg.setCreateTime(new Date(CreateTime));
	}
}
