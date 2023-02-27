package de.ollie.viewplanter.core.extract;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Deque;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import de.ollie.viewplanter.core.extract.ToDequeTokenizer;

public class ToDequeTokenizerTest {

	@Nested
	class TestsOfConstructor {

		@Test
		void throwsAnExceptionWhenCalled() {
			assertThrows(UnsupportedOperationException.class, () -> new ToDequeTokenizer());
		}

	}

	@Nested
	class TestsOfMethod_tokenize_String {

		@Test
		void throwsAnException_passingANullValue() {
			assertThrows(NullPointerException.class, () -> ToDequeTokenizer.tokenize(null));
		}

		@Test
		void returnsAEmptyDeque_passingAEmptyString() {
			assertTrue(ToDequeTokenizer.tokenize("").isEmpty());
		}

		@Test
		void returnsADequeWithTokens_passingAStringWithContent() {
			// Prepare
			String s0 = "s0";
			String s1 = "s1";
			String s2 = "s2";
			String s = s0 + " " + s1 + " " + s2;
			// Run
			Deque<String> returned = ToDequeTokenizer.tokenize(s);
			// Run & Check
			assertEquals(s0, returned.pop());
			assertEquals(s1, returned.pop());
			assertEquals(s2, returned.pop());
		}

	}

}
