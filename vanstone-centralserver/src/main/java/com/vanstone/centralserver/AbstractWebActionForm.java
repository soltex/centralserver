/**
 * 
 */
package com.vanstone.centralserver;

import com.vanstone.centralserver.common.Constants;


/**
 * @author shipeng
 */
public class AbstractWebActionForm {

	private Integer p =1;
	private String rel = Constants.DWZ_DEFAULT_CONTAINER_ID;

	public Integer getP() {
		return p;
	}
	
	public void setP(Integer p) {
		this.p = p;
	}

	public String getRel() {
		return rel;
	}

	public void setRel(String rel) {
		this.rel = rel;
	}
	
}
