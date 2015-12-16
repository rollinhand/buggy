package de.bergsysteme.buggy;

/***
 * Container for FogBugz API information. This is used internally
 * while connecting with the server where FogBugz is hosted.
 * @author Björn Berg, bjoern.berg@gmx.de
 * @since 2012-09-13
 * @version 1.0
 */
public class Api {
	// major version
	private int version;
	// minimum version
	private int minversion;
	// URL to connect to ending on asp or php
	private String url;
	
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	public void setVersion(String version) {
		this.version = DataType.toInteger(version);
	}
	public int getMinversion() {
		return minversion;
	}
	public void setMinversion(int minversion) {
		this.minversion = minversion;
	}
	public void setMinversion(String minversion) {
		this.minversion = DataType.toInteger(minversion);
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
}
