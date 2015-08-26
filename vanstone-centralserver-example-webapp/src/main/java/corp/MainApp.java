/**
 * 
 */
package corp;

import com.vanstone.centralserver.common.corp.user.CorpUserInfo;
import com.vanstone.centralserver.common.util.DebugUtil;
import com.vanstone.centralserver.common.weixin.WeixinException;
import com.vanstone.weixin.corp.client.WeixinCorpClientFactory;
import com.vanstone.weixin.corp.client.WeixinCorpClientManager;
import com.vanstone.weixin.corp.client.conf.xml.DefaultXmlConfInitiator;

/**
 * @author shipeng
 *
 */
public class MainApp {

	private static WeixinCorpClientManager weixinCorpClientManager = WeixinCorpClientFactory.getWeixinCorpClientManager();
	
	public static void main(String[] args) {
		try {
			DefaultXmlConfInitiator initiator = new DefaultXmlConfInitiator();
			initiator.initial();
			CorpUserInfo userInfo = weixinCorpClientManager.getCorpUserInfo("shipeng");
			DebugUtil.print(userInfo);
		} catch (WeixinException e) {
			e.printStackTrace();
		}
	}
	
}
