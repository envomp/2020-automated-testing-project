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
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.File;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestWeatherApplication.class})
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

	@BeforeEach
	@SneakyThrows
	void setUp() {
		when(apiRequestService.fetchForecastReportDTO(anyString()))
				.thenReturn(WeatherReportFactory.getThreeHourIntervalWeatherReportDTO());

		when(apiRequestService.fetchCurrentWeatherReportDTO(anyString()))
				.thenReturn(WeatherReportFactory.getWeatherReportDTO());

		when(apiRequestService.fetchForecastReportDTO("x"))
				.thenThrow(new IOException("Bad name"));
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
	void failsOnMissingInput() {
		properties.setInputPath(WeatherReportFactory.getMissingWeatherReportInputLocation());
		Throwable thrown = catchThrowable(() -> consoleService.parseInput());
		assertThat(thrown).isInstanceOf(InvalidInputException.class);
	}

	@Test
	@SneakyThrows
	void readsInputAndWritesWeatherReportMainDetails() {
		properties.setInputPath(WeatherReportFactory.getSampleWeatherReportInputLocation());
		consoleService.parseInput();

		String output = WeatherReportFactory.getSampleWeatherReportOutputLocation();

		WeatherReport report = objectMapper.readValue(new File(output), WeatherReport.class);
		assertEquals("Munich", report.getWeatherReportDetails().getCity());
		assertEquals("48.14,11.58", report.getWeatherReportDetails().getCoordinates());
		assertEquals("Celsius", report.getWeatherReportDetails().getTemperatureUnit());
	}

	@Test
	@SneakyThrows
	void readsInputAndWritesCurrentWeatherReport() {
		properties.setInputPath(WeatherReportFactory.getSampleWeatherReportInputLocation());
		consoleService.parseInput();

		String output = WeatherReportFactory.getSampleWeatherReportOutputLocation();

		WeatherReport report = objectMapper.readValue(new File(output), WeatherReport.class);
		assertEquals("2020-12-01", report.getCurrentWeatherReport().getDate());
		assertEquals(75, report.getCurrentWeatherReport().getHumidity());
		assertEquals(985, report.getCurrentWeatherReport().getPressure());
		assertEquals(-5, report.getCurrentWeatherReport().getTemperature());
	}

	@Test
	@SneakyThrows
	void readsInputAndWritesForecastReport() {
		properties.setInputPath(WeatherReportFactory.getSampleWeatherReportInputLocation());
		consoleService.parseInput();

		String output = WeatherReportFactory.getSampleWeatherReportOutputLocation();

		WeatherReport report = objectMapper.readValue(new File(output), WeatherReport.class);
		assertEquals(3, report.getForecastReport().size());

		assertEquals("2020-12-02", report.getForecastReport().get(0).getDate());
		assertEquals("2020-12-03", report.getForecastReport().get(1).getDate());
		assertEquals("2020-12-04", report.getForecastReport().get(2).getDate());

		for (int i = 0; i < 3; i++) {
			int step = (int) Math.pow(10, i);
			assertEquals(45 * step, report.getForecastReport().get(i).getWeather().getHumidity());
			assertEquals(45 * step, report.getForecastReport().get(i).getWeather().getPressure());
			assertEquals(45 * step, report.getForecastReport().get(i).getWeather().getTemperature());
		}
	}
}