package de.bergsysteme.fogbugz;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class DataType {
	
	/***
	 * Conversion from String to Boolean. This conversion also accepts
	 * numeric values as String and converts them to boolean.
	 * @param arg String for evaluation.
	 * @return true or false.
	 */
	static boolean toBoolean(String arg) {
		boolean bValue = false;
		int id = 0;
		try {
			id = Integer.parseInt(arg);
			bValue = id > 0 ? true : false;
		} catch (NumberFormatException e1) {
			try {
				bValue = Boolean.parseBoolean(arg);
			} catch (Exception e) {
				bValue = false;
			}
		}
		return bValue;
	}
	
	/***
	 * Conversion from String to Integer.
	 * @param arg String for evaluation.
	 * @return integer value.
	 */
	static int toInteger(String arg) {
		int iValue = 0;
		try {
			iValue = Integer.parseInt(arg);
		} catch (NumberFormatException e) {
			iValue = 0;
		}
		return iValue;
	}
	
	/***
	 * Conversion from String to float.
	 * @param arg Dtring for evaluation;
	 * @return float value;
	 */
	static float toFloat(String arg) {
		float fValue = 0;
		try {
			fValue = Float.parseFloat(arg);
		} catch (NumberFormatException e) {
			fValue = 0;
		}
		return fValue;
	}
	
	static Date toDate(String arg) {
		Date date = null;
		// Match: 2007-05-06T22:47:59Z
		try {
			if (arg.matches("[0-9]{4}-[0-9]{2}-[0-9]{2}T[0-9]{2}:[0-9]{2}:[0-9]{2}Z")) {
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
				date = df.parse(arg);
			} 
		} catch (ParseException e) {
			date = null;
		}
		
		return date;
	}
}
