package de.ollie.viewplanter.core;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import de.ollie.viewplanter.core.extract.port.ViewInfoReaderPort.Parameters;
import de.ollie.viewplanter.jdbc.JDBCConnectionDataGetter;
import de.ollie.viewplanter.jdbc.MariaSQLViewInfoReaderAdapter;

@Disabled
class ViewPlanterForMariaDBSpecialTest {

	@Test
	void t() {
		System.out
				.println(
						new ViewPlanter(
								new Parameters()
										.addValue(
												JDBCConnectionDataGetter.JDBC_DRIVER_CLASS_NAME,
												"org.mariadb.jdbc.Driver")
										.addValue(JDBCConnectionDataGetter.JDBC_PASSWORD, "password")
										.addValue(JDBCConnectionDataGetter.JDBC_SCHEME_NAME, "test")
										.addValue(
												JDBCConnectionDataGetter.JDBC_URL,
												"jdbc:mariadb://localhost:3306/test")
										.addValue(JDBCConnectionDataGetter.JDBC_USER_NAME, "test"),
								new MariaSQLViewInfoReaderAdapter()).plant());
	}

}
