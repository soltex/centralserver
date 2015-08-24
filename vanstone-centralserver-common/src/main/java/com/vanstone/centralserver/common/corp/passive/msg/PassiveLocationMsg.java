/**
 * 
 */
package com.vanstone.centralserver.common.corp.passive.msg;

import org.dom4j.Element;

import com.vanstone.centralserver.common.corp.passive.AbstractPassiveMsg;
import com.vanstone.centralserver.common.corp.passive.PassiveCorpMsgType;

/**
 * @author shipeng
 *
 */
public class PassiveLocationMsg extends AbstractPassiveMsg {

	private double locationX;
	private double locationY;
	private double scale;
	private String label;

	public PassiveLocationMsg() {
		super(PassiveCorpMsgType.LOCATION);
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

	@Override
	protected void initial_internal(Element rootElement) {
		Element Location_XElement = rootElement.element("Location_X");
		if (Location_XElement != null) {
			this.locationX = Location_XElement.getTextTrim() != null && !Location_XElement.getTextTrim().equals("") ? Double.parseDouble(Location_XElement.getTextTrim()) : 0;
		}
		Element Location_YElement = rootElement.element("Location_Y");
		if (Location_YElement != null) {
			this.locationY = Location_YElement.getTextTrim() != null && !Location_YElement.getTextTrim().equals("") ? Double.parseDouble(Location_YElement.getTextTrim()) : 0;
		}
		Element ScaleElement = rootElement.element("Scale");
		if (ScaleElement != null) {
			this.scale = ScaleElement.getTextTrim() != null && !ScaleElement.getTextTrim().equals("") ? Double.parseDouble(ScaleElement.getTextTrim()) : 0;
		}
		Element LabelElement = rootElement.element("Label");
		if (LabelElement != null) {
			this.label = LabelElement.getTextTrim();
		}
	}
	
}
