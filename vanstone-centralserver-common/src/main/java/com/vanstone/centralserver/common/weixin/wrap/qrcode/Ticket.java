/**
 * 
 */
package com.vanstone.centralserver.common.weixin.wrap.qrcode;

/**
 * @author shipeng
 * 
 */
public class Ticket {

	/** 获取的二维码ticket，凭借此ticket可以在有效时间内换取二维码。 */
	private String ticket;
	/** 二维码的有效时间，以秒为单位。最大不超过1800。 */
	private Integer expireSeconds;

	public String getTicket() {
		return ticket;
	}
	
	public void setTicket(String ticket) {
		this.ticket = ticket;
	}
	
	public Integer getExpireSeconds() {
		return expireSeconds;
	}

	public void setExpireSeconds(Integer expireSeconds) {
		this.expireSeconds = expireSeconds;
	}

}
