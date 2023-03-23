package de.ollie.viewplanter.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import de.ollie.viewplanter.core.extract.port.ViewInfoReaderPort;
import de.ollie.viewplanter.jdbc.StatementFactory.StatementData;
import de.ollie.viewplanter.jdbc.model.JDBCConnectionData;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SQLViewInfoReaderAdapter implements ViewInfoReaderPort {

	public static final String VIEW_NAME = "view_name";
	public static final String VIEW_STATEMENT = "view_statement";

	private final StatementFactory statementFactory;
	private final ViewDataResultSetReader viewDataResultSetReader;

	@Override
	public List<ViewInfoData> read(Parameters parameters) {
		List<ViewInfoData> result = new ArrayList<>();
		Connection connection = null;
		StatementData statementData = null;
		ResultSet resultSet = null;
		try {
			JDBCConnectionData jdbcConnectionData = new JDBCConnectionDataGetter().getFromParameters(parameters);
			Class.forName(jdbcConnectionData.getDriverClassName());
			statementData = statementFactory.createStatement(jdbcConnectionData);
			resultSet =
					viewDataResultSetReader
							.readResultSet(statementData.getStatement(), jdbcConnectionData.getSchemeName());
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
				statementData.getStatement().close();
			} catch (Exception e) {
				throw new RuntimeException("Error while closing the statement: " + e.getMessage());
			}
			try {
				statementData.getConnection().close();
			} catch (Exception e) {
				throw new RuntimeException("Error while closing the connection: " + e.getMessage());
			}
		}
		return result;
	}
}
