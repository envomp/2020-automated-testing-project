package ee.taltech.weather.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import ee.taltech.weather.TestWeatherApplication;
import ee.taltech.weather.WeatherReportFactory;
import ee.taltech.weather.configuration.Properties;
import ee.taltech.weather.model.report.io.WeatherReport;
import ee.taltech.weather.service.ConsoleService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.File;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestWeatherApplication.class})
public class IntegrationTest {

	@Autowired
	private ConsoleService consoleService;
	@Autowired
	private Properties properties;
	@Autowired
	private ObjectMapper objectMapper;

	@AfterEach
	void cleanUp() {
		WeatherReportFactory.getWeatherReportOutputLocation().forEach(output -> new File(output).delete());
		new File(WeatherReportFactory.getBadWeatherReportOutputLocation()).delete();
	}

	@Test
	@SneakyThrows
	void parsesInputAndWritesWeatherReportMainDetails() {
		properties.setInputPath(WeatherReportFactory.getWeatherReportInputLocation());
		consoleService.parseInput();

		List<String> weatherReportOutputLocation = WeatherReportFactory.getWeatherReportOutputLocation();
		for (int i = 0; i < weatherReportOutputLocation.size(); i++) {
			String output = weatherReportOutputLocation.get(i);
			WeatherReport report = objectMapper.readValue(new File(output), WeatherReport.class);
			assertEquals(List.of("Tallinn", "Tartu", "Pärnu", "Jõgeva").get(i), report.getWeatherReportDetails().getCity());
			assertEquals(List.of("59.44,24.75", "58.38,26.73", "58.39,24.50", "58.75,26.39").get(i), report.getWeatherReportDetails().getCoordinates());
			assertEquals("Celsius", report.getWeatherReportDetails().getTemperatureUnit());
		}
	}

	@Test
	@SneakyThrows
	void parsesInputAndWritesCurrentWeatherReport() {
		properties.setInputPath(WeatherReportFactory.getWeatherReportInputLocation());
		consoleService.parseInput();

		List<String> weatherReportOutputLocation = WeatherReportFactory.getWeatherReportOutputLocation();
		for (String output : weatherReportOutputLocation) {
			WeatherReport report = objectMapper.readValue(new File(output), WeatherReport.class);
			assertEquals(report.getCurrentWeatherReport().getDate(), getDaysFromNow(0));
		}
	}

	@Test
	@SneakyThrows
	void parsesInputAndWritesForecastReport() {
		properties.setInputPath(WeatherReportFactory.getWeatherReportInputLocation());
		consoleService.parseInput();

		List<String> weatherReportOutputLocation = WeatherReportFactory.getWeatherReportOutputLocation();
		for (int i = 0; i < weatherReportOutputLocation.size(); i++) {
			String output = weatherReportOutputLocation.get(i);
			WeatherReport report = objectMapper.readValue(new File(output), WeatherReport.class);
			for (int day = 0; day < 3; day++) {
				assertEquals(report.getForecastReport().get(day).getDate(), getDaysFromNow(day + 1));
			}
		}
	}

	private String getDaysFromNow(int days) {
		Date today = Calendar.getInstance().getTime();
		LocalDateTime newDay = today.toInstant().plus(days, ChronoUnit.DAYS)
				.atOffset(ZoneOffset.ofHours(2)).toLocalDateTime();
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_DATE;
		return newDay.format(dateTimeFormatter);
	}

	@Test
	@SneakyThrows
	void parsesBadInputAndDoesntWriteOutput() {
		properties.setInputPath(WeatherReportFactory.getBadWeatherReportInputLocation());
		consoleService.parseInput();

		String output = WeatherReportFactory.getBadWeatherReportOutputLocation();
		assertFalse(new File(output).exists());
	}
}
