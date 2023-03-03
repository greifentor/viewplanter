package de.ollie.viewplanter.core.extract;

public class WithStatementCutter {

	public String cutWithStatement(String statement) {
		if (statement == null) {
			return null;
		} else if (statement.isEmpty()) {
			return "";
		}
		if (!statement.toUpperCase().contains("WITH")) {
			return statement;
		}
		String s = statement;
		int pos = 0;
		int parenthesisCounter = 1;
		while (s.charAt(pos) != '(') {
			pos++;
		}
		do {
			while ((parenthesisCounter != 0) && (s.charAt(pos) != ')')) {
				pos++;
			}
			s = s.substring(pos + 1);
			pos = 0;
		} while (!s.trim().toUpperCase().startsWith("SELECT"));
		return s.trim();
	}

}
