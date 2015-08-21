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
public class PassiveLocationSelectEvent extends AbstractPassiveEvent {

	private String eventKey;
	private double locationX;
	private double locationY;
	private double scale;
	private String label;
	private String poiname;

	public PassiveLocationSelectEvent() {
		super(CorpWeixinEvent.LOCATION_SELECT);
	}

	public String getEventKey() {
		return eventKey;
	}

	public void setEventKey(String eventKey) {
		this.eventKey = eventKey;
	}

	public double getLocationX() {
		return locationX;
	}

	public void setLocationX(double locationX) {
		this.locationX = locationX;
	}

	public double getLocationY() {
		return locationY;
	}

	public void setLocationY(double locationY) {
		this.locationY = locationY;
	}

	public double getScale() {
		return scale;
	}

	public void setScale(double scale) {
		this.scale = scale;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getPoiname() {
		return poiname;
	}

	public void setPoiname(String poiname) {
		this.poiname = poiname;
	}

	@Override
	protected void initial_internal(Element rootElement) {
		Element EventKeyElement = rootElement.element("EventKey");
		if (EventKeyElement != null) {
			this.eventKey = EventKeyElement.getTextTrim();
		}
		Element SendLocationInfoElement = rootElement.element("SendLocationInfo");
		if (SendLocationInfoElement == null) {
			return;
		}
		Element Location_XElement = SendLocationInfoElement.element("Location_X");
		if (Location_XElement != null) {
			this.locationX = Location_XElement.getTextTrim() != null && !Location_XElement.getTextTrim().equals("") ? Double.parseDouble(Location_XElement.getTextTrim()) : 0;
		}
		Element Location_YElement = SendLocationInfoElement.element("Location_Y");
		if (Location_YElement != null) {
			this.locationY = Location_YElement.getTextTrim() != null && !Location_YElement.getTextTrim().equals("") ? Double.parseDouble(Location_YElement.getTextTrim()) : 0;
		}
		Element ScaleElement = SendLocationInfoElement.element("Scale");
		if (ScaleElement != null) {
			this.scale = ScaleElement.getTextTrim() != null && !ScaleElement.getTextTrim().equals("") ? Double.parseDouble(ScaleElement.getTextTrim()) : 0;
		}
		Element LabelElement = SendLocationInfoElement.element("Label");
		if (LabelElement != null) {
			this.label = LabelElement.getTextTrim();
		}
		Element PoinameElement = SendLocationInfoElement.element("Poiname");
		if (PoinameElement != null) {
			this.poiname = PoinameElement.getTextTrim();
		}
	}
}
