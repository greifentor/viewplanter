package de.ollie.viewplanter.core.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Generated;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
@Generated
public class TableData implements DatabaseObject {

	private String name;
	@Setter(AccessLevel.NONE)
	private Type type = Type.TABLE;

}