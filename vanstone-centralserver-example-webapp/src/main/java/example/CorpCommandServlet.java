/**
 * 
 */
package example;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.vanstone.centralserver.common.ServletUtil;
import com.vanstone.centralserver.common.util.aes.AesException;
import com.vanstone.centralserver.common.util.aes.WXBizMsgCrypt;

/**
 * @author shipeng
 *
 */
public class CorpCommandServlet extends HttpServlet {

	private static final long serialVersionUID = -8399140147629854479L;

	public static final String TOKEN = "sagacity";
	
	public static final String ENCODING_AES_KEY = "jWmYm7qr5nMoAUwZRjGtBxmz3KA1tkAj3ykkR6q2B2C";
	
	public static final String CORP_ID = "wx87c2c0c1d9ec9f7a";
	
	@Override
	protected void doPost(HttpServletRequest servletRequest, HttpServletResponse servletResponse) throws ServletException, IOException {
		
		if (__is_valiate_echostring(servletRequest, servletResponse)) {
			__validate_echostring(servletRequest, servletResponse);
			return;
		}
		
		
		String bodyxml = ServletUtil.readRequestBody(servletRequest);
		try {
			String msg_signature = servletRequest.getParameter("msg_signature");
			String timestamp = servletRequest.getParameter("timestamp");
			String nonce = servletRequest.getParameter("nonce");
			
			Document document = DocumentHelper.parseText(bodyxml);
			Element rootElement = document.getRootElement();
			
			Element ToUserNameElement = rootElement.element("ToUserName");
			Element AgentIDElement = rootElement.element("AgentID");
			Element Encrypt = rootElement.element("Encrypt");
			
			WXBizMsgCrypt msgCrypt = new WXBizMsgCrypt(TOKEN, ENCODING_AES_KEY, CORP_ID);
			String xmlcontent = msgCrypt.DecryptMsg(msg_signature, timestamp, nonce, bodyxml);
			System.out.println(xmlcontent);
			
			System.out.println(" ========================== ");
			
			//send msg
			Document xmlDocument = DocumentHelper.createDocument();
			Element xmlElement = xmlDocument.addElement("xml");
			Element ToUserNameElement1 = xmlElement.addElement("ToUserName");
			ToUserNameElement1.setText("shipeng");
			
			Element FromUserNameElement  =xmlElement.addElement("FromUserName");
			FromUserNameElement.setText(CORP_ID);
			
			Element CreateTimeElement = xmlElement.addElement("CreateTime");
			CreateTimeElement.setText(String.valueOf(new Date().getTime()));
			
			Element MsgTypeElement = xmlElement.addElement("MsgType");
			MsgTypeElement.setText("text");
			
			Element ContentElement  =xmlElement.addElement("Content");
			ContentElement.setText("asdasdasdasdasd ");
			
			System.out.println(xmlDocument.asXML());
			
			System.out.println(" ================================");
			
			String encryptstring = msgCrypt.EncryptMsg(xmlDocument.asXML(), timestamp, nonce);
			
			System.out.println(encryptstring);
			
			
//			Document responseDocument = DocumentHelper.createDocument();
//			
//			Element responseElement = responseDocument.addElement("xml");
//			
//			Element EncryptElement = responseElement.addElement("Encrypt");
//			EncryptElement.setText(encryptstring);
//			
//			Element MsgSignatureElement = responseElement.addElement("MsgSignature");
//			MsgSignatureElement.setText(msg_signature);
//			
//			Element TimeStampElement = responseElement.addElement("TimeStamp");
//			TimeStampElement.setText(timestamp);
//			
//			Element NonceElement = responseElement.addElement("Nonce");
//			NonceElement.setText(nonce);
//			
//			System.out.println(responseDocument.asXML());
			ServletUtil.write(servletResponse, encryptstring);
			System.out.println(" ================================");
			
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (AesException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	
	
	
	
	private boolean __is_valiate_echostring(HttpServletRequest servletRequest, HttpServletResponse servletResponse) {
		String echostring = servletRequest.getParameter("echostr");
		if(echostring == null || echostring.equals("")) {
			return false;
		}
		return true;
	}
	
	private void __validate_echostring(HttpServletRequest servletRequest, HttpServletResponse servletResponse) {
		
		String msg_signature = servletRequest.getParameter("msg_signature");
		String timestamp = servletRequest.getParameter("timestamp");
		String nonce = servletRequest.getParameter("nonce");
		String echoStr = servletRequest.getParameter("echostr");
		try {
			WXBizMsgCrypt wxBizMsgCrypt = new WXBizMsgCrypt(TOKEN, ENCODING_AES_KEY, CORP_ID);
			String string = wxBizMsgCrypt.VerifyURL(msg_signature, timestamp, nonce, echoStr);
			servletResponse.getWriter().print(string);
		} catch (AesException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return;
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doPost(req, resp);
	}
}
