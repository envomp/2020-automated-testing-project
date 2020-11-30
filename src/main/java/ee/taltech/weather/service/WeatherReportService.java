package ee.taltech.weather.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import ee.taltech.weather.exceptions.BadResponseException;
import ee.taltech.weather.model.report.api.ThreeHourIntervalWeatherReportDTO;
import ee.taltech.weather.model.report.api.WeatherReportDTO;
import ee.taltech.weather.model.report.io.WeatherReport;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class WeatherReportService {

	private final Logger logger;
	private final ApiRequestService apiRequestService;
	private final ObjectMapper objectMapper;

	@SneakyThrows
	public String fetchReport(String cityName) {
		ThreeHourIntervalWeatherReportDTO dto = apiRequestService.fetchForecastReportDTO(cityName);
		WeatherReportDTO prepend = apiRequestService.fetchCurrentWeatherReportDTO(cityName);

		if (dto.getCod() == 200) {
			logger.debug("API request was successful");
			dto.getList().add(0, prepend);
			return objectMapper.writeValueAsString(WeatherReport.fromWeatherReportDTO(dto));
		} else {
			throw new BadResponseException("API request yielded an error: " + dto.getMessage().toString());
		}
	}

}
