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
public class PassiveScancodeWaitmsgEvent extends AbstractPassiveEvent {
	
	private String eventKey;
	private String scanResult;
	private String scanType;
	
	public PassiveScancodeWaitmsgEvent() {
		super(CorpWeixinEvent.SCANCODE_WAITMSG);
	}

	public String getEventKey() {
		return eventKey;
	}

	public void setEventKey(String eventKey) {
		this.eventKey = eventKey;
	}
	
	public String getScanResult() {
		return scanResult;
	}
	
	public void setScanResult(String scanResult) {
		this.scanResult = scanResult;
	}
	
	public String getScanType() {
		return scanType;
	}

	public void setScanType(String scanType) {
		this.scanType = scanType;
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
