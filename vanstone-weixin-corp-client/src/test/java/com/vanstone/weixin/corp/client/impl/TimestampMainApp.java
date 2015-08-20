/**
 * 
 */
package com.vanstone.weixin.corp.client.impl;

import java.sql.Timestamp;

/**
 * @author shipeng
 *
 */
public class TimestampMainApp {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		long time = 1440064695000L;
		Timestamp timestamp = new Timestamp(time);
		System.out.println(timestamp);
	}

}
