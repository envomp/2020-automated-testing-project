package ee.taltech.weather;

import com.fasterxml.jackson.databind.ObjectMapper;
import ee.taltech.weather.model.DateDTO;
import ee.taltech.weather.model.report.api.ClimateDTO;
import ee.taltech.weather.model.report.api.ThreeHourIntervalWeatherReportDTO;
import ee.taltech.weather.model.report.api.WeatherReportDTO;
import ee.taltech.weather.model.report.io.WeatherReport;
import lombok.SneakyThrows;
import org.springframework.core.io.ClassPathResource;

import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

public class WeatherReportFactory {
	public static WeatherReportDTO getWeatherReportDTO() {
		return WeatherReportDTO.builder()
				.dt(new DateDTO(1606823335L))
				.main(ClimateDTO.builder()
						.humidity(74.5f)
						.pressure(984.6f)
						.temp(-5.4f)
						.build())
				.build();
	}

	@SneakyThrows
	public static ThreeHourIntervalWeatherReportDTO getThreeHourIntervalWeatherReportDTO() {
		ClassLoader classloader = Thread.currentThread().getContextClassLoader();
		InputStream stream = classloader.getResourceAsStream("sample_passed_response.json");
		ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(stream, ThreeHourIntervalWeatherReportDTO.class);
	}

	@SneakyThrows
	public static WeatherReport getWeatherReport() {
		ClassLoader classloader = Thread.currentThread().getContextClassLoader();
		InputStream stream = classloader.getResourceAsStream("sample_passed_response.json");
		ObjectMapper mapper = new ObjectMapper();
		ThreeHourIntervalWeatherReportDTO dto = mapper.readValue(stream, ThreeHourIntervalWeatherReportDTO.class);
		return WeatherReport.fromWeatherReportDTO(dto);
	}

	@SneakyThrows
	public static String getSampleWeatherReportInputLocation() {
		ClassPathResource resource = new ClassPathResource("sample_input.txt");
		return resource.getFile().getAbsolutePath();
	}

	@SneakyThrows
	public static String getSampleWeatherReportOutputLocation() {
		ClassPathResource resource = new ClassPathResource("sample_input.txt");
		String path = resource.getFile().getAbsolutePath();
		return path.replace("sample_input.txt", "Munich" + ".json");
	}

	@SneakyThrows
	public static String getWeatherReportInputLocation() {
		ClassPathResource resource = new ClassPathResource("sample_passed_inputs.txt");
		return resource.getFile().getAbsolutePath();
	}

	@SneakyThrows
	public static String getMissingWeatherReportInputLocation() {
		ClassPathResource resource = new ClassPathResource("sample_passed_inputs.txt");
		return resource.getFile().getAbsolutePath().replace("sample_passed_inputs", "missing_input");
	}

	@SneakyThrows
	public static List<String> getWeatherReportOutputLocation() {
		ClassPathResource resource = new ClassPathResource("sample_passed_inputs.txt");
		String path = resource.getFile().getAbsolutePath();
		return List.of("Tallinn", "Tartu", "Pärnu", "Jõgeva")
				.stream().map(x -> path.replace("sample_passed_inputs.txt", x + ".json"))
				.collect(Collectors.toList());
	}

	@SneakyThrows
	public static String getBadWeatherReportInputLocation() {
		ClassPathResource resource = new ClassPathResource("sample_bad_inputs.txt");
		return resource.getFile().getAbsolutePath();
	}

	@SneakyThrows
	public static String getBadWeatherReportOutputLocation() {
		ClassPathResource resource = new ClassPathResource("sample_bad_inputs.txt");
		String path = resource.getFile().getAbsolutePath();
		return path.replace("sample_bad_inputs.txt", "x" + ".json");
	}
}
