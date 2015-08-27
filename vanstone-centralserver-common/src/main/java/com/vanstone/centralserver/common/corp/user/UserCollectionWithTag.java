/**
 * 
 */
package com.vanstone.centralserver.common.corp.user;

import java.util.ArrayList;
import java.util.List;

/**
 * @author shipeng
 *
 */
public class UserCollectionWithTag {

	private int tagid;
	private List<CorpUserInfo> corpUserInfos = new ArrayList<CorpUserInfo>();
	private List<Integer> partylist = new ArrayList<Integer>();
	
	public int getTagid() {
		return tagid;
	}

	public void setTagid(int tagid) {
		this.tagid = tagid;
	}
	
	public List<CorpUserInfo> getCorpUserInfos() {
		return corpUserInfos;
	}

	public List<Integer> getPartylist() {
		return partylist;
	}
	
	public void addCorpUserInfo(CorpUserInfo corpUserInfo) {
		this.corpUserInfos.add(corpUserInfo);
	}
	
	public void addCorpUserInfos(List<CorpUserInfo> corpUserInfos) {
		if (corpUserInfos == null || corpUserInfos.size() <=0) {
			return ;
		}
		this.corpUserInfos.addAll(corpUserInfos);
	}
	
	public void addParty(int departmentid) {
		this.partylist.add(departmentid);
	}
	
	public void addPartys(List<Integer> departmentids) {
		this.partylist.addAll(departmentids);
	}
	
}
