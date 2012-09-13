package de.bergsysteme.buggy.convert;

import de.bergsysteme.buggy.printer.IPrinter;
import de.bergsysteme.buggy.resolve.ResolverListener;

public interface IConverter
extends ResolverListener {
	public static final String PROPERTY_FIELDS = "fields";
	public static final String PROPERTY_QUERY = "query";
	public static final String PROPERTY_PRINTER_NAME = "printer.name";
	public static final String PROPERTY_AUTHENTICATION = "auth.url";
	
	public abstract String[] getColumns();
	
	public abstract String getQuery();
	
	/***
	 * Calls the Hashtable method put. Provided for parallelism with the getProperty method. 
	 * Enforces use of strings for property keys and values. The value returned is the 
	 * result of the Hashtable call to put. 
	 * @param key the key to be placed into this property list.
	 * @param value the value corresponding to key.
	 */
	public abstract void setProperty(String key, String value);
	
	/***
	 * Searches for the property with the specified key in this property list.
	 * @param key the hashtable key.
	 * @return the value in this property list with the specified key value.
	 */
	public abstract String getProperty(String key);
	
	/***
	 * Searches for the property with the specified key in this property list. 
	 * The method returns the default value argument if the property is not found. 
	 * @param key the hashtable key.
	 * @param a default value.
	 * @return the value in this property list with the specified key value.
	 */
	public abstract String getProperty(String key, String defaultValue);
	
	/***
	 * Returns a string array of all the keys in this property list.
	 * @return a string array of all keys.
	 */
	public abstract String[] propertyNames();

	/***
	 * Executes the converter with the given settings and properties.
	 * The conversion is printed to the given {@link IPrinter}.
	 */
	public abstract void execute();
}