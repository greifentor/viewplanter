package de.ollie.viewplanter.core;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import de.ollie.viewplanter.core.extract.port.ViewInfoReaderPort;
import de.ollie.viewplanter.core.extract.port.ViewInfoReaderPort.Parameters;

@ExtendWith(MockitoExtension.class)
class ViewPlanterTest {

	private ViewPlanter unitUnderTest;

	@BeforeEach
	void setUp() {
		Parameters parameter = new Parameters();
		unitUnderTest = new ViewPlanter(parameter, new TestViewInfoReaderPort());
	}

	@Nested
	class TestsOfMethod_plant {

		@Test
		void returnsTheCorrectPlantUMLScript() {
			assertEquals(
					"@startuml\n" //
							+ "!define table(x) entity x << (T, white) >>\n" //
							+ "!define view(x) entity x << (V, yellow >>\n" //
							+ "table(TABLE0) {}\n" //
							+ "table(TABLE1) {}\n" //
							+ "table(TABLE2) {}\n" //
							+ "view(SUPER_VIEW) {}\n" //
							+ "view(THE_VIEW) {}\n" //
							+ "SUPER_VIEW --> TABLE2\n" //
							+ "SUPER_VIEW --> THE_VIEW\n" //
							+ "THE_VIEW --> TABLE0\n" //
							+ "THE_VIEW --> TABLE1\n" //
							+ "THE_VIEW --> TABLE2\n" //
							+ "@enduml",
					unitUnderTest.plant().toString());
		}
	}

}

class TestViewInfoReaderPort implements ViewInfoReaderPort {

	@Override
	public List<ViewInfoData> read(Parameters parameters) {
		return List
				.of(
						new ViewInfoData()
								.setName("SUPER_VIEW")
								.setViewStatement("CREATE VIEW SUPER_VIEW AS SELECT * FROM THE_VIEW, TABLE2"),
						new ViewInfoData()
								.setName("THE_VIEW")
								.setViewStatement(
										"CREATE VIEW THE_VIEW AS SELECT * FROM TABLE0 t0, TABLE2 LEFT OUTER JOIN TABLE1 t1 ON t1.t2Id = t0.id WHERE bla = 'bla'"));
	}

}