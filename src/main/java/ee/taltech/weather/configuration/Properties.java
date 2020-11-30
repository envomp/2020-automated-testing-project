package ee.taltech.weather.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.okhttp.OkHttpClient;
import ee.taltech.weather.WeatherApplication;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@ConfigurationProperties("weather.report")
@Component
@Getter
@Setter
public class Properties {
	private String token = "token";
	private String inputPath = "./input.json";

	@Bean
	public Logger logger() {
		return LoggerFactory.getLogger(WeatherApplication.class);
	}

	@Bean
	public ObjectMapper mapper() {
		return new ObjectMapper();
	}

	@Bean
	public OkHttpClient client() {
		return new OkHttpClient();
	}
}