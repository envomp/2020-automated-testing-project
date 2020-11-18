package ee.taltech.weather.io.report;

import com.fasterxml.jackson.databind.ObjectMapper;
import ee.taltech.weather.api.ApiClient;
import ee.taltech.weather.api.report.ThreeHourIntervalWeatherReportDTO;
import lombok.*;

import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WeatherReport {
	private WeatherReportDetails weatherReportDetails;
	private CurrentWeatherReport currentWeatherReport;
	private List<ForecastReport> forecastReport;

	@SneakyThrows
	public static String fetchFromAPI(String cityName, String token) {
		String url = "api.openweathermap.org/data/2.5/forecast?q=" + cityName + "&units=metric&appid=" + token;
		ObjectMapper mapper = new ObjectMapper();

		try {
			InputStream inputStream = ApiClient.fetchStreamFromUrl(url);
			return parseWeatherReportDtoInputStream(mapper, inputStream);
		} catch (Exception e) {
			return e.getLocalizedMessage();
		}
	}

	@SneakyThrows
	static String parseWeatherReportDtoInputStream(ObjectMapper mapper, InputStream inputStream) {
		ThreeHourIntervalWeatherReportDTO dto = mapper.readValue(inputStream, ThreeHourIntervalWeatherReportDTO.class);

		if (dto.getCod() == 200) {
			return mapper.writeValueAsString(fromWeatherReportDTO(dto));
		} else {
			return dto.getMessage().toString();
		}
	}

	public static WeatherReport fromWeatherReportDTO(ThreeHourIntervalWeatherReportDTO dto) {
		return WeatherReport.builder()
				.weatherReportDetails(WeatherReportDetails.from(dto))
				.currentWeatherReport(CurrentWeatherReport.from(dto.getToday()))
				.forecastReport(dto.getThreeDayForecast()
						.stream()
						.map(ForecastReport::from)
						.collect(Collectors.toList()))
				.build();
	}
}
