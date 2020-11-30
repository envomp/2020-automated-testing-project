package ee.taltech.weather.model.report.io;

import ee.taltech.weather.model.report.api.WeatherReportDTO;
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
				.date(dto.getDt().getDate().toLocalDate().toString())
				.weather(Weather.from(dto))
				.build();
	}
}
