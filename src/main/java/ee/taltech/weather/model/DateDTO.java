package ee.taltech.weather.model;

import lombok.Getter;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

@Getter
public class DateDTO {
	private static final long MILLISECONDS_IN_SECOND = 1000;

	private final Date date;

	public DateDTO(long date) {
		this.date = new Date(date * MILLISECONDS_IN_SECOND);
	}

	public String getFormattedDate() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		return dateFormat.format(date);
	}
}
