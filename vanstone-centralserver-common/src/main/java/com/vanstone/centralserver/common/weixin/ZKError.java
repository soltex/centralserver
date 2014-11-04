/**
 * 
 */
package com.vanstone.centralserver.common.weixin;

/**
 * @author shipengpipi@126.com
 *
 */
public class ZKError extends RuntimeException {

    private static final long serialVersionUID = -2929167650330320895L;
    
    public ZKError() {
    	super();
    }
    
    public ZKError(String errorMsg) {
    	super(errorMsg);
    }
    
    public ZKError(Exception exception) {
    	super(exception);
    }
}
