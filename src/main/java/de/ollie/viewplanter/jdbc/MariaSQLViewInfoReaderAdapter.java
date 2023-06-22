package de.ollie.viewplanter.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MariaSQLViewInfoReaderAdapter extends SQLViewInfoReaderAdapter {

	public MariaSQLViewInfoReaderAdapter() {
		super(new StatementFactory(), new ViewDataResultSetReader() {
			@Override
			public ResultSet readResultSet(Statement statement, String schemeName) throws SQLException {
				String sql =
						"select TABLE_NAME as " + VIEW_NAME + ", VIEW_DEFINITION as " + VIEW_STATEMENT
								+ " from INFORMATION_SCHEMA.VIEWS where TABLE_SCHEMA = '" + schemeName + "'";
				return statement.executeQuery(sql);
			}
		});
	}
}
