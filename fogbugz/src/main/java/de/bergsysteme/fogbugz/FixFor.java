package de.bergsysteme.fogbugz;

import java.util.Date;

public class FixFor {
	// fixfor id
	private int ixFixFor;
	// fixfor String
	private String sFixFor;
	// is fixfor deleted
	private boolean fDeleted;
	// fixfor date
	private Date dt;
	// earliest starting date
	private Date dtStart;
	// not concerning starting date
	private String sStartNote;
	// project id
	private int ixProject;
	// project name
	private String sProject;
	
	public FixFor() {
		this.setIxFixFor(0);
		this.setsFixFor(null);
		this.setfDeleted(false);
		//this.setDt(null);
		this.setDtStart(null);
		this.setsStartNote(null);
		this.setIxProject(0);
		this.setsProject(null);
	}
	
	public int getIxFixFor() {
		return ixFixFor;
	}

	public void setIxFixFor(int ixFixFor) {
		this.ixFixFor = ixFixFor;
	}
	
	public void setIxFixFor(String ixFixFor) {
		this.ixFixFor = Integer.parseInt(ixFixFor);
	}

	public String getsFixFor() {
		return sFixFor;
	}

	public void setsFixFor(String sFixFor) {
		this.sFixFor = sFixFor;
	}

	public boolean isfDeleted() {
		return fDeleted;
	}

	public void setfDeleted(boolean fDeleted) {
		this.fDeleted = fDeleted;
	}
	
	public void setfDeleted(String fDeleted) {
		this.fDeleted = Boolean.parseBoolean(fDeleted);
	}

	public Date getDt() {
		return dt;
	}

	public void setDt(Date dt) {
		this.dt = dt;
	}
	
	// FIXME We need an algorithm
	public void setDt(String dt) {
		//this.dt = Date.parse(dt);
	}

	public Date getDtStart() {
		return dtStart;
	}

	public void setDtStart(Date dtStart) {
		this.dtStart = dtStart;
	}

	public String getsStartNote() {
		return sStartNote;
	}

	public void setsStartNote(String sStartNote) {
		this.sStartNote = sStartNote;
	}

	public int getIxProject() {
		return ixProject;
	}

	public void setIxProject(int ixProject) {
		this.ixProject = ixProject;
	}
	
	public void setIxProject(String ixProject) {
		this.ixProject = Integer.parseInt(ixProject);
	}

	public String getsProject() {
		return sProject;
	}

	public void setsProject(String sProject) {
		this.sProject = sProject;
	}
}
