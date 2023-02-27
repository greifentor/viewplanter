package de.ollie.viewplanter.core;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.StringTokenizer;

public class ToDequeTokenizer {

	ToDequeTokenizer() {
		throw new UnsupportedOperationException("This is a utility class! Don't create any instances there from!");
	}

	public static Deque<String> tokenize(String s) {
		Deque<String> stack = new ArrayDeque<>();
		List<String> l = getTokensAsList(new StringTokenizer(s));
		for (int i = l.size() - 1; i >= 0; i--) {
			stack.push(l.get(i));
		}
		return stack;
	}

	private static List<String> getTokensAsList(StringTokenizer st) {
		List<String> l = new ArrayList<>();
		while (st.hasMoreTokens()) {
			l.add(st.nextToken());
		}
		return l;
	}

}
