/**
 * 
 */
package com.vanstone.centralserver.common.corp.js;

/**
 * @author shipeng
 * Ticket包装类
 */
public class TicketObject {

	private String ticket;
	private String signature;
	private String url;
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

}
