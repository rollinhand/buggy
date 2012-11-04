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
package de.bergsysteme.buggy.writer;

import java.util.Hashtable;

import de.bergsysteme.buggy.writer.Writer;

/***
 * Retrieves an instance of {@link Writer} by its given name.
 * 
 * @author Björn Berg, bjoern.berg@gmx.de
 * @version 1.0
 * @since 2012-10-30
 */
public class WriterFactory {
	private static WriterFactory _instance;
	private Hashtable<String, Class<?>> table;
	
	private WriterFactory() {
		this.table = new Hashtable<String, Class<?>>();
		this.table.put("excel", ExcelWriter.class);
		this.table.put("console", ConsoleWriter.class);
	}
	
	/***
	 * Returns the instance of {@link WriterFactory}.
	 * @return current instance of {@link WriterFactory}.
	 */
	public static WriterFactory getInstance() {
		if (_instance == null) {
			_instance = new WriterFactory();
		}
		return _instance;
	}
	
	/***
	 * Returns an instance of {@link Writer}.
	 * The writer is searched by its name. If the given name is not supported,
	 * the method will throw an {@link IllegalArgumentException}. In case of an
	 * error during instantiation of the object appropriate exceptions are thrown.
	 * @param name name of the writer.
	 * @return instance of writer.
	 * @throws IllegalAccessException  
	 * @throws InstantiationException
	 */
	public Writer findWriterByName(String name) 
	throws InstantiationException, IllegalAccessException {
		Class<?> clazz = table.get(name);
		if (clazz == null) {
			throw new IllegalArgumentException("Writer is not supported: " + name);
		}
		return (Writer) clazz.newInstance();
	}
}
