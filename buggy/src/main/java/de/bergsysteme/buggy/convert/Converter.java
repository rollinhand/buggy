package de.bergsysteme.buggy.convert;

import java.util.Hashtable;
import java.util.List;
import java.util.logging.Level;

import org.apache.log4j.Logger;

import de.bergsysteme.buggy.Connection;
import de.bergsysteme.buggy.Error;
import de.bergsysteme.buggy.resolve.Processor;
import de.bergsysteme.buggy.resolve.ResolverListener;

public abstract class Converter
implements ResolverListener, IConverter {
	protected Logger logger;
	protected Connection connection;
	protected String[] fields;
	private String query;
	protected Hashtable<String, String> table;
	
	public Converter() {
		logger = Logger.getRootLogger();
		table = new Hashtable<String, String>();
		table.put(PROPERTY_FIELDS, "");
		table.put(PROPERTY_QUERY, "");
		table.put(PROPERTY_AUTHENTICATION, "");
		table.put(PROPERTY_PRINTER_NAME, "console");
	}
	
	protected boolean validateAuthentication(String authentication) {
		boolean success = false;
		logger.trace("Authentication String: " + authentication);
		if (authentication.matches(".*/.*@.*")) {
			int endOfEmail = authentication.indexOf("/");
			int endOfPwd = authentication.lastIndexOf("@");
			String email = authentication.substring(0, endOfEmail);
			String pwd = authentication.substring(endOfEmail + 1, endOfPwd);
			String host = authentication.substring(endOfPwd + 1);
			logger.trace(String.format("Debug: %s/%s@%s", email, pwd, host));
			
			connection = new Connection();
			connection.setEmail(email);
			connection.setPassword(pwd);
			connection.setServerURL(host);
			
			success = true;
		} else {
			logger.warn("Authentication string is not valid.");			
			success = false;
		}
		
		return success;
	}
	
	/* (non-Javadoc)
	 * @see de.bergsysteme.buggy.convert.IConverter#notify(java.util.List)
	 */
	public void notify(List<Object> l) {
		if (l == null || l.size() == 0) {
			logger.warn("No results.");
		} else {
			logger.info("Returned results: " + l.size());
			
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
