package ee.taltech.weather;

import ee.taltech.weather.service.ConsoleService;
import lombok.AllArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@AllArgsConstructor
public class WeatherApplication implements ApplicationRunner {

	private final ConsoleService consoleService;

	public static void main(String[] args) {
		SpringApplication.run(WeatherApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) {
		consoleService.parseInput();
	}
}