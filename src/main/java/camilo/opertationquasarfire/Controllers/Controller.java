package camilo.opertationquasarfire.Controllers;

import org.springframework.web.bind.annotation.RestController;
import camilo.opertationquasarfire.Models.Satellite;
import camilo.opertationquasarfire.Models.SatelliteRequest;
import camilo.opertationquasarfire.Models.SatellitesRequest;
import camilo.opertationquasarfire.Models.SpaceshipResponse;
import camilo.opertationquasarfire.Services.ServiceIntf;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
public class Controller {

    /**
     * The service interface for the controller.
     */
    @Autowired
    ServiceIntf service;

    /**
     * Handles the POST request to retrieve spaceship information from a list of satellite data
     * 
     * @param request the list of satellite data
     * @return the spaceship information
     */
    @Operation(summary = "Get spaceship information from satellites", description = "This endpoint receives a list of satellites and returns the spaceship information")
    @PostMapping("/topsecret/")
    public ResponseEntity<SpaceshipResponse> postSatellites(
            @RequestBody SatellitesRequest request) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.service.getSpaceshipData(request.getSatellites()));
    }

    /**
     * Handles the POST request to update data for a specific satellite and gets updated satellite data.
     * 
     * @param satelliteName the name of the satellite to update
     * @param request the satellite data to update
     * @return The list of all satellite data
     */
    @Operation(summary = "Set one satellite data", description = "This endpoint allows updating data for a specific satellite and returns updated satellite data.")
    @PostMapping("/topsecret_split/{satelliteName}")
    public ResponseEntity<Satellite> postSatelliteData(
            @PathVariable String satelliteName, @RequestBody SatelliteRequest request) {
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(this.service.setSatellite(satelliteName, request));

    }
    
    /**
     * Handles the GET request to retrieve spaceship information.
     * 
     * @return the spaceship information
     */
    @Operation(summary = "Get spaceship information from satellites", description = "This endpoint returns the spaceship information.")
    @GetMapping("/topsecret_split/")
    public ResponseEntity<SpaceshipResponse> getSpaceshipData() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.service.getSpaceshipData());
    }

    /**
     * Handles the GET request to retrieve all satellite data.
     * 
     * @return the list of all satellites
     */
    @Operation(summary = "Get all the satellite data", description = "This endpoint returns all the satellite data.")
    @GetMapping("/satellites/")
    public ResponseEntity<List<Satellite>> getSatellites() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.service.getSatellites());
    }

}
