package ee.taltech.weather.io.report;

import ee.taltech.weather.api.report.WeatherReportDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ForecastReport {
	private String date;
	private Weather weather;

	public static ForecastReport from(WeatherReportDTO dto) {
		return ForecastReport.builder()
				.date(dto.getDt().toLocalDate().toString())
				.weather(Weather.from(dto))
				.build();
	}
}
