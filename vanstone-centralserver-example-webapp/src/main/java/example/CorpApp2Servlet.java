/**
 * 
 */
package example;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.vanstone.centralserver.common.corp.ICorpApp;
import com.vanstone.weixin.corp.client.conf.CorpClientConf;
import com.vanstone.weixin.corp.client.servlet.AbstractCorpWeixinServlet;

/**
 * @author shipeng
 *
 */
public class CorpApp2Servlet extends AbstractCorpWeixinServlet{

	private static final long serialVersionUID = -8399140147629854479L;

	
	@Override
	public ICorpApp getCorpApp() {
		return CorpClientConf.getInstance().getCorpApp(2);
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doPost(req, resp);
	}
}
