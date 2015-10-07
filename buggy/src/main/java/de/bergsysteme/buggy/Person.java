package de.bergsysteme.buggy;

public class Person {
	private int ixPerson;
	private String sFullName;
	private String sEmail;
	private String sPhone;
	private boolean fAdministrator;
	private boolean fCommunity;
	private boolean fVirtual;
	private boolean fDeleted;
	private boolean fNotify;
	private String sHomepage;
	private String sLocale;
	private String sLanguage;
	private String sTimeZoneKey;
	private boolean fExpert;
	public int getIxPerson() {
		return ixPerson;
	}
	public void setIxPerson(int ixPerson) {
		this.ixPerson = ixPerson;
	}
	public void setIxPerson(String ixPerson) {
		this.ixPerson = DataType.toInteger(ixPerson);
	}
	public String getsFullName() {
		return sFullName;
	}
	public void setsFullName(String sFullName) {
		this.sFullName = sFullName;
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
	public boolean isfAdministrator() {
		return fAdministrator;
	}
	public void setfAdministrator(boolean fAdministrator) {
		this.fAdministrator = fAdministrator;
	}
	public void setfAdministrator(String fAdministrator) {
		this.fAdministrator = DataType.toBoolean(fAdministrator);
	}
	public boolean isfCommunity() {
		return fCommunity;
	}
	public void setfCommunity(boolean fCommunity) {
		this.fCommunity = fCommunity;
	}
	public void setfCommunity(String fCommunity) {
		this.fCommunity = DataType.toBoolean(fCommunity);
	}
	public boolean isfVirtual() {
		return fVirtual;
	}
	public void setfVirtual(boolean fVirtual) {
		this.fVirtual = fVirtual;
	}
	public void setfVirtual(String fVirtual) {
		this.fVirtual = DataType.toBoolean(fVirtual);
	}
	public boolean isfDeleted() {
		return fDeleted;
	}
	public void setfDeleted(boolean fDeleted) {
		this.fDeleted = fDeleted;
	}
	public void setfDeleted(String fDeleted) {
		this.fDeleted = DataType.toBoolean(fDeleted);
	}
	public boolean isfNotify() {
		return fNotify;
	}
	public void setfNotify(boolean fNotify) {
		this.fNotify = fNotify;
	}
	public void setfNotify(String fNotify) {
		this.fNotify = DataType.toBoolean(fNotify);
	}
	public String getsHomepage() {
		return sHomepage;
	}
	public void setsHomepage(String sHomepage) {
		this.sHomepage = sHomepage;
	}
	public String getsLocale() {
		return sLocale;
	}
	public void setsLocale(String sLocale) {
		this.sLocale = sLocale;
	}
	public String getsLanguage() {
		return sLanguage;
	}
	public void setsLanguage(String sLanguage) {
		this.sLanguage = sLanguage;
	}
	public String getsTimeZoneKey() {
		return sTimeZoneKey;
	}
	public void setsTimeZoneKey(String sTimeZoneKey) {
		this.sTimeZoneKey = sTimeZoneKey;
	}
	public boolean isfExpert() {
		return fExpert;
	}
	public void setfExpert(boolean fExpert) {
		this.fExpert = fExpert;
	}
	public void setfExpert(String fExpert) {
		this.fExpert = DataType.toBoolean(fExpert);
	}
	@Override
	public String toString() {
		return String.format("[%02d] %s", getIxPerson(), getsFullName());
	}
	
	
	
	
	/*
	 * <ixPerson>11</ixPerson> -- person (id)
<sFullName>Old MacDonald</sFullName> -- person name
<sEmail>grandpa@oldmacdonald.com</sEmail> -- person email
<sPhone></sPhone> -- person phone
<fAdministrator>false</fAdministrator> -- true if user is a site admin
<fCommunity>false</fCommunity> -- true if user is a community user
<fVirtual>false</fVirtual> -- true if user is a virtual user
<fDeleted>false</fDeleted> -- true if account is inactive
<fNotify>true</fNotify> -- true if user receives email notifications
<sHomepage></sHomepage> -- homepage url
<sLocale>en-us</sLocale> -- The locale code for the date/number
language (i.e., "en-us") ( "*" means use browser format )
<sLanguage>en-us</sLanguage> -- The locale code for the UI language
(i.e., "en-us") ( "*" means use browser format )
<sTimeZoneKey>Eastern Standard Time</sTimeZoneKey> -- A key that
defines the time zone setting for this person's account 
(i.e., "Eastern Standard Time"). The values for this setting are
operating system dependent.
( "*" means use fogbugz default )
<fExpert>false</fExpert> -- No longer used
</person>
	 */

}
