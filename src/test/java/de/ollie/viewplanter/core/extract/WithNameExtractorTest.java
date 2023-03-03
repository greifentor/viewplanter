package de.ollie.viewplanter.core.extract;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class WithNameExtractorTest {

	@Nested
	class TestsOfConstructor {

		@Test
		void throwsAnException_whenCalled() {
			assertThrows(UnsupportedOperationException.class, () -> new WithNameExtractor());
		}

	}

	@Nested
	class extractWithStatementNames_ViewInfoData {

		@Test
		void returnsAnEmptyList_passingANullValue() {
			assertTrue(WithNameExtractor.extractWithStatementNames(null).isEmpty());
		}

		@Test
		void returnsAnEmptyList_passingAViewInfoDataWithAnEmptyViewStatement() {
			assertTrue(WithNameExtractor.extractWithStatementNames("").isEmpty());
		}

		@Test
		void returnsAnEmptyList_passingAViewInfoDataWithNoWithStatements() {
			assertEquals(List.of(), WithNameExtractor.extractWithStatementNames("select * from blubs"));
		}

		@Test
		void returnsAListWithOneWithStatementName_passingAViewInfoDataWithAWithStatement() {
			assertEquals(List.of("A"),
					WithNameExtractor.extractWithStatementNames("with A as (select * from B) select * from C"));
		}

		@Test
		void returnsAListWithThreeViewStatement_passingAViewInfoDataWithAWithStatementAndTwoSelects() {
			assertEquals(List.of("A", "D"),
					WithNameExtractor.extractWithStatementNames(
							"with A as (select * from B), D as (select * from E) select * from C"));
		}

	}

}
