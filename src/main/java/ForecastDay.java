import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ForecastDay {
    private Integer day;
    private String temperature;
    private String wind;
}
