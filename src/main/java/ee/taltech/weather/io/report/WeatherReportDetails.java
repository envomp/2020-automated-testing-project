package ee.taltech.weather.io.report;

import ee.taltech.weather.api.report.ThreeHourIntervalWeatherReportDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WeatherReportDetails {
	private String city;
	private String coordinates;
	@Builder.Default
	private final String temperatureUnit = "Celsius";

	public static WeatherReportDetails from(ThreeHourIntervalWeatherReportDTO dto) {
		return WeatherReportDetails.builder()
				.city(dto.getCity().getName())
				.coordinates(dto.getCity().getCoord().formatted())
				.build();
	}
}
