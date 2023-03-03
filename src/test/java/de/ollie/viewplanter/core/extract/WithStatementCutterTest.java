package de.ollie.viewplanter.core.extract;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class WithStatementCutterTest {

	@InjectMocks
	private WithStatementCutter unitUnderTest;

	@Nested
	class TestsOfMethod_cutWithStatement_String {

		@Test
		void returnsANullValue_passingANullValue() {
			assertNull(unitUnderTest.cutWithStatement(null));
		}

		@Test
		void returnsAnEmptyString_passingAnEmptyString() {
			assertEquals("", unitUnderTest.cutWithStatement(""));
		}

		@Test
		void returnsThePassedString_passingStringWithNoWithStatement() {
			String expected = "select * from A";
			assertEquals(expected, unitUnderTest.cutWithStatement(expected));
		}

		@Test
		void returnsTheCutString_passingStringWithASimpleWithStatement() {
			String expected = "select * from B";
			assertEquals(expected, unitUnderTest.cutWithStatement("with B as (select * from A) " + expected));
		}

		@Test
		void returnsTheCutString_passingStringWithAComplexWithStatement() {
			String expected = "select * from B";
			assertEquals(
					expected,
					unitUnderTest
							.cutWithStatement(
									"with B (C, D) as (select * from A), E as (select * from F) " + expected));
		}

	}

}