package ee.taltech.weather;

import com.fasterxml.jackson.databind.ObjectMapper;
import ee.taltech.weather.model.DateDTO;
import ee.taltech.weather.model.report.api.ClimateDTO;
import ee.taltech.weather.model.report.api.ThreeHourIntervalWeatherReportDTO;
import ee.taltech.weather.model.report.api.WeatherReportDTO;
import ee.taltech.weather.model.report.io.WeatherReport;
import lombok.SneakyThrows;
import org.springframework.core.io.ClassPathResource;

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

	@SneakyThrows
	public static String getWeatherReportLocation() {
		ClassPathResource resource = new ClassPathResource("sample_passed_response.json");
		return resource.getPath();
	}

	@SneakyThrows
	public static WeatherReport getFailedWeatherReport() {
		ClassLoader classloader = Thread.currentThread().getContextClassLoader();
		InputStream stream = classloader.getResourceAsStream("sample_failed_response.json");
		ObjectMapper mapper = new ObjectMapper();
		ThreeHourIntervalWeatherReportDTO dto = mapper.readValue(stream, ThreeHourIntervalWeatherReportDTO.class);
		return WeatherReport.fromWeatherReportDTO(dto);
	}

	@SneakyThrows
	public static String getFailedWeatherReportLocation() {
		ClassPathResource resource = new ClassPathResource("sample_failed_response.json");
		return resource.getPath();
	}
}
