package de.bergsysteme.fogbugz.printer;

import static org.junit.Assert.*;
import junit.framework.Assert;

import org.junit.Test;

public class PrinterFactoryTest {

	@Test
	public void testGetInstance() {
		Assert.assertNotNull(PrinterFactory.getInstance());
	}

	@Test
	public void testFindPrinterByName() {
		try {
			Assert.assertNull(PrinterFactory.getInstance().findPrinterByName("abcdef"));
		} catch (Exception e) {
			Assert.assertSame(IllegalArgumentException.class, e.getClass());
		}
		
		try {
			Assert.assertNotNull(PrinterFactory.getInstance().findPrinterByName("csv"));
		} catch (Exception e) {
			fail("CSV: Should not throw an Exception.");
		}
		
		try {
			Assert.assertNotNull(PrinterFactory.getInstance().findPrinterByName("console"));
		} catch (Exception e) {
			fail("Console: Should not throw an Exception.");
		}
		
		try {
			Assert.assertNotNull(PrinterFactory.getInstance().findPrinterByName("latex"));
		} catch (Exception e) {
			e.printStackTrace();
			fail("Latex: Should not throw an Exception.");
			
		}
	}
}
