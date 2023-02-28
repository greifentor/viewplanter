package de.ollie.viewplanter.jdbc;

import de.ollie.viewplanter.core.extract.port.ViewInfoReaderPort.Parameters;
import de.ollie.viewplanter.jdbc.model.JDBCConnectionData;

public class JDBCConnectionDataGetter {

	public static final String JDBC_DRIVER_CLASS_NAME = "jdbc.driver.class.name";
	public static final String JDBC_PASSWORD = "jdbc.password";
	public static final String JDBC_SCHEME_NAME = "jdbc.scheme.name";
	public static final String JDBC_USER_NAME = "jdbc.user.name";
	public static final String JDBC_URL = "jdbc.url";

	public JDBCConnectionData getFromParameters(Parameters parameters) {
		if (parameters == null) {
			return null;
		}
		JDBCConnectionData jdbcConnectionData = new JDBCConnectionData();
		parameters.findValueByNameAsString(JDBC_DRIVER_CLASS_NAME).ifPresent(jdbcConnectionData::setDriverClassName);
		parameters.findValueByNameAsString(JDBC_PASSWORD).ifPresent(jdbcConnectionData::setPassword);
		parameters.findValueByNameAsString(JDBC_SCHEME_NAME).ifPresent(jdbcConnectionData::setSchemeName);
		parameters.findValueByNameAsString(JDBC_URL).ifPresent(jdbcConnectionData::setUrl);
		parameters.findValueByNameAsString(JDBC_USER_NAME).ifPresent(jdbcConnectionData::setUserName);
		return jdbcConnectionData;
	}

}
