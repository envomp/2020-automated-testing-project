package ee.taltech.weather.api;

import lombok.SneakyThrows;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ApiClient {

	@SneakyThrows
	public static InputStream fetchStreamFromUrl(String url_string) {
		URL url = new URL(url_string);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestProperty("accept", "application/json");
		return connection.getInputStream();
	}

}
