package de.ollie.viewplanter.core;

import de.ollie.viewplanter.core.extract.port.ViewInfoReaderPort.Parameters;
import de.ollie.viewplanter.jdbc.PostgreSQLViewInfoReaderAdapter;

public class ViewPlanter {

	public void run(Parameters parameters) {
		new ViewDependencyInformationCollector(parameters, new PostgreSQLViewInfoReaderAdapter())
				.collectViewDependencyInformation()
				.forEach(System.out::println);
	}

}
