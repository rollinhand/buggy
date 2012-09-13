package de.bergsysteme.buggy;

public class Project {
	// project id
	private int ixProject;
	// project name
	private String sProject;
	// person who is the default owner for this project (id) --> Search in Person
	private int ixPersonOwner;
	// name of owner
	private String sPersonOwner;
	// email of owner
	private String sEmail;
	// phone of owner
	private String sPhone;
	// true if this is the Inbox project
	private boolean fInbox;
	// type of group this project is part of (1 = client /2 = dept)
	private int iType;
	// group (id) this project is part of
	private int ixGroup;
	// name of group this project is part of
	private String sGroup;
	
	public Project() {
		this.setIxProject(0);
		this.setsProject(null);
		this.setIxPersonOwner(0);
		this.setsPersonOwner(null);
		this.setsEmail(null);
		this.setsPhone(null);
		this.setfInbox(false);
		this.setiType(0);
		this.setIxGroup(0);
		this.setsGroup(null);
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

	public int getIxPersonOwner() {
		return ixPersonOwner;
	}

	public void setIxPersonOwner(int ixPersonOwner) {
		this.ixPersonOwner = ixPersonOwner;
	}
	
	public void setIxPersonOwner(String ixPersonOwner) {
		this.ixPersonOwner = Integer.parseInt(ixPersonOwner);
	}

	public String getsPersonOwner() {
		return sPersonOwner;
	}

	public void setsPersonOwner(String sPersonOwner) {
		this.sPersonOwner = sPersonOwner;
	}

	public String getsEmail() {
		return sEmail;
	}

	public void setsEmail(String sEmail) {
		this.sEmail = sEmail;
	}

	public String getsPhone() {
		return sPhone;
	}

	public void setsPhone(String sPhone) {
		this.sPhone = sPhone;
	}

	public boolean isfInbox() {
		return fInbox;
	}

	public void setfInbox(boolean fInbox) {
		this.fInbox = fInbox;
	}
	
	public void setfInbox(String fInbox) {
		this.fInbox = Boolean.parseBoolean(fInbox);
	}

	public int getiType() {
		return iType;
	}

	public void setiType(int iType) {
		this.iType = iType;
	}
	
	public void setiType(String iType) {
		this.iType = Short.parseShort(iType);
	}

	public int getIxGroup() {
		return ixGroup;
	}

	public void setIxGroup(int ixGroup) {
		this.ixGroup = ixGroup;
	}
	
	public void setIxGroup(String ixGroup) {
		this.ixGroup = Integer.parseInt(ixGroup);
	}

	public String getsGroup() {
		return sGroup;
	}

	public void setsGroup(String sGroup) {
		this.sGroup = sGroup;
	}
}
