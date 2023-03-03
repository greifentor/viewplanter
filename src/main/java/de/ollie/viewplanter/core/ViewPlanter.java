package de.ollie.viewplanter.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import de.ollie.viewplanter.core.extract.port.ViewInfoReaderPort;
import de.ollie.viewplanter.core.extract.port.ViewInfoReaderPort.Parameters;
import de.ollie.viewplanter.core.model.DatabaseObject;
import de.ollie.viewplanter.core.model.DatabaseObject.Type;
import de.ollie.viewplanter.core.model.PlantUMLScript;
import de.ollie.viewplanter.core.model.ViewData;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ViewPlanter {

	private static final String START_UML_TAG = "@startuml";
	private static final String END_UML_TAG = "@enduml";

	private final Parameters parameters;
	private final ViewInfoReaderPort viewInfoReaderPort;

	private List<ViewData> viewDatas;

	public synchronized PlantUMLScript plant() {
		collectViewDependencyInformation();
		return convertViewDependencyInformationToPlantUMLScript();
	}

	private void collectViewDependencyInformation() {
		viewDatas =
				new ViewDependencyInformationCollector(parameters, viewInfoReaderPort)
						.collectViewDependencyInformation();
	}

	private PlantUMLScript convertViewDependencyInformationToPlantUMLScript() {
		PlantUMLScript script = new PlantUMLScript();
		script.addLines(START_UML_TAG);
		script.addLines(databaseObjectsDefinitions());
		script.addLines(viewAndTableDefinitions());
		script.addLines(relationsBetweenTheViewsAndTables());
		script.addLines(END_UML_TAG);
		return script;
	}

	private String[] databaseObjectsDefinitions() {
		return new String[] {
				"!define table(x) entity x << (T, white) >>",
				"!define view(x) entity x << (V, yellow >>" };
	}

	private String[] viewAndTableDefinitions() {
		Set<String> viewAndTableNames =
				viewDatas
						.stream()
						.map(viewData -> createViewDefinition(viewData.getName()))
						.collect(Collectors.toSet());
		viewAndTableNames
				.addAll(
						viewDatas
								.stream()
								.flatMap(viewData -> Stream.of(viewData.getReferencedObjects()))
								.map(referencedObject -> createMatchingDefinition(referencedObject))
								.collect(Collectors.toSet()));
		return viewAndTableNames
				.stream()
				.sorted((s0, s1) -> s0.compareTo(s1))
				.collect(Collectors.toList())
				.toArray(new String[0]);
	}

	private String createViewDefinition(String name) {
		return "entity (" + name + ") << (V, yellow >>";
	}

	private String createMatchingDefinition(DatabaseObject dbo) {
		if (dbo.getType() == Type.VIEW) {
			return createViewDefinition(dbo.getName());
		}
		return createTableDefinition(dbo.getName());
	}

	private String createTableDefinition(String name) {
		return "entity (" + name + ") << (T, white) >>";
	}

	private String[] relationsBetweenTheViewsAndTables() {
		List<String> relations = new ArrayList<>();
		viewDatas.forEach(viewData -> relations.addAll(createRelations(viewData)));
		return relations
				.stream()
				.sorted((s0, s1) -> s0.compareTo(s1))
				.collect(Collectors.toList())
				.toArray(new String[relations.size()]);
	}

	private List<String> createRelations(ViewData viewData) {
		return List
				.of(viewData.getReferencedObjects())
				.stream()
				.map(referencedObject -> viewData.getName() + " --> " + referencedObject.getName())
				.collect(Collectors.toList());
	}

}