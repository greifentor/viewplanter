package de.ollie.viewplanter.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import de.ollie.viewplanter.core.extract.port.ViewInfoReaderPort;
import de.ollie.viewplanter.jdbc.model.JDBCConnectionData;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SQLViewInfoReaderAdapter implements ViewInfoReaderPort {

	public static final String VIEW_NAME = "view_name";
	public static final String VIEW_STATEMENT = "view_statement";

	private final ViewDataResultSetReader viewDataResultSetReader;

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
			resultSet = viewDataResultSetReader.readResultSet(statement, jdbcConnectionData.getSchemeName());
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
