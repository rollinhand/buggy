package de.bergsysteme.buggy;

import java.util.Date;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.bergsysteme.buggy.DataType;

public class DataTypeTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testToBoolean() {
		Assert.assertTrue(DataType.toBoolean("true"));
		Assert.assertFalse(DataType.toBoolean("false"));
		Assert.assertTrue(DataType.toBoolean("1"));
		Assert.assertFalse(DataType.toBoolean("0"));
		Assert.assertFalse(DataType.toBoolean("hello"));
	}

	@Test
	public void testToInteger() {
		Assert.assertEquals(100, DataType.toInteger("100"));
		Assert.assertEquals(1, DataType.toInteger("1"));
	}

	@Test
	public void testToFloat() {
		Assert.assertEquals((float)100.111, (float)DataType.toFloat("100.111"), 0);
		Assert.assertEquals((float)1.222, (float)DataType.toFloat("1.222"), 0);
	}

	@Test
	public void testToDate() {
		Date d = DataType.toDate("2007-05-06T22:47:59Z");
		Assert.assertNotNull(d);
	}
}
