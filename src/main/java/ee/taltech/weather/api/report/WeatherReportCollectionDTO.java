package ee.taltech.weather.api.report;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherReportCollectionDTO {
	private Object message;
	private int cod;
	private List<WeatherReportDTO> list;
	private CityDTO city;
}
