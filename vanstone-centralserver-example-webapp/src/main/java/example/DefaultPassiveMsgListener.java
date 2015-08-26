/**
 * 
 */
package example;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.vanstone.centralserver.common.corp.PassiveMsgListener;
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
import com.vanstone.centralserver.common.corp.passive.reply.PassiveTextReply;
import com.vanstone.centralserver.common.util.DebugUtil;
import com.vanstone.centralserver.common.weixin.WeixinException;
import com.vanstone.weixin.corp.client.WeixinCorpClientFactory;
import com.vanstone.weixin.corp.client.WeixinCorpClientManager;
import com.vanstone.weixin.corp.client.conf.CorpClientConf;

/**
 * @author shipeng
 *
 */
public class DefaultPassiveMsgListener implements PassiveMsgListener {

	/* (non-Javadoc)
	 * @see com.vanstone.centralserver.common.corp.PassiveMsgListener#OnPassiveImageMsg(com.vanstone.centralserver.common.corp.passive.msg.PassiveImageMsg, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public void OnPassiveImageMsg(PassiveImageMsg msg, HttpServletRequest servletRequest, HttpServletResponse servletResponse) {
		DebugUtil.print(msg);
	}
	
	/* (non-Javadoc)
	 * @see com.vanstone.centralserver.common.corp.PassiveMsgListener#OnPassiveLocationMsg(com.vanstone.centralserver.common.corp.passive.msg.PassiveLocationMsg, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public void OnPassiveLocationMsg(PassiveLocationMsg msg, HttpServletRequest servletRequest, HttpServletResponse servletResponse) {
		DebugUtil.print(msg);
	}

	/* (non-Javadoc)
	 * @see com.vanstone.centralserver.common.corp.PassiveMsgListener#OnPassiveShortvideoMsg(com.vanstone.centralserver.common.corp.passive.msg.PassiveShortvideoMsg, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public void OnPassiveShortvideoMsg(PassiveShortvideoMsg msg, HttpServletRequest servletRequest, HttpServletResponse servletResponse) {
		DebugUtil.print(msg);
	}

	/* (non-Javadoc)
	 * @see com.vanstone.centralserver.common.corp.PassiveMsgListener#OnPassiveTextMsg(com.vanstone.centralserver.common.corp.passive.msg.PassiveTextMsg, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public void OnPassiveTextMsg(PassiveTextMsg msg, HttpServletRequest servletRequest, HttpServletResponse servletResponse) {
		WeixinCorpClientManager weixinCorpClientManager = WeixinCorpClientFactory.getWeixinCorpClientManager();
		DebugUtil.print(msg);
		PassiveTextReply reply = new PassiveTextReply();
		try {
			reply.setContent("我是测试使用的东西，你看看<a href='" + weixinCorpClientManager.createOAuth2RedirectUrl("http://testcorp.sagacityidea.com/test", "ABC") + "'>测试</a>");
		} catch (WeixinException e1) {
			e1.printStackTrace();
		}
		reply.setCreateTime(new Date());
		reply.setFromUserName(msg.getToUserName());
		reply.setToUserName(msg.getFromUserName());
		
		try {
			weixinCorpClientManager.sendCorpReply(CorpClientConf.getInstance().getCorpApp(Integer.parseInt(msg.getAgentID())), reply, String.valueOf(msg.getTimestamp()), msg.getNonce(), servletResponse);
		} catch (WeixinException e) {
			e.printStackTrace();
		}
	}
	
	/* (non-Javadoc)
	 * @see com.vanstone.centralserver.common.corp.PassiveMsgListener#OnPassiveVideoMsg(com.vanstone.centralserver.common.corp.passive.msg.PassiveVideoMsg, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public void OnPassiveVideoMsg(PassiveVideoMsg msg, HttpServletRequest servletRequest, HttpServletResponse servletResponse) {
		DebugUtil.print(msg);
	}

	/* (non-Javadoc)
	 * @see com.vanstone.centralserver.common.corp.PassiveMsgListener#OnPassiveVoiceMsg(com.vanstone.centralserver.common.corp.passive.msg.PassiveVoiceMsg, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public void OnPassiveVoiceMsg(PassiveVoiceMsg msg, HttpServletRequest servletRequest, HttpServletResponse servletResponse) {
		DebugUtil.print(msg);
	}

	/* (non-Javadoc)
	 * @see com.vanstone.centralserver.common.corp.PassiveMsgListener#OnPassiveBatchJobResultEvent(com.vanstone.centralserver.common.corp.passive.event.PassiveBatchJobResultEvent, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public void OnPassiveBatchJobResultEvent(PassiveBatchJobResultEvent Event, HttpServletRequest servletRequest, HttpServletResponse servletResponse) {
		DebugUtil.print(Event);
	}

	/* (non-Javadoc)
	 * @see com.vanstone.centralserver.common.corp.PassiveMsgListener#OnPassiveClickEvent(com.vanstone.centralserver.common.corp.passive.event.PassiveClickEvent, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public void OnPassiveClickEvent(PassiveClickEvent Event, HttpServletRequest servletRequest, HttpServletResponse servletResponse) {
		DebugUtil.print(Event);
	}

	/* (non-Javadoc)
	 * @see com.vanstone.centralserver.common.corp.PassiveMsgListener#OnPassiveEnterAgentEvent(com.vanstone.centralserver.common.corp.passive.event.PassiveEnterAgentEvent, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public void OnPassiveEnterAgentEvent(PassiveEnterAgentEvent Event, HttpServletRequest servletRequest, HttpServletResponse servletResponse) {
		DebugUtil.print(Event);
	}

	/* (non-Javadoc)
	 * @see com.vanstone.centralserver.common.corp.PassiveMsgListener#OnPassiveLocationEvent(com.vanstone.centralserver.common.corp.passive.event.PassiveLocationEvent, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public void OnPassiveLocationEvent(PassiveLocationEvent Event, HttpServletRequest servletRequest, HttpServletResponse servletResponse) {
		DebugUtil.print(Event);
	}

	/* (non-Javadoc)
	 * @see com.vanstone.centralserver.common.corp.PassiveMsgListener#OnPassiveLocationSelectEvent(com.vanstone.centralserver.common.corp.passive.event.PassiveLocationSelectEvent, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public void OnPassiveLocationSelectEvent(PassiveLocationSelectEvent Event, HttpServletRequest servletRequest, HttpServletResponse servletResponse) {
		DebugUtil.print(Event);
	}

	/* (non-Javadoc)
	 * @see com.vanstone.centralserver.common.corp.PassiveMsgListener#OnPassivePicPhotoORAlbumEvent(com.vanstone.centralserver.common.corp.passive.event.PassivePicPhotoORAlbumEvent, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public void OnPassivePicPhotoORAlbumEvent(PassivePicPhotoORAlbumEvent Event, HttpServletRequest servletRequest, HttpServletResponse servletResponse) {
		DebugUtil.print(Event);
	}

	/* (non-Javadoc)
	 * @see com.vanstone.centralserver.common.corp.PassiveMsgListener#OnPassivePicSysphotoEvent(com.vanstone.centralserver.common.corp.passive.event.PassivePicSysphotoEvent, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public void OnPassivePicSysphotoEvent(PassivePicSysphotoEvent Event, HttpServletRequest servletRequest, HttpServletResponse servletResponse) {
		DebugUtil.print(Event);
	}

	/* (non-Javadoc)
	 * @see com.vanstone.centralserver.common.corp.PassiveMsgListener#OnPassivePicWeixinEvent(com.vanstone.centralserver.common.corp.passive.event.PassivePicWeixinEvent, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public void OnPassivePicWeixinEvent(PassivePicWeixinEvent Event, HttpServletRequest servletRequest, HttpServletResponse servletResponse) {
		DebugUtil.print(Event);
	}

	/* (non-Javadoc)
	 * @see com.vanstone.centralserver.common.corp.PassiveMsgListener#OnPassiveScancodePushEvent(com.vanstone.centralserver.common.corp.passive.event.PassiveScancodePushEvent, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public void OnPassiveScancodePushEvent(PassiveScancodePushEvent Event, HttpServletRequest servletRequest, HttpServletResponse servletResponse) {
		DebugUtil.print(Event);
	}

	/* (non-Javadoc)
	 * @see com.vanstone.centralserver.common.corp.PassiveMsgListener#OnPassiveScancodeWaitMsgEvent(com.vanstone.centralserver.common.corp.passive.event.PassiveScancodeWaitmsgEvent, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public void OnPassiveScancodeWaitMsgEvent(PassiveScancodeWaitmsgEvent msg, HttpServletRequest servletRequest, HttpServletResponse servletResponse) {
		DebugUtil.print(msg);
	}

	/* (non-Javadoc)
	 * @see com.vanstone.centralserver.common.corp.PassiveMsgListener#OnPassiveSubscribeEvent(com.vanstone.centralserver.common.corp.passive.event.PassiveSubscribeEvent, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public void OnPassiveSubscribeEvent(PassiveSubscribeEvent Event, HttpServletRequest servletRequest, HttpServletResponse servletResponse) {
		DebugUtil.print(Event);
	}

	/* (non-Javadoc)
	 * @see com.vanstone.centralserver.common.corp.PassiveMsgListener#OnPassiveUnsubscribeEvent(com.vanstone.centralserver.common.corp.passive.event.PassiveUnsubscribeEvent, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public void OnPassiveUnsubscribeEvent(PassiveUnsubscribeEvent Event, HttpServletRequest servletRequest, HttpServletResponse servletResponse) {
		DebugUtil.print(Event);
	}

	/* (non-Javadoc)
	 * @see com.vanstone.centralserver.common.corp.PassiveMsgListener#OnPassiveViewEvent(com.vanstone.centralserver.common.corp.passive.event.PassiveViewEvent, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public void OnPassiveViewEvent(PassiveViewEvent Event, HttpServletRequest servletRequest, HttpServletResponse servletResponse) {
		DebugUtil.print(Event);
	}

}
