package de.ollie.viewplanter.core.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Generated;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
@Generated
public class ViewData implements DatabaseObject {

	private String name;
	@Setter(AccessLevel.NONE)
	private Type type = Type.VIEW;
	@Getter(AccessLevel.NONE)
	@Setter(AccessLevel.NONE)
	private List<DatabaseObject> referencedObjects = new ArrayList<>();

	public ViewData addReferencedObjects(List<? extends DatabaseObject> databaseObjects) {
		if (databaseObjects != null) {
			addReferencedObjects(databaseObjects.toArray(new DatabaseObject[databaseObjects.size()]));
		}
			return this;
	}

	public ViewData addReferencedObjects(DatabaseObject... databaseObjects) {
		for (DatabaseObject databaseObject : databaseObjects) {
			referencedObjects.add(databaseObject);
		}
		return this;
	}

	public DatabaseObject[] getReferencedObjects() {
		return referencedObjects.toArray(new DatabaseObject[referencedObjects.size()]);
	}

	public ViewData removeReferencedObject(DatabaseObject databaseObject) {
		if (databaseObject != null) {
			referencedObjects.remove(databaseObject);
		}
		return this;
	}

	public Optional<DatabaseObject> findByName(String name) {
		return referencedObjects.stream().filter(dbo -> dbo.getName().equals(name)).findFirst();
	}

}