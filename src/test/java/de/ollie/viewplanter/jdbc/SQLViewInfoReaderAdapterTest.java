package de.ollie.viewplanter.jdbc;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SQLViewInfoReaderAdapterTest {

	@Mock
	private ViewDataResultSetReader viewDataResultSetReader;

	@InjectMocks
	private SQLViewInfoReaderAdapter unitUnderTest;

	@Nested
	class TestsOfMethod_read_Parameters {

		@Test
		void throwsAnException_passingANullValue() {
			assertThrows(RuntimeException.class, () -> unitUnderTest.read(null));
		}

	}

}
