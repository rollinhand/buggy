package de.bergsysteme.buggy.resolve;

import java.util.Enumeration;
import java.util.Hashtable;

/***
 * Determines correct getter or setter name of an object.
 * 
 * @author Bjoern Berg, bjoern.berg@gmx.de
 * @version 1.0
 * @since 2012-07-25
 *
 */
public class MethodFormatter {
	private static MethodFormatter _instance;
	protected Hashtable<String, String> prefixTable = new Hashtable<String, String>();
	
	private MethodFormatter() {
		prefixTable.put("dt", "Dt");
		prefixTable.put("ix", "Ix");
		prefixTable.put("f", "f");
		prefixTable.put("s", "s");
		prefixTable.put("hrs", "Hrs");
	}
	
	private static MethodFormatter getInstance() {
		if (_instance == null) {
			_instance = new MethodFormatter();
		}
		return _instance;
	}

	/***
	 * From the given name, the method tries to evaluate the correct
	 * name for the getter-method.
	 * 
	 * @param name for the getter.
	 * @return formatted name as expected.
	 */
	public static String getMethod(String name) {
		String prefix = checkPrefix(name.toLowerCase());
		String trunk = name.substring(prefix.length(), name.length());
		return String.format("get%s%s", prefix, trunk);
	}
	
	/***
	 * From the given name, the method tries to evaluate the correct
	 * name for the setter-method.
	 * 
	 * @param name for the setter.
	 * @return formatted name as expected.
	 */
	public static String setMethod(String name) {
		String prefix = checkPrefix(name.toLowerCase());
		String trunk = name.substring(prefix.length(), name.length());
		return String.format("set%s%s", prefix, trunk);
	}
	
	/***
	 * Evaluate prefix.
	 * 
	 * @param name expected method trunk.
	 * @return prefix.
	 */
	private static String checkPrefix(String name) {
		String prefix = "";
		Enumeration<String> keys = getInstance().prefixTable.keys();
		while (keys.hasMoreElements() && prefix == "") {
			String key = keys.nextElement();
			if (name.startsWith(key)) { prefix = getInstance().prefixTable.get(key); }
		}
		return prefix;
	}
}
