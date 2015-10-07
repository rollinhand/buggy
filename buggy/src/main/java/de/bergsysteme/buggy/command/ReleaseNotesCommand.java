package de.bergsysteme.buggy.command;

public class ReleaseNotesCommand extends DefaultCommand {
	private static final String PREDEFINED_CMD = "search&q=";
	
	private String fixFor;
	private String project;
	private String area;

	public String getFixFor() {
		return fixFor;
	}

	public void setFixFor(String fixFor) {
		this.fixFor = fixFor;
	}

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}
	
	private String getCols() {
		return "ixBug,sCategory,sTitle,sReleaseNotes";
	}
	
	private String buildCommand() {
		StringBuffer cmd = new StringBuffer();
		cmd.append(PREDEFINED_CMD);
		if (getFixFor() != null) {
			cmd.append(String.format("fixfor:%s", getFixFor()));
		}
		if (getProject() != null) {
			cmd.append(String.format(" AND project:%s", getProject()));
		}
		if (getArea() != null) {
			cmd.append(String.format(" AND area:%s", getArea()));
		}
		if (getCols() != null) {
			cmd.append(String.format("&cols=%s", getCols()));
		}
		return cmd.toString();
	}

	@Override
	public String getCommand() {
		return buildCommand();
	}

	@Override
	public String toString() {
		return getCommand(); 
	}
}
