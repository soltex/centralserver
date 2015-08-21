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
public class PassiveLocationEvent extends AbstractPassiveEvent{
	
	private double latitude;
	private double longitude;
	private double precision;
	
	public PassiveLocationEvent() {
		super(CorpWeixinEvent.LOCATION);
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getPrecision() {
		return precision;
	}

	public void setPrecision(double precision) {
		this.precision = precision;
	}

	@Override
	protected void initial_internal(Element rootElement) {
		Element LatitudeElement = rootElement.element("Latitude");
		if (LatitudeElement != null) {
			this.latitude = LatitudeElement.getTextTrim() != null && !LatitudeElement.getTextTrim().equals("") ? Double.parseDouble(LatitudeElement.getTextTrim()) : 0;
		}
		Element LongitudeElement = rootElement.element("Longitude");
		if (LongitudeElement != null) {
			this.longitude = LongitudeElement.getTextTrim() != null && !LongitudeElement.getTextTrim().equals("") ? Double.parseDouble(LongitudeElement.getTextTrim()) : 0;
		}
		Element PrecisionElement = rootElement.element("Precision");
		if (PrecisionElement != null) {
			this.precision = PrecisionElement.getTextTrim() != null && !PrecisionElement.getTextTrim().equals("") ? Double.parseDouble(PrecisionElement.getTextTrim()) : 0;
		}
	}
	
}
