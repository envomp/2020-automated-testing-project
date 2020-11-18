package ee.taltech.weather.io.report;

import ee.taltech.weather.api.report.WeatherReportDTO;
import ee.taltech.weather.WeatherReportFactory;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MainDetailsAndCurrentWeatherReportTest {

	@Test
	@SneakyThrows
	void fromWeatherReportDtoReportDetails() {
		WeatherReport report = WeatherReportFactory.getWeatherReport();

		assertEquals("Munich", report.getWeatherReportDetails().getCity());
		assertEquals("48.14,11.58", report.getWeatherReportDetails().getCoordinates());
		assertEquals("Celsius", report.getWeatherReportDetails().getTemperatureUnit());
	}

	@Test
	void from() {
		WeatherReportDTO dto = WeatherReportFactory.getWeatherReportDTO();

		CurrentWeatherReport report = CurrentWeatherReport.from(dto);

		assertEquals(-5, report.getTemperature());
		assertEquals(75, report.getHumidity());
		assertEquals(985, report.getPressure());
		assertEquals("2019-10-01", report.getDate());
	}

	@Test
	@SneakyThrows
	void fromWeatherReportDtoCurrentReport() {
		WeatherReport report = WeatherReportFactory.getWeatherReport();

		assertEquals("2020-11-17", report.getCurrentWeatherReport().getDate());
		assertEquals(87, report.getCurrentWeatherReport().getHumidity());
		assertEquals(1029, report.getCurrentWeatherReport().getPressure());
		assertEquals(278, report.getCurrentWeatherReport().getTemperature());
	}
}