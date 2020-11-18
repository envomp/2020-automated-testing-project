package ee.taltech.weather;

import com.fasterxml.jackson.databind.ObjectMapper;
import ee.taltech.weather.api.report.ClimateDTO;
import ee.taltech.weather.api.report.ThreeHourIntervalWeatherReportDTO;
import ee.taltech.weather.api.report.WeatherReportDTO;
import ee.taltech.weather.components.DateDTO;
import ee.taltech.weather.io.report.WeatherReport;
import lombok.SneakyThrows;

import java.io.InputStream;

public class WeatherReportFactory {
	public static WeatherReportDTO getWeatherReportDTO() {
		return WeatherReportDTO.builder()
				.dt(new DateDTO(1569931200L))
				.main(ClimateDTO.builder()
						.humidity(74.5f)
						.pressure(984.6f)
						.temp(-5.4f)
						.build())
				.build();
	}

	@SneakyThrows
	public static WeatherReport getWeatherReport() {
		ClassLoader classloader = Thread.currentThread().getContextClassLoader();
		InputStream stream = classloader.getResourceAsStream("sample_passed_response.json");
		ObjectMapper mapper = new ObjectMapper();
		ThreeHourIntervalWeatherReportDTO dto = mapper.readValue(stream, ThreeHourIntervalWeatherReportDTO.class);
		return WeatherReport.fromWeatherReportDTO(dto);
	}
}
