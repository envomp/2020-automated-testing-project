package ee.taltech.weather.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import ee.taltech.weather.configuration.Properties;
import ee.taltech.weather.model.report.api.ThreeHourIntervalWeatherReportDTO;
import ee.taltech.weather.model.report.api.WeatherReportDTO;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@AllArgsConstructor
public class ApiRequestService {

	private final Properties properties;
	private final Logger logger;
	private final ObjectMapper mapper;
	private final OkHttpClient client;


	private static String getForecastUrl(String cityName, String token) {
		return "https://api.openweathermap.org/data/2.5/forecast?q=" + cityName + "&units=metric&appid=" + token;
	}

	private static String getCurrentWeatherUrl(String cityName, String token) {
		return "https://api.openweathermap.org/data/2.5/weather?q=" + cityName + "&units=metric&appid=" + token;
	}

	public ThreeHourIntervalWeatherReportDTO fetchForecastReportDTO(String cityName) throws IOException {
		Request request = new Request.Builder()
				.url(getForecastUrl(cityName, properties.getToken()))
				.build();

		logger.debug("Making a forecast request for city: {}", cityName);
		Response response = client.newCall(request).execute();
		return mapper.readValue(response.body().byteStream(), ThreeHourIntervalWeatherReportDTO.class);
	}

	public WeatherReportDTO fetchCurrentWeatherReportDTO(String cityName) throws IOException {
		Request request = new Request.Builder()
				.url(getCurrentWeatherUrl(cityName, properties.getToken()))
				.build();

		logger.debug("Making a current request for city: {}", cityName);
		Response response = client.newCall(request).execute();
		return mapper.readValue(response.body().byteStream(), WeatherReportDTO.class);
	}

}
