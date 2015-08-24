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
public class CorpApp0Servlet extends AbstractCorpWeixinServlet {

	private static final long serialVersionUID = -4316866273769806455L;

	/* (non-Javadoc)
	 * @see com.vanstone.weixin.corp.client.servlet.AbstractCorpWeixinServlet#getCorpApp()
	 */
	@Override
	public ICorpApp getCorpApp() {
		return CorpClientConf.getInstance().getCorpApp(0);
	}

}
