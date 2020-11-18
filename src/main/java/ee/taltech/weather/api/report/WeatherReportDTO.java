package ee.taltech.weather.api.report;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import ee.taltech.weather.components.DateDTO;
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
