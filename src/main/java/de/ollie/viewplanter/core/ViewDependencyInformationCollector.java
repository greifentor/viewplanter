package de.ollie.viewplanter.core;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import de.ollie.viewplanter.core.extract.TableExtractor;
import de.ollie.viewplanter.core.extract.port.ViewInfoReaderPort;
import de.ollie.viewplanter.core.extract.port.ViewInfoReaderPort.Parameters;
import de.ollie.viewplanter.core.extract.port.ViewInfoReaderPort.ViewInfoData;
import de.ollie.viewplanter.core.model.TableData;
import de.ollie.viewplanter.core.model.ViewData;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ViewDependencyInformationCollector {

	private final Parameters parameters;
	private final ViewInfoReaderPort viewInfoReaderPort;

	private Map<String, ViewData> foundViews = new HashMap<>();
	private List<ViewInfoData> viewInfos;

	public synchronized List<ViewData> collectViewDependencyInformation() {
		clearFoundViews();
		readViewInfos();
		forEachReadViewInfo(this::processToViewData);
		setViewReferences();
		return convertFoundViewsToSortedList();
	}

	private void clearFoundViews() {
		foundViews.clear();
	}

	private void readViewInfos() {
		viewInfos = viewInfoReaderPort.read(parameters);
	}

	private void forEachReadViewInfo(Consumer<ViewInfoData> processor) {
		viewInfos.forEach(processor::accept);
	}

	private void processToViewData(ViewInfoData viewInfo) {
		List<TableData> referencedTables = new TableExtractor().extract(viewInfo.getViewStatement());
		foundViews
				.put(
						viewInfo.getName(),
						new ViewData().setName(viewInfo.getName()).addReferencedObjects(referencedTables));
	}

	private void setViewReferences() {
		for (String keyToChangeFor : foundViews.keySet()) {
			for (String key : foundViews.keySet()) {
				ViewData view = foundViews.get(key);
				view.findByName(keyToChangeFor).ifPresent(dbo -> {
					view.removeReferencedObject(dbo);
					view.addReferencedObjects(foundViews.get(keyToChangeFor));
				});
			}
		}
	}

	private List<ViewData> convertFoundViewsToSortedList() {
		return foundViews
				.entrySet()
				.stream()
				.map(Entry::getValue)
				.sorted((v0, v1) -> v0.getName().compareTo(v1.getName()))
				.collect(Collectors.toList());
	}

}
