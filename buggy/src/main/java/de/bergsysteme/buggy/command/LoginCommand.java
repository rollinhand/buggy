package de.bergsysteme.buggy.command;

import de.bergsysteme.buggy.Connection;

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
