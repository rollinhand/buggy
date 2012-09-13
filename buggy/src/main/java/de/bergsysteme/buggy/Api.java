package de.bergsysteme.buggy;

public class Api {
	private int version;
	private int minversion;
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
