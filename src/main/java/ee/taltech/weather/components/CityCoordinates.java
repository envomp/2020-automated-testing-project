package ee.taltech.weather.components;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.text.DecimalFormat;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CityCoordinates {
	@Min(-180)
	@Max(180)
	private float lat;
	@Min(-180)
	@Max(180)
	private float lon;

	public String formatted() {
		DecimalFormat f = new DecimalFormat("##.00");
		return f.format(lat) + "," + f.format(lon);
	}
}
