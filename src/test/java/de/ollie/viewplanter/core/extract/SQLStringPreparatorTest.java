package de.ollie.viewplanter.core.extract;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import de.ollie.viewplanter.core.extract.SQLStringPreparator;

@ExtendWith(MockitoExtension.class)
class SQLStringPreparatorTest {

	@Nested
	class TestsOfConstructor {

		@Test
		void throwsAnExceptionWhenCalled() {
			assertThrows(UnsupportedOperationException.class, () -> new SQLStringPreparator());
		}

	}

	@Nested
	class TestsOfMethod_prepareSQLString_String {

		@Test
		void returnsANullValue_passingANullValue() {
			assertNull(SQLStringPreparator.prepareSQLString(null));
		}

		@Test
		void changesParenthesisToSpaces() {
			assertEquals("  ", SQLStringPreparator.prepareSQLString("()"));
		}

		@Test
		void setsASpaceBeforeAndAfterEachComma() {
			assertEquals(" ,  , ", SQLStringPreparator.prepareSQLString(",,"));
		}

	}

}
