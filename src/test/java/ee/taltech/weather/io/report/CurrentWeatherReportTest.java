package ee.taltech.weather.io.report;

import ee.taltech.weather.api.report.WeatherReportDTO;
import ee.taltech.weather.api.report.WeatherReportDtoFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CurrentWeatherReportTest {

	@Test
	void from() {
		WeatherReportDTO dto = WeatherReportDtoFactory.getWeatherReportDTO();

		CurrentWeatherReport report = CurrentWeatherReport.from(dto);

		assertEquals(-5, report.getTemperature());
		assertEquals(75, report.getHumidity());
		assertEquals(985, report.getPressure());
		assertEquals("2019-10-01", report.getDate());
	}
}