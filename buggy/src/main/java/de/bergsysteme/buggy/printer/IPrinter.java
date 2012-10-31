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

import de.bergsysteme.buggy.Case;
import de.bergsysteme.buggy.Project;
import de.bergsysteme.buggy.writer.Writer;

/***
 * Generic Interface for all registered printers. 
 * Only printers which implement this interface can be used as printer. 
 * Every printer can be configured using setProperty.
 * Keys should be unique between all printers because outer programs can influence the
 * settings for a printer.
 * If the printer has to send the output to a file, work with the property key 'file'.
 * 
 * @author Bjoern Berg, bjoern.berg@gmx.de
 * @version 1.0
 * @since 2012-07-31
 * @deprecated use {@link Writer} interface instead.
 */
public interface IPrinter {
	/***
	 * Prints the content of FogBugz elements.
	 * Columns represent the fields inside the FogBugz element represented by data.
	 * Data is an array of FogBugz elements, like {@link Case} or {@link Project}.
	 * 
	 * @param columns the fields which should be printed.
	 * @param data FogBugz elements.
	 */
	public abstract void print(String[] columns, Object[] data) throws Exception;
	
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
}