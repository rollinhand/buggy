package de.bergsysteme.fogbugz.convert;

import java.util.Hashtable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.bergsysteme.fogbugz.Connection;
import de.bergsysteme.fogbugz.Error;
import de.bergsysteme.fogbugz.resolve.Processor;
import de.bergsysteme.fogbugz.resolve.ResolverListener;

public abstract class Converter
implements ResolverListener, IConverter {
	protected Logger logger;
	protected Connection connection;
	protected String[] fields;
	private String query;
	protected Hashtable<String, String> table;
	
	public Converter() {
		logger = Logger.getLogger(Processor.PROJECT);
		table = new Hashtable<String, String>();
		table.put(PROPERTY_FIELDS, "");
		table.put(PROPERTY_QUERY, "");
		table.put(PROPERTY_AUTHENTICATION, "");
		table.put(PROPERTY_PRINTER_NAME, "console");
	}
	
	protected boolean validateAuthentication(String authentication) {
		boolean success = false;
		logger.finest("Authentication String: " + authentication);
		if (authentication.matches(".*/.*@.*")) {
			int endOfEmail = authentication.indexOf("/");
			int endOfPwd = authentication.lastIndexOf("@");
			String email = authentication.substring(0, endOfEmail);
			String pwd = authentication.substring(endOfEmail + 1, endOfPwd);
			String host = authentication.substring(endOfPwd + 1);
			logger.log(Level.FINEST, String.format("Debug: %s/%s@%s", email, pwd, host));
			
			connection = new Connection();
			connection.setEmail(email);
			connection.setPassword(pwd);
			connection.setServerURL(host);
			
			success = true;
		} else {
			logger.log(Level.WARNING, "Authentication string is not valid.");			
			success = false;
		}
		
		return success;
	}
	
	/* (non-Javadoc)
	 * @see de.bergsysteme.fogbugz.convert.IConverter#notify(java.util.List)
	 */
	public void notify(List<Object> l) {
		if (l == null || l.size() == 0) {
			logger.log(Level.WARNING, "No results.");
		} else {
			logger.log(Level.INFO, "Returned results: " + l.size());
			
			if (l.size() == 1) {
				Object obj = l.get(0);
				if (obj instanceof Error) {
					return;
				} else {
					print(l);
				}
			} else {
				print(l);
			}
		}
	}

	public String[] getColumns() {
		return fields;
	}

	public String getQuery() {
		return this.query;
	}

	public void setProperty(String key, String value) {
		table.put(key, value);
	}

	public String getProperty(String key) {
		return table.get(key);
	}

	public String getProperty(String key, String defaultValue) {
		String value = getProperty(key);
		if (value == null) value = defaultValue;
		return value;
	}

	public String[] propertyNames() {
		return table.keySet().toArray(new String[0]);
	}

	public abstract void execute();
	
	protected abstract void print(List<Object> list);
}
