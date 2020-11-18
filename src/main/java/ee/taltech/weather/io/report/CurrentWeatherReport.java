package ee.taltech.weather.io.report;

import ee.taltech.weather.api.report.WeatherReportDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CurrentWeatherReport {
	private String date;
	@Min(-274)
	@Max(274)
	private int temperature;
	@Min(0)
	@Max(100)
	private int humidity;
	@Min(0)
	@Max(10000)
	private int pressure;

	public static CurrentWeatherReport from(WeatherReportDTO dto) {
		return CurrentWeatherReport.builder()
				.date(dto.getDt().toLocalDate().toString())
				.temperature(Math.round(dto.getMain().getTemp()))
				.humidity(Math.round(dto.getMain().getHumidity()))
				.pressure(Math.round(dto.getMain().getPressure()))
				.build();
	}
}
