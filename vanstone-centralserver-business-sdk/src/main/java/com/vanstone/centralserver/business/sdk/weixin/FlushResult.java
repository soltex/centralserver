/**
 * 
 */
package com.vanstone.centralserver.business.sdk.weixin;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import com.vanstone.centralserver.common.MyAssert;

/**
 * 刷新结果
 * @author shipengpipi@126.com
 */
public class FlushResult {
	
	private Collection<IWeixinInfo> failWeixinInfos = Collections.synchronizedCollection(new ArrayList<IWeixinInfo>());

	/**
	 * 是否全部成功
	 * @return
	 */
	public boolean isSuccess() {
		return this.failWeixinInfos.size() == 0;
	}
	
	/**
	 * 获取失败数量
	 * @return
	 */
	public int getFailSize() {
		return failWeixinInfos.size();
	}
	
	/**
	 * 添加失败Id
	 * @param failId
	 */
	public void addFailWeixinInfo(IWeixinInfo weixinInfo) {
		MyAssert.notNull(weixinInfo);
		this.failWeixinInfos.add(weixinInfo);
	}
	
	public String toResultString() {
		StringBuffer sb = new StringBuffer();
		if (this.isSuccess()) {
			sb.append("success");
		}else{
			sb.append("fail.");
			for (IWeixinInfo weixinInfo : failWeixinInfos) {
				sb.append("\n");
				sb.append("weixin : ").append(weixinInfo.getAppid());
			}
		}
		return sb.toString();
	}
}
