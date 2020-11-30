package ee.taltech.weather.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import ee.taltech.weather.TestWeatherApplication;
import ee.taltech.weather.WeatherReportFactory;
import ee.taltech.weather.configuration.Properties;
import ee.taltech.weather.exceptions.InvalidInputException;
import ee.taltech.weather.model.report.io.WeatherReport;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { TestWeatherApplication.class })
class ConsoleServiceTest {

	@MockBean
	private ApiRequestService apiRequestService;

	@Autowired
	@InjectMocks
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
	void failsOnInvalidInputFormat() {
		properties.setInputPath("/narnia/input.csv");
		Throwable thrown = catchThrowable(() -> consoleService.parseInput());
		assertThat(thrown).isInstanceOf(InvalidInputException.class);
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
	@SneakyThrows
	void parsesInputAndWritesToOutputs() {
		properties.setInputPath(WeatherReportFactory.getWeatherReportInputLocation());
		consoleService.parseInput();

		for (String output : WeatherReportFactory.getWeatherReportOutputLocation()) {
			WeatherReport report = objectMapper.readValue(new File(output), WeatherReport.class);
			assertEquals(EXPECTED_CITY_NAME, report.getWeatherReportDetails().getCity());
		}
	}

	@Test
	@SneakyThrows
	void parsesBadInputAndWritesErrorToOutputs() {
		properties.setInputPath(WeatherReportFactory.getBadWeatherReportInputLocation());
		consoleService.parseInput();

		String output = WeatherReportFactory.getBadWeatherReportOutputLocation();
		assertEquals(BAD_NAME_ERROR_MESSAGE, Files.readString(new File(output).toPath()));
	}
}