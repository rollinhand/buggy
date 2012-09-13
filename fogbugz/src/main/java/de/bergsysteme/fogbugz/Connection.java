package de.bergsysteme.fogbugz;

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
}
