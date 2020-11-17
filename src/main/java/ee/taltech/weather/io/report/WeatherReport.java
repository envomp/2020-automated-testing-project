package ee.taltech.weather.io.report;

import com.fasterxml.jackson.databind.ObjectMapper;
import ee.taltech.weather.api.ApiClient;
import ee.taltech.weather.api.report.WeatherReportCollectionDTO;
import lombok.*;

import java.io.InputStream;
import java.util.List;

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
		String url = "api.openweathermap.org/data/2.5/forecast?q=" + cityName + "&appid=" + token;
		ObjectMapper mapper = new ObjectMapper();

		try {
			InputStream inputStream = ApiClient.fetchStreamFromUrl(url);
			return parseWeatherReportDtoInputStream(mapper, inputStream);
		} catch (Exception e) {
			return e.getLocalizedMessage();
		}
	}

	static String parseWeatherReportDtoInputStream(ObjectMapper mapper, InputStream inputStream) throws java.io.IOException {
		WeatherReportCollectionDTO dto = mapper.readValue(inputStream, WeatherReportCollectionDTO.class);

		if (dto.getCod() == 200) {
			return mapper.writeValueAsString(fromWeatherReportDTO(dto));
		} else {
			return dto.getMessage().toString();
		}
	}

	public static WeatherReport fromWeatherReportDTO(WeatherReportCollectionDTO dto) {
		return WeatherReport.builder().build();
	}
}
