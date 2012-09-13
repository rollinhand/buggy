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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Hashtable;
import java.util.logging.Logger;

import de.bergsysteme.fogbugz.Messages;
import de.bergsysteme.fogbugz.resolve.ObjectInspector;
import de.bergsysteme.fogbugz.resolve.Processor;

/***
 * Prints the content of FogBugz elements as CSV file.
 * This printer supports several properties to configure the behaviour:
 * <ul>
 *   <li>csv.datasetDelimiter: Delimiter to separate datasets from each other. By default
 *   the delimiter depends on the platform the software is running on. Most common delimiters
 *   are \n, \r or \r\n. You do not have to set this option.</li>
 *   <li>csv.fieldDelimiter: Delimiter for a column; possible: Comma, Semicolon, Tab or Colon.</li>
 *   <li>csv.fieldQuotation: Sign for quotation. If fieldDelimiter is used inside the content of 
 *   the field, the field must be masked with the quote sign.</li>
 *   <li>csv.enableQuotation: Activate quotation for every field.</li>
 *   <li>file: Filename for writing the output.</li>
 * </ul>
 * The printer is pre-configured with the generic options to generate an Excel-conform CSV file.
 * 
 * @author Björn Berg, bjoern.berg@gmx.de
 * @version 1.0
 * @since 2012-07-31
 */
public class CSVPrinter implements IPrinter {
	// Static declarations for properties
	private static final String DATASET_DELIMITER = "csv.datasetDelimiter";
	private static final String FIELD_DELIMITER = "csv.fieldDelimiter";
	private static final String FIELD_QUOTATION = "csv.fieldQuotation";
	private static final String ENABLE_QUOTATION = "csv.enableQuotation";
	private static final String FILE = "file";
	// Logging instance.
	private Logger logger;
	// Columns to print.
	private String[] availableColumns;
	// PrintWriter instance.
	private PrintWriter out;
	// New mechanism for delegating printer with properties
	Hashtable<String, String> properties;
	
	public CSVPrinter(String filename) {
		this();
		setProperty(FILE, filename);
	}
	
	public CSVPrinter() {
		this.logger = Logger.getLogger(Processor.PROJECT);
		this.availableColumns = null;
		properties = new Hashtable<String, String>();
		properties.put(DATASET_DELIMITER, System.getProperty("line.separator"));
		properties.put(FIELD_DELIMITER, ";");
		properties.put(FIELD_QUOTATION, "\"");
		properties.put(ENABLE_QUOTATION, "false");
	}
	
	private String getDataSetDelimiter() {
		return getProperty(DATASET_DELIMITER);
	}

	private String getFieldDelimiter() {
		return getProperty(FIELD_DELIMITER);
	}

	private boolean isQuotationEnabled() {
		return Boolean.parseBoolean(getProperty(ENABLE_QUOTATION));
	}
	
	private String getFieldQuotation() {
		return getProperty(FIELD_QUOTATION);
	}
	
	private File getFile() {
		String filename = getProperty(FILE);
		return new File(filename);
	}

	/* (non-Javadoc)
	 * @see de.bergsysteme.fogbugz.printer.IPrinter#print(java.lang.String[], java.lang.Object[])
	 */
	public void print(String[] columns, Object[] data) throws Exception {
		int size = columns.length;
		if (size == 0) {
			logger.warning("No columns given.");
			return;
		}
		if (out == null) {
			out = new PrintWriter(new BufferedWriter(new FileWriter(getFile())));
		}
		this.availableColumns = columns;
		printHead(this.availableColumns);
		printBody(data);
		out.flush();
		out.close();
	}
	
	/***
	 * Set the readable names for the columns to print. Names must be in the
	 * same order like the columns.
	 * 
	 * @param names Array of names.
	 */
	private void printHead(String[] names) {
		if (names != null) {
			int size = names.length;
			String format = createFormatString(size);
			// Translate field names into current language
			String[] messageFields = new String[size];
			for (int i=0; i < size; ++i) {
				String messageCode = String.format("Field.%s", names[i].toLowerCase());
				messageFields[i] = Messages.getString(messageCode);
			}
			String head = String.format(format, (Object[])messageFields);
			out.println(head);
			messageFields = null;
		} else {
			logger.severe("Column names cannot be null.");
		}
	}
	
	private String createFormatString(int size) {
		String format = "";
		for(int i=0; i < size; ++i) { format += "%s"+getFieldDelimiter(); }
		format = format.substring(0, format.length() - 1);
		return format;
	}
	
	private String[] formatContent(String[] content) {
		String quote = getFieldQuotation();
		for (int i=0; i < content.length; ++i) {
			String field = content[i];
			if (isQuotationEnabled()) {
				logger.info("Quotation is activated.");
				field = String.format("%c%s%c", quote, field, quote);
			} else {
				if (field.contains(getDataSetDelimiter()) || 
					field.contains(String.valueOf(getFieldDelimiter()))) {
					field = String.format("%c%s%c", quote, field, quote);
				}
			}
			content[i] = field;
		}
		return content;
	}

	protected void printBody(Object[] listOfObjects) {
		String[] printableData;
		int size = this.availableColumns.length;
		
		try {
			for (Object obj : listOfObjects) {
				printableData = new String[size];
				int index = 0;
				for (String fieldName : availableColumns) {
					Method m = ObjectInspector.getInstance().findGetter(obj, fieldName);
					if (m != null) {
						Object result = m.invoke(obj, (Object[])null);
						printableData[index++] = String.valueOf(result);
					} else {
						logger.severe("Column " + fieldName + " unknown.");
					}
				}
				String format = createFormatString(size);
				printableData = formatContent(printableData);
				String line = String.format(format, (Object[])printableData);
				logger.info("Writing: " + line);
				out.println(line);
			}
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	public void setProperty(String key, String value) {
		this.properties.put(key, value);
	}

	public String getProperty(String key) {
		return this.properties.get(key);
	}

	public String getProperty(String key, String defaultValue) {
		String value = getProperty(key);
		if (value == null) { value = defaultValue; }
		return value;
	}

	public String[] propertyNames() {
		return this.properties.keySet().toArray(new String[0]);
	}
}
