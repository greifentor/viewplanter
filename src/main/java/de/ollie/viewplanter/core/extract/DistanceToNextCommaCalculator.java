package de.ollie.viewplanter.core.extract;

import java.util.Deque;
import java.util.List;

public class DistanceToNextCommaCalculator {

	DistanceToNextCommaCalculator() {
		throw new UnsupportedOperationException("This is a utility class! Don't create any instances there from!");
	}

	public static int calc(Deque<String> deque) {
		List<String> l = List.of(deque.toArray(new String[0]));
		for (int i = 0, leni = l.size(); i < leni; i++) {
			if (",".equals(l.get(i))) {
				return i;
			}
		}
		return Integer.MAX_VALUE;
	}

}