package camilo.opertationquasarfire.Services;

import java.util.List;
import org.apache.commons.math3.fitting.leastsquares.LevenbergMarquardtOptimizer;
import org.apache.commons.math3.linear.RealVector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.lemmingapex.trilateration.NonLinearLeastSquaresSolver;
import com.lemmingapex.trilateration.TrilaterationFunction;
import camilo.opertationquasarfire.Exceptions.InformationException;
import camilo.opertationquasarfire.Exceptions.ResquestException;
import camilo.opertationquasarfire.Models.Position;
import camilo.opertationquasarfire.Models.Satellite;
import camilo.opertationquasarfire.Models.SatelliteRequest;
import camilo.opertationquasarfire.Models.SpaceshipResponse;
import camilo.opertationquasarfire.Repositories.Repository;
import camilo.opertationquasarfire.Utils.UtilRebuilMessage;

@Service
public class ServiceImp implements ServiceIntf {

    @Autowired
    Repository repository;

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
        // LinearLeastSquaresSolver lSolver = new LinearLeastSquaresSolver(new
        // TrilaterationFunction(arrayPositions, arrayDistances));
        // RealVector realVector = lSolver.solve();
        NonLinearLeastSquaresSolver nLSolver = new NonLinearLeastSquaresSolver(
                new TrilaterationFunction(arrayPositions, arrayDistances), new LevenbergMarquardtOptimizer());
        RealVector realVector = nLSolver.solve().getPoint();
        Position location = new Position(realVector.toArray()[0], realVector.toArray()[1]);
        return location;
    }

    @Override
    public String getMessage(List<List<String>> messages) {
        return UtilRebuilMessage.rebuilMessage(messages);
    }

    @Override
    public SpaceshipResponse getSpaceshipData(List<SatelliteRequest> satellitesRequest) {
        if (satellitesRequest == null || satellitesRequest.size() < 3)
            throw new ResquestException("There is not enough information from satellites.");
        for (SatelliteRequest sRequest : satellitesRequest) {
            if (sRequest == null || sRequest.getName() == null)
                throw new ResquestException("There is not enough information from satellites.");
            Satellite satellite = this.repository.getSatelliteByName(sRequest.getName());
            if (satellite == null)
                throw new ResquestException("That satellite does not exist: '" + sRequest.getName() + "'.");
            if (sRequest.getDistance() == null || sRequest.getMessage() == null)
                throw new ResquestException(
                        "There is not enough information from satellite:" + sRequest.getName() + "'.");
            satellite.setDistance(sRequest.getDistance());
            satellite.setMessage(sRequest.getMessage());
        }
        SpaceshipResponse spaceshipData = new SpaceshipResponse(
                this.getLocation(this.repository.getDistances()),
                this.getMessage(this.repository.getMessages()));

        return spaceshipData;
    }

    public SpaceshipResponse getSpaceshipData() throws RuntimeException {
        List<Satellite> satellites = this.repository.getSatellites();
        if (satellites == null || satellites.size() < 3)
            throw new InformationException("There is not enough information from satellites.");
        for (Satellite s : satellites) {
            if (s.getDistance() == null || s.getMessage() == null) {
                throw new InformationException("There is a satellite without information.");
            }
        }
        SpaceshipResponse spaceshipData = new SpaceshipResponse(
                this.getLocation(this.repository.getDistances()),
                this.getMessage(this.repository.getMessages()));
        return spaceshipData;
    }

    @Override
    public Satellite setSatellite(String name, SatelliteRequest request) {
        Satellite satellite = this.repository.getSatelliteByName(name);
        if (satellite == null)
            throw new InformationException("That satellite does not exist: '" + name + "'.");
        satellite.setDistance(request.getDistance());
        satellite.setMessage(request.getMessage());
        return satellite;
    }

}
