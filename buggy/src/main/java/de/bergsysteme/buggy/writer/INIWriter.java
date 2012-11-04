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

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import de.bergsysteme.buggy.resolve.Field;

/***
 * Writes the received data to a file in INI-Format.
 * 
 * @author Björn Berg, bjoern.berg@gmx.de
 * @version 1.0
 * @since 2012-10-30
 *
 */
public class INIWriter extends Writer {
	private static final String DATASET_FORMAT = "%s = %s";
	private static final String HEADER_FORMAT = "[%s %d]";
	private BufferedWriter bwriter;
	
	public INIWriter() {
		this(null);
	}
	
	protected INIWriter(OutputStream out) {
		super(out);
		bwriter = new BufferedWriter(new OutputStreamWriter(out));
	}

	@Override
	public void write(String[] fieldnames, Object[] values) throws IOException {
		for (int valueIndex=0; valueIndex < values.length; ++valueIndex) {
			bwriter.write(String.format(HEADER_FORMAT, "Dataset ", valueIndex + 1));
			for (int fieldIndex=0; fieldIndex < fieldnames.length; ++fieldIndex) {
				// Get the key for output
				String fieldname = fieldnames[fieldIndex];
				String key = Field.translate(fieldname);
				// Get the value for output
				Object data = values[valueIndex];
				String value = getValue(fieldname, data);
				// Organize data and print it to console
				bwriter.write(String.format(DATASET_FORMAT, key, value));
			}
		}
	}

	@Override
	public void close() throws IOException {
		bwriter.flush();
		bwriter.close();		
	}
}
