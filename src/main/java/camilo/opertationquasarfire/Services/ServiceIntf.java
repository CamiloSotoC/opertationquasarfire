package camilo.opertationquasarfire.services;

import java.util.List;

import camilo.opertationquasarfire.models.Position;
import camilo.opertationquasarfire.models.Satellite;
import camilo.opertationquasarfire.models.SatelliteRequest;
import camilo.opertationquasarfire.models.SpaceshipResponse;

public interface ServiceIntf {

    List<Satellite> getSatellites();

    Position getLocation(List<Double> distances);

    String getMessage(List<List<String>> messages);

    SpaceshipResponse getSpaceshipData(List<SatelliteRequest> satellitesRequest);

    SpaceshipResponse getSpaceshipData();

    Satellite setSatellite(String name, SatelliteRequest request);

}