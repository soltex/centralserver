/**
 * 
 */
package com.vanstone.centralserver.common.corp.passive.event;

/**
 * @author shipeng
 *
 */
public enum JobType {

	SYNC_USER("增量更新成员", "sync_user"), 
	
	REPLACE_USER("全量覆盖成员", "replace_user"), 
	
	INVITE_USER("邀请成员关注","invite_user"), 
	
	REPLACE_PARTY("全量覆盖部门", "replace_party");
	
	private JobType(String desc, String code) {
		this.desc = desc;
		this.code = code;
	}

	private String desc;
	private String code;

	public String getDesc() {
		return desc;
	}

	public String getCode() {
		return code;
	}
	
	public static JobType parseIgnoreCase(String jobstring) {
		if (jobstring == null || jobstring.equals("")) {
			return null;
		}
		JobType[] jobTypes = JobType.values();
		for (JobType item : jobTypes) {
			if (item.getCode().equalsIgnoreCase(jobstring)) {
				return item;
			}
		}
		return null;
	}
}
