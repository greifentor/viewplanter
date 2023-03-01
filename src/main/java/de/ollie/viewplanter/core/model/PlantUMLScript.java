package de.ollie.viewplanter.core.model;

import java.util.ArrayList;
import java.util.List;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Generated;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
@Generated
public class PlantUMLScript {
	
	@Getter(AccessLevel.NONE)
	@Setter(AccessLevel.NONE)
	private List<String> lines = new ArrayList<>();

	public PlantUMLScript addLines(String... lines) {
		for (String line : lines) {
			this.lines.add(line);
		}
		return this;
	}

	@Override
	public String toString() {
		return lines.stream().reduce((s0, s1) -> s0 + "\n" + s1).orElse("");
	}

}