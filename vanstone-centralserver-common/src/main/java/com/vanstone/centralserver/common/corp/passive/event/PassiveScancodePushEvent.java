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
public class PassiveScancodePushEvent extends AbstractPassiveEvent {

	private String eventKey;
	private String scanType;
	private String scanResult;

	public PassiveScancodePushEvent() {
		super(CorpWeixinEvent.SCANCODE_PUSH);
	}

	public String getEventKey() {
		return eventKey;
	}

	public void setEventKey(String eventKey) {
		this.eventKey = eventKey;
	}

	public String getScanType() {
		return scanType;
	}

	public void setScanType(String scanType) {
		this.scanType = scanType;
	}

	public String getScanResult() {
		return scanResult;
	}

	public void setScanResult(String scanResult) {
		this.scanResult = scanResult;
	}

	@Override
	protected void initial_internal(Element rootElement) {
		Element EventKeyElement = rootElement.element("EventKey");
		if (EventKeyElement != null) {
			this.eventKey = EventKeyElement.getTextTrim();
		}

		Element ScanCodeInfoElement = rootElement.element("ScanCodeInfo");
		if (ScanCodeInfoElement == null) {
			return;
		}

		Element ScanTypeElement = ScanCodeInfoElement.element("ScanType");
		if (ScanTypeElement != null) {
			this.scanType = ScanTypeElement.getTextTrim();
		}

		Element ScanResultElement = ScanCodeInfoElement.element("ScanResult");
		if (ScanResultElement != null) {
			this.scanResult = ScanResultElement.getTextTrim();
		}
	}

}
