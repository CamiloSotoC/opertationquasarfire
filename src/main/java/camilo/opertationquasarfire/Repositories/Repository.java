package camilo.opertationquasarfire.Repositories;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import camilo.opertationquasarfire.Models.Position;
import camilo.opertationquasarfire.Models.Satellite;

@Component//Repository
public class Repository{ 
    
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

    public List<Satellite> getSatellites() {               
        if (satellites == null || satellites.size()==0){            
            satellites = new ArrayList<>();            
            satellites.add(new Satellite("kenobi", new Position(kenobiX, kenobiY)));
            satellites.add(new Satellite("skywalker", new Position(skywalkerX, skywalkerY)));
            satellites.add(new Satellite("sato", new Position(satoX, satoY)));
        }
        return satellites;
    }

    public Satellite getSatelliteByName(String name){              
        for (Satellite satellite : this.getSatellites()) {
            if (satellite.getName().equalsIgnoreCase(name)) {
                return satellite;
            }
        }
        return null;
    }
    public List<Double> getDistances(){
        List<Double> distances = new ArrayList<>();
        for (Satellite satellite : this.getSatellites()) {
            distances.add(satellite.getDistance());
        }
        return distances;   
    }
    public List<List<String>> getMessages(){
        List<List<String>> messages = new ArrayList<>();
        for (Satellite satellite : this.getSatellites()) {
            messages.add(satellite.getMessage());
        }
        return messages;
    }
}
