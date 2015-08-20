/**
 * 
 */
package com.vanstone.centralserver.common.util;

import java.sql.Timestamp;
import java.util.Date;

import com.vanstone.centralserver.common.CommonDateUtil;

/**
 * @author shipeng
 *
 */
public class UnixJavaDateTimeUtil {
	
	/**
	 * unix转java datetime 时间戳管理
	 * @param timestamp
	 * @return
	 */
	public static Date unixToJavaDateTime(long timestamp) {
		return new Date(timestamp*1000);
	}
	
	public static int javaDateTimeToUnixTimestamp(Date date) {
		Timestamp ts = new Timestamp(date.getTime());
		return (int)(ts.getTime()/1000);
	}
	
	public static void main(String[] args) {
		System.out.println(javaDateTimeToUnixTimestamp(new Date()));
		Date time = new Date(1440082751);
		System.out.println(CommonDateUtil.date2String(time, CommonDateUtil.PATTERN_STANDARD));
	}
}
