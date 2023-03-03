package de.ollie.viewplanter.jdbc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.anyString;

import de.ollie.viewplanter.core.extract.port.ViewInfoReaderPort.Parameters;
import de.ollie.viewplanter.jdbc.model.JDBCConnectionData;

@ExtendWith(MockitoExtension.class)
class JDBCConnectionDataGetterTest {

	private static final String DRIVER_CLASS_NAME = "driver name";
	private static final String PASSWORD = "password";
	private static final String SCHEME_NAME = "scheme.name";
	private static final String URL = "url";
	private static final String USER_NAME = "user name";

	@Mock
	private Parameters parameters;

	@InjectMocks
	private JDBCConnectionDataGetter unitUnderTest;

	@Nested
	class TestsOfMethod_getFromParameters_Parameters {

		@Test
		void returnsANullValue_passingANullValue() {
			assertNull(unitUnderTest.getFromParameters(null));
		}

		@Test
		void returnsAnEmptyJDBCConnectionDataObject_passingAnEmptyParametersObject() {
			// Prepare
			JDBCConnectionData expected = new JDBCConnectionData();
			when(parameters.findValueByNameAsString(anyString())).thenReturn(Optional.empty());
			// Run & Check
			assertEquals(expected, unitUnderTest.getFromParameters(parameters));
		}

		private void trainMock() {
			when(parameters.findValueByNameAsString(JDBCConnectionDataGetter.JDBC_DRIVER_CLASS_NAME))
					.thenReturn(Optional.of(DRIVER_CLASS_NAME));
			when(parameters.findValueByNameAsString(JDBCConnectionDataGetter.JDBC_PASSWORD))
					.thenReturn(Optional.of(PASSWORD));
			when(parameters.findValueByNameAsString(JDBCConnectionDataGetter.JDBC_SCHEME_NAME))
					.thenReturn(Optional.of(SCHEME_NAME));
			when(parameters.findValueByNameAsString(JDBCConnectionDataGetter.JDBC_URL)).thenReturn(Optional.of(URL));
			when(parameters.findValueByNameAsString(JDBCConnectionDataGetter.JDBC_USER_NAME))
					.thenReturn(Optional.of(USER_NAME));
		}

		@Test
		void returnsJDBCConnectionDataWithCorrectDriverName_passingAParametersObjectWithDriverName() {
			trainMock();
			assertEquals(DRIVER_CLASS_NAME, unitUnderTest.getFromParameters(parameters).getDriverClassName());
		}

		@Test
		void returnsJDBCConnectionDataWithCorrectPassword_passingAParametersObjectWithPassword() {
			trainMock();
			assertEquals(PASSWORD, unitUnderTest.getFromParameters(parameters).getPassword());
		}

		@Test
		void returnsJDBCConnectionDataWithCorrectURL_passingAParametersObjectWithSchemeName() {
			trainMock();
			assertEquals(SCHEME_NAME, unitUnderTest.getFromParameters(parameters).getSchemeName());
		}

		@Test
		void returnsJDBCConnectionDataWithCorrectURL_passingAParametersObjectWithURL() {
			trainMock();
			assertEquals(URL, unitUnderTest.getFromParameters(parameters).getUrl());
		}

		@Test
		void returnsJDBCConnectionDataWithCorrectURL_passingAParametersObjectWithUserName() {
			trainMock();
			assertEquals(USER_NAME, unitUnderTest.getFromParameters(parameters).getUserName());
		}

	}

}
