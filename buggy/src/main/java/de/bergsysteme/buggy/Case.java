package de.bergsysteme.buggy;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Case {
	// Case number.
	private int ixBug;			
	// Parent case number.
	private int ixBugParent;	
	// List of available subcases.
	private List<Integer> ixBugChildren;
	// List of tags
	private List<String> tags;
	// Flag if case is open
	private boolean fOpen;
	// Title
	private String sTitle;
	// Original title if case was received by mail
	private String sOrginalTitle;
	// short string with case's latest comment 
	private String sLatestTextSummary;
	// ixBugEvent for latest event with actual text comment 
	private int ixBugEventLatestText;
	// project id
	private int ixProject;
	// project name
	private String sProject;
	// area id
	private int ixArea;
	// area name
	private String sArea;
	// group id
	private int ixGroup;
	// person case is assigned to (id) -> Search in Person 
	private int ixPersonAssignedTo;
	// person case is assigned to (name) -> Search in Person
	private String sPersonAssignedTo;
	// email of person case is assigned to
	private String sEmailAssignedTo;
	// person case was opene by (id) -> Search in Person
	private int ixPersonOpenedBy;
	// person case was resolved by (id) -> Search in Person
	private int ixPersonResolvedBy;
	// person case was closed by (id) -> Search in Person
	private int ixPersonClosedBy;
	// person case was last edited by (id) -> Search in Person
	private int ixPersonLastEditedBy;
	// status (id)
	private int ixStatus;
	// status (name)
	private String sStatus;
	// priority id -> Search in Priority
	private int ixPriority;
	// priority name -> Search in Priority
	private String sPriority;
	// fixfor (id)
	private int ixFixFor;
	// fixfor (name)
	private String sFixFor;
	// date of fixfor
	private Date dtFixFor;
	// version field (custom field #1)
	private String sVersion;
	// computer field (custom field #2) 
	private String sComputer;
	// hours of original estimate (0 if no estimate) 
	private float hrsOrigEst;
	// hours of current estimate
	private float hrsCurrEst;
	// total elapsed hours - includes all time from time intervals PLUS hrsElapsedExtra time
	private float hrsElapsed;
	// number of occurrences (minus 1) of this bug (increased via bugzscout)
	// to display the actual number of occurrences, add 1 to this number
	private int c;
	// if there is a customer contact for this case, this is their email 
	private String sCustomerEmail;
	// if this case came in via dispatcho, the mailbox it came in on -> Search in MailBox
	private int ixMailbox;
	// category (id)
	private int ixCategory;
	// category (name)
	private String sCategory;
	// date case was opened
	private Date dtOpened;
	// date case was resolved
	private Date dtResolved;
	// date case was closed
	private Date dtClosed;
	// latest bugevent
	private int ixBugEventLatest;
	// the date when this case was last updated
	private Date dtLastUpdated;
	// has this case been replied to?
	private boolean fReplied;
	// has this case been forwarded?
	private boolean fForwarded;
	// id for customer to view bug (bug number + 8 letters e.g. 4003_XFLFFFCS)  
	private String sTicket;
	// id of discussion topic if case is related -> Search for Discussion
	private int ixDiscussTopic;
	// date this case is due (empty if no due date) 
	private Date dtDue; 
	// release notes
	private String sReleaseNotes;
	// the ixBugEventLatest when you last viewed this case 
	private int ixBugEventLastView;
	// the date when you last viewed this case 
	private Date dtLastView;
	// comma separated list of other related case numbers 
	private List<Integer> ixRelatedBugs;
	// if this case is a Scout case, this ID is the unique identifier 
	private String sScoutDescription;
	// this is the message displayed to users when they submit a case that matches this sScoutDescription 
	private String sScoutMessage;
	// whether we are still recording occurrences of this crash or not 
	private boolean fScoutStopReporting;
	// true if you are subscribed to this case, otherwise false
	private boolean fSubscribed;
	// user defined fields
	private Map<String, String> mUserdefinedField;
	
	public Case() {
		mUserdefinedField = new LinkedHashMap<String, String>();
	}

	public int getIxBug() {
		return ixBug;
	}

	public void setIxBug(int ixBug) {
		this.ixBug = ixBug;
	}
	
	public void setIxBug(String ixBug) {
		this.ixBug = DataType.toInteger(ixBug);
	}

	public int getIxBugParent() {
		return ixBugParent;
	}

	public void setIxBugParent(int ixBugParent) {
		this.ixBugParent = ixBugParent;
	}
	
	public void setIxBugParent(String ixBugParent) {
		this.ixBugParent = DataType.toInteger(ixBugParent);
	}

	public List<Integer> getIxBugChildren() {
		return ixBugChildren;
	}

	public void setIxBugChildren(List<Integer> ixBugChildren) {
		this.ixBugChildren = ixBugChildren;
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public boolean isfOpen() {
		return fOpen;
	}

	public void setfOpen(boolean fOpen) {
		this.fOpen = fOpen;
	}
	
	public void setfOpen(String fOpen) {
		this.fOpen = DataType.toBoolean(fOpen);
	}

	public String getsTitle() {
		return sTitle;
	}

	public void setsTitle(String sTitle) {
		this.sTitle = sTitle;
	}

	public String getsOrginalTitle() {
		return sOrginalTitle;
	}

	public void setsOrginalTitle(String sOrginalTitle) {
		this.sOrginalTitle = sOrginalTitle;
	}

	public String getsLatestTextSummary() {
		return sLatestTextSummary;
	}

	public void setsLatestTextSummary(String sLatestTextSummary) {
		this.sLatestTextSummary = sLatestTextSummary;
	}

	public int getIxBugEventLatestText() {
		return ixBugEventLatestText;
	}

	public void setIxBugEventLatestText(int ixBugEventLatestText) {
		this.ixBugEventLatestText = ixBugEventLatestText;
	}
	
	public void setIxBugEventLatestText(String ixBugEventLatestText) {
		this.ixBugEventLatestText = DataType.toInteger(ixBugEventLatestText);
	}

	public int getIxProject() {
		return ixProject;
	}

	public void setIxProject(int ixProject) {
		this.ixProject = ixProject;
	}
	
	public void setIxProject(String ixProject) {
		this.ixProject = DataType.toInteger(ixProject);
	}

	public String getsProject() {
		return sProject;
	}

	public void setsProject(String sProject) {
		this.sProject = sProject;
	}

	public int getIxArea() {
		return ixArea;
	}

	public void setIxArea(int ixArea) {
		this.ixArea = ixArea;
	}
	
	public void setIxArea(String ixArea) {
		this.ixArea = DataType.toInteger(ixArea);
	}

	public String getsArea() {
		return sArea;
	}

	public void setsArea(String sArea) {
		this.sArea = sArea;
	}

	public int getIxGroup() {
		return ixGroup;
	}

	public void setIxGroup(int ixGroup) {
		this.ixGroup = ixGroup;
	}
	
	public void setIxGroup(String ixGroup) {
		this.ixGroup = DataType.toInteger(ixGroup);
	}

	public int getIxPersonAssignedTo() {
		return ixPersonAssignedTo;
	}

	public void setIxPersonAssignedTo(int ixPersonAssignedTo) {
		this.ixPersonAssignedTo = ixPersonAssignedTo;
	}
	
	public void setIxPersonAssignedTo(String ixPersonAssignedTo) {
		this.ixPersonAssignedTo = DataType.toInteger(ixPersonAssignedTo);
	}

	public String getsPersonAssignedTo() {
		return sPersonAssignedTo;
	}

	public void setsPersonAssignedTo(String sPersonAssignedTo) {
		this.sPersonAssignedTo = sPersonAssignedTo;
	}

	public String getsEmailAssignedTo() {
		return sEmailAssignedTo;
	}

	public void setsEmailAssignedTo(String sEmailAssignedTo) {
		this.sEmailAssignedTo = sEmailAssignedTo;
	}

	public int getIxPersonOpenedBy() {
		return ixPersonOpenedBy;
	}

	public void setIxPersonOpenedBy(int ixPersonOpenedBy) {
		this.ixPersonOpenedBy = ixPersonOpenedBy;
	}
	
	public void setIxPersonOpenedBy(String ixPersonOpenedBy) {
		this.ixPersonOpenedBy = DataType.toInteger(ixPersonOpenedBy);
	}

	public int getIxPersonResolvedBy() {
		return ixPersonResolvedBy;
	}

	public void setIxPersonResolvedBy(int ixPersonResolvedBy) {
		this.ixPersonResolvedBy = ixPersonResolvedBy;
	}
	
	public void setIxPersonResolvedBy(String ixPersonResolvedBy) {
		this.ixPersonResolvedBy = DataType.toInteger(ixPersonResolvedBy);
	}

	public int getIxPersonClosedBy() {
		return ixPersonClosedBy;
	}

	public void setIxPersonClosedBy(int ixPersonClosedBy) {
		this.ixPersonClosedBy = ixPersonClosedBy;
	}
	
	public void setIxPersonClosedBy(String ixPersonClosedBy) {
		this.ixPersonClosedBy = DataType.toInteger(ixPersonClosedBy);
	}

	public int getIxPersonLastEditedBy() {
		return ixPersonLastEditedBy;
	}

	public void setIxPersonLastEditedBy(int ixPersonLastEditedBy) {
		this.ixPersonLastEditedBy = ixPersonLastEditedBy;
	}
	
	public void setIxPersonLastEditedBy(String ixPersonLastEditedBy) {
		this.ixPersonLastEditedBy = DataType.toInteger(ixPersonLastEditedBy);
	}

	public int getIxStatus() {
		return ixStatus;
	}

	public void setIxStatus(int ixStatus) {
		this.ixStatus = ixStatus;
	}
	
	public void setIxStatus(String ixStatus) {
		this.ixStatus = DataType.toInteger(ixStatus);
	}

	public String getsStatus() {
		return sStatus;
	}

	public void setsStatus(String sStatus) {
		this.sStatus = sStatus;
	}

	public int getIxPriority() {
		return ixPriority;
	}

	public void setIxPriority(int ixPriority) {
		this.ixPriority = ixPriority;
	}
	
	public void setIxPriority(String ixPriority) {
		this.ixPriority = DataType.toInteger(ixPriority);
	}

	public String getsPriority() {
		return sPriority;
	}

	public void setsPriority(String sPriority) {
		this.sPriority = sPriority;
	}

	public int getIxFixFor() {
		return ixFixFor;
	}

	public void setIxFixFor(int ixFixFor) {
		this.ixFixFor = ixFixFor;
	}
	
	public void setIxFixFor(String ixFixFor) {
		this.ixFixFor = DataType.toInteger(ixFixFor);
	}

	public String getsFixFor() {
		return sFixFor;
	}

	public void setsFixFor(String sFixFor) {
		this.sFixFor = sFixFor;
	}

	public Date getDtFixFor() {
		return dtFixFor;
	}

	public void setDtFixFor(Date dtFixFor) {
		this.dtFixFor = dtFixFor;
	}
	
	public void setDtFixFor(String dtFixFor) {
		this.dtFixFor = DataType.toDate(dtFixFor);
	}

	public String getsVersion() {
		return sVersion;
	}

	public void setsVersion(String sVersion) {
		this.sVersion = sVersion;
	}

	public String getsComputer() {
		return sComputer;
	}

	public void setsComputer(String sComputer) {
		this.sComputer = sComputer;
	}

	public float getHrsOrigEst() {
		return hrsOrigEst;
	}

	public void setHrsOrigEst(float hrsOrigEst) {
		this.hrsOrigEst = hrsOrigEst;
	}
	
	public void setHrsOrigEst(String hrsOrigEst) {
		this.hrsOrigEst = DataType.toFloat(hrsOrigEst);
	}

	public float getHrsCurrEst() {
		return hrsCurrEst;
	}

	public void setHrsCurrEst(float hrsCurrEst) {
		this.hrsCurrEst = hrsCurrEst;
	}
	
	public void setHrsCurrEst(String hrsCurrEst) {
		this.hrsCurrEst = DataType.toFloat(hrsCurrEst);
	}

	public float getHrsElapsed() {
		return hrsElapsed;
	}

	public void setHrsElapsed(float hrsElapsed) {
		this.hrsElapsed = hrsElapsed;
	}
	
	public void setHrsElapsed(String hrsElapsed) {
		this.hrsElapsed = DataType.toFloat(hrsElapsed);
	}

	public int getC() {
		return c;
	}

	public void setC(int c) {
		this.c = c;
	}
	
	public void setC(String c) {
		this.c = DataType.toInteger(c);
	}

	public String getsCustomerEmail() {
		return sCustomerEmail;
	}

	public void setsCustomerEmail(String sCustomerEmail) {
		this.sCustomerEmail = sCustomerEmail;
	}

	public int getIxMailbox() {
		return ixMailbox;
	}

	public void setIxMailbox(int ixMailbox) {
		this.ixMailbox = ixMailbox;
	}
	
	public void setIxMailbox(String ixMailbox) {
		this.ixMailbox = DataType.toInteger(ixMailbox);
	}

	public int getIxCategory() {
		return ixCategory;
	}

	public void setIxCategory(int ixCategory) {
		this.ixCategory = ixCategory;
	}
	
	public void setIxCategory(String ixCategory) {
		this.ixCategory = DataType.toInteger(ixCategory);
	}

	public String getsCategory() {
		return sCategory;
	}

	public void setsCategory(String sCategory) {
		this.sCategory = sCategory;
	}

	public Date getDtOpened() {
		return dtOpened;
	}

	public void setDtOpened(Date dtOpened) {
		this.dtOpened = dtOpened;
	}
	
	public void setDtOpened(String dtOpened) {
		this.dtOpened = DataType.toDate(dtOpened);
	}

	public Date getDtResolved() {
		return dtResolved;
	}

	public void setDtResolved(Date dtResolved) {
		this.dtResolved = dtResolved;
	}
	
	public void setDtResolved(String dtResolved) {
		this.dtResolved = DataType.toDate(dtResolved);
	}

	public Date getDtClosed() {
		return dtClosed;
	}

	public void setDtClosed(Date dtClosed) {
		this.dtClosed = dtClosed;
	}
	
	public void setDtClosed(String dtClosed) {
		this.dtClosed = DataType.toDate(dtClosed);
	}

	public int getIxBugEventLatest() {
		return ixBugEventLatest;
	}

	public void setIxBugEventLatest(int ixBugEventLatest) {
		this.ixBugEventLatest = ixBugEventLatest;
	}
	
	public void setIxBugEventLatest(String ixBugEventLatest) {
		this.ixBugEventLatest = DataType.toInteger(ixBugEventLatest);
	}

	public Date getDtLastUpdated() {
		return dtLastUpdated;
	}

	public void setDtLastUpdated(Date dtLastUpdated) {
		this.dtLastUpdated = dtLastUpdated;
	}
	
	public void setDtLastUpdated(String dtLastUpdated) {
		this.dtLastUpdated = DataType.toDate(dtLastUpdated);
	}

	public boolean isfReplied() {
		return fReplied;
	}

	public void setfReplied(boolean fReplied) {
		this.fReplied = fReplied;
	}
	
	public void setfReplied(String fReplied) {
		this.fReplied = DataType.toBoolean(fReplied);
	}

	public boolean isfForwarded() {
		return fForwarded;
	}

	public void setfForwarded(boolean fForwarded) {
		this.fForwarded = fForwarded;
	}
	
	public void setfForwarded(String fForwarded) {
		this.fForwarded = DataType.toBoolean(fForwarded);
	}

	public String getsTicket() {
		return sTicket;
	}

	public void setsTicket(String sTicket) {
		this.sTicket = sTicket;
	}

	public int getIxDiscussTopic() {
		return ixDiscussTopic;
	}

	public void setIxDiscussTopic(int ixDiscussTopic) {
		this.ixDiscussTopic = ixDiscussTopic;
	}
	
	public void setIxDiscussTopic(String ixDiscussTopic) {
		this.ixDiscussTopic = DataType.toInteger(ixDiscussTopic);
	}

	public Date getDtDue() {
		return dtDue;
	}

	public void setDtDue(Date dtDue) {
		this.dtDue = dtDue;
	}
	
	public void setDtDue(String dtDue) {
		this.dtDue = DataType.toDate(dtDue);
	}

	public String getsReleaseNotes() {
		return sReleaseNotes;
	}

	public void setsReleaseNotes(String sReleaseNotes) {
		this.sReleaseNotes = sReleaseNotes;
	}

	public int getIxBugEventLastView() {
		return ixBugEventLastView;
	}

	public void setIxBugEventLastView(int ixBugEventLastView) {
		this.ixBugEventLastView = ixBugEventLastView;
	}
	
	public void setIxBugEventLastView(String ixBugEventLastView) {
		this.ixBugEventLastView = DataType.toInteger(ixBugEventLastView);
	}

	public Date getDtLastView() {
		return dtLastView;
	}

	public void setDtLastView(Date dtLastView) {
		this.dtLastView = dtLastView;
	}
	
	public void setDtLastView(String dtLastView) {
		this.dtLastView = DataType.toDate(dtLastView);
	}

	public List<Integer> getIxRelatedBugs() {
		return ixRelatedBugs;
	}

	public void setIxRelatedBugs(List<Integer> ixRelatedBugs) {
		this.ixRelatedBugs = ixRelatedBugs;
	}

	public String getsScoutDescription() {
		return sScoutDescription;
	}

	public void setsScoutDescription(String sScoutDescription) {
		this.sScoutDescription = sScoutDescription;
	}

	public String getsScoutMessage() {
		return sScoutMessage;
	}

	public void setsScoutMessage(String sScoutMessage) {
		this.sScoutMessage = sScoutMessage;
	}

	public boolean isfScoutStopReporting() {
		return fScoutStopReporting;
	}

	public void setfScoutStopReporting(boolean fScoutStopReporting) {
		this.fScoutStopReporting = fScoutStopReporting;
	}
	
	public void setfScoutStopReporting(String fScoutStopReporting) {
		this.fScoutStopReporting = DataType.toBoolean(fScoutStopReporting);
	}

	public boolean isfSubscribed() {
		return fSubscribed;
	}

	public void setfSubscribed(boolean fSubscribed) {
		this.fSubscribed = fSubscribed;
	}
	
	public void setfSubscribed(String fSubscribed) {
		this.fSubscribed = DataType.toBoolean(fSubscribed);
	}
	
	public void setUserdefinedField(String fieldname, String value) {
		fieldname = fieldname.toLowerCase();
		this.mUserdefinedField.put(fieldname, value);
	}
	
	public String getUserdefinedField(String fieldname) {
		String value = null;
		fieldname = fieldname.toLowerCase();
		if (hasUserdefinedField()) { value = this.mUserdefinedField.get(fieldname); }
		return value;
	}
	
	public boolean hasUserdefinedField() {
		return (this.mUserdefinedField.size() > 0) ? true : false;
	}
	
	public String[] listUserdefinedFields() {
		String[] list = new String[0];
		if (hasUserdefinedField()) {
			list = this.mUserdefinedField.keySet().toArray(new String[0]);
		}
		return list;
	}
}
