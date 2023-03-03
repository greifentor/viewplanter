package de.ollie.viewplanter.core.extract;

import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import de.ollie.viewplanter.core.model.TableData;

public class TableExtractor {

	private static final String FROM = "FROM";
	private static final String JOIN = "JOIN";
	private static final String SELECT = "SELECT";

	public List<TableData> extract(String sql) {
		return extractTables(sql);
	}

	private List<TableData> extractTables(String sql) {
		Set<TableData> tables = new HashSet<>();
		List<String> withNames = WithNameExtractor.extractWithStatementNames(sql);
		sql = SQLStringPreparator.prepareSQLString(sql);
		Deque<String> stack = ToDequeTokenizer.tokenize(sql);
		while (!stack.isEmpty()) {
			String token = stack.pop();
			if (FROM.equalsIgnoreCase(token)) {
				addTable(tables, stack.pop());
				int distance = DistanceToNextCommaCalculator.calc(stack);
				while (!stack.isEmpty() && (distance < 2)) {
					stack.pop();
					if (distance == 1) {
						stack.pop();
					}
					addTable(tables, stack.pop());
					distance = DistanceToNextCommaCalculator.calc(stack);
				}
			} else if (JOIN.equalsIgnoreCase(token)) {
				addTable(tables, stack.pop());
			}
		}
		return tables.stream()
				.filter(t -> !withNames.contains(t.getName()))
				.sorted((t0, t1) -> t0.getName().compareTo(t1.getName()))
				.collect(Collectors.toList());
	}

	private TableData newTableData(String tableName) {
		return new TableData().setName(tableName);
	}

	private void addTable(Set<TableData> tables, String tableName) {
		if (!SELECT.equalsIgnoreCase(tableName)) {
			tables.add(newTableData(tableName));
		}
	}

}
