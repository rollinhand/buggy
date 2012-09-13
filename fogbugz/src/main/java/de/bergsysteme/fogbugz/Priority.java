package de.bergsysteme.fogbugz;

/***
 * Contains id and name for the priority of a case.
 * 
 * @author Bjoern Berg, bjoern.berg@gmx.de
 * @version 1.0
 * @since 2012-06-27
 *
 */
public class Priority {
	// priority id
	private int ixPriority;
	// priority name
	private String sPriority;
	
	public Priority() {
		this.setIxPriority(0);
		this.setsPriority(null);
	}

	/***
	 * Returns the priority id.
	 * @return priority id.
	 */
	public int getIxPriority() {
		return ixPriority;
	}

	/***
	 * Sets the priorities id.
	 * @param ixPriority priority id.
	 */
	public void setIxPriority(int ixPriority) {
		this.ixPriority = ixPriority;
	}
	
	/***
	 * Sets the priorities id from String.
	 * @param ixPriority priority id.
	 */
	public void setIxPriority(String ixPriority) {
		this.ixPriority = Integer.parseInt(ixPriority);
	}

	/***
	 * Returns the name for priority.
	 * @return name of priority.
	 */
	public String getsPriority() {
		return sPriority;
	}

	/***
	 * Sets the name for priority.
	 * @param sPriority name of priority.
	 */
	public void setsPriority(String sPriority) {
		this.sPriority = sPriority;
	}
}
