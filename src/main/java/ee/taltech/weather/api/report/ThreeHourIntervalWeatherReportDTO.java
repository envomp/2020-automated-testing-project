package ee.taltech.weather.api.report;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import ee.taltech.weather.components.DateDTO;
import lombok.*;

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
	@Size(min = 40)
	private List<WeatherReportDTO> list;
	private CityDTO city;

	public WeatherReportDTO getToday() {
		return list.get(0);
	}

	public List<WeatherReportDTO> getThreeDayForecast() {
		try {
			return generateThreeDayForecast();
		} catch (IndexOutOfBoundsException e) {
			throw new InvalidStructureException("Given report didn't have 3 forecast worth of data");
		}
	}

	@SneakyThrows
	private List<WeatherReportDTO> generateThreeDayForecast() {
		List<WeatherReportDTO> reports = new ArrayList<>();
		DateDTO last = getToday().getDt();

		float average_temp = 0;
		float average_humidity = 0;
		float average_pressure = 0;
		int count = 0;
		boolean skip = true;
		int i = 0;

		while (reports.size() < 3) {
			WeatherReportDTO report = list.get(i);
			DateDTO cur = report.getDt();

			if (!cur.getDate().toString().equals(last.getDate().toString())) {
				if (!skip) {
					average_temp /= count;
					average_humidity /= count;
					average_pressure /= count;

					reports.add(WeatherReportDTO.builder()
							.dt(last)
							.main(ClimateDTO.builder()
									.humidity(average_humidity)
									.pressure(average_pressure)
									.temp(average_temp)
									.build())
							.build());
				} else {
					skip = false;
					count += 1;
					average_temp += report.getMain().getTemp();
					average_humidity += report.getMain().getHumidity();
					average_pressure += report.getMain().getPressure();
				}
			}

			last = cur;
			i++;
		}

		return reports;
	}
}
