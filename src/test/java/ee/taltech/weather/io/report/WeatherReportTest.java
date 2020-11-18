package ee.taltech.weather.io.report;

import com.fasterxml.jackson.databind.ObjectMapper;
import ee.taltech.weather.api.report.ThreeHourIntervalWeatherReportDTO;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WeatherReportTest {

	@Test
	@SneakyThrows
	void parseWeatherReportDtoInputStreamSuccessfulResponse() {
		ClassLoader classloader = Thread.currentThread().getContextClassLoader();
		InputStream stream = classloader.getResourceAsStream("sample_passed_response.json");
		ObjectMapper mapper = new ObjectMapper();
		String json = WeatherReport.parseWeatherReportDtoInputStream(mapper, stream);

		mapper.readValue(json, WeatherReport.class);

		// parsing was successful
	}

	@Test
	@SneakyThrows
	void parseWeatherReportDtoInputStreamUnsuccessfulResponse() {
		ClassLoader classloader = Thread.currentThread().getContextClassLoader();
		InputStream stream = classloader.getResourceAsStream("sample_failed_response.json");
		ObjectMapper mapper = new ObjectMapper();

		String response = WeatherReport.parseWeatherReportDtoInputStream(mapper, stream);

		assertEquals("city not found", response);
	}

	@Test
	@SneakyThrows
	void fromWeatherReportDtoCurrentReport() {
		WeatherReport report = getWeatherReport();

		assertEquals("2020-11-17", report.getCurrentWeatherReport().getDate());
		assertEquals(87, report.getCurrentWeatherReport().getHumidity());
		assertEquals(1029, report.getCurrentWeatherReport().getPressure());
		assertEquals(278, report.getCurrentWeatherReport().getTemperature());
	}

	@Test
	@SneakyThrows
	void fromWeatherReportDtoReportDetails() {
		WeatherReport report = getWeatherReport();

		assertEquals("Munich", report.getWeatherReportDetails().getCity());
		assertEquals("48.14,11.58", report.getWeatherReportDetails().getCoordinates());
		assertEquals("Celsius", report.getWeatherReportDetails().getTemperatureUnit());
	}

	@Test
	@SneakyThrows
	void fromWeatherReportDtoForecast() {
		WeatherReport report = getWeatherReport();

		assertEquals(3, report.getForecastReport().size());
		assertEquals("2020-11-18", report.getForecastReport().get(0).getDate());
		assertEquals(84, report.getForecastReport().get(0).getWeather().getHumidity());
		assertEquals(1030, report.getForecastReport().get(0).getWeather().getPressure());
		assertEquals(278, report.getForecastReport().get(0).getWeather().getTemperature());
	}

	private static WeatherReport getWeatherReport() throws java.io.IOException {
		ClassLoader classloader = Thread.currentThread().getContextClassLoader();
		InputStream stream = classloader.getResourceAsStream("sample_passed_response.json");
		ObjectMapper mapper = new ObjectMapper();
		ThreeHourIntervalWeatherReportDTO dto = mapper.readValue(stream, ThreeHourIntervalWeatherReportDTO.class);
		return WeatherReport.fromWeatherReportDTO(dto);
	}
}