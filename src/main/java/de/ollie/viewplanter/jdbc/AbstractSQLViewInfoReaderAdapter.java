package de.ollie.viewplanter.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import de.ollie.viewplanter.core.extract.port.ViewInfoReaderPort;
import de.ollie.viewplanter.jdbc.model.JDBCConnectionData;

abstract class AbstractSQLViewInfoReaderAdapter implements ViewInfoReaderPort {

	static final String VIEW_NAME = "view_name";
	static final String VIEW_STATEMENT = "view_statement";

	/**
	 * @param statement  A statement to access the database.
	 * @param schemeName The name of the scheme whose view information are to read.
	 * @return A result set with fields "view_name" (String) which contains the name of a view and "view_statement" with
	 *         the content of the view.
	 * @throws SQLException In case of something went wrong while creating the result set.
	 */
	abstract ResultSet getReadViewDataResultSet(Statement statement, String schemeName) throws SQLException;

	@Override
	public List<ViewInfoData> read(Parameters parameters) {
		List<ViewInfoData> result = new ArrayList<>();
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			JDBCConnectionData jdbcConnectionData = new JDBCConnectionDataGetter().getFromParameters(parameters);
			Class.forName(jdbcConnectionData.getDriverClassName());
			connection =
					DriverManager
							.getConnection(
									jdbcConnectionData.getUrl(),
									jdbcConnectionData.getUserName(),
									jdbcConnectionData.getPassword());
			statement = connection.createStatement();
			resultSet = getReadViewDataResultSet(statement, jdbcConnectionData.getSchemeName());
			while (resultSet.next()) {
				result
						.add(
								new ViewInfoData()
										.setName(resultSet.getString(VIEW_NAME))
										.setViewStatement(resultSet.getString(VIEW_STATEMENT)));
			}
		} catch (Exception e) {
			throw new RuntimeException("There went something wrong while reading the view data: " + e.getMessage());
		} finally {
			try {
				resultSet.close();
			} catch (Exception e) {
				throw new RuntimeException("Error while closing the result set: " + e.getMessage());
			}
			try {
				statement.close();
			} catch (Exception e) {
				throw new RuntimeException("Error while closing the statement: " + e.getMessage());
			}
			try {
				connection.close();
			} catch (Exception e) {
				throw new RuntimeException("Error while closing the connection: " + e.getMessage());
			}
		}
		return result;
	}
}
