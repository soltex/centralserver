/**
 * 
 */
package com.vanstone.centralserver.common.weixin.wrap.msg.mass;

/**
 * @author shipeng
 *
 */
public abstract class AbstractGroupMassMsg extends AbstractMassMsg {

	public AbstractGroupMassMsg(String msgtype) {
		super(msgtype);
	}
	
	/**分组ID号*/
	private String groupId;

	public String getGroupId() {
		return groupId;
	}
	
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	
}
