package de.ollie.viewplanter.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PostgreSQLViewInfoReaderAdapter extends AbstractSQLViewInfoReaderAdapter {

	@Override
	ResultSet getReadViewDataResultSet(Statement statement, String schemeName) throws SQLException {
		String sql =
				"select viewname as " + VIEW_NAME + ", definition as " + VIEW_STATEMENT
						+ " from pg_catalog.pg_views where schemaname = '" + schemeName + "'";
		return statement.executeQuery(sql);
	}

}
