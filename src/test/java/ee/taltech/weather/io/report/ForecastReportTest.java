package ee.taltech.weather.io.report;

import com.fasterxml.jackson.databind.ObjectMapper;
import ee.taltech.weather.WeatherReportFactory;
import ee.taltech.weather.exceptions.InvalidStructureException;
import ee.taltech.weather.model.report.api.ThreeHourIntervalWeatherReportDTO;
import ee.taltech.weather.model.report.io.WeatherReport;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ForecastReportTest {

	@Test
	@SneakyThrows
	void fromWeatherReportDtoForecast() {
		WeatherReport report = WeatherReportFactory.getWeatherReport();

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

	@Test
	@SneakyThrows
	void getThreeDayForecastIsThrowsExceptionWhenDatesAreWrong() {
		ClassLoader classloader = Thread.currentThread().getContextClassLoader();
		InputStream stream = classloader.getResourceAsStream("sample_insufficient_response.json");
		ObjectMapper mapper = new ObjectMapper();
		ThreeHourIntervalWeatherReportDTO dto = mapper.readValue(stream, ThreeHourIntervalWeatherReportDTO.class);
		Throwable thrown = catchThrowable(() -> WeatherReport.fromWeatherReportDTO(dto));
		assertThat(thrown).isInstanceOf(InvalidStructureException.class);
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