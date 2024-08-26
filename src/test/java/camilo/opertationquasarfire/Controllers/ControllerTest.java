package camilo.opertationquasarfire.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import camilo.opertationquasarfire.models.Position;
import camilo.opertationquasarfire.models.Satellite;
import camilo.opertationquasarfire.models.SatelliteRequest;
import camilo.opertationquasarfire.models.SatellitesRequest;
import camilo.opertationquasarfire.models.SpaceshipResponse;
import camilo.opertationquasarfire.services.ServiceIntf;

@ExtendWith(MockitoExtension.class)
class ControllerTest {

    @InjectMocks
    private Controller controller;

    @Mock
    private ServiceIntf service;

    @Test
    void testGetSatellites() {
        List<Satellite> response = List.of(
                new Satellite("kenobi", new Position(-500, -200)),
                new Satellite("skywalker", new Position(100, -100)));
        new Satellite("sato", new Position(500, 100));
        when(service.getSatellites()).thenReturn(response);
        ResponseEntity<List<Satellite>> result = controller.getSatellites();
        verify(service).getSatellites();
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(response, result.getBody());
    }

    @Test
    void testGetSpaceshipData() {
        SpaceshipResponse response = new SpaceshipResponse(
                new Position(-58.315252587138595, -69.55141837312165), "este es un mensaje secreto");
        when(service.getSpaceshipData()).thenReturn(response);
        ResponseEntity<SpaceshipResponse> result = controller.getSpaceshipData();
        verify(service).getSpaceshipData();
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(response, result.getBody());

    }

    @Test
    void testPostSatelliteData() {
        String satelliteName = "kenobi";
        SatelliteRequest request = new SatelliteRequest(50.0, List.of("este", "", "un", "", ""));
        Satellite response = new Satellite("kenobi", new Position(-500, -200), 50.0, List.of("este", "", "un", "", ""));
        when(service.setSatellite(satelliteName, request)).thenReturn(response);
        ResponseEntity<Satellite> result = controller.postSatelliteData(satelliteName, request);
        verify(service).setSatellite(satelliteName, request);
        assertEquals(HttpStatus.ACCEPTED, result.getStatusCode());
        assertEquals(response, result.getBody());

    }

    @Test
    void testPostSatellites() {
        List<SatelliteRequest> satellitesRequest = List.of(
                new SatelliteRequest("kenobi", 100.0, List.of("este", "", "", "mensaje", "")),
                new SatelliteRequest("skywalker", 115.5, List.of("", "es", "", "", "secreto")),
                new SatelliteRequest("sato", 142.7, List.of("este", "", "un", "", "")));
        SatellitesRequest request = new SatellitesRequest();
        request.setSatellites(satellitesRequest);
        SpaceshipResponse response = new SpaceshipResponse(
                new Position(-58.315252587138595, -69.55141837312165), "este es un mensaje secreto");
        when(service.getSpaceshipData(request.getSatellites())).thenReturn(response);
        ResponseEntity<SpaceshipResponse> result = controller.postSatellites(request);
        verify(service).getSpaceshipData(request.getSatellites());
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(response, result.getBody());
    }

    @Test
    void testHome() {
        HashMap<String, String> map = new HashMap<>();
        map.put("appName", "Springboot - API - Opertation Quasar Fire");
        map.put("swagger-ui",
                "https://opertationquasarfire-production.up.railway.app/swagger-ui/index.html");
        ResponseEntity<HashMap<String, String>> response = controller.home();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(map, response.getBody());
    }

}
