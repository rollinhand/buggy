package de.bergsysteme.buggy.resolve;

import java.util.List;
import java.util.logging.Level;
import org.apache.log4j.Logger;

import de.bergsysteme.buggy.Api;
import de.bergsysteme.buggy.Connection;
import de.bergsysteme.buggy.Error;
import de.bergsysteme.buggy.ErrorCode;
import de.bergsysteme.buggy.FogBugzException;
import de.bergsysteme.buggy.Token;
import de.bergsysteme.buggy.command.ApiCommand;
import de.bergsysteme.buggy.command.ICommand;
import de.bergsysteme.buggy.command.LoginCommand;

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
		logger = Logger.getRootLogger();
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
		logger.info("Trying to detect the api page to call.");
		checkingApi = true;
		ApiCommand api = new ApiCommand();
		execute(api);
	}
	
	public static void execute(ICommand command) throws FogBugzException {
		if (getPage() == null && !checkingApi) { checkApi(); }
		
		if (!checkingApi && !loggedIn) {
			logger.info("Token was not set. Trying to login.");			
			login(); 
			if (getToken().getToken() != null) {
				logger.info("Login was successful: " + getToken().getToken());
			} else {
				logger.warn( "Login failed.");
			}
		}
		
		// Generate Request with URLFetcher
		URLFetcher.setCommand(command);
		URLFetcher.setConnection(getConnection());
		URLFetcher.setToken(getToken());
		URLFetcher.setRequestPage(getPage());
		String uri = URLFetcher.generateRequest();
		logger.info( "Using URL for request: " + uri);
		
		// Execute command
		command.addListener(Processor.getInstance());
		command.execute(uri);
	}

	public void notify(List<Object> l) {
		logger.trace ("Processor was notified by ResponseResolver.");
		if (l != null) { workOnList(l); }
	}
	
	private void workOnList(List<Object> list) {
		// Things for the processor
		if (list.size() == 1) {
			Object obj = list.get(0);
			if (obj instanceof Token) {
				setToken((Token)obj);
				loggedIn = true;
				logger.trace ("Token was returned by request.");
			} else if (obj instanceof Api) {
				Api api = (Api) obj;
				String url = api.getUrl();
				url = url.replace("?", "");
				setPage(url);
				checkingApi = false;
				logger.trace( "API request was succesful.");
				logger.trace( api.getMinversion());
			} else if (obj instanceof Error) {
				Error error = (Error) obj;				
				logger.warn( "Error in processing instructions: " + error.getError() + ", Code: " + error.getCode());
				logger.warn( ErrorCode.translateCode(error.getCode()));
			} else {
				logger.trace( "List does not contain objects for Processor.");				
			}
		}
	}
}
