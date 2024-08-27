package camilo.opertationquasarfire.repositories;

import camilo.opertationquasarfire.models.Satellite;
import java.util.List;

public interface RepositoryIntf {

    public List<Satellite> getSatellites();
    public Satellite getSatelliteByName(String name);
    public List<Double> getDistances();
    public List<List<String>> getMessages();
}