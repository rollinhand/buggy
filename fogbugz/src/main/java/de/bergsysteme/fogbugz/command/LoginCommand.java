package de.bergsysteme.fogbugz.command;

import de.bergsysteme.fogbugz.Connection;

public class LoginCommand extends DefaultCommand {
	// Command
	private String cmd;
	// Connection
	private Connection connection;
	
	public LoginCommand() {
		cmd = "logon&email=%s&password=%s";
	}
		
	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	public String getCommand() {
		cmd = String.format(cmd, connection.getEmail(), connection.getPassword());
		return cmd;
	}
}
