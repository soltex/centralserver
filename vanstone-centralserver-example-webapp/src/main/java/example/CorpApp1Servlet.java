/**
 * 
 */
package example;

import com.vanstone.centralserver.common.corp.ICorpApp;
import com.vanstone.weixin.corp.client.conf.CorpClientConf;
import com.vanstone.weixin.corp.client.servlet.AbstractCorpWeixinServlet;

/**
 * @author shipeng
 *
 */
public class CorpApp1Servlet extends AbstractCorpWeixinServlet{

	private static final long serialVersionUID = -8399140147629854479L;

	
	@Override
	public ICorpApp getCorpApp() {
		return CorpClientConf.getInstance().getCorpApp(1);
	}
	
}
