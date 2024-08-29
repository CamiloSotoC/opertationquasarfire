package camilo.opertationquasarfire.controllers;

import org.springframework.web.bind.annotation.RestController;
import camilo.opertationquasarfire.models.Satellite;
import camilo.opertationquasarfire.models.SatelliteRequest;
import camilo.opertationquasarfire.models.SatellitesRequest;
import camilo.opertationquasarfire.models.SpaceshipResponse;
import camilo.opertationquasarfire.services.ServiceIntf;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
public class Controller {

    /**
     * The service interface for the controller.
     */
    private final ServiceIntf service;
    private static Logger logger = LoggerFactory.getLogger(Controller.class);

    Controller(ServiceIntf service) {
        this.service = service;
    }

    /**
     * Handles the POST request to retrieve spaceship information from a list of
     * satellite data
     * 
     * @param request the list of satellite data
     * @return the spaceship information
     */
    @Operation(summary = "Get spaceship information from satellites", description = "This endpoint receives a list of satellites and returns the spaceship information")
    @PostMapping("/topsecret/")
    public ResponseEntity<SpaceshipResponse> postSatellites(
            @RequestBody SatellitesRequest request) {
                logger.info("POST request to endpoint '/topsecret/'.");
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.service.getSpaceshipData(request.getSatellites()));
    }

    /**
     * Handles the POST request to update data for a specific satellite and gets
     * updated satellite data.
     * 
     * @param satelliteName the name of the satellite to update
     * @param request       the satellite data to update
     * @return The list of all satellite data
     */
    @Operation(summary = "Set one satellite data", description = "This endpoint allows updating data for a specific satellite and returns updated satellite data.")
    @PostMapping("/topsecret_split/{satelliteName}")
    public ResponseEntity<Satellite> postSatelliteData(
            @PathVariable String satelliteName, @RequestBody SatelliteRequest request) {
                logger.info("POST request to endpoint '/topsecret/{}'.",satelliteName);
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
        logger.info("GET request to endpoint '/topsecret_split/'.");
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
        logger.info("GET request to endpoint '/satellites/'.");
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.service.getSatellites());
    }

    /**
     * Handles the GET request to home page.
     * 
     * @return home page
     */
    @Operation(summary = "Home page", description = "Home page.")
    @GetMapping("")
    public ResponseEntity<HashMap<String, String>> home() {
        logger.info("GET request to endpoint '/'.");
        HashMap<String, String> map = new HashMap<>();
        map.put("appName", "Springboot - API - Opertation Quasar Fire");
        map.put("swagger-ui",
                "https://opertationquasarfire-production.up.railway.app/swagger-ui/index.html");
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(map);
    }

}
