package ee.taltech.weather;

import ee.taltech.weather.exceptions.InvalidInputException;
import ee.taltech.weather.service.ConsoleService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@AllArgsConstructor
public class WeatherApplication implements ApplicationRunner {

	private final Logger logger;
	private final ConsoleService consoleService;

	public static void main(String[] args) {
		SpringApplication.run(WeatherApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) {
		try {
			consoleService.parseInput();
			logger.info("Results were successfully generated");
		} catch (InvalidInputException e) {
			logger.error("No results were generated");
		}
	}
}