package ee.taltech.weather.api.report;

import ee.taltech.weather.model.CityCoordinates;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class CityCoordinatesDTOTest {

	@Test
	void formatted() {
		CityCoordinates dto = CityCoordinates.builder().lat(1.1111f).lon(179).build();

		String actual = dto.formatted();

		assertEquals("1.11,179.00", actual);
	}
}