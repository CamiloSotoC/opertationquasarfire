package camilo.opertationquasarfire.services;

import java.util.List;
import org.apache.commons.math3.fitting.leastsquares.LevenbergMarquardtOptimizer;
import org.apache.commons.math3.linear.RealVector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.lemmingapex.trilateration.NonLinearLeastSquaresSolver;
import com.lemmingapex.trilateration.TrilaterationFunction;
import camilo.opertationquasarfire.exceptions.InformationException;
import camilo.opertationquasarfire.exceptions.ResquestException;
import camilo.opertationquasarfire.models.Position;
import camilo.opertationquasarfire.models.Satellite;
import camilo.opertationquasarfire.models.SatelliteRequest;
import camilo.opertationquasarfire.models.SpaceshipResponse;
import camilo.opertationquasarfire.repositories.RepositoryImpl;
import camilo.opertationquasarfire.utils.UtilRebuilMessage;

@Service
public class ServiceImpl implements ServiceIntf {

    private final RepositoryImpl repository;
    private static Logger logger = LoggerFactory.getLogger(ServiceImpl.class);

    ServiceImpl(RepositoryImpl repository) {
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
        if (satellitesRequest == null || satellitesRequest.size() < 3){
            logger.error("RQE1 - There is not enough information from satellites.");
            throw new ResquestException("RQE1");
        }
            
        for (SatelliteRequest sRequest : satellitesRequest) {
            if (sRequest == null || sRequest.getName() == null){
                logger.error("RQE2 - There is not enough information from satellites.");
                throw new ResquestException("RQE2");
            }
                
            Satellite satellite = this.repository.getSatelliteByName(sRequest.getName());
            if (satellite == null){
                logger.error("RQE3 - That satellite does not exist: '" + sRequest.getName() + "'.");
                throw new ResquestException("RQE3","That satellite does not exist: '" + sRequest.getName() + "'.");
            }
                
            if (sRequest.getDistance() == null || sRequest.getMessage() == null){
                logger.error("RQE4 - There is not enough information from satellite: '" + sRequest.getName() + "'.");
                throw new ResquestException("RQE4",
                        "There is not enough information from satellite: '" + sRequest.getName() + "'.");
            }
                
            satellite.setDistance(sRequest.getDistance());
            satellite.setMessage(sRequest.getMessage());
        }
        return new SpaceshipResponse(
            this.getLocation(this.repository.getDistances()),
            this.getMessage(this.repository.getMessages()));
    }

    @Override
    public SpaceshipResponse getSpaceshipData() throws RuntimeException {
        List<Satellite> satellites = this.repository.getSatellites();
        if (satellites == null || satellites.size() < 3){
            logger.error("INE1 - There is not enough information from satellites.");            
            throw new InformationException("INE1");
        }
            
        for (Satellite s : satellites) {
            if (s.getDistance() == null || s.getMessage() == null) {
                logger.error("INE2 - There is not enough information from satellites.");         
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
        if (satellite == null){
            logger.error("INE3 - There is not enough information from satellites.");
            throw new InformationException("INE3","That satellite does not exist: '" + name + "'.");
        }
            
        satellite.setDistance(request.getDistance());
        satellite.setMessage(request.getMessage());
        return satellite;
    }

}
