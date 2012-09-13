package de.bergsysteme.buggy;

public class FogBugzException extends Exception {

	public FogBugzException(String string) {
		super(string);
	}

	public FogBugzException(Exception e) {
		super(e);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -2975162232833154656L;

}
