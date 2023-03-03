package de.ollie.viewplanter.core.extract;

import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class WithNameExtractor {

	private static final String AS = "AS";
	private static final String SELECT = "SELECT";
	private static final String WITH = "WITH";

	WithNameExtractor() {
		throw new UnsupportedOperationException("This is a utility class! Don't create any instances there from!");
	}

	public static List<String> extractWithStatementNames(String statement) {
		List<String> result = new ArrayList<>();
		if (isViewStatementNullOrEmpty(statement) || !statement.toUpperCase().contains(WITH)) {
			return result;
		}
		return extractWithStatementNamesFromSQLStatement(statement);
	}

	private static boolean isViewStatementNullOrEmpty(String statement) {
		return (statement == null) || statement.isEmpty();
	}

	private static List<String> extractWithStatementNamesFromSQLStatement(String statement) {
		statement = statement.replace(")", " ) ").replace("(", " ( ").replace(",", " , ");
		Deque<String> tokens = ToDequeTokenizer.tokenize(statement);
		List<String> result = new ArrayList<>();
		while (!tokens.isEmpty() && !tokens.pop().equalsIgnoreCase(WITH))
			;
		do {
			result.add(tokens.pop());
			while (!tokens.isEmpty() && !tokens.pop().equalsIgnoreCase(AS))
				;
			tokens.pop();
			int parenthesis = 1;
			while (!tokens.isEmpty() && (parenthesis != 0)) {
				String token = tokens.pop();
				if (token.equals(")")) {
					parenthesis--;
				} else if (token.equals("(")) {
					parenthesis++;
				}
			}
		} while (!tokens.isEmpty() && !tokens.pop().equalsIgnoreCase(SELECT));
		return result;
	}

}
