package ee.taltech.weather.input;

import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

class ConsoleIOTest {

	@Test
	void readReadsAndWrites() {
		InputStream in = new java.io.ByteArrayInputStream("in".getBytes());
		OutputStream outputStream = new ByteArrayOutputStream();
		PrintStream printStream = new PrintStream(outputStream);

		ConsoleIO.read(in, printStream);

		assertTrue(printStream.toString().isBlank());
	}
}