package ee.taltech.weather.api.report;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CityCoordinatesDTO {
	@Min(-180)
	@Max(180)
	private float lat;
	@Min(-180)
	@Max(180)
	private float lon;

	public String formatted() {
		return null;
	}
}
