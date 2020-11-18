package ee.taltech.weather.api.report;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ThreeHourIntervalWeatherReportDTO {
	private Object message;
	private int cod;
	@Size(min = 26)
	private List<WeatherReportDTO> list;
	private CityDTO city;

	public WeatherReportDTO getToday() {
		return list.get(0);
	}

	public List<WeatherReportDTO> getThreeDayForecast() {
		List<WeatherReportDTO> reports = new ArrayList<>();
		for (int i = 1; i <= 3; i++) {
			int intervalsInDay = 8;
			reports.add(list.get(intervalsInDay * i));
		}
		return reports;
	}
}
