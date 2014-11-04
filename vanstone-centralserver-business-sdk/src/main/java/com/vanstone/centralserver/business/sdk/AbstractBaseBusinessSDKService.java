/**
 * 
 */
package com.vanstone.centralserver.business.sdk;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.PlatformTransactionManager;

import com.vanstone.framework.business.services.AbstractBusinessService;

/**
 * @author shipeng
 *
 */
public abstract class AbstractBaseBusinessSDKService extends AbstractBusinessService {

	private static final long serialVersionUID = 1834876901679107970L;

	@Override
	@Autowired
	@Qualifier(value="jdbcTransactionManager")
	public void setTransactionManager(
			PlatformTransactionManager transactionManager) {
		super.setTransactionManager(transactionManager);
	}
	
}
