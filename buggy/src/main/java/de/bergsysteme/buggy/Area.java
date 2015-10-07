package de.bergsysteme.buggy;

public class Area {
	// area id
	private int ixArea;
	// area name
	private String sArea;
	// project (id) this area belongs to
	private int ixProject;
	// project (name) this area belongs to
	private String sProject;
	// default owner of this area. if empty then use project owner
	private int ixPersonOwner;
	// name of owner of this area. if empty then use project owner
	private String sPersonOwner;
	// type of area: 0 = normal, 1 = Not Spam, 2 = Undecided, 3 = Spam (Inbox areas only)
	private int nType;
	// number of documents trained into area (autosorted areas only)
	private int cDoc;

	public Area() {
		this.setIxArea(0);
		this.setsArea(null);
		this.setIxProject(0);
		this.setsProject(null);
		this.setIxPersonOwner(0);
		this.setsPersonOwner(null);
		this.setnType(0);
		this.setcDoc(0);
	}

	public int getIxArea() {
		return ixArea;
	}

	public void setIxArea(int ixArea) {
		this.ixArea = ixArea;
	}

	public String getsArea() {
		return sArea;
	}

	public void setsArea(String sArea) {
		this.sArea = sArea;
	}

	public int getIxProject() {
		return ixProject;
	}

	public void setIxProject(int ixProject) {
		this.ixProject = ixProject;
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

	public String getsPersonOwner() {
		return sPersonOwner;
	}

	public void setsPersonOwner(String sPersonOwner) {
		this.sPersonOwner = sPersonOwner;
	}

	public int getnType() {
		return nType;
	}

	public void setnType(int nType) {
		this.nType = nType;
	}

	public int getcDoc() {
		return cDoc;
	}

	public void setcDoc(int cDoc) {
		this.cDoc = cDoc;
	}
}
