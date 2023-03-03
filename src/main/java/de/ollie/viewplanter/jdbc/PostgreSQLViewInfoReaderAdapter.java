package de.ollie.viewplanter.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import de.ollie.viewplanter.core.extract.port.ViewInfoReaderPort;
import de.ollie.viewplanter.jdbc.model.JDBCConnectionData;

public class PostgreSQLViewInfoReaderAdapter implements ViewInfoReaderPort {

	@Override
	public List<ViewInfoData> read(Parameters parameters) {
		List<ViewInfoData> result = new ArrayList<>();
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			JDBCConnectionData jdbcConnectionData = new JDBCConnectionDataGetter().getFromParameters(parameters);
			Class.forName(jdbcConnectionData.getDriverClassName());
			connection = DriverManager.getConnection(jdbcConnectionData.getUrl(),
					jdbcConnectionData.getUserName(),
					jdbcConnectionData.getPassword());
			statement = connection.createStatement();
			String sql = "select viewname as view_name, definition as view_statement from pg_catalog.pg_views where schemaname = '"
					+ jdbcConnectionData.getSchemeName() + "'";
			resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				result.add(new ViewInfoData().setName(resultSet.getString("view_name"))
						.setViewStatement(resultSet.getString("view_statement")));
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
