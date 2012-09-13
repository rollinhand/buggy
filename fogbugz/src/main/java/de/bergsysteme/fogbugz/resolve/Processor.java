package de.bergsysteme.fogbugz.resolve;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.bergsysteme.fogbugz.Api;
import de.bergsysteme.fogbugz.Connection;
import de.bergsysteme.fogbugz.Error;
import de.bergsysteme.fogbugz.ErrorCode;
import de.bergsysteme.fogbugz.FogBugzException;
import de.bergsysteme.fogbugz.Token;
import de.bergsysteme.fogbugz.command.ApiCommand;
import de.bergsysteme.fogbugz.command.ICommand;
import de.bergsysteme.fogbugz.command.LoginCommand;

public class Processor implements ResolverListener {
	// Package
	public static final String PROJECT = "fogbugz";
	// Instance variable
	private static Processor _instance;
	// Connection
	private Connection connection;
	// Token
	private Token token;
	// Page to interact
	private String page = null;
	// Page for resolving SOAP
	public static final String resolvePage = "api.xml";
	// Status flags
	private static boolean checkingApi = false;
	private static boolean loggedIn = false;
	
	private static Logger logger = null;
	
	private Processor() {
		connection = null;
		token = new Token();	// empty Dummy
		
		// Instantiate Logger
		logger = Logger.getLogger(PROJECT);
		logger.setLevel(Level.FINEST);
	}
	
	protected static Processor getInstance() {
		if (_instance == null) {
			_instance = new Processor();
		}
		return _instance;
	}
	
	public static Connection getConnection() {
		return getInstance().connection;
	}

	public static void setConnection(Connection connection) {
		getInstance().connection = connection;
	}
	
	private static Token getToken() {
		return getInstance().token;
	}
	
	private static void setToken(Token t) {
		getInstance().token = t;
	}
	
	private static String getPage() {
		return getInstance().page;
	}
	
	private static void setPage(String p) {
		getInstance().page = p;
	}
	
	private static void login () throws FogBugzException {
		loggedIn = true;
		LoginCommand login = new LoginCommand();
		login.setConnection(getConnection());
		execute(login);
	}
	
	private static void checkApi () throws FogBugzException {		
		logger.log(Level.INFO, "Trying to detect the api page to call.");
		checkingApi = true;
		ApiCommand api = new ApiCommand();
		execute(api);
	}
	
	public static void execute(ICommand command) throws FogBugzException {
		if (getPage() == null && !checkingApi) { checkApi(); }
		
		if (!checkingApi && !loggedIn) {
			logger.log(Level.INFO, "Token was not set. Trying to login.");			
			login(); 
			if (getToken().getToken() != null) {
				logger.log(Level.INFO, "Login was successful: " + getToken().getToken());
			} else {
				logger.log(Level.SEVERE, "Login failed.");
			}
		}
		
		// Generate Request with URLFetcher
		URLFetcher.setCommand(command);
		URLFetcher.setConnection(getConnection());
		URLFetcher.setToken(getToken());
		URLFetcher.setRequestPage(getPage());
		String uri = URLFetcher.generateRequest();
		logger.log(Level.FINE, "Using URL for request: " + uri);
		
		// Execute command
		command.addListener(Processor.getInstance());
		command.execute(uri);
	}

	public void notify(List<Object> l) {
		logger.log(Level.FINEST, "Processor was notified by ResponseResolver.");
		if (l != null) { workOnList(l); }
	}
	
	private void workOnList(List<Object> list) {
		// Things for the processor
		if (list.size() == 1) {
			Object obj = list.get(0);
			if (obj instanceof Token) {
				setToken((Token)obj);
				loggedIn = true;
				logger.log(Level.FINEST, "Token was returned by request.");
			} else if (obj instanceof Api) {
				Api api = (Api) obj;
				String url = api.getUrl();
				url = url.replace("?", "");
				setPage(url);
				checkingApi = false;
				logger.log(Level.FINEST, "API request was succesful.");
				logger.log(Level.FINEST, String.format("FogBugz version: %d.%d", api.getVersion(), api.getMinversion()));
			} else if (obj instanceof Error) {
				Error error = (Error) obj;				
				logger.log(Level.SEVERE, "Error in processing instructions: " + error.getError() + ", Code: " + error.getCode());
				logger.log(Level.SEVERE, ErrorCode.translateCode(error.getCode()));
			} else {
				logger.log(Level.FINEST, "List does not contain objects for Processor.");				
			}
		}
	}
}
