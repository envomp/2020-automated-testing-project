package ee.taltech.weather.api.report;

import ee.taltech.weather.components.DateDTO;

public class WeatherReportDtoFactory {
	public static WeatherReportDTO getWeatherReportDTO() {
		return WeatherReportDTO.builder()
				.dt(new DateDTO(1569931200L))
				.main(ClimateDTO.builder()
						.humidity(74.5f)
						.pressure(984.6f)
						.temp(-5.4f)
						.build())
				.build();
	}
}
