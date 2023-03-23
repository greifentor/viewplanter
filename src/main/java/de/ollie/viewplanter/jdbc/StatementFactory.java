package de.ollie.viewplanter.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import de.ollie.viewplanter.jdbc.model.JDBCConnectionData;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;

public class StatementFactory {

	@Accessors(chain = true)
	@AllArgsConstructor
	@Getter
	public static class StatementData {
		private Connection connection;
		private Statement statement;
	}

	public StatementData createStatement(JDBCConnectionData jdbcConnectionData) throws SQLException {
		Connection connection =
				DriverManager
						.getConnection(
								jdbcConnectionData.getUrl(),
								jdbcConnectionData.getUserName(),
								jdbcConnectionData.getPassword());
		return new StatementData(connection, connection.createStatement());
	}

}
