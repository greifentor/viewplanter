package de.ollie.viewplanter.core.model;

public interface DatabaseObject {

	public enum Type {

		TABLE,
		VIEW;

	}

	String getName();

	Type getType();

}
