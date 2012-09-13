package de.bergsysteme.fogbugz;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PriorityTest {
	private Priority naked;
	private Priority filled;
	
	private static final String DEF_PRIO_TEXT = "Meine Priorität";
	private static final int DEF_PRIO_ID = 30;
	
	@Before
	public void setUp() throws Exception {
		naked = new Priority();
		filled = new Priority();
		filled.setIxPriority(DEF_PRIO_ID);
		filled.setsPriority(DEF_PRIO_TEXT);
	}

	@After
	public void tearDown() throws Exception {
		naked = null;
		filled = null;
	}

	@Test
	public void testGetIxPriority() {
		assertEquals(DEF_PRIO_ID, filled.getIxPriority());
		assertEquals(0, naked.getIxPriority());
	}

	@Test
	public void testSetIxPriorityInt() {
		filled.setIxPriority(10);
		assertEquals(10, filled.getIxPriority());
	}

	@Test
	public void testSetIxPriorityString() {
		filled.setIxPriority("20");
		assertEquals(20, filled.getIxPriority());
	}

	@Test
	public void testGetsPriority() {
		assertEquals(DEF_PRIO_TEXT, filled.getsPriority());
	}

	@Test
	public void testSetsPriority() {
		filled.setsPriority("Tada");
		assertEquals("Tada", filled.getsPriority());
	}
}
