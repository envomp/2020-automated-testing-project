package ee.taltech.weather.io.report;

import com.fasterxml.jackson.databind.ObjectMapper;
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
		WeatherReport report = mapper.readValue(json, WeatherReport.class); // assert successful parsing
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
}