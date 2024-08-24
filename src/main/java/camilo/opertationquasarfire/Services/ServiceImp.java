package camilo.opertationquasarfire.Services;

import java.util.Arrays;
import java.util.List;
import org.apache.commons.math3.fitting.leastsquares.LevenbergMarquardtOptimizer;
import org.apache.commons.math3.linear.RealVector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.lemmingapex.trilateration.NonLinearLeastSquaresSolver;
import com.lemmingapex.trilateration.TrilaterationFunction;

import camilo.opertationquasarfire.Exceptions.InformationException;
import camilo.opertationquasarfire.Models.Position;
import camilo.opertationquasarfire.Models.Satellite;
import camilo.opertationquasarfire.Models.SatelliteRequest;
import camilo.opertationquasarfire.Models.SpaceshipResponse;
import camilo.opertationquasarfire.Repositories.Repository;

@Service
public class ServiceImp implements ServiceIntf {

    @Autowired
    Repository repository;
    
    public List<Satellite> getSatellites(){
        return this.repository.getSatellites();
    }
    @Override
    public Position getLocation(List<Double> distances) {
        //System.out.println("distances:"+distances);
        List<Satellite> satellites = this.repository.getSatellites();
        double[][] arrayPositions= new double[satellites.size()][2];
        for(int i=0;i<satellites.size();i++){            
            arrayPositions[i][0]=satellites.get(i).getPosition().getX();
            arrayPositions[i][1]=satellites.get(i).getPosition().getY();
        }
        double[] arrayDistances = distances.stream().mapToDouble(i -> i).toArray();
        
        //LinearLeastSquaresSolver lSolver = new LinearLeastSquaresSolver(new TrilaterationFunction(arrayPositions, arrayDistances));
        NonLinearLeastSquaresSolver nLSolver = new NonLinearLeastSquaresSolver(new TrilaterationFunction(arrayPositions, arrayDistances), new LevenbergMarquardtOptimizer());
        //RealVector realVector = lSolver.solve();
        RealVector realVector2 = nLSolver.solve().getPoint();
        
        //Position location = new Position(realVector.toArray()[0], realVector.toArray()[1]);        
        Position location2 = new Position(realVector2.toArray()[0], realVector2.toArray()[1]);
        //System.out.println("location1="+location.getX()+","+location.getY());
        //System.out.println("location2="+location2.getX()+","+location2.getY());
        return location2;        
    }

    @Override
    public String getMessage(List<List<String>> messages) {
        //System.out.println("messages:"+messages);
        int numWords = messages.get(0).size();
        List<String> messageList = Arrays.asList(new String[numWords]);
        for(int i=0; i < numWords;i++){                                    
            for(int j=0; j < messages.size();j++){                
                if(!messages.get(j).get(i).isEmpty()){                    
                    messageList.set(i, messages.get(j).get(i));
                    break;
                }                
            }
        }        
        return String.join(" ", messageList);        
    }

    @Override
    public SpaceshipResponse getSpaceshipData(List<SatelliteRequest> satellitesRequest){          
        for(SatelliteRequest sRequest: satellitesRequest){
            Satellite satellite = this.repository.getSatelliteByName(sRequest.getName());
            if(satellite == null) throw new InformationException("That satellite does not exist: "+sRequest.getName());
            satellite.setDistance(sRequest.getDistance());
            satellite.setMessage(sRequest.getMessage());
        }
        SpaceshipResponse spaceshipData = new SpaceshipResponse(
                this.getLocation(this.repository.getDistances())
                ,this.getMessage(this.repository.getMessages()));                        
        
        return spaceshipData;
    }

    public SpaceshipResponse getSpaceshipData()throws RuntimeException{
        List<Satellite> satellites = this.repository.getSatellites();
        if(satellites == null || satellites.size()<3){
            throw new InformationException("There is not enough information");
        }        
        for(Satellite s: satellites){                        
            if (s.getDistance() == null || s.getMessage()== null) {                
                throw new InformationException("There is a satellite without information");
            }
        }

        SpaceshipResponse spaceshipData = new SpaceshipResponse(
                this.getLocation(this.repository.getDistances())
                ,this.getMessage(this.repository.getMessages()));
        return spaceshipData;
    }

    @Override
    public List<Satellite> setSatellite(String name, SatelliteRequest request) {        
       Satellite satellite =  this.repository.getSatelliteByName(name);
       if (satellite == null) throw new InformationException("That satellite does not exist: " + name);              
       satellite.setDistance(request.getDistance());
       satellite.setMessage(request.getMessage());
       return this.repository.getSatellites();

    }
    
}
