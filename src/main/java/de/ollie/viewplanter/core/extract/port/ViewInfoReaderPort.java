package de.ollie.viewplanter.core.extract.port;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Generated;
import lombok.Getter;
import lombok.experimental.Accessors;

public interface ViewInfoReaderPort {

	@Accessors(chain = true)
	@Data
	@Generated
	public class Parameters {

		@Getter(AccessLevel.NONE)
		private Map<String, Object> values = new HashMap<>();

		public Parameters addValue(String name, Object value) {
			if ((name != null) && (value != null)) {
				values.put(name, value);
			}
			return this;
		}

		public Optional<String> findValueByNameAsString(String name) {
			return Optional.ofNullable((String) values.get(name));
		}

	}

	@Accessors(chain = true)
	@Data
	@Generated
	public static class ViewInfoData {

		private String name;
		private String viewStatement;

	}

	List<ViewInfoData> read(Parameters parameters);

}
