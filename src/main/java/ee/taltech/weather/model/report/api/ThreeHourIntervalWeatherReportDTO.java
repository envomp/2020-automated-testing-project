package ee.taltech.weather.model.report.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import ee.taltech.weather.model.DateDTO;
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
		DateDTO today = getToday().getDt();

		int position = 0;

		for (int i = 0; i < 3; i++) {
			for (int pointer = position; pointer < list.size(); pointer++) {
				DateDTO newDay = list.get(pointer).getDt();
				if (!newDay.getFormattedDate().equals(today.getFormattedDate())) {
					position = pointer;
					WeatherReportDTO newReport = generateDaysReport(position);
					reports.add(newReport);
					today = newReport.getDt();
					break;
				}
			}
		}

		return reports;
	}

	private WeatherReportDTO generateDaysReport(Integer pointer) {
		float totalTemp = 0f;
		float totalHumidity = 0f;
		float totalPressure = 0f;
		int count = 0;
		DateDTO today = list.get(pointer).getDt();

		while (list.get(pointer).getDt().getFormattedDate().equals(today.getFormattedDate())) {
			ClimateDTO dto = list.get(pointer).getMain();
			totalTemp += dto.getTemp();
			totalHumidity += dto.getHumidity();
			totalPressure += dto.getPressure();
			count += 1;
			pointer += 1;
		}

		return WeatherReportDTO.builder()
				.dt(today)
				.main(ClimateDTO.builder()
						.humidity(totalHumidity / count)
						.pressure(totalPressure / count)
						.temp(totalTemp / count)
						.build())
				.build();
	}
}
