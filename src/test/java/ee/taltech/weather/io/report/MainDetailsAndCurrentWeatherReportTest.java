package ee.taltech.weather.io.report;

import ee.taltech.weather.WeatherReportFactory;
import ee.taltech.weather.model.report.api.WeatherReportDTO;
import ee.taltech.weather.model.report.io.CurrentWeatherReport;
import ee.taltech.weather.model.report.io.WeatherReport;
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
	void reportIsRoundedProperty() {
		WeatherReportDTO dto = WeatherReportFactory.getWeatherReportDTO();

		CurrentWeatherReport report = CurrentWeatherReport.from(dto);

		assertEquals("2020-12-01", report.getDate());
		assertEquals(-5, report.getTemperature());
		assertEquals(75, report.getHumidity());
		assertEquals(985, report.getPressure());
	}

	@Test
	@SneakyThrows
	void fromWeatherReportDtoCurrentReport() {
		WeatherReport report = WeatherReportFactory.getWeatherReport();

		assertEquals("2020-12-01", report.getCurrentWeatherReport().getDate());
		assertEquals(90, report.getCurrentWeatherReport().getHumidity());
		assertEquals(1015, report.getCurrentWeatherReport().getPressure());
		assertEquals(275, report.getCurrentWeatherReport().getTemperature());
	}
}