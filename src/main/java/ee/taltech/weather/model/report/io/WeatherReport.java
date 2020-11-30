package ee.taltech.weather.model.report.io;

import ee.taltech.weather.model.report.api.ThreeHourIntervalWeatherReportDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
