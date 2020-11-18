package ee.taltech.weather.components;

import lombok.Getter;

import java.sql.Date;

@Getter
public class DateDTO {
	private static final long MILLISECONDS_IN_SECOND = 1000;

	private final Date date;

	public DateDTO(long date) {
		this.date = new Date(date * MILLISECONDS_IN_SECOND);
	}
}
