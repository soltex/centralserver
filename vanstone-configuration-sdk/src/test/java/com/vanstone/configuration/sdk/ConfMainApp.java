/**
 * 
 */
package com.vanstone.configuration.sdk;

/**
 * @author shipeng
 * 
 */
public class ConfMainApp {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		IConfSDKManager confSDKManager = ConfSDKFactory.getConfSDKManager();
		try {
			confSDKManager.updateConf("innodev.pdp.systemplate.group", "First_Subscribe_Template", "呵呵呵呵呵呵呵");
		} catch (ConfNotFoundException e) {
			e.printStackTrace();
		}

		// System.out.println(confSDKManager.addConf("aaa", "sss",
		// "sdfsdfsdfsdfsdfsdf"));
	}

}
