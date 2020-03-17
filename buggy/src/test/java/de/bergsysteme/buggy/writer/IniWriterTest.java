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

import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.bergsysteme.buggy.Case;

// Simple test for INI writer
public class IniWriterTest {
	private static final String INI_TEST_TXT = "ini_test.txt";
	private String[] fieldnames = {"ixbug", "sTitle"};
	private Object[] data;
	private Writer writer;
	private OutputStream out;

	@Before
	public void setUp() throws Exception {
		data = new Case[3];
		for (int i=0; i < data.length; ++i) {
			data[i] = createCase(i);
		}
		out = new FileOutputStream(INI_TEST_TXT);
		writer = new IniWriter(out);
	}

	// Factory to create Cases
	private Object createCase(int i) {
		Case c = new Case();
		c.setIxBug(i);
		c.setsTitle("Fallnummer " + i);
		return c;
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testClose() {
		try {
			writer.close();
		} catch (IOException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testWrite() {
		try {
			writer.write(fieldnames, data);
			writer.close();
			File f = new File(INI_TEST_TXT);
			Assert.assertTrue(f.exists());
			Assert.assertTrue(f.length() > 100);
		} catch (IOException e) {
			fail(e.getMessage());
		}
	}
}
