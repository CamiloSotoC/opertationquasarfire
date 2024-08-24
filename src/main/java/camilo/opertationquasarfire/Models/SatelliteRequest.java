package camilo.opertationquasarfire.Models;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class SatelliteRequest {
    private String name;
    private Double distance;
    private List<String> message;

    public SatelliteRequest(Double distance, List<String> message) {
        this.distance = distance;
        this.message = message;
    }

}
