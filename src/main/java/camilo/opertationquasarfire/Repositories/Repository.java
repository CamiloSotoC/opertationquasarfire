package camilo.opertationquasarfire.Repositories;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import camilo.opertationquasarfire.Models.Position;
import camilo.opertationquasarfire.Models.Satellite;
import jakarta.annotation.PostConstruct;

@Component
public class Repository {

    @Value("${satellite.kenobiX}")
    private double kenobiX;
    @Value("${satellite.kenobiY}")
    private double kenobiY;
    @Value("${satellite.skywalkerX}")
    private double skywalkerX;
    @Value("${satellite.skywalkerY}")
    private double skywalkerY;
    @Value("${satellite.satoX}")
    private double satoX;
    @Value("${satellite.satoY}")
    private double satoY;
    private List<Satellite> satellites;

    @PostConstruct
    public void init() {
        satellites = new ArrayList<>();
        satellites.add(new Satellite("Kenobi", new Position(kenobiX, kenobiY)));
        satellites.add(new Satellite("Skywalker", new Position(skywalkerX, skywalkerY)));
        satellites.add(new Satellite("Sato", new Position(satoX, satoY)));
    }

    public List<Satellite> getSatellites() {        
        return satellites;
    }

    public Satellite getSatelliteByName(String name) {
        for (Satellite satellite : this.getSatellites()) {
            if (satellite.getName().equalsIgnoreCase(name)) {
                return satellite;
            }
        }
        return null;
    }

    public List<Double> getDistances() {
        List<Double> distances = new ArrayList<>();
        for (Satellite satellite : this.getSatellites()) {
            distances.add(satellite.getDistance());
        }
        return distances;
    }

    public List<List<String>> getMessages() {
        List<List<String>> messages = new ArrayList<>();
        for (Satellite satellite : this.getSatellites()) {
            messages.add(satellite.getMessage());
        }
        return messages;
    }
}
