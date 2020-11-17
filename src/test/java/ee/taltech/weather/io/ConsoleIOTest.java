package ee.taltech.weather.io;

import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

class ConsoleIOTest {

	@Test
	void invokeReadsAndWrites() {
		InputStream in = new java.io.ByteArrayInputStream("in".getBytes());
		OutputStream outputStream = new ByteArrayOutputStream();

		ConsoleIO.invoke(in, outputStream);

		assertFalse(outputStream.toString().isBlank());
	}
}