package camilo.opertationquasarfire.Services;

import java.util.List;

import camilo.opertationquasarfire.Models.Position;
import camilo.opertationquasarfire.Models.Satellite;
import camilo.opertationquasarfire.Models.SatelliteRequest;
import camilo.opertationquasarfire.Models.SpaceshipResponse;

public interface ServiceIntf {

    List<Satellite> getSatellites();
    Position getLocation(List<Double> distances);
    String getMessage(List<List<String>> messages);    
    SpaceshipResponse getSpaceshipData(List<SatelliteRequest> satellites);
    SpaceshipResponse getSpaceshipData();
    List<Satellite> setSatellite(String name, SatelliteRequest request);
   
}