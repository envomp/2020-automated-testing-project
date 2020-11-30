package ee.taltech.weather.model.report.io;

import ee.taltech.weather.model.report.api.ThreeHourIntervalWeatherReportDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WeatherReportDetails {
	@Builder.Default
	private final String temperatureUnit = "Celsius";
	private String city;
	private String coordinates;

	public static WeatherReportDetails from(ThreeHourIntervalWeatherReportDTO dto) {
		return WeatherReportDetails.builder()
				.city(dto.getCity().getName())
				.coordinates(dto.getCity().getCoord().formatted())
				.build();
	}
}
