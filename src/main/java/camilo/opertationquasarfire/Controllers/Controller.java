package camilo.opertationquasarfire.Controllers;

import org.springframework.web.bind.annotation.RestController;
import camilo.opertationquasarfire.Models.Satellite;
import camilo.opertationquasarfire.Models.SatelliteRequest;
import camilo.opertationquasarfire.Models.SatellitesRequest;
import camilo.opertationquasarfire.Models.SpaceshipResponse;
import camilo.opertationquasarfire.Services.ServiceIntf;
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

    @Autowired
    ServiceIntf service;

    @PostMapping("/topsecret")
    public ResponseEntity<SpaceshipResponse> postSatellites(
            @RequestBody SatellitesRequest request) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.service.getSpaceshipData(request.getSatellites()));
    }

    @PostMapping("/topsecret_split/{satelliteName}")
    public ResponseEntity<List<Satellite>> postSatelliteData(
            @PathVariable String satelliteName, @RequestBody SatelliteRequest request) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.service.setSatellite(satelliteName, request));

    }

    @GetMapping("/topsecret_split/")
    public ResponseEntity<SpaceshipResponse> getSpaceshipData() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.service.getSpaceshipData());
    }

    @GetMapping("/satellites")
    public ResponseEntity<List<Satellite>> getSatellites() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.service.getSatellites());
    }

}
