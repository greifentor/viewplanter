package de.ollie.viewplanter.core.extract;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import de.ollie.viewplanter.core.model.TableData;

@ExtendWith(MockitoExtension.class)
public class TableExtractorTest {

	private static final TableData TABLE_A = new TableData().setName("TableA");
	private static final TableData TABLE_B = new TableData().setName("TableB");
	private static final TableData TABLE_C = new TableData().setName("TableC");
	private static final TableData WITH_C = new TableData().setName("WithC");
	private static final TableData WITH_VIEW_B = new TableData().setName("WithViewB");

	@InjectMocks
	private TableExtractor unitUnderTest;

	@Nested
	class TestsOfMethod_extract_String {

		@Test
		void throwsAnException_passingANullValue() {
			assertThrows(NullPointerException.class, () -> unitUnderTest.extract(null));
		}

		@Test
		void returnsAnEmptyList_passingAnEmptyString() {
			assertTrue(unitUnderTest.extract("").isEmpty());
		}

		@Test
		void returnsAListWithASingleTableData_passingASimpleSelectStatement() {
			assertEquals(List.of(TABLE_A), unitUnderTest.extract("select * from " + TABLE_A.getName()));
		}

		@Test
		void returnsACorrectListWithTableData_passingASelectStatementWithNaturalJoin() {
			assertEquals(List.of(TABLE_A, TABLE_B),
					unitUnderTest.extract("select * from " + TABLE_A.getName() + ", " + TABLE_B.getName()));
		}

		@Test
		void returnsACorrectListWithTableData_passingASelectStatementWithNaturalJoinAndAliases() {
			assertEquals(List.of(TABLE_A, TABLE_B),
					unitUnderTest.extract("select * from " + TABLE_A.getName() + " a, " + TABLE_B.getName() + " b"));
		}

		@Test
		void returnsACorrectListWithTableData_passingASelectStatementWithAThreeTableNaturalJoin() {
			assertEquals(List.of(TABLE_A, TABLE_B, TABLE_C),
					unitUnderTest.extract("select * from " + TABLE_A.getName() + ", " + TABLE_B.getName() + ", "
							+ TABLE_C.getName()));
		}

		@Test
		void returnsACorrectListWithTableData_passingASelectStatementWithAJoin() {
			assertEquals(List.of(TABLE_A, TABLE_B),
					unitUnderTest.extract(
							"select * from " + TABLE_A.getName() + " join " + TABLE_B.getName() + " on a.id = b.id"));
		}

		@Test
		void returnsACorrectListWithTableData_passingASelectStatementWithAJoinAndParenthizes() {
			assertEquals(List.of(TABLE_A, TABLE_B),
					unitUnderTest.extract(
							"select * from (" + TABLE_A.getName() + " join " + TABLE_B.getName() + " on a.id = b.id)"));
		}

		@Test
		void returnsACorrectListWithTableData_passingASelectStatementWithAFromFollowedByASelect() {
			assertEquals(List.of(TABLE_A, TABLE_B),
					unitUnderTest.extract(
							"select * from (select x from " + TABLE_A.getName() + ", " + TABLE_B.getName() + ")"));
		}

		@Test
		void returnsACorrectListWithTableData_passingASelectStatementWithAJoinFollowedByASelect() {
			assertEquals(List.of(TABLE_A, TABLE_B),
					unitUnderTest.extract("select * from (" + TABLE_A.getName() + " join ( select * from "
							+ TABLE_B.getName() + ") on a.id = sid)"));
		}

		@Test
		void returnsACorrectListWithTableData_passingASelectStatementWithAWithStatement() {
			assertEquals(List.of(TABLE_A, TABLE_B, TABLE_C),
					unitUnderTest.extract(
							"with " + WITH_C.getName() + " as (select * from " + TABLE_C.getName() + ") select * from ("
									+ TABLE_A.getName() + " join " + TABLE_B.getName() + " on a.id = b.id)"));
		}

		@Test
		void returnsACorrectListWithTableData_passingASelectStatementWithAWithStatementReferencingAPartOfTheWithStatement() {
			assertEquals(List.of(TABLE_A, TABLE_B, TABLE_C),
					unitUnderTest.extract("with " + WITH_C.getName() + " as (select * from " + TABLE_C.getName() + "), "
							+ WITH_VIEW_B.getName() + " as (select * from " + TABLE_B.getName() + ", "
							+ WITH_C.getName() + ") select * from " + TABLE_A.getName() + ", " + TABLE_B.getName()));
		}

	}

}
