/**
 * 
 */
package com.vanstone.centralserver.common.zk;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;

/**
 * @author shipeng
 * 
 */
public class CuratorFrameworkBuilder {

	/**
	 * 创建CuratorFramework Client
	 * @param connectionString
	 * @param connectionTimeoutMs
	 * @return
	 */
	public static CuratorFramework createCuratorFramework(String connectionString, int connectionTimeoutMs) {
		CuratorFramework curatorFramework = CuratorFrameworkFactory.builder().connectString(connectionString)
				.retryPolicy(new RetryNTimes(Integer.MAX_VALUE, 1000)).connectionTimeoutMs(connectionTimeoutMs).build();
		return curatorFramework;
	}
	
	/**
	 * 创建CuratorFramework并且启动
	 * @param connectionString
	 * @param connectionTimeoutMs
	 * @return
	 */
	public static CuratorFramework createCuratorFrameworkAndStart(String connectionString, int connectionTimeoutMs) {
		CuratorFramework curatorFramework = createCuratorFramework(connectionString, connectionTimeoutMs);
		if (curatorFramework != null) {
			curatorFramework.start();
		}
		return curatorFramework;
	}
}
