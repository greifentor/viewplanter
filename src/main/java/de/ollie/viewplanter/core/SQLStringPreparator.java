package de.ollie.viewplanter.core;

public class SQLStringPreparator {

	SQLStringPreparator() {
		throw new UnsupportedOperationException("This is a utility class! Don't create any instances there from!");
	}

	public static String prepareSQLString(String sql) {
		if (sql == null) {
			return null;
		}
		sql = removeBrackets(sql);
		sql = changeCommataToHaveThemAsSeparatedTokens(sql);
		return sql;
	}

	private static String changeCommataToHaveThemAsSeparatedTokens(String sql) {
		return sql.replace(",", " , ");
	}

	private static String removeBrackets(String sql) {
		return sql.replace("(", " ").replace(")", " ");
	}

}
