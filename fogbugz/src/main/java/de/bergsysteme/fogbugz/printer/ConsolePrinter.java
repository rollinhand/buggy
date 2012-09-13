/***************************************************************************                                                                                      
 *                                                                                                                                                               
 *  Copyright (c) 2012, Berg Systeme                                                                                                                        
 *                                                                                                                                                               
 *  Licensed under the Apache License, Version 2.0 (the "License");                                                                                              
 *  you may not use this file except in compliance with the License.                                                                                             
 *  You may obtain a copy of the License at                                                                                                                      
 *                                                                                                                                                               
 *         http://www.apache.org/licenses/LICENSE-2.0.txt                                                                                                        
 *                                                                                                                                                               
 *  Unless required by applicable law or agreed to in writing, software                                                                                          
 *  distributed under the License is distributed on an "AS IS" BASIS,                                                                                            
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.                                                                                     
 *  See the License for the specific language governing permissions and                                                                                          
 *  limitations under the License.                                                                                                                               
 *                                                                                                                                                               
 ***************************************************************************/
package de.bergsysteme.fogbugz.printer;

import java.lang.reflect.Method;
import java.util.Hashtable;

import de.bergsysteme.fogbugz.Messages;
import de.bergsysteme.fogbugz.resolve.ObjectInspector;

public class ConsolePrinter 
implements IPrinter {
	private static final String CONSOLE_FORMAT = "%s = %s";
	private static final String FIELD_FORMAT = "Field.%s";
	private static final String FORMAT_KEY = "console.format";
	
	private Hashtable<String, String> table;
	
	public ConsolePrinter() {
		table = new Hashtable<String, String>();
		table.put(FORMAT_KEY, CONSOLE_FORMAT);
	}

	public void print(String[] columns, Object[] data) 
	throws Exception {
		int fieldLength = columns.length;
		int size = data.length;
		
		for (int d=0; d < size; ++d) {
			for (int f=0; f < fieldLength; ++f) {
				String field = String.format(FIELD_FORMAT, columns[f].toLowerCase());
				String name = Messages.getString(field);
				Method m = ObjectInspector.getInstance().findGetter(data[d], columns[f]);
				String line = null;
				if (m != null) {
					Object result = m.invoke(data[d], (Object[])null);
					line = String.format(getConsoleFormat(), name, result);
				} else {
					line = String.format(getConsoleFormat(), name, "");
				}
				System.out.println(line);
			}
		}
	}
	
	private String getConsoleFormat() {
		return getProperty(FORMAT_KEY, CONSOLE_FORMAT);
	}

	public void setProperty(String key, String value) {
		this.table.put(key, value);
	}

	public String getProperty(String key) {
		return this.table.get(key);
	}

	public String getProperty(String key, String defaultValue) {
		String value = null;
		value = getProperty(key);
		if (value == null) value = defaultValue;
		return value;
	}

	public String[] propertyNames() {
		return this.table.keySet().toArray(new String[0]);
	}
}
