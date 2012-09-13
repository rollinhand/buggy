package de.bergsysteme.fogbugz;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CategoryTest {
	private Category category;

	@Before
	public void setUp() throws Exception {
		category = new Category();
	}

	@After
	public void tearDown() throws Exception {
		category = null;
	}

	@Test
	public void testGetIxCategory() {
		category.setIxCategory(200);
		assertEquals(200, category.getIxCategory());
	}

	@Test
	public void testSetIxCategoryInt() {
		category.setIxCategory(300);
		assertEquals(300, category.getIxCategory());
	}

	@Test
	public void testSetIxCategoryString() {
		category.setIxCategory("300");
		assertEquals(300, category.getIxCategory());
		category.setIxCategory("NAN");
		assertNotSame(300, category.getIxCategory());
	}

	@Test
	public void testSetfIsScheduleItemBoolean() {
		category.setfIsScheduleItem(true);
		assertTrue(category.isfIsScheduleItem());
		category.setfIsScheduleItem(false);
		assertFalse(category.isfIsScheduleItem());
	}

	@Test
	public void testSetfIsScheduleItemString() {
		category.setfIsScheduleItem("true");
		assertTrue(category.isfIsScheduleItem());
		category.setfIsScheduleItem("false");
		assertFalse(category.isfIsScheduleItem());
		category.setfIsScheduleItem("1");
		assertTrue(category.isfIsScheduleItem());
		category.setfIsScheduleItem("0");
		assertFalse(category.isfIsScheduleItem());
		category.setfIsScheduleItem("-1");
		assertFalse(category.isfIsScheduleItem());
	}

}
