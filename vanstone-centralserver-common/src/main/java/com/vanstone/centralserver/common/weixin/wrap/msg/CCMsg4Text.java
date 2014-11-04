/**
 * 
 */
package com.vanstone.centralserver.common.weixin.wrap.msg;

import java.util.LinkedHashMap;
import java.util.Map;

import com.vanstone.centralserver.common.JsonUtil;

/**
 * 发送文本消息
	{
	    "touser":"OPENID",
	    "msgtype":"text",
	    "text":
	    {
	         "content":"Hello World"
	    }
	}
 * @author shipeng
 *
 */
public class CCMsg4Text extends AbstractCustomerServiceMsg {
	
	/**文本消息内容*/
	private String content;
	
	public CCMsg4Text() {
		this.setMsgtype(AbstractMsg.COMMON_TYPE_TEXT);
	}
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	@Override
	public String toJson() {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("touser", this.getTouser());
		map.put("msgtype", this.getMsgtype());
		Map<String, Object> textMap = new LinkedHashMap<String, Object>();
		textMap.put("content", this.getContent());
		map.put("text", textMap);
		return JsonUtil.object2PrettyString(map,false);
	}
	
}
