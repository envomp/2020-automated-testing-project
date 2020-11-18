package ee.taltech.weather.io.report;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Weather {
	@Min(-274)
	@Max(274)
	private int temperature;
	@Min(0)
	@Max(100)
	private int humidity;
	@Min(0)
	@Max(10000)
	private int pressure;
}
