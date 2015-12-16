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
package de.bergsysteme.buggy.printer;

import java.util.Hashtable;

import de.bergsysteme.buggy.writer.Writer;

/***
 * Retrieves an instance of {@link IPrinter} by its given name.
 * 
 * @author Björn Berg, bjoern.berg@gmx.de
 * @version 1.0
 * @since 2012-07-31
 * @deprecated use {@link Writer} interface.
 */
public class PrinterFactory {
	private static PrinterFactory _instance;
	private Hashtable<String, Class<?>> table;
	
	private PrinterFactory() {
		this.table = new Hashtable<String, Class<?>>();
		this.table.put("csv", CSVPrinter.class);
		this.table.put("console", ConsolePrinter.class);
		this.table.put("latex", LaTeXPrinter.class);
	}
	
	/***
	 * Returns the instance of {@link WriterFactory}.
	 * @return current instance of {@link WriterFactory}.
	 */
	public static PrinterFactory getInstance() {
		if (_instance == null) {
			_instance = new PrinterFactory();
		}
		return _instance;
	}
	
	/***
	 * Returns an instance of {@link IPrinter}.
	 * The printer is searched by its name. If the given name is not supported,
	 * the method will throw an {@link IllegalArgumentException}. In case of an
	 * error during instantiation of the object appropriate exceptions are thrown.
	 * @param name name of the printer.
	 * @return instance of printer.
	 * @throws IllegalAccessException  
	 * @throws InstantiationException
	 */
	public IPrinter findPrinterByName(String name) 
	throws InstantiationException, IllegalAccessException {
		Class<?> clazz = table.get(name);
		if (clazz == null) {
			throw new IllegalArgumentException("Printer is not supported: " + name);
		}
		return (IPrinter) clazz.newInstance();
	}
}
