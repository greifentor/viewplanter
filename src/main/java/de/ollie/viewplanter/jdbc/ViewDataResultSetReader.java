package de.ollie.viewplanter.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public interface ViewDataResultSetReader {

	/**
	 * @param statement  A statement to access the database.
	 * @param schemeName The name of the scheme whose view information are to read.
	 * @return A result set with fields "view_name" (String) which contains the name of a view and "view_statement" with
	 *         the content of the view.
	 * @throws SQLException In case of something went wrong while creating the result set.
	 */
	ResultSet readResultSet(Statement statement, String schemeName) throws SQLException;

}
