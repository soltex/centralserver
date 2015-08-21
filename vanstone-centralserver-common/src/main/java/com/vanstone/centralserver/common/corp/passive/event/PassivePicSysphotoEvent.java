/**
 * 
 */
package com.vanstone.centralserver.common.corp.passive.event;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.Element;

import com.vanstone.centralserver.common.corp.passive.CorpWeixinEvent;

/**
 * @author shipeng
 *
 */
public class PassivePicSysphotoEvent extends AbstractPassiveEvent {

	private String eventKey;
	private int count;
	private List<String> picMd5Sums = new ArrayList<String>();

	public PassivePicSysphotoEvent() {
		super(CorpWeixinEvent.PIC_SYSPHOTO);
	}

	public String getEventKey() {
		return eventKey;
	}

	public void setEventKey(String eventKey) {
		this.eventKey = eventKey;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public List<String> getPicMd5Sums() {
		return picMd5Sums;
	}

	public void addPicMd5Sum(String picMd5Sum) {
		if (picMd5Sum != null && !picMd5Sum.equals("")) {
			this.picMd5Sums.add(picMd5Sum);
		}
	}
	
	public void clearPicMd5Sums() {
		this.picMd5Sums.clear();
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void initial_internal(Element rootElement) {
		Element EventKeyElement = rootElement.element("EventKey");
		if (EventKeyElement != null) {
			this.eventKey = EventKeyElement.getTextTrim();
		}
		Element SendPicsInfoElement = rootElement.element("SendPicsInfo");
		if (SendPicsInfoElement == null) {
			return;
		}
		Element CountElement = SendPicsInfoElement.element("Count");
		if (CountElement != null) {
			this.count = (CountElement != null && CountElement.getTextTrim() != null && !CountElement.getTextTrim().equals("")) ? Integer.parseInt(CountElement.getTextTrim()) : 0;
		}
		Element PicListElement = SendPicsInfoElement.element("PicList");
		if (PicListElement == null) {
			return;
		}
		List<Element> itemElements = PicListElement.elements("item");
		if (itemElements != null && itemElements.size() > 0) {
			for (Element itemElement : itemElements) {
				Element PicMd5SumElement = itemElement.element("PicMd5Sum");
				if (PicMd5SumElement != null) {
					this.addPicMd5Sum(PicMd5SumElement.getTextTrim());
				}
			}
		}
	}
}