package de.bergsysteme.fogbugz;

import static org.junit.Assert.*;

import org.junit.Test;

public class ErrorCodeTest {

	@Test
	public void testGetInstance() {
		// Test if Instantiation works without problems.
		assertNotNull(ErrorCode.getInstance());
	}

	@Test
	public void testTranslateCode() {
		final String NOT_FOUND = Messages.getString("ErrorCode.na");
		// Test internal getTranslation with several codes.
		assertFalse(NOT_FOUND.toString() == ErrorCode.translateCode(0));
		assertFalse(NOT_FOUND.toString() == ErrorCode.translateCode(25));
		assertTrue(NOT_FOUND.toString() == ErrorCode.translateCode(-1));
		assertTrue(NOT_FOUND.toString() == ErrorCode.translateCode(30));
	}
}
