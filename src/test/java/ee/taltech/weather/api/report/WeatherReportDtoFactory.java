package ee.taltech.weather.api.report;

import java.sql.Date;

public class WeatherReportDtoFactory {
	public static WeatherReportDTO getWeatherReportDTO() {
		return WeatherReportDTO.builder()
				.dt(new Date(1569931200000L))
				.main(ClimateDTO.builder()
						.humidity(74.5f)
						.pressure(984.6f)
						.temp(-5.4f)
						.build())
				.build();
	}
}
