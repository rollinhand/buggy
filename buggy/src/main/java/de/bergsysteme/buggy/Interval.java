package de.bergsysteme.buggy;

import java.util.Date;

public class Interval {
	private int ixBug; 			// case id for this interval
	private int ixInterval;		// interval (id) 
	private Date dtStart;		// start time of interval
	private Date dtEnd;			// end time of interval
	private String sTitle;		// title of case
	private int ixPerson;		// person this interval applies to
	private boolean fdeleted;	// deleted entry
	
	public int getIxBug() {
		return ixBug;
	}
	public void setIxBug(int ixBug) {
		this.ixBug = ixBug;
	}
	public void setIxBug(String ixBug) {
		this.ixBug = DataType.toInteger(ixBug);
	}
	public int getIxInterval() {
		return ixInterval;
	}
	public void setIxInterval(int ixInterval) {
		this.ixInterval = ixInterval;
	}
	public void setIxInterval(String ixInterval) {
		this.ixInterval = DataType.toInteger(ixInterval);
	}
	public Date getDtStart() {
		return dtStart;
	}
	public void setDtStart(Date dtStart) {
		this.dtStart = dtStart;
	}
	public void setDtStart(String dtStart) {
		this.dtStart = DataType.toDate(dtStart);
	}
	public Date getDtEnd() {
		return dtEnd;
	}
	public void setDtEnd(Date dtEnd) {
		this.dtEnd = dtEnd;
	}
	public void setDtEnd(String dtEnd) {
		this.dtEnd = DataType.toDate(dtEnd);
	}
	public String getsTitle() {
		return sTitle;
	}
	public void setsTitle(String sTitle) {
		this.sTitle = sTitle;
	}
	public int getIxPerson() {
		return ixPerson;
	}
	public void setIxPerson(int ixPerson) {
		this.ixPerson = ixPerson;
	}
	public void setIxPerson(String ixPerson) {
		this.ixPerson = DataType.toInteger(ixPerson);
	}
	/*public float getDuration() {
		final int hours_millis = 1000 * 60 * 60;
		return (getDtEnd().getTime()/hours_millis) - (getDtStart().getTime()/hours_millis);
	}*/
	public boolean isFdeleted() {
		return fdeleted;
	}
	public void setFdeleted(boolean fdeleted) {
		this.fdeleted = fdeleted;
	}
	public void setFdeleted(String fdeleted) {
		this.fdeleted = DataType.toBoolean(fdeleted);
	}
}
