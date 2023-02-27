package de.ollie.viewplanter.core.extract;

import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import de.ollie.viewplanter.core.model.TableData;

public class TableExtractor {

	private static final String FROM = "FROM";
	private static final String JOIN = "JOIN";

	public List<TableData> extract(String sql) {
		List<TableData> tables = new ArrayList<>();
		sql = SQLStringPreparator.prepareSQLString(sql);
		Deque<String> stack = ToDequeTokenizer.tokenize(sql);
		while (!stack.isEmpty()) {
			String token = stack.pop();
			if (FROM.equalsIgnoreCase(token)) {
				tables.add(newTableData(stack.pop()));
				int distance = DistanceToNextCommaCalculator.calc(stack);
				while (!stack.isEmpty() && (distance < 2)) {
					stack.pop();
					if (distance == 1) {
						stack.pop();
					}
					tables.add(newTableData(stack.pop()));
					distance = DistanceToNextCommaCalculator.calc(stack);
				}
			} else if (JOIN.equalsIgnoreCase(token)) {
				tables.add(newTableData(stack.pop()));
			}
		}
		return tables;
	}

	private TableData newTableData(String tableName) {
		return new TableData().setName(tableName);
	}

}
