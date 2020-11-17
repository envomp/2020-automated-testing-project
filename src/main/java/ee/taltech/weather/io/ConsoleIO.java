package ee.taltech.weather.io;

import ee.taltech.weather.report.WeatherReport;
import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

public class ConsoleIO implements IO {

	@SneakyThrows
	public static void invoke(InputStream inputStream, OutputStream outputStream) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
		String output = WeatherReport.constructJson(reader.readLine());
		outputStream.write(output.getBytes());
		outputStream.flush();
	}

}
