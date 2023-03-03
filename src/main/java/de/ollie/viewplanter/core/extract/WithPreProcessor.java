package de.ollie.viewplanter.core.extract;

import java.util.ArrayList;
import java.util.List;

import de.ollie.viewplanter.core.extract.port.ViewInfoReaderPort.ViewInfoData;

public class WithPreProcessor {

	private WithStatementCutter withStatementCutter = new WithStatementCutter();

	public List<ViewInfoData> extractWithStatements(ViewInfoData viewInfoData) {
		if (viewInfoData == null) {
			return null;
		}
		List<ViewInfoData> result = new ArrayList<>();
		if (isViewStatementNullOrEmpty(viewInfoData)) {
			return result;
		} else if (!viewInfoData.getViewStatement().toUpperCase().contains("WITH")) {
			result.add(viewInfoData);
		} else {
			result.add(viewInfoData);
			result.addAll(extractWithToSeparateStatements(viewInfoData));
			viewInfoData.setViewStatement(withStatementCutter.cutWithStatement(viewInfoData.getViewStatement()));
		}
		return result;
	}

	private boolean isViewStatementNullOrEmpty(ViewInfoData viewInfoData) {
		return (viewInfoData.getViewStatement() == null) || viewInfoData.getViewStatement().isEmpty();
	}
	
	private List<ViewInfoData> extractWithToSeparateStatements(ViewInfoData viewInfoData) {
		String statement = viewInfoData.getViewStatement();
		return findWithStatements(statement);
	}
	
	private List<ViewInfoData> findWithStatements(String statement) {
		List<ViewInfoData> result = new ArrayList<>();
		String s = statement.toUpperCase();
		int start = s.indexOf("AS");
		int end = start;
		while (s.charAt(end) != '(') {
			end++;
		}
		start = end + 1;
		int parenthesisCounter = 1;
		while ((parenthesisCounter != 0) && (s.charAt(end) != ')')) {
			end++;
			if (s.charAt(end) == '(') {
				parenthesisCounter++;
			} else if (s.charAt(end) == ')') {
				parenthesisCounter--;
			}
		}
		result.add(new ViewInfoData().setName("NAME").setViewStatement(statement.substring(start, end)));

		return result;
	}

}
