package camilo.opertationquasarfire.Controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import camilo.opertationquasarfire.Models.Position;
import camilo.opertationquasarfire.Models.Satellite;
import camilo.opertationquasarfire.Models.SatelliteRequest;
import camilo.opertationquasarfire.Models.SatellitesRequest;
import camilo.opertationquasarfire.Models.SpaceshipResponse;
import camilo.opertationquasarfire.Services.ServiceIntf;

@ExtendWith(MockitoExtension.class)
public class ControllerTest {

    @InjectMocks
    private Controller controller;

    @Mock
    private ServiceIntf service;

    @BeforeEach
    void setup() {
        // Initialize the controller with the mock service
    }

    @Test
    void testGetSatellites() {
        // Create a mock response object
        List<Satellite> response = List.of(
            new Satellite("kenobi", new Position(-500, -200)),
            new Satellite("skywalker", new Position(100, -100)));
            new Satellite("sato", new Position(500, 100));

        // Stub the service method to return the mock response
        when(service.getSatellites()).thenReturn(response);

        // Call the controller method
        ResponseEntity<List<Satellite>> result = controller.getSatellites();

        // Verify that the service method was called
        verify(service).getSatellites();

        // Assert that the response is correct
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(response, result.getBody());
    }

    @Test
    void testGetSpaceshipData() {
         // Create a mock response object
         SpaceshipResponse response = new SpaceshipResponse(
            new Position(-58.315252587138595, -69.55141837312165),"este es un mensaje secreto");

         // Stub the service method to return the mock response
         when(service.getSpaceshipData()).thenReturn(response);
 
         // Call the controller method
         ResponseEntity<SpaceshipResponse> result = controller.getSpaceshipData();
 
         // Verify that the service method was called
         verify(service).getSpaceshipData();
 
         // Assert that the response is correct
         assertEquals(HttpStatus.OK, result.getStatusCode());
         assertEquals(response, result.getBody());

    }

    @Test
    void testPostSatelliteData() {
        // Create a mock request object
        String satelliteName = "kenobi";
        SatelliteRequest request = new SatelliteRequest( 50.0, List.of("este","","un","",""));
        
        // Create a mock response object
        Satellite response = new Satellite("kenobi", new Position(-500, -200), 50.0, List.of("este","","un","",""));

        // Stub the service method to return the mock response
        when(service.setSatellite(satelliteName, request)).thenReturn(response);

        // Call the controller method
        ResponseEntity<Satellite> result = controller.postSatelliteData(satelliteName, request);

        // Verify that the service method was called
        verify(service).setSatellite(satelliteName, request);

        // Assert that the response is correct
        assertEquals(HttpStatus.ACCEPTED, result.getStatusCode());
        assertEquals(response, result.getBody());

    }

    @Test
    void testPostSatellites() {
         // Create a mock request object
         List<SatelliteRequest> satellitesRequest = List.of(
            new SatelliteRequest("kenobi", 100.0, List.of("este","","","mensaje","")),
            new SatelliteRequest("skywalker", 115.5, List.of("","es","","","secreto")),
            new SatelliteRequest("sato", 142.7, List.of("este","","un","","")));
        SatellitesRequest request = new SatellitesRequest();
        request.setSatellites(satellitesRequest);

        // Create a mock response object
        SpaceshipResponse response = new SpaceshipResponse(
            new Position(-58.315252587138595, -69.55141837312165),"este es un mensaje secreto");

        // Stub the service method to return the mock response
        when(service.getSpaceshipData(request.getSatellites())).thenReturn(response);

        // Call the controller method
        ResponseEntity<SpaceshipResponse> result = controller.postSatellites(request);

        // Verify that the service method was called
        verify(service).getSpaceshipData(request.getSatellites());

        // Assert that the response is correct
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(response, result.getBody());
    }
}
