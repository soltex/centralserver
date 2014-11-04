/**
 * 
 */
package com.vanstone.centralserver.common.zk;

import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.data.Stat;

import com.vanstone.centralserver.common.Constants;
import com.vanstone.centralserver.common.MyAssert;
import com.vanstone.centralserver.common.weixin.ZKError;

/**
 * ZK 管理
 * @author shipengpipi@126.com
 */
public class ZKManager {
	
	private static class ZKManagerInstance {
		private static final ZKManager instance = new ZKManager();
	}
	
	private ZKManager() {}
	
	/**
	 * 获取ZKManager实例
	 * @return
	 */
	public static ZKManager getInstance() {
		return ZKManagerInstance.instance;
	}
	
	/**
	 * 写入ZK节点数据
	 * @param curatorFramework
	 * @param node
	 * @param value
	 */
	public void setNodeValue(CuratorFramework curatorFramework,String node, String value) {
		MyAssert.notNull(curatorFramework);
		MyAssert.hasText(node);
	    try {
	    	Stat nodeStat = curatorFramework.checkExists().forPath(node);
	    	if (nodeStat == null) {
	    		curatorFramework.create().creatingParentsIfNeeded().forPath(node, value != null ? value.getBytes(Constants.SYS_CHARSET_UTF8) : null);
	    		return;
	    	}
	        curatorFramework.setData().inBackground().forPath(node, value != null ? value.getBytes(Constants.SYS_CHARSET_UTF8) : null);
        } catch (Exception e) {
	        e.printStackTrace();
	        throw new ZKError(e);
        }
	}
	
	/**
	 * 删除节点
	 * @param curatorFramework
	 * @param node
	 */
	public void deleteNode(CuratorFramework curatorFramework, String node) {
		MyAssert.notNull(curatorFramework);
		MyAssert.hasText(node);
		try {
			curatorFramework.delete().deletingChildrenIfNeeded().inBackground().forPath(node);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ZKError(e);
		}
	}
	
	/**
	 * 判断节点是否存在
	 * @param curatorFramework
	 * @param node
	 * @return
	 */
	public boolean existsNode(CuratorFramework curatorFramework, String node) {
		MyAssert.notNull(curatorFramework);
		MyAssert.hasText(node);
		try {
			Stat nodeStat = curatorFramework.checkExists().forPath(node);
			if (nodeStat == null) {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ZKError(e);
		}
		return true;
	}
	
	/**
	 * 获取节点值
	 * @param curatorFramework
	 * @param node
	 * @return
	 */
	public String getNodeValue(CuratorFramework curatorFramework, String node) {
		MyAssert.notNull(curatorFramework);
		MyAssert.hasText(node);
		try {
			Stat nodeStat = curatorFramework.checkExists().forPath(node);
			if (nodeStat == null) {
				return null;
			}
			return new String(curatorFramework.getData().forPath(node), Constants.SYS_CHARSET_UTF8);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ZKError(e);
		}
	}
}
