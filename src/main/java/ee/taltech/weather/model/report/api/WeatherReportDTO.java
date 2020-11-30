package ee.taltech.weather.model.report.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import ee.taltech.weather.model.DateDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherReportDTO {
	private DateDTO dt;
	private ClimateDTO main;
}
