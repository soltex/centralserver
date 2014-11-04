/**
 * 
 */
package com.vanstone.centralserver.common.weixin.wrap.msg.mass;

/**
 * 群发消息任务结果
 * @author shipeng
 */
public class MassResult {

	private String type;
	private int errcode;
	private String errmsg;
	private int msg_id;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public String getErrmsg() {
		return errmsg;
	}

	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}

	public int getMsg_id() {
		return msg_id;
	}

	public void setMsg_id(int msg_id) {
		this.msg_id = msg_id;
	}

	public int getErrcode() {
		return errcode;
	}

	public void setErrcode(int errcode) {
		this.errcode = errcode;
	}
	
}
