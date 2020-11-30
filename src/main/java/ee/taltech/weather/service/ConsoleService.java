package ee.taltech.weather.service;

import ee.taltech.weather.configuration.Properties;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

@Service
@AllArgsConstructor
public class ConsoleService {

	private final Logger logger;
	private final Properties properties;
	private final WeatherReportService weatherReportService;

	@SneakyThrows
	public void parseInput() {
		Path path = new File(properties.getInputPath()).toPath();
		if (path.toFile().isFile()) {
			String[] cityNames = new String(Files.readAllBytes(path)).split("[\\s,]+");
			Arrays.stream(cityNames).forEach(name -> {
				if (!name.isBlank()) {
					String outputPath = path.getParent().toFile().getAbsolutePath() + "/" + name + ".json";
					writeCityResultToOutput(name, outputPath);
				}
			});
		} else {
			logger.error("No such directory: {}", path.toAbsolutePath().toString());
		}

	}

	private void writeCityResultToOutput(String name, String outputPath) {
		if (new File(outputPath).exists()) {
			logger.warn("File at output path: {} already exists", outputPath);
		}

		try {
			FileWriter myWriter = new FileWriter(outputPath);
			myWriter.write(weatherReportService.fetchReport(name));
			myWriter.close();
		} catch (IOException e) {
			logger.error("Failed writing to output stream with message: {}", e.getLocalizedMessage());
		}
	}
}
