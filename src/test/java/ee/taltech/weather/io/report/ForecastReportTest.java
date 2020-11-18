package ee.taltech.weather.io.report;

import com.fasterxml.jackson.databind.ObjectMapper;
import ee.taltech.weather.WeatherReportFactory;
import ee.taltech.weather.api.report.InvalidStructureException;
import ee.taltech.weather.api.report.ThreeHourIntervalWeatherReportDTO;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class ForecastReportTest {

	@Test
	@SneakyThrows
	void fromWeatherReportDtoForecast() {
		WeatherReport report = WeatherReportFactory.getWeatherReport();

		assertEquals(3, report.getForecastReport().size());
		assertEquals("2020-11-18", report.getForecastReport().get(0).getDate());
		assertEquals(84, report.getForecastReport().get(0).getWeather().getHumidity());
		assertEquals(1030, report.getForecastReport().get(0).getWeather().getPressure());
		assertEquals(278, report.getForecastReport().get(0).getWeather().getTemperature());
	}

	@Test
	@SneakyThrows
	void getThreeDayForecastIsThrowsExeptionWhenDatesAreWrong() {
		try {
			ClassLoader classloader = Thread.currentThread().getContextClassLoader();
			InputStream stream = classloader.getResourceAsStream("sample_insufficient_response.json");
			ObjectMapper mapper = new ObjectMapper();
			ThreeHourIntervalWeatherReportDTO dto = mapper.readValue(stream, ThreeHourIntervalWeatherReportDTO.class);
			WeatherReport.fromWeatherReportDTO(dto);
		} catch (InvalidStructureException ignored) {
		} catch (Exception e) {
			fail();
		}
	}

	@Test
	@SneakyThrows
	void getThreeDayForecastIsInAscendingOrder() {
		WeatherReport report = WeatherReportFactory.getWeatherReport();

		List<Date> dates = report.getForecastReport().stream().map(x -> Date.valueOf(x.getDate())).collect(Collectors.toList());
		Date last = null;

		for (Date date : dates) {
			assertTrue(last == null || date.after(last));
			last = date;
		}
	}
}