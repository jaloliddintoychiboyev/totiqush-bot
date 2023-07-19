import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Temperature {
    private String temperature;
    private String wind;
    private String description;
private ForecastDay[] forecast;
}
/*{"temperature":"+37 °C",
"wind":"19 km/h",
"description":"Sunny",
"forecast":[{"day":"1",
"temperature":"27 °C",
"wind":"15 km/h"},
{"day":"2","temperature":"+29 °C",
"wind":"22 km/h"},
{"day":"3",
"temperature":" °C",
"wind":"36 km/h"}]}*/