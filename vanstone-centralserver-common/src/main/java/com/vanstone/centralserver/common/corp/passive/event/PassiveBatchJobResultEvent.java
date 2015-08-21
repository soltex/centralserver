/**
 * 
 */
package com.vanstone.centralserver.common.corp.passive.event;

import org.dom4j.Element;

import com.vanstone.centralserver.common.corp.passive.CorpWeixinEvent;

/**
 * @author shipeng
 *
 */
public class PassiveBatchJobResultEvent extends AbstractPassiveEvent {

	private String jobID;
	private JobType jobType;
	private String errCode;
	private String errMsg;

	public PassiveBatchJobResultEvent() {
		super(CorpWeixinEvent.BATCH_JOB_RESULT);
	}

	public String getJobID() {
		return jobID;
	}

	public void setJobID(String jobID) {
		this.jobID = jobID;
	}

	public JobType getJobType() {
		return jobType;
	}
	
	public void setJobType(JobType jobType) {
		this.jobType = jobType;
	}

	public String getErrCode() {
		return errCode;
	}

	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	@Override
	protected void initial_internal(Element rootElement) {
		Element BatchJobElement = rootElement.element("BatchJob");
		if (BatchJobElement == null) {
			return;
		}
		Element JobIdElement = BatchJobElement.element("JobId");
		if(JobIdElement != null) {
			this.jobID = JobIdElement.getTextTrim();
		}
		Element JobTypeElement = BatchJobElement.element("JobType");
		if (JobTypeElement != null) {
			this.jobType = JobType.parseIgnoreCase(JobIdElement.getTextTrim());
		}
		Element ErrCodeElement = BatchJobElement.element("ErrCode");
		if (ErrCodeElement != null) {
			this.errCode = ErrCodeElement.getTextTrim();
		}
		Element ErrMsgElement = BatchJobElement.element("ErrMsg");
		if (ErrMsgElement != null) {
			this.errMsg = ErrMsgElement.getTextTrim();
		}
	}
}
