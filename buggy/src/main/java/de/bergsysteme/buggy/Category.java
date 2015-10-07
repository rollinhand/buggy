package de.bergsysteme.buggy;

/***
 * Category container.
 * 
 * @author Bjoern Berg, bjoern.berg@gmx.de
 * @version 1.0
 * @since 2012-06-27
 *
 */
public class Category {
	// category id
	private int ixCategory;
	// category name
	private String sCategory;
	// category plural name
	private String sPlural;
	// when a case of this category type is resolved, the status id of the default (selected) status
	private int ixStatusDefault;
	//  true if this is a schedule item category 
	private boolean fIsScheduleItem;

	public Category() {
		this.setIxCategory(0);
		this.setsCategory(null);
		this.setsPlural(null);
		this.setIxStatusDefault(0);
		this.setfIsScheduleItem(false);
	}

	/***
	 * Returns id for category.
	 * @return numeric id.
	 */
	public int getIxCategory() {
		return ixCategory;
	}

	/***
	 * Sets id for category.
	 * @param ixCategory numeric identifier.
	 */
	public void setIxCategory(int ixCategory) {
		this.ixCategory = ixCategory;
	}
	
	/***
	 * Sets id for category from String representation.
	 * @param ixCategory numeric identifier as String.
	 */
	public void setIxCategory(String ixCategory) {
		this.ixCategory = DataType.toInteger(ixCategory);
	}

	/***
	 * Returns the name for category.
	 * @return name.
	 */
	public String getsCategory() {
		return sCategory;
	}

	/***
	 * Sets the name for category.
	 * @param sCategory name.
	 */
	public void setsCategory(String sCategory) {
		this.sCategory = sCategory;
	}

	/***
	 * Returns plural name for category.
	 * @return plural name.
	 */
	public String getsPlural() {
		return sPlural;
	}

	/***
	 * Sets the plural name for category.
	 * @param sPlural plural name.
	 */
	public void setsPlural(String sPlural) {
		this.sPlural = sPlural;
	}

	/***
	 * Returns the status id of the default (selected) status, 
	 * when a case of this category type is resolved. 
	 * @return default status.
	 */
	public int getIxStatusDefault() {
		return ixStatusDefault;
	}

	/***
	 * Sets the default status.
	 * @param ixStatusDefault default status.
	 */
	public void setIxStatusDefault(int ixStatusDefault) {
		this.ixStatusDefault = ixStatusDefault;
	}
	
	/***
	 * Sets the default status.
	 * @param ixStatusDefault default status as String.
	 */
	public void setIxStatusDefault(String ixStatusDefault) {
		this.ixStatusDefault = DataType.toInteger(ixStatusDefault);
	}

	/***
	 * Returns if this is a schedule item category.
	 * @return true is a schedule item otherwise false.
	 */
	public boolean isfIsScheduleItem() {
		return fIsScheduleItem;
	}

	public void setfIsScheduleItem(boolean fIsScheduleItem) {
		this.fIsScheduleItem = fIsScheduleItem;
	}
	
	public void setfIsScheduleItem(String fIsScheduleItem) {
		this.fIsScheduleItem = DataType.toBoolean(fIsScheduleItem);
	}
}
