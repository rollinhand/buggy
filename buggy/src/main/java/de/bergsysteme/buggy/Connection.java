package de.bergsysteme.buggy;

public class Connection {
	// Email address for user authentication
	private String email;
	// password
	private String password;
	// url of server
	private String serverURL;
	
	public Connection() {
		setEmail(null);
		setPassword(null);
		setServerURL(null);
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getServerURL() {
		return serverURL;
	}

	public void setServerURL(String serverURL) {
		this.serverURL = serverURL;
	}
	
	public void setAuthentication(String authentication) {
		if (authentication.matches(".*/.*@.*")) {
			int endOfEmail = authentication.indexOf("/");
			int endOfPwd = authentication.lastIndexOf("@");
			String email = authentication.substring(0, endOfEmail);
			String pwd = authentication.substring(endOfEmail + 1, endOfPwd);
			String host = authentication.substring(endOfPwd + 1);
			
			setEmail(email);
			setPassword(pwd);
			setServerURL(host);	
		}
	}
}
