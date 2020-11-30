package ee.taltech.weather.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import ee.taltech.weather.WeatherReportFactory;
import ee.taltech.weather.configuration.Properties;
import ee.taltech.weather.model.report.io.WeatherReport;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
class ConsoleServiceTest {

	@MockBean
	private ApiRequestService apiRequestService;

	@Autowired
	private ConsoleService consoleService;

	@Autowired
	private Properties properties;

	@Autowired
	private ObjectMapper objectMapper;

	private final String EXPECTED_CITY_NAME = "Munich";
	private final String BAD_NAME_ERROR_MESSAGE = "Bad name";

	@BeforeEach
	@SneakyThrows
	void setUp() {
		when(apiRequestService.fetchWeatherReportDTO(anyString()))
				.thenReturn(WeatherReportFactory.getThreeHourIntervalWeatherReportDTO());

		when(apiRequestService.fetchWeatherReportDTO("x"))
				.thenThrow(new IOException(BAD_NAME_ERROR_MESSAGE));
	}

	@AfterEach
	void cleanUp() {
		WeatherReportFactory.getWeatherReportOutputLocation().forEach(output -> new File(output).delete());
		new File(WeatherReportFactory.getBadWeatherReportOutputLocation()).delete();
	}

	@Test
	void readsInputAndWritesToOutputs() {
		properties.setInputPath(WeatherReportFactory.getWeatherReportInputLocation());
		consoleService.parseInput();

		WeatherReportFactory.getWeatherReportOutputLocation().forEach(output -> {
			assertTrue(new File(output).isFile());
			assertTrue(new File(output).delete());
		});
	}

	@Test
	void parsesInputAndWritesToOutputs() {
		properties.setInputPath(WeatherReportFactory.getWeatherReportInputLocation());
		consoleService.parseInput();

		WeatherReportFactory.getWeatherReportOutputLocation().forEach(output -> {
			try {
				WeatherReport report = objectMapper.readValue(new File(output), WeatherReport.class);
				assertEquals(EXPECTED_CITY_NAME, report.getWeatherReportDetails().getCity());
			} catch (IOException e) {
				fail();
			}
		});
	}

	@Test
	@SneakyThrows
	void parsesBadInputAndWritesErrorToOutputs() {
		properties.setInputPath(WeatherReportFactory.getBadWeatherReportInputLocation());
		consoleService.parseInput();

		String output = WeatherReportFactory.getBadWeatherReportOutputLocation();

		try {
			objectMapper.readValue(new File(output), WeatherReport.class);
			fail();
		} catch (IOException e) {
			assertEquals(BAD_NAME_ERROR_MESSAGE, Files.readString(new File(output).toPath()));
		}

	}
}