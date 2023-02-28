package de.ollie.viewplanter.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import de.ollie.viewplanter.core.extract.port.ViewInfoReaderPort;
import de.ollie.viewplanter.core.extract.port.ViewInfoReaderPort.Parameters;
import de.ollie.viewplanter.core.extract.port.ViewInfoReaderPort.ViewInfoData;
import de.ollie.viewplanter.core.model.ViewData;

@ExtendWith(MockitoExtension.class)
class ViewDependencyInformationCollectorITest {

	private static final String VIEW0_NAME = "view0";
	private static final String VIEW1_NAME = "view1";
	private static final String TABLE0_NAME = "table0";
	private static final String TABLE1_NAME = "table1";
	private static final String TABLE2_NAME = "table2";

	@Mock
	private Parameters parameters;
	@Mock
	private ViewInfoReaderPort viewInfoReaderPort;

	private ViewDependencyInformationCollector unitUnderTest;

	@BeforeEach
	void setUp() {
		unitUnderTest = new ViewDependencyInformationCollector(parameters, viewInfoReaderPort);
	}

	@Test
	void happyRun() {
		// Prepare
		ViewInfoData viewInfo0 = new ViewInfoData().setName(VIEW0_NAME)
				.setViewStatement("SELECT * FROM " + TABLE0_NAME + " JOIN " + TABLE1_NAME);
		ViewInfoData viewInfo1 = new ViewInfoData().setName(VIEW1_NAME)
				.setViewStatement("SELECT * FROM " + VIEW0_NAME + " JOIN " + TABLE2_NAME);
		when(viewInfoReaderPort.read(parameters)).thenReturn(List.of(viewInfo0, viewInfo1));
		// Run
		List<ViewData> result = unitUnderTest.collectViewDependencyInformation();
		// Check
		assertEquals(VIEW0_NAME, result.get(0).getName());
		assertTrue(result.get(0).findByName(TABLE0_NAME).isPresent());
		assertTrue(result.get(0).findByName(TABLE1_NAME).isPresent());
		assertEquals(VIEW1_NAME, result.get(1).getName());
		assertTrue(result.get(1).findByName(TABLE2_NAME).isPresent());
		assertTrue(result.get(1).findByName(VIEW0_NAME).isPresent());
	}

}
