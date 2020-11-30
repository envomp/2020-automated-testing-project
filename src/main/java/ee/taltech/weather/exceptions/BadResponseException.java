package ee.taltech.weather.exceptions;

public class BadResponseException extends RuntimeException {
	public BadResponseException(String message) {
		super(message);
	}
}
