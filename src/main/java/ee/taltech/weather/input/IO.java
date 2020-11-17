package ee.taltech.weather.input;

import java.io.InputStream;
import java.io.OutputStream;

public interface IO {

	void read(InputStream inputStream, OutputStream outputStream);

}
