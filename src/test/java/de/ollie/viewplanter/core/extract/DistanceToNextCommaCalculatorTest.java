package de.ollie.viewplanter.core.extract;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayDeque;
import java.util.Deque;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import de.ollie.viewplanter.core.extract.DistanceToNextCommaCalculator;

@ExtendWith(MockitoExtension.class)
class DistanceToNextCommaCalculatorTest {

	private Deque<String> deque;

	@BeforeEach
	void setUp() {
		deque = new ArrayDeque<>();
	}

	@Nested
	class TestsOfConstructor {

		@Test
		void throwsAnExceptionWhenCalled() {
			assertThrows(UnsupportedOperationException.class, () -> new DistanceToNextCommaCalculator());
		}

	}

	@Nested
	class TestsOfMethod_calc_Deque {

		@Test
		void throwsAnException_passingANullValue() {
			assertThrows(NullPointerException.class, () -> DistanceToNextCommaCalculator.calc(null));
		}

		@Test
		void returnsIntegerMAX_VALUE_passingAnEmptyDeque() {
			assertEquals(Integer.MAX_VALUE, DistanceToNextCommaCalculator.calc(deque));
		}

		@Test
		void returnsIntegerMAX_VALUE_havingNoCommataStringsInPassedDeque() {
			deque.push(";op");
			assertEquals(Integer.MAX_VALUE, DistanceToNextCommaCalculator.calc(deque));
		}

		@Test
		void returnsZero_havingACommataStringsOnlyInDeque() {
			deque.push(",");
			assertEquals(0, DistanceToNextCommaCalculator.calc(deque));
		}

		@Test
		void returnsOne_havingACommataStringsAsSecondStringInDeque() {
			deque.push(",");
			deque.push(";op");
			assertEquals(1, DistanceToNextCommaCalculator.calc(deque));
		}

	}

}
