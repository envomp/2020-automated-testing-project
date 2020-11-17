package ee.taltech.weather.io.report;

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
}
