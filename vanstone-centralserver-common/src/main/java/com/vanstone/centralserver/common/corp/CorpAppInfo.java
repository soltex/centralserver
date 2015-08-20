/**
 * 
 */
package com.vanstone.centralserver.common.corp;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * @author shipeng
 * 
 */
public class CorpAppInfo {
	private int agentID;
	private String name;
	private String squareLogoUrl;
	private String roundLogoUrl;
	private String description;
	private List<Userinfo> allowUserinfos = new ArrayList<CorpAppInfo.Userinfo>();
	private Set<Integer> allowPartys = new LinkedHashSet<Integer>();
	private Set<Integer> allowTags = new LinkedHashSet<Integer>();
	private boolean close = false;
	private String redirectDomain;
	private ReportLocationFlag reportLocationFlag = ReportLocationFlag.Not_Uplaod;
	private boolean reportuser = false;
	private boolean reportenter=false;
	
	
	
	
	
	
	
	
	public boolean isClose() {
		return close;
	}

	public void setClose(boolean close) {
		this.close = close;
	}

	public ReportLocationFlag getReportLocationFlag() {
		return reportLocationFlag;
	}

	public void setReportLocationFlag(ReportLocationFlag reportLocationFlag) {
		this.reportLocationFlag = reportLocationFlag;
	}

	public boolean isReportuser() {
		return reportuser;
	}

	public void setReportuser(boolean reportuser) {
		this.reportuser = reportuser;
	}

	public boolean isReportenter() {
		return reportenter;
	}

	public void setReportenter(boolean reportenter) {
		this.reportenter = reportenter;
	}
	
	public int getAgentID() {
		return agentID;
	}

	public void setAgentID(int agentID) {
		this.agentID = agentID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSquareLogoUrl() {
		return squareLogoUrl;
	}

	public void setSquareLogoUrl(String squareLogoUrl) {
		this.squareLogoUrl = squareLogoUrl;
	}

	public String getRoundLogoUrl() {
		return roundLogoUrl;
	}

	public void setRoundLogoUrl(String roundLogoUrl) {
		this.roundLogoUrl = roundLogoUrl;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getRedirectDomain() {
		return redirectDomain;
	}

	public void setRedirectDomain(String redirectDomain) {
		this.redirectDomain = redirectDomain;
	}

	public void addTag(int tag) {
		this.allowTags.add(tag);
	}
	
	public void clearTags() {
		this.allowTags.clear();
	}
	
	public List<Userinfo> getAllowUserinfos() {
		return allowUserinfos;
	}

	public Set<Integer> getAllowPartys() {
		return allowPartys;
	}

	public Set<Integer> getAllowTags() {
		return allowTags;
	}

	public void addParty(int party) {
		this.allowPartys.add(party);
	}
	
	public void clearPartys() {
		this.allowPartys.clear();
	}
	
	public void addUserinfo(String userID, String status) {
		Userinfo userinfo = new Userinfo();
		userinfo.setUserID(userID);
		userinfo.setStatus(status);
		this.allowUserinfos.add(userinfo);
	}
	
	public void clearUserinfos() {
		this.allowUserinfos.clear();
	}
	
	public static class Userinfo {
		private String userID;
		private String status;
		public String getUserID() {
			return userID;
		}
		public void setUserID(String userID) {
			this.userID = userID;
		}
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
		
	}
	
}
