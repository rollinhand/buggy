package de.bergsysteme.buggy.resolve;

import java.util.Hashtable;

import de.bergsysteme.buggy.Connection;
import de.bergsysteme.buggy.Token;
import de.bergsysteme.buggy.command.ApiCommand;
import de.bergsysteme.buggy.command.ICommand;

public class URLFetcher {
	private static final String CMD_STRING = "%s/%s?cmd=%s&token=%s";
	private static final String LOGON_STRING = "%s/%s?cmd=%s";
	private static final String API_CHECK_STRING = "%s/%s";
	// Instance variable
	private static URLFetcher _instance;
	// Connection
	private Connection connection;
	// Token
	private Token token;
	// Page
	private String requestPage;
	// Command
	private ICommand command;
	// Conversion Table
	private static Hashtable<String, String> escapeMap;
	
	private URLFetcher() {	
		connection = null;
		token = null;
		requestPage = null;
		initEscapeMap();
	}
	
	private void initEscapeMap() {
		escapeMap = new Hashtable<String, String>();
		escapeMap.put("Ä", "%C4");
		escapeMap.put("Ü", "%DC");
		escapeMap.put("Ö", "%D6");
		escapeMap.put("ä", "%E4");
		escapeMap.put("ü", "%FC");
		escapeMap.put("ö", "%F6");
		escapeMap.put("ß", "%DF");		
		escapeMap.put(" ", "%20");
		escapeMap.put(";", "%3B");
		escapeMap.put("\\|", "%7C");
		escapeMap.put("<", "%3C");
		escapeMap.put(">", "%3E");
		escapeMap.put("#", "%23");
		escapeMap.put("%", "%25");
		escapeMap.put("\\{", "%7B");
		escapeMap.put("\\}", "%7D");
		//escapeMap.put("\\", "%5C");
		escapeMap.put("\\^", "%5E");
		escapeMap.put("\\[", "%5B");
		escapeMap.put("\\]", "%5D");
		//escapeMap.put("\\?", "%3F");
	}
	
	protected static URLFetcher getInstance() {
		if (_instance == null) {
			_instance = new URLFetcher();
		}
		return _instance;
	}
	
	public static void setConnection(Connection connection) {
		getInstance().connection = connection;
	}

	public static void setToken(Token token) {
		getInstance().token = token;
	}
	
	public static void setRequestPage(String page) {
		getInstance().requestPage = page;
	}
	
	public static void setCommand(ICommand command) {
		getInstance().command = command;
	}
	
	protected static String replaceChars(String url) {
		String[] keys = escapeMap.keySet().toArray(new String[0]);
		for (String key : keys) {
			String replacement = escapeMap.get(key);
			url = url.replaceAll(key, replacement);
		}
	
		return url;
	}

	public static String generateRequest() throws IllegalArgumentException {
		// Before generating the URL we have to replace some signs
		String serverUrl = getInstance().connection.getServerURL();
		String command = getInstance().command.getCommand();
		String token = getInstance().token.getToken();
		
		String url = null;
		
		if (getInstance().command instanceof ApiCommand) {
			url = String.format(API_CHECK_STRING, serverUrl, command);
		} else if (token == null || token == "") {
			url = String.format(LOGON_STRING, serverUrl, getInstance().requestPage, command);
		} else { 
			url = String.format(CMD_STRING, serverUrl, getInstance().requestPage, command, token);												
		}
		
		url = replaceChars(url);
		
		return url;
	}
}
