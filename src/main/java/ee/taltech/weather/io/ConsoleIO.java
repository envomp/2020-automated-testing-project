package ee.taltech.weather.io;

import ee.taltech.weather.io.report.WeatherReport;
import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

public class ConsoleIO {

	@SneakyThrows
	public static void invoke(InputStream inputStream, OutputStream outputStream) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
		String report = WeatherReport.fetchFromAPI(reader.readLine(), "");
		outputStream.write(report.getBytes());
		outputStream.flush();
	}
}
