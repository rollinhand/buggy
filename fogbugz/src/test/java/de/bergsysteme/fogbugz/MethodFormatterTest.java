package de.bergsysteme.fogbugz;

import static org.junit.Assert.*;

import org.junit.Test;

import de.bergsysteme.fogbugz.resolve.MethodFormatter;

public class MethodFormatterTest {

	@Test
	public void testGetMethod() {
		// Still incorrect formatted
		assertTrue("getDtdue".equals(MethodFormatter.getMethod("dtdue")));
		assertTrue("getIxbug".equals(MethodFormatter.getMethod("ixbug")));
		assertTrue("getsformat".equals(MethodFormatter.getMethod("sformat")));
		assertTrue("getdhello".equals(MethodFormatter.getMethod("dhello")));
		
		// Correct formatted and expected
		assertTrue("getDtDue".equals(MethodFormatter.getMethod("dtDue")));
		assertTrue("getIxBug".equals(MethodFormatter.getMethod("ixBug")));
		assertTrue("getsFormat".equals(MethodFormatter.getMethod("sFormat")));
		assertTrue("getdHello".equals(MethodFormatter.getMethod("dHello")));
	}

	@Test
	public void testSetMethod() {
		// Still incorrect formatted
		assertTrue("setDtdue".equals(MethodFormatter.setMethod("dtdue")));
		assertTrue("setIxbug".equals(MethodFormatter.setMethod("ixbug")));
		assertTrue("setsformat".equals(MethodFormatter.setMethod("sformat")));
		assertTrue("setdhello".equals(MethodFormatter.setMethod("dhello")));
		
		// Correct formatted and expected
		assertTrue("setDtDue".equals(MethodFormatter.setMethod("dtDue")));
		assertTrue("setIxBug".equals(MethodFormatter.setMethod("ixBug")));
		assertTrue("setsFormat".equals(MethodFormatter.setMethod("sFormat")));
		assertTrue("setdHello".equals(MethodFormatter.setMethod("dHello")));
	}

}
