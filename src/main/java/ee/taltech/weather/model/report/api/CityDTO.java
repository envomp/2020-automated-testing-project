package ee.taltech.weather.model.report.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import ee.taltech.weather.model.CityCoordinates;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CityDTO {
	private String name;
	private CityCoordinates coord;
}
