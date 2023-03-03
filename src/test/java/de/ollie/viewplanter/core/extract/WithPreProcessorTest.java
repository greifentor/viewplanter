package de.ollie.viewplanter.core.extract;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import de.ollie.viewplanter.core.extract.port.ViewInfoReaderPort.ViewInfoData;

@ExtendWith(MockitoExtension.class)
class WithPreProcessorTest {

	@Mock
	private ViewInfoData viewInfoData;

	@InjectMocks
	private WithPreProcessor unitUnderTest;

	@Nested
	class extractWithStatements_ViewInfoData {

		@Test
		void returnsANullValue_passingANullValue() {
			assertNull(unitUnderTest.extractWithStatements(null));
		}

		@Test
		void returnsAnEmptyList_passingAViewInfoDataWithAnEmptyViewStatement() {
			when(viewInfoData.getViewStatement()).thenReturn("");
			assertTrue(unitUnderTest.extractWithStatements(viewInfoData).isEmpty());
		}

		@Test
		void returnsAListWithTheViewStatement_passingAViewInfoDataWithNoWithStatements() {
			when(viewInfoData.getViewStatement()).thenReturn("select * from blubs");
			assertEquals(List.of(viewInfoData), unitUnderTest.extractWithStatements(viewInfoData));
		}

		@Test
		void returnsAListWithTwoViewStatement_passingAViewInfoDataWithAWithStatement() {
			when(viewInfoData.getName()).thenReturn("NAME");
			when(viewInfoData.getViewStatement()).thenReturn("with A as (select * from B) select * from C");
			assertEquals(
					List
							.of(
									viewInfoData,
									new ViewInfoData()
											.setName(viewInfoData.getName())
											.setViewStatement("select * from B")),
					unitUnderTest.extractWithStatements(viewInfoData));
		}

		@Test
		void returnsAListWithThreeViewStatement_passingAViewInfoDataWithAWithStatementAndTwoSelects() {
			ViewInfoData viewInfoData =
					new ViewInfoData()
							.setName("NAME")
							.setViewStatement("with A as (select * from B), D (select * from E) select * from C");
			assertEquals(
					List
							.of(
									viewInfoData,
									new ViewInfoData()
											.setName("A")
											.setViewStatement("select * from B"),
									new ViewInfoData()
											.setName("B")
											.setViewStatement("select * from E")),
					unitUnderTest.extractWithStatements(viewInfoData));
		}

	}

}
