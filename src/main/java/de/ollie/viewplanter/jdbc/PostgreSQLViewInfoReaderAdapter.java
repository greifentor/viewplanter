package de.ollie.viewplanter.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PostgreSQLViewInfoReaderAdapter extends SQLViewInfoReaderAdapter {

	public PostgreSQLViewInfoReaderAdapter() {
		super(new StatementFactory(), new ViewDataResultSetReader() {
			@Override
			public ResultSet readResultSet(Statement statement, String schemeName) throws SQLException {
				String sql =
						"select viewname as " + VIEW_NAME + ", definition as " + VIEW_STATEMENT
								+ " from pg_catalog.pg_views where schemaname = '" + schemeName + "'";
				return statement.executeQuery(sql);
			}
		});
	}

}
