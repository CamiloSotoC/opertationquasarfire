package camilo.opertationquasarfire.services;

import java.util.List;
import org.apache.commons.math3.fitting.leastsquares.LevenbergMarquardtOptimizer;
import org.apache.commons.math3.linear.RealVector;
import org.springframework.stereotype.Service;
import com.lemmingapex.trilateration.NonLinearLeastSquaresSolver;
import com.lemmingapex.trilateration.TrilaterationFunction;
import camilo.opertationquasarfire.exceptions.InformationException;
import camilo.opertationquasarfire.exceptions.ResquestException;
import camilo.opertationquasarfire.models.Position;
import camilo.opertationquasarfire.models.Satellite;
import camilo.opertationquasarfire.models.SatelliteRequest;
import camilo.opertationquasarfire.models.SpaceshipResponse;
import camilo.opertationquasarfire.repositories.Repository;
import camilo.opertationquasarfire.utils.UtilRebuilMessage;

@Service
public class ServiceImp implements ServiceIntf {

    private final Repository repository;

    ServiceImp(Repository repository) {
        this.repository = repository;
    }

    public List<Satellite> getSatellites() {
        return this.repository.getSatellites();
    }

    @Override
    public Position getLocation(List<Double> distances) {
        List<Satellite> satellites = this.repository.getSatellites();
        double[][] arrayPositions = new double[satellites.size()][2];
        for (int i = 0; i < satellites.size(); i++) {
            arrayPositions[i][0] = satellites.get(i).getPosition().getX();
            arrayPositions[i][1] = satellites.get(i).getPosition().getY();
        }
        double[] arrayDistances = distances.stream().mapToDouble(i -> i).toArray();
        /*
        LinearLeastSquaresSolver lSolver = new LinearLeastSquaresSolver(new
        TrilaterationFunction(arrayPositions, arrayDistances));
        RealVector realVector = lSolver.solve();
        */
        NonLinearLeastSquaresSolver nLSolver = new NonLinearLeastSquaresSolver(
                new TrilaterationFunction(arrayPositions, arrayDistances),
                new LevenbergMarquardtOptimizer());
        RealVector realVector = nLSolver.solve().getPoint();
        return new Position(realVector.toArray()[0], realVector.toArray()[1]);
    }

    @Override
    public String getMessage(List<List<String>> messages) {
        return UtilRebuilMessage.rebuilMessage(messages);
    }

    @Override
    public SpaceshipResponse getSpaceshipData(List<SatelliteRequest> satellitesRequest) {
        if (satellitesRequest == null || satellitesRequest.size() < 3)
            throw new ResquestException("RQE1");
        for (SatelliteRequest sRequest : satellitesRequest) {
            if (sRequest == null || sRequest.getName() == null)
                throw new ResquestException("RQE2");
            Satellite satellite = this.repository.getSatelliteByName(sRequest.getName());
            if (satellite == null)
                throw new ResquestException("RQE3","That satellite does not exist: '" + sRequest.getName() + "'.");
            if (sRequest.getDistance() == null || sRequest.getMessage() == null)
                throw new ResquestException("RQE4",
                        "There is not enough information from satellite: '" + sRequest.getName() + "'.");
            satellite.setDistance(sRequest.getDistance());
            satellite.setMessage(sRequest.getMessage());
        }
        return new SpaceshipResponse(
            this.getLocation(this.repository.getDistances()),
            this.getMessage(this.repository.getMessages()));
    }

    public SpaceshipResponse getSpaceshipData() throws RuntimeException {
        List<Satellite> satellites = this.repository.getSatellites();
        if (satellites == null || satellites.size() < 3)
            throw new InformationException("INE1");
        for (Satellite s : satellites) {
            if (s.getDistance() == null || s.getMessage() == null) {
                throw new InformationException("INE2");
            }
        }
        return new SpaceshipResponse(
            this.getLocation(this.repository.getDistances()),
            this.getMessage(this.repository.getMessages()));
    }

    @Override
    public Satellite setSatellite(String name, SatelliteRequest request) {
        Satellite satellite = this.repository.getSatelliteByName(name);
        if (satellite == null)
            throw new InformationException("INE3","That satellite does not exist: '" + name + "'.");
        satellite.setDistance(request.getDistance());
        satellite.setMessage(request.getMessage());
        return satellite;
    }

}
