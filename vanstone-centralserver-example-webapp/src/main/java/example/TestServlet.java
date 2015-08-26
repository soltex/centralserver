/**
 * 
 */
package example;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.vanstone.centralserver.common.corp.oauth2.OAuth2Result;
import com.vanstone.centralserver.common.corp.oauth2.RedirectResult;
import com.vanstone.centralserver.common.util.DebugUtil;
import com.vanstone.centralserver.common.weixin.WeixinException;
import com.vanstone.weixin.corp.client.WeixinCorpClientFactory;
import com.vanstone.weixin.corp.client.WeixinCorpClientManager;

/**
 * @author shipeng
 *
 */
public class TestServlet extends HttpServlet {

	private static final long serialVersionUID = -6782940230097209416L;
	

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		WeixinCorpClientManager weixinCorpClientManager = WeixinCorpClientFactory.getWeixinCorpClientManager();
		RedirectResult rr = weixinCorpClientManager.getRedirectResult(req);
		
		try {
			OAuth2Result result = weixinCorpClientManager.getUserInfo( rr.getCode());
			
			DebugUtil.print(result);
			
			
		} catch (WeixinException e) {
			e.printStackTrace();
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doPost(req, resp);
	}
	
}
