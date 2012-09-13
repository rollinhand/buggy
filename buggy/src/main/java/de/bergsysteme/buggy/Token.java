package de.bergsysteme.buggy;

/***
 * Container for the token.
 * 
 * @author Bjoern Berg, bjoern.berg@gmx.de
 * @version 1.0
 * @since 2012-06-27
 *
 */
public class Token {
	// the Token
	private String token;
	// valid flag
	private boolean valid;
	
	public Token() {
		this.setToken(null);
		this.setValid(false);
	}

	/***
	 * Returns the token.
	 * @return the token.
	 */
	public String getToken() {
		return token;
	}

	/***
	 * Set Token.
	 * @param token 
	 */
	public void setToken(String token) {
		this.token = token;
	}

	/***
	 * Checks if token is valid for session.
	 * @return true if still valid, false for reconnect.
	 */
	public boolean isValid() {
		return valid;
	}

	/***
	 * Sets the token to valid or invalid state.
	 * @param valid must be true oder false.
	 */
	public void setValid(boolean valid) {
		this.valid = valid;
	}
}
